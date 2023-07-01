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
        name = "esilv.pregel.triangleCount.stream",
        executionMode = ExecutionMode.STREAM,
        description = "Triangle counts with Pregel"
)
@Generated("org.neo4j.gds.pregel.PregelProcessor")
public final class TriangleCountPregelStreamSpecification implements AlgorithmSpec<TriangleCountPregelAlgorithm, PregelResult, TriangleCountPregelConfig, Stream<PregelStreamResult>, TriangleCountPregelAlgorithmFactory> {
    @Override
    public String name() {
        return TriangleCountPregelAlgorithm.class.getSimpleName();
    }

    @Override
    public TriangleCountPregelAlgorithmFactory algorithmFactory(ExecutionContext executionContext) {
        return new TriangleCountPregelAlgorithmFactory();
    }

    @Override
    public NewConfigFunction<TriangleCountPregelConfig> newConfigFunction() {
        return (__, userInput) -> TriangleCountPregelConfig.of(userInput);
    }

    @Override
    public ComputationResultConsumer<TriangleCountPregelAlgorithm, PregelResult, TriangleCountPregelConfig, Stream<PregelStreamResult>> computationResultConsumer(
            ) {
        return new PregelStreamComputationResultConsumer<>();
    }
}
