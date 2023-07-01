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
        name = "esilv.pregel.bfs.parent.stream",
        executionMode = ExecutionMode.STREAM,
        description = "BFS (parent with minimum id) with Pregel"
)
@Generated("org.neo4j.gds.pregel.PregelProcessor")
public final class BFSParentPregelStreamSpecification implements AlgorithmSpec<BFSParentPregelAlgorithm, PregelResult, BFSPregelConfig, Stream<PregelStreamResult>, BFSParentPregelAlgorithmFactory> {
    @Override
    public String name() {
        return BFSParentPregelAlgorithm.class.getSimpleName();
    }

    @Override
    public BFSParentPregelAlgorithmFactory algorithmFactory(ExecutionContext executionContext) {
        return new BFSParentPregelAlgorithmFactory();
    }

    @Override
    public NewConfigFunction<BFSPregelConfig> newConfigFunction() {
        return (__, userInput) -> BFSPregelConfig.of(userInput);
    }

    @Override
    public ComputationResultConsumer<BFSParentPregelAlgorithm, PregelResult, BFSPregelConfig, Stream<PregelStreamResult>> computationResultConsumer(
            ) {
        return new PregelStreamComputationResultConsumer<>();
    }
}
