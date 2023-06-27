package com.neo4j.pregel;

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

    private static final String FSM = "fsm";

    /* Each node will have this value-schema during pregel computation */
    @Override
    public PregelSchema schema(FrequentSubgraphMiningPregelConfig config) {
        return new PregelSchema.Builder()
                .add(FSM, ValueType.LONG)
                .build();
    }
    
    /* Called in the beginning of the first superstep of the Pregel computation and allows initializing node values */
    @Override
    public void init(InitContext<FrequentSubgraphMiningPregelConfig> context) {

        var nodeProperties = context.nodePropertyKeys();

        var nodeId = context.nodeId();

        context.logMessage("Node ID: '" + nodeId + "' NodeProperties: " + nodeProperties);

        context.setNodeValue(FSM, 0l);
    }

    /* Called for each node in every superstep */
    @Override
    public void compute(ComputeContext<FrequentSubgraphMiningPregelConfig> context, Messages messages) {
        if (context.isInitialSuperstep()) {
            // Initialization step
            // long[] empty_fsm_array = {};
            context.setNodeValue(FSM, 0l);
        } else {

            // long[] fsms = context.longArrayNodeValue(FSM);
            // long[] new_fsms = new long[fsms.length + 1];

            int i = 0;
            for (var message: messages) {
                i++;
                System.out.println("Message No. " + i + " for Node: " + context.nodeId() + " Message: " + message);
            }

            context.setNodeValue(FSM, context.nodeId());
            context.sendToNeighbors(context.nodeId());
        }
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

        static FrequentSubgraphMiningPregelConfig of(CypherMapWrapper userInput) {
            return new FrequentSubgraphMiningPregelConfigImpl(userInput);
        }
    }
}
