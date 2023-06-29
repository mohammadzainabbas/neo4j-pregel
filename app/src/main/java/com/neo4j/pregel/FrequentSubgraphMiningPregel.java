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

import java.util.Optional;

@PregelProcedure(name = "esilv.pregel.fsm", modes = { GDSMode.STREAM, GDSMode.MUTATE }, description = "Frequent Pattern Mining :: Neo4j - Approximate Frequent Subgraph Mining with Pregel")
public class FrequentSubgraphMiningPregel implements PregelComputation<FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig> {

    public static final String FSM = "fsm";
    public static final String G_ID = "gid";
    public static final String POS_X = "pos_x";
    public static final String POS_Y = "pos_y";
    public static final String RATING = "rating";
    public static final String DEGREE = "degree";


    /* Each node will have this value-schema during pregel computation */
    @Override
    public PregelSchema schema(FrequentSubgraphMiningPregelConfig config) {
        return new PregelSchema.Builder()
                .add(FSM, ValueType.LONG_ARRAY)
                .add(DEGREE, ValueType.LONG_ARRAY) // TODO: extend ComputeContext so you can get any long/double value by providing a node_id
                .add(G_ID, ValueType.LONG)
                .add(POS_X, ValueType.DOUBLE)
                .add(POS_Y, ValueType.DOUBLE)
                .add(RATING, ValueType.DOUBLE)
                .build();
    }
    
    /* Called in the beginning of the first superstep of the Pregel computation and allows initializing node values */
    @Override
    public void init(InitContext<FrequentSubgraphMiningPregelConfig> context) {

        long[] nodeDegree = {context.degree()};
        context.setNodeValue(DEGREE, nodeDegree);
        
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

        long[] empty_fsm_array = {};
        context.setNodeValue(FSM, empty_fsm_array);
    }

    /* Called for each node in every superstep */
    @Override
    public void compute(ComputeContext<FrequentSubgraphMiningPregelConfig> context, Messages messages) {
        var nodeId = context.nodeId();
        var idToInsert = new MutableLong(-1);

        long[] fsms = context.longArrayNodeValue(FSM);

        // First superstep
        if (context.isInitialSuperstep()) {
            // add nodeId to FSM
            idToInsert.setValue(nodeId);
        } else {
            // iterate over all messages (coming from all the neighbors) and pick the one with highest degree 
            // (NOTE: can be discussed which one to pick)

            var max_degree = new MutableLong(-1);
            var max_degree_node_id = new MutableLong(-1);

            for (var message: messages) {
                var from_node_id = message.longValue();
                var from_degree = context.longArrayNodeValue(DEGREE, from_node_id)[0];

                if (from_degree > max_degree.longValue()) {
                    max_degree.setValue(from_degree);
                    max_degree_node_id.setValue(from_node_id);
                }
            }

            idToInsert.setValue(max_degree_node_id.longValue()); // add the node with highest degree to FSM
        }

        if (idToInsert.longValue() == -1) { // nothing to add
            context.voteToHalt();
        } else {
            long[] new_fsms = new long[fsms.length + 1];
            System.arraycopy(fsms, 0, new_fsms, 0, fsms.length); // copy existing nodeIds
            new_fsms[fsms.length] = idToInsert.longValue(); // add the id that we want to insert
            context.setNodeValue(FSM, new_fsms); // update paths internally (for each node)
        }

        // send node_id to all neighbors (to let them know where they got this message from)
        context.sendToNeighbors(nodeId);
    }

    // @Override
    // public Optional<Reducer> reducer() {
    //     return Optional.of(new Reducer.Sum());
    // }

    // @Override
    // public boolean masterCompute(MasterComputeContext<FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig> context) {
    //     return context.superstep() >= 2; // stop after 5 supersteps
    // }

    @ValueClass
    @Configuration("FrequentSubgraphMiningPregelConfigImpl")
    @SuppressWarnings("immutables:subtype")
    public interface FrequentSubgraphMiningPregelConfig extends PregelProcedureConfig, SeedConfig {

        @Override
        default boolean isAsynchronous() {
            return true;
        }

        @Value.Default
        default long maxRepeatNodes() {
            return 0;
        }

        static FrequentSubgraphMiningPregelConfig of(CypherMapWrapper userInput) {
            return new FrequentSubgraphMiningPregelConfigImpl(userInput);
        }
    }
}
