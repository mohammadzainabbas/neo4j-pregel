package com.neo4j.pregel;

import java.util.stream.Stream;
import javax.annotation.processing.Generated;
import org.neo4j.gds.beta.pregel.PregelResult;
import org.neo4j.gds.executor.AlgorithmSpec;
import org.neo4j.gds.executor.ComputationResultConsumer;
import org.neo4j.gds.executor.ExecutionContext;
import org.neo4j.gds.executor.ExecutionMode;
import org.neo4j.gds.executor.GdsCallable;
import org.neo4j.gds.executor.NewConfigFunction;
import org.neo4j.gds.pregel.proc.PregelStatsComputationResultConsumer;
import org.neo4j.gds.pregel.proc.PregelStatsResult;

@GdsCallable(
        name = "esilv.pregel.cc.stats",
        executionMode = ExecutionMode.STATS,
        description = "Connected Components with Pregel"
)
@Generated("org.neo4j.gds.pregel.PregelProcessor")
public final class ConnectedComponentsPregelStatsSpecification implements AlgorithmSpec<ConnectedComponentsPregelAlgorithm, PregelResult, ConnectedComponentsConfig, Stream<PregelStatsResult>, ConnectedComponentsPregelAlgorithmFactory> {
    @Override
    public String name() {
        return ConnectedComponentsPregelAlgorithm.class.getSimpleName();
    }

    @Override
    public ConnectedComponentsPregelAlgorithmFactory algorithmFactory(
            ExecutionContext executionContext) {
        return new ConnectedComponentsPregelAlgorithmFactory();
    }

    @Override
    public NewConfigFunction<ConnectedComponentsConfig> newConfigFunction() {
        return (__, userInput) -> ConnectedComponentsConfig.of(userInput);
    }

    @Override
    public ComputationResultConsumer<ConnectedComponentsPregelAlgorithm, PregelResult, ConnectedComponentsConfig, Stream<PregelStatsResult>> computationResultConsumer(
            ) {
        return new PregelStatsComputationResultConsumer<>();
    }
}
