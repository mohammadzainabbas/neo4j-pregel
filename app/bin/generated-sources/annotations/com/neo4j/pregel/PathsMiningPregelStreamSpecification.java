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
        description = "Paths Mining with Pregel (find all paths of length 'max_iteration') - Frequent Pattern Mining :: Neo4j"
)
@Generated("org.neo4j.gds.pregel.PregelProcessor")
public final class PathsMiningPregelStreamSpecification implements AlgorithmSpec<PathsMiningPregelAlgorithm, PregelResult, PathsMiningPregel.PathsMiningPregelConfig, Stream<PregelStreamResult>, PathsMiningPregelAlgorithmFactory> {
    @Override
    public String name() {
        return PathsMiningPregelAlgorithm.class.getSimpleName();
    }

    @Override
    public PathsMiningPregelAlgorithmFactory algorithmFactory(ExecutionContext executionContext) {
        return new PathsMiningPregelAlgorithmFactory();
    }

    @Override
    public NewConfigFunction<PathsMiningPregel.PathsMiningPregelConfig> newConfigFunction() {
        return (__, userInput) -> PathsMiningPregel.PathsMiningPregelConfig.of(userInput);
    }

    @Override
    public ComputationResultConsumer<PathsMiningPregelAlgorithm, PregelResult, PathsMiningPregel.PathsMiningPregelConfig, Stream<PregelStreamResult>> computationResultConsumer(
            ) {
        return new PregelStreamComputationResultConsumer<>();
    }
}
