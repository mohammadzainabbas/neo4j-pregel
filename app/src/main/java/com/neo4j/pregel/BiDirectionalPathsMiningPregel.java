package com.neo4j.pregel;

import org.immutables.value.Value;
import org.apache.commons.lang3.mutable.MutableLong;
import org.neo4j.gds.annotation.Configuration;
import org.neo4j.gds.annotation.ValueClass;
import org.neo4j.gds.api.nodeproperties.ValueType;
import org.neo4j.gds.beta.pregel.Messages;
import org.neo4j.gds.beta.pregel.PregelComputation;
import org.neo4j.gds.beta.pregel.BidirectionalPregelComputation;
import org.neo4j.gds.beta.pregel.context.ComputeContext.BidirectionalComputeContext;
import org.neo4j.gds.beta.pregel.context.InitContext.BidirectionalInitContext;
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

import com.carrotsearch.hppc.LongHashSet;
import com.carrotsearch.hppc.procedures.LongProcedure;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Optional;
import java.util.function.LongConsumer;
import java.util.stream.Collectors;

@PregelProcedure(name = "esilv.pregel.bi_find_paths", modes = { GDSMode.STREAM, GDSMode.MUTATE }, description = "Bi-directional Paths Mining with Pregel (find all paths of length 'max_iteration') - Frequent Pattern Mining :: Neo4j")
public class BidirectionalPathsMiningPregel implements BidirectionalPregelComputation<BidirectionalPathsMiningPregel.BidirectionalPathsMiningPregelConfig> {

    // INTERNALS
    public static final String PATH = "path_";
    public static final String G_ID = "gid";
    public static final String POS_X = "pos_x";
    public static final String POS_Y = "pos_y";
    public static final String RATING = "rating";
    public static final String NODE_INFO = "node_info";
    
    public static final long IDENTIFIER = -1;

    /*
     * Combine the two longs into one
     */
    public long encode(long value1, long value2) {
        return (value1 << 32) | (value2 & 0xFFFFFFFFL);  
    }
    
    /*
     * Extract the two longs
     */
    public long[] decode(long result) {
        long result1 = result >> 32;
        long result2 = result & 0xFFFFFFFFL;
        return new long[] {result1, result2};
    }

    /*
     * Compare two encoded values
     */
    public boolean sameEncodedValue(long value1, long value2) {

        long[] decoded_value1 = decode(value1);
        long[] decoded_value2 = decode(value2);

        boolean exactly_same = decoded_value1[0] == decoded_value2[0] && decoded_value1[1] == decoded_value2[1]; // (0_1) == (0_1)
        boolean exchanged_same = decoded_value1[1] == decoded_value2[0] && decoded_value1[0] == decoded_value2[1]; // (0_1) == (1_0)

        return exactly_same || exchanged_same; // (0_1) == (0_1) || (0_1) == (1_0)
    }
    
    /*
     * Convert ArrayList<Long> to long[]
     */
    public long[] arrayListToNativeArray(ArrayList<Long> arrayList) {
        return arrayList.stream().mapToLong(Long::longValue).toArray();
    }

    /*
     * Convert long[] to ArrayList<Long> (for dynamic array)
     */
    public ArrayList<Long> nativeArrayToArrayList(long[] nativeArray) {
        return Arrays.stream(nativeArray).boxed().collect(Collectors.toCollection(ArrayList::new));
    }

    /*
     * Remove duplicates from ArrayList<T>
     */
    public <T> ArrayList<T> removeDuplicates(ArrayList<T> list) {
        return list.stream().distinct().collect(Collectors.toCollection(ArrayList::new));
        // return list.stream().distinct().collect(Collectors.toList());
    }
     
    /* Each node will have this value-schema during pregel computation */
    @Override
    public PregelSchema schema(BidirectionalPathsMiningPregelConfig config) {

        var schema = new PregelSchema.Builder()
            .add(NODE_INFO, ValueType.LONG_ARRAY) // [degree, orginal_id] 
            .add(G_ID, ValueType.LONG)
            .add(POS_X, ValueType.DOUBLE)
            .add(POS_Y, ValueType.DOUBLE)
            .add(RATING, ValueType.DOUBLE);

        for (var i = 0; i < config.maxIterations(); i++) {
            schema.add(PATH + i, ValueType.LONG_ARRAY); // every step has its own PATH
        }

        return schema.build();
    }
    
    /* Called in the beginning of the first superstep of the Pregel computation and allows initializing node values */
    @Override
    public void init(BidirectionalInitContext<BidirectionalPathsMiningPregelConfig> context) {

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
        
        if (context.nodeProperties(G_ID) != null) {
            context.setNodeValue(G_ID, context.nodeProperties(G_ID).longValue(nodeId));
        }

        for (var i = 0; i < context.config().maxIterations(); i++) {
            context.setNodeValue(PATH + i, new long[]{}); // initialize all paths to empty array
        }
    }

    /* Called for each node in every superstep */
    @Override
    public void compute(BidirectionalComputeContext<BidirectionalPathsMiningPregelConfig> context, Messages messages) {
        var nodeId = context.nodeId();
        var nodeOriginalId = context.toOriginalId(); // for showing correct IDs in the output
        int superstep = context.superstep();
        var stepKey = PATH + superstep;

        var neighbors = new ArrayList<Long>();
        context.forEachNeighbor(neighbors::add);
        var neighborsWithoutDuplicates = removeDuplicates(neighbors);

        // First superstep
        if (context.isInitialSuperstep() && superstep == PathFindingPhase.INIT_PATH.step) {
            context.setNodeValue(stepKey, new long[] {nodeOriginalId, IDENTIFIER});

            // unique messages (to avoid duplicate paths)
            // neighbors.forEach((LongProcedure) neighbor_node_id -> context.sendTo(neighbor_node_id, nodeOriginalId));
            context.sendToNeighbors(nodeOriginalId); // send node_id to all neighbors (to let them know where they got this message from)
        } 
        else if (superstep == PathFindingPhase.CONNECT_NEIGHBORS_PATH.step) {
            var messages_list = new ArrayList<Long>();
            var _messages = new ArrayList<Long>();

            for (var msg: messages) {
                long message = msg.longValue();
                _messages.add(message);
                var from_node_id = message;
                var to_node_id = nodeOriginalId;

                // disable self-loops
                // if (from_node_id == to_node_id && !context.config().withRepeition()) {
                if (from_node_id == to_node_id) {
                    continue;
                }

                // @TODO: remove duplicate messages (multiple links between two nodes i.e: 0 -> 1, 0 -> 1)
                // Add only one link in case of duplicate links (since duplicate links doesn't effect the path)
                var value = encode(from_node_id, to_node_id);
                messages_list.add(value);
            }

            if (messages_list.isEmpty()) { // no neighbors
                context.voteToHalt();
                return;
            }

            for (var msg: messages_list) {
                // send encoded (from_node_id, to_node_id) to all neighbors (to let them know where they got this message from
                context.sendToNeighbors(msg); 
            }

            messages_list.add(IDENTIFIER);
            var messages_array = arrayListToNativeArray(messages_list);
            context.setNodeValue(stepKey, messages_array); // update paths internally (for each node)
        } else if (superstep >= PathFindingPhase.COMPUTE_PATH.step) {
            // iterate over all messages (coming from all the neighbors) and add them all to PATH 
            // (NOTE: each superstep is separated via some unique identifier)

            var previousKey = PATH + (superstep - 1);
            HashMap<Long, ArrayList<Long>> messages_map = new HashMap<Long, ArrayList<Long>>();
            
            for (var msg: messages) { // @TODO: recheck this logic (to build the correct message list)
                long[] message = decode(msg.longValue());
                var from_node_id = message[0];
                var to_node_id = message[1];
                
                messages_map.computeIfAbsent(from_node_id, k -> new ArrayList<Long>()).add(to_node_id); // add to the hashmap's array list against the key
            }
            
            var new_path = new ArrayList<Long>();
            var path_buffer = new ArrayList<Long>();
            var previous_messages = context.longArrayNodeValue(previousKey);

            for (int i = 0; i < previous_messages.length; i++) {
                long previous_message = previous_messages[i];
                
                if (previous_messages[i + 1] == IDENTIFIER) {
                    long[] decoded_previous_message = decode(previous_message);
                    long previous_message_to_node = decoded_previous_message[1];

                    var message_list = messages_map.get(previous_message_to_node);
                    if (message_list != null) {
                        
                        var temp = new ArrayList<Long>();
                        
                        for (var message: message_list) {
                            
                            temp.addAll(path_buffer);

                            long value = encode(previous_message_to_node, message);

                            temp.add(value);
                            temp.add(IDENTIFIER);
                        }

                        new_path.addAll(temp);
                        path_buffer.clear();
                        temp.clear();
                        continue;
                    }
                }                
                path_buffer.add(previous_message);
                if (previous_message == IDENTIFIER) {
                    new_path.addAll(path_buffer);
                    path_buffer.clear();
                }
            }

            if (!new_path.isEmpty()) {
                long[] new_paths = arrayListToNativeArray(new_path);
                context.setNodeValue(stepKey, new_paths); // update paths internally (for each node)

                for (var message: messages) {
                    context.sendToNeighbors(message); // send node_id to all neighbors (to let them know where they got this message from)
                }
            } else {
                context.voteToHalt();
            }
        }
    }

    // @Override
    // public Optional<Reducer> reducer() {
    //     return Optional.of(new Reducer.Sum());
    // }

    @Override
    public boolean masterCompute(MasterComputeContext<BidirectionalPathsMiningPregel.BidirectionalPathsMiningPregelConfig> context) {
        return context.superstep() >= 2; // stop after 2 supersteps
    }

    enum PathFindingPhase {
        INIT_PATH(0),
        CONNECT_NEIGHBORS_PATH(1),
        COMPUTE_PATH(2);

        final int step;

        PathFindingPhase(int i) {
            step = i;
        }
    }

    @ValueClass
    @Configuration("BidirectionalPathsMiningPregelConfigImpl")
    @SuppressWarnings("immutables:subtype")
    public interface BidirectionalPathsMiningPregelConfig extends PregelProcedureConfig, SeedConfig {

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

        static BidirectionalPathsMiningPregelConfig of(CypherMapWrapper userInput) {
            return new BidirectionalPathsMiningPregelConfigImpl(userInput);
        }
    }
}
