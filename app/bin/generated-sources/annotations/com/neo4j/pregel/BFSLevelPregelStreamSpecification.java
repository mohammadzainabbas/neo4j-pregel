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
        name = "esilv.pregel.bfs.level.stream",
        executionMode = ExecutionMode.STREAM,
        description = "BFS (with level/iteration) with Pregel"
)
@Generated("org.neo4j.gds.pregel.PregelProcessor")
public final class BFSLevelPregelStreamSpecification implements AlgorithmSpec<BFSLevelPregelAlgorithm, PregelResult, BFSPregelConfig, Stream<PregelStreamResult>, BFSLevelPregelAlgorithmFactory> {
    @Override
    public String name() {
        return BFSLevelPregelAlgorithm.class.getSimpleName();
    }

    @Override
    public BFSLevelPregelAlgorithmFactory algorithmFactory(ExecutionContext executionContext) {
        return new BFSLevelPregelAlgorithmFactory();
    }

    @Override
    public NewConfigFunction<BFSPregelConfig> newConfigFunction() {
        return (__, userInput) -> BFSPregelConfig.of(userInput);
    }

    @Override
    public ComputationResultConsumer<BFSLevelPregelAlgorithm, PregelResult, BFSPregelConfig, Stream<PregelStreamResult>> computationResultConsumer(
            ) {
        return new PregelStreamComputationResultConsumer<>();
    }
}
