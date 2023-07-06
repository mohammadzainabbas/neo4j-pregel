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
        name = "esilv.pregel.find_triangles.stream",
        executionMode = ExecutionMode.STREAM,
        description = "Find Triangles :: Neo4j - Find triangles with Pregel"
)
@Generated("org.neo4j.gds.pregel.PregelProcessor")
public final class FindTrianglesPregelStreamSpecification implements AlgorithmSpec<FindTrianglesPregelAlgorithm, PregelResult, FindTrianglesPregel.FindTrianglesPregelConfig, Stream<PregelStreamResult>, FindTrianglesPregelAlgorithmFactory> {
    @Override
    public String name() {
        return FindTrianglesPregelAlgorithm.class.getSimpleName();
    }

    @Override
    public FindTrianglesPregelAlgorithmFactory algorithmFactory(ExecutionContext executionContext) {
        return new FindTrianglesPregelAlgorithmFactory();
    }

    @Override
    public NewConfigFunction<FindTrianglesPregel.FindTrianglesPregelConfig> newConfigFunction() {
        return (__, userInput) -> FindTrianglesPregel.FindTrianglesPregelConfig.of(userInput);
    }

    @Override
    public ComputationResultConsumer<FindTrianglesPregelAlgorithm, PregelResult, FindTrianglesPregel.FindTrianglesPregelConfig, Stream<PregelStreamResult>> computationResultConsumer(
            ) {
        return new PregelStreamComputationResultConsumer<>();
    }
}
