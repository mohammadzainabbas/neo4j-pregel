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
import org.neo4j.gds.pregel.proc.PregelMutateComputationResultConsumer;
import org.neo4j.gds.pregel.proc.PregelMutateResult;

@GdsCallable(
        name = "esilv.pregel.fsm.mutate",
        executionMode = ExecutionMode.MUTATE_NODE_PROPERTY,
        description = "Frequent Pattern Mining :: Neo4j - Approximate Frequent Subgraph Mining with Pregel"
)
@Generated("org.neo4j.gds.pregel.PregelProcessor")
public final class FindTrianglesPregelMutateSpecification implements AlgorithmSpec<FindTrianglesPregelAlgorithm, PregelResult, FindTrianglesPregel.FindTrianglesPregelConfig, Stream<PregelMutateResult>, FindTrianglesPregelAlgorithmFactory> {
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
    public ComputationResultConsumer<FindTrianglesPregelAlgorithm, PregelResult, FindTrianglesPregel.FindTrianglesPregelConfig, Stream<PregelMutateResult>> computationResultConsumer(
            ) {
        return new PregelMutateComputationResultConsumer<>();
    }
}
