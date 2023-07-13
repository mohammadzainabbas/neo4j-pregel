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
import org.neo4j.gds.pregel.proc.PregelStreamComputationResultConsumer;
import org.neo4j.gds.pregel.proc.PregelStreamResult;

@GdsCallable(
        name = "esilv.pregel.find_paths.stream",
        executionMode = ExecutionMode.STREAM,
        description = "Path Mining with Pregel - Frequent Pattern Mining :: Neo4j"
)
@Generated("org.neo4j.gds.pregel.PregelProcessor")
public final class FrequentSubgraphMiningPregelStreamSpecification implements AlgorithmSpec<FrequentSubgraphMiningPregelAlgorithm, PregelResult, FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig, Stream<PregelStreamResult>, FrequentSubgraphMiningPregelAlgorithmFactory> {
    @Override
    public String name() {
        return FrequentSubgraphMiningPregelAlgorithm.class.getSimpleName();
    }

    @Override
    public FrequentSubgraphMiningPregelAlgorithmFactory algorithmFactory(
            ExecutionContext executionContext) {
        return new FrequentSubgraphMiningPregelAlgorithmFactory();
    }

    @Override
    public NewConfigFunction<FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig> newConfigFunction(
            ) {
        return (__, userInput) -> FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig.of(userInput);
    }

    @Override
    public ComputationResultConsumer<FrequentSubgraphMiningPregelAlgorithm, PregelResult, FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig, Stream<PregelStreamResult>> computationResultConsumer(
            ) {
        return new PregelStreamComputationResultConsumer<>();
    }
}
