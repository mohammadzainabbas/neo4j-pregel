package com.neo4j.pregel;

import org.immutables.value.Value;
import org.apache.commons.lang3.mutable.MutableLong;
import org.neo4j.gds.annotation.Configuration;
import org.neo4j.gds.annotation.ValueClass;
import org.neo4j.gds.api.nodeproperties.ValueType;
import org.neo4j.gds.beta.pregel.Messages;
import org.neo4j.gds.beta.pregel.PregelComputation;
import org.neo4j.gds.beta.pregel.PregelProcedureConfig;
import org.neo4j.gds.beta.pregel.PregelSchema;
import org.neo4j.gds.beta.pregel.Reducer;
import org.neo4j.gds.beta.pregel.annotation.GDSMode;
import org.neo4j.gds.beta.pregel.annotation.PregelProcedure;
import org.neo4j.gds.beta.pregel.context.ComputeContext;
import org.neo4j.gds.beta.pregel.context.InitContext;
import org.neo4j.gds.beta.pregel.context.MasterComputeContext;
import org.neo4j.gds.config.SeedConfig;
import org.neo4j.gds.core.CypherMapWrapper;

import com.carrotsearch.hppc.procedures.LongProcedure;
import com.carrotsearch.hppc.LongHashSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.function.LongConsumer;

@PregelProcedure(name = "esilv.pregel.find_triangles", modes = { GDSMode.STREAM, GDSMode.MUTATE }, description = "Find Triangles :: Neo4j - Find triangles with Pregel")
public class FindTrianglesPregel implements PregelComputation<FindTrianglesPregel.FindTrianglesPregelConfig> {

    public static final String FSM = "fsm";
    public static final String POS_X = "pos_x";
    public static final String POS_Y = "pos_y";
    public static final String RATING = "rating";
    public static final String NODE_INFO = "node_info";
    
    public static final long IDENTIFIER = -1;

    enum Phase {
        MERGE_NEIGHBORS(1),
        UPDATE_PATHS(2);

        final long step;

        Phase(int i) {
            step = i;
        }
    }
    
    /* Each node will have this value-schema during pregel computation */
    @Override
    public PregelSchema schema(FindTrianglesPregelConfig config) {
        // TODO: extend ComputeContext so you can get any long/double value by providing a node_id
        return new PregelSchema.Builder()
                .add(FSM, ValueType.LONG_ARRAY)
                .add(NODE_INFO, ValueType.LONG_ARRAY) // [degree, orginal_id] 
                .add(POS_X, ValueType.DOUBLE)
                .add(POS_Y, ValueType.DOUBLE)
                .add(RATING, ValueType.DOUBLE)
                .build();
    }
    
    /* Called in the beginning of the first superstep of the Pregel computation and allows initializing node values */
    @Override
    public void init(InitContext<FindTrianglesPregelConfig> context) {

        long[] nodeInfo = {context.degree(), context.toOriginalId()};
        context.setNodeValue(NODE_INFO, nodeInfo);
        
        var nodeId = context.nodeId();
        var nodeProperties = new String[] { "pos_x", "pos_y", "rating" };
        
        for (var property: nodeProperties) {
            var _property = context.nodeProperties(property);
            if (_property == null) {
                context.logMessage("Property '" + property + "' not found");
                continue;
            }
            context.setNodeValue(property, _property.doubleValue(nodeId));
        }

        long[] empty_fsm_array = {};
        context.setNodeValue(FSM, empty_fsm_array);
    }

    /* Called for each node in every superstep */
    @Override
    public void compute(ComputeContext<FindTrianglesPregelConfig> context, Messages messages) {
        var nodeId = context.nodeId();
        var nodeOriginalId = context.toOriginalId(); // for showing correct IDs in the output
        boolean newMessage = false;

        long[] fsms = context.longArrayNodeValue(FSM);
        // long[] to ArrayList<Long> (for dynamic array)
        ArrayList<Long> new_fsm = Arrays.stream(fsms).boxed().collect(Collectors.toCollection(ArrayList::new));
        
        // First superstep
        if (context.isInitialSuperstep()) {
            // long[] new_fsms = new long[fsms.length + 1];
            // System.arraycopy(fsms, 0, new_fsms, 0, fsms.length); // copy existing nodeIds
            // new_fsms[fsms.length] = nodeOriginalId; // add the id that we want to insert
            new_fsm.add(nodeOriginalId); // add the id that we want to insert
            new_fsm.add(IDENTIFIER); // add the unique identifier to separate supersteps
            newMessage = true;
        } else {
            var neighborsOfA = new LongHashSet(context.degree());
            context.forEachDistinctNeighbor(neighborsOfA::add);

            long nodeA = context.nodeId();
            var trianglesFromNodeA = new MutableLong();

            neighborsOfA.forEach((LongProcedure) nodeB -> {
                if (nodeB > nodeA) {
                    LongConsumer findTriangles = nodeC -> {
                        // find common neighbors of A
                        // check indexed neighbors of A
                        if (nodeC > nodeB && neighborsOfA.contains(nodeC)) {
                            trianglesFromNodeA.increment();
                            context.sendTo(nodeB, 1);
                            context.sendTo(nodeC, 1);
                        }
                    };
                    if (context.isMultiGraph()) {
                        context.forEachDistinctNeighbor(nodeB, findTriangles);
                    } else {
                        context.forEachNeighbor(nodeB, findTriangles);
                    }
                }
            });








            // iterate over all messages (coming from all the neighbors) and add them all to FSM 
            // (NOTE: each superstep is separated via some unique identifier)
            ArrayList<Long> messages_list = new ArrayList<Long>();
            for (var message: messages) {
                var from_node_id = message.longValue();
                var from_node_info = context.longArrayNodeValue(NODE_INFO, from_node_id);
                // var from_node_degree = from_node_info[0];
                var from_node_original_id = from_node_info[1];
                
                if (from_node_original_id == nodeOriginalId && !context.config().withRepeition()) {
                    continue; // disallow self-loops
                }
                
                messages_list.add(from_node_original_id);
            }

            if (!context.config().withRepeition()) {
                // remove duplicates
                messages_list = (ArrayList<Long>) messages_list.stream().distinct().collect(Collectors.toList());
            }
            
            if (messages_list.size() > 0) { // if there are any messages
                newMessage = true;
                new_fsm.addAll(messages_list);
                new_fsm.add(IDENTIFIER); // add the unique identifier to separate supersteps
            }
        }

        if (newMessage) {
            // convert ArrayList<Long> back to long[]
            long[] new_fsms = new_fsm.stream().mapToLong(Long::longValue).toArray();
            context.setNodeValue(FSM, new_fsms); // update paths internally (for each node)
            context.sendToNeighbors(nodeId); // send node_id to all neighbors (to let them know where they got this message from)
        } 
        else {
            context.voteToHalt();
        }
    }

    // @Override
    // public Optional<Reducer> reducer() {
    //     return Optional.of(new Reducer.Sum());
    // }

    // @Override
    // public boolean masterCompute(MasterComputeContext<FindTrianglesPregel.FindTrianglesPregelConfig> context) {
    //     return context.superstep() >= 2; // stop after 2 supersteps
    // }

    @ValueClass
    @Configuration("FindTrianglesPregelConfigImpl")
    @SuppressWarnings("immutables:subtype")
    public interface FindTrianglesPregelConfig extends PregelProcedureConfig, SeedConfig {

        @Override
        default boolean isAsynchronous() {
            return true;
        }

        @Value.Default
        @Configuration.Key("maxRepeatNodes")
        default long maxRepeatNodes() {
            return 0;
        }

        @Value.Default
        @Configuration.Key("withRepeition")
        default boolean withRepeition() {
            return false;
        }

        static FindTrianglesPregelConfig of(CypherMapWrapper userInput) {
            return new FindTrianglesPregelConfigImpl(userInput);
        }
    }
}
