package com.neo4j.pregel;

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
                .add(G_ID, ValueType.LONG)
                .add(DEGREE, ValueType.LONG)
                .add(POS_X, ValueType.DOUBLE)
                .add(POS_Y, ValueType.DOUBLE)
                .add(RATING, ValueType.DOUBLE)
                .build();
    }
    
    /* Called in the beginning of the first superstep of the Pregel computation and allows initializing node values */
    @Override
    public void init(InitContext<FrequentSubgraphMiningPregelConfig> context) {

        var nodeDegree = context.degree();
        context.setNodeValue(DEGREE, nodeDegree);
        
        var nodeId = context.nodeId();
        var nodeProperties = new String[] { "pos_x", "pos_y", "rating" };

        var _str = "Node ID: '" + nodeId + "' ";
        for (var property: nodeProperties) {
            var _property = context.nodeProperties(property);
            if (_property == null) {
                context.logMessage("Property '" + property + "' not found");
                continue;
            }
            context.setNodeValue(property, _property.doubleValue(nodeId));
            _str = _str + property + ": '" + _property.doubleValue(nodeId) + "' ";
        }
        
        context.logMessage(_str);

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
        var idToInsert = new MutableLong(0);

        long[] fsms = context.longArrayNodeValue(FSM);
        long[] new_fsms = new long[fsms.length + 1];
        System.arraycopy(fsms, 0, new_fsms, 0, fsms.length); // copy existing nodeIds

        // First superstep
        if (context.isInitialSuperstep()) {
            // add nodeId to FSM
            idToInsert.setValue(nodeId);
        } else {

            long[] fsms = context.longArrayNodeValue(FSM);
            long[] new_fsms = new long[fsms.length + 1];
            System.arraycopy(fsms, 0, new_fsms, 0, fsms.length); // copy existing nodeIds

            new_fsms[fsms.length] = context.nodeId(); // add current nodeId


            
            // int i = 0;
            // for (var message: messages) {
            //     i++;
            //     System.out.println("Superstep No. " + superstep + " Message No. " + i + " for Node: " + context.nodeId() + " Message: " + message);
            // }


            // context.setNodeValue(FSM, context.nodeId());
        }

        context.setNodeValue(FSM, new_fsms); // update paths internally (for each node)
        
        // send node_id to all neighbors (to let them know where they got this message from)
        context.sendToNeighbors(nodeId);
    }

    // @Override
    // public Optional<Reducer> reducer() {
    //     return Optional.of(new Reducer.Sum());
    // }

    // @Override
    // public double applyRelationshipWeight(double nodeValue, double relationshipWeight) {
    //     // ! assuming normalized relationshipWeights (sum of outgoing edge weights = 1
    //     // and none negative weights)
    //     return nodeValue * relationshipWeight;
    // }

    @Override
    public boolean masterCompute(MasterComputeContext<FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig> context) {
        return context.superstep() >= 2; // stop after 5 supersteps
    }

    @ValueClass
    @Configuration("FrequentSubgraphMiningPregelConfigImpl")
    @SuppressWarnings("immutables:subtype")
    public interface FrequentSubgraphMiningPregelConfig extends PregelProcedureConfig, SeedConfig {

        @Override
        default boolean isAsynchronous() {
            return true;
        }

        static FrequentSubgraphMiningPregelConfig of(CypherMapWrapper userInput) {
            return new FrequentSubgraphMiningPregelConfigImpl(userInput);
        }
    }
}
