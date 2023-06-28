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
        name = "esilv.pregel.sssp.stream",
        executionMode = ExecutionMode.STREAM,
        description = "Single source shortest path (SSSP) with Pregel"
)
@Generated("org.neo4j.gds.pregel.PregelProcessor")
public final class SingleSourceShortestPathPregelStreamSpecification implements AlgorithmSpec<SingleSourceShortestPathPregelAlgorithm, PregelResult, SingleSourceShortestPathPregel.SingleSourceShortestPathPregelConfig, Stream<PregelStreamResult>, SingleSourceShortestPathPregelAlgorithmFactory> {
    @Override
    public String name() {
        return SingleSourceShortestPathPregelAlgorithm.class.getSimpleName();
    }

    @Override
    public SingleSourceShortestPathPregelAlgorithmFactory algorithmFactory(
            ExecutionContext executionContext) {
        return new SingleSourceShortestPathPregelAlgorithmFactory();
    }

    @Override
    public NewConfigFunction<SingleSourceShortestPathPregel.SingleSourceShortestPathPregelConfig> newConfigFunction(
            ) {
        return (__, userInput) -> SingleSourceShortestPathPregel.SingleSourceShortestPathPregelConfig.of(userInput);
    }

    @Override
    public ComputationResultConsumer<SingleSourceShortestPathPregelAlgorithm, PregelResult, SingleSourceShortestPathPregel.SingleSourceShortestPathPregelConfig, Stream<PregelStreamResult>> computationResultConsumer(
            ) {
        return new PregelStreamComputationResultConsumer<>();
    }
}
