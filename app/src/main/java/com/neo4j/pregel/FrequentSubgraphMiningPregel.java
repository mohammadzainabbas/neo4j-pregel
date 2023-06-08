package com.neo4j.pregel;

import org.checkerframework.checker.units.qual.A;
import org.immutables.value.Value;
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

import java.util.Set;
import java.lang.reflect.Array;
import java.util.HashSet;
import java.util.Optional;

@PregelProcedure(name = "esilv.pregel.fsm", modes = { GDSMode.STREAM, GDSMode.MUTATE }, description = "Frequent Pattern Mining :: Neo4j - Approximate Frequent Subgraph Mining with Pregel")
public class FrequentSubgraphMiningPregel implements PregelComputation<FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig> {

    private static final String FSM = "fsm";

    /* Each node will have this value-schema during pregel computation */
    @Override
    public PregelSchema schema(FrequentSubgraphMiningPregelConfig config) {
        return new PregelSchema.Builder()
                .add(FSM, ValueType.LONG_ARRAY)
                .build();
    }
    
    /* Called in the beginning of the first superstep of the Pregel computation and allows initializing node values */
    // @Override
    // public void init(InitContext<FrequentSubgraphMiningPregelConfig> context) {
    //     var initialValue = context.config().seedProperty() != null
    //             ? context.nodeProperties(context.config().seedProperty()).doubleValue(context.nodeId())
    //             : 1.0 / context.nodeCount();
    //     context.setNodeValue(FSM, initialValue);

    //     weighted = context.config().hasRelationshipWeightProperty();
    // }

    /* Called for each node in every superstep */
    @Override
    public void compute(ComputeContext<FrequentSubgraphMiningPregelConfig> context, Messages messages) {
        if (context.isInitialSuperstep()) {
            // Initialization step
            long[] empty_fsm_array = {};
            context.setNodeValue(FSM, empty_fsm_array);
        } else {
            long[] fsms = context.longArrayNodeValue(FSM);
            long[] new_fsms = new long[fsms.length + 1];

            for (int i = 0; i < new_fsms.length; i++) {
                if (i == new_fsms.length - 1) {
                    new_fsms[i] = context.nodeId();
                } else {
                    new_fsms[i] = fsms[i];
                }
            }

            context.setNodeValue(FSM, new_fsms);
            context.sendToNeighbors(new_fsms);
        }
    }

    @Override
    public Optional<Reducer> reducer() {
        return Optional.of(new Reducer.Sum());
    }

    @Override
    public double applyRelationshipWeight(double nodeValue, double relationshipWeight) {
        // ! assuming normalized relationshipWeights (sum of outgoing edge weights = 1
        // and none negative weights)
        return nodeValue * relationshipWeight;
    }

    @Override
    public boolean masterCompute(MasterComputeContext<FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig> context) {
        return context.superstep() >= 5; // stop after 5 supersteps
    }

    @ValueClass
    @Configuration("FrequentSubgraphMiningPregelConfigImpl")
    @SuppressWarnings("immutables:subtype")
    public interface FrequentSubgraphMiningPregelConfig extends PregelProcedureConfig, SeedConfig {
        @Value.Default
        default int maxIterations() {
            return 10;
        }

        static FrequentSubgraphMiningPregelConfig of(CypherMapWrapper userInput) {
            return new FrequentSubgraphMiningPregelConfigImpl(userInput);
        }
    }
}
