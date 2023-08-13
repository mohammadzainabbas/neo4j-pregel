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
        name = "esilv.pregel.find_paths.mutate",
        executionMode = ExecutionMode.MUTATE_NODE_PROPERTY,
        description = "Paths Mining with Pregel (find all paths of length 'max_iteration') - Frequent Pattern Mining :: Neo4j"
)
@Generated("org.neo4j.gds.pregel.PregelProcessor")
public final class PathsMiningGADMPregelMutateSpecification implements AlgorithmSpec<PathsMiningGADMPregelAlgorithm, PregelResult, PathsMiningGADMPregel.PathsMiningGADMPregelConfig, Stream<PregelMutateResult>, PathsMiningGADMPregelAlgorithmFactory> {
    @Override
    public String name() {
        return PathsMiningGADMPregelAlgorithm.class.getSimpleName();
    }

    @Override
    public PathsMiningGADMPregelAlgorithmFactory algorithmFactory(
            ExecutionContext executionContext) {
        return new PathsMiningGADMPregelAlgorithmFactory();
    }

    @Override
    public NewConfigFunction<PathsMiningGADMPregel.PathsMiningGADMPregelConfig> newConfigFunction(
            ) {
        return (__, userInput) -> PathsMiningGADMPregel.PathsMiningGADMPregelConfig.of(userInput);
    }

    @Override
    public ComputationResultConsumer<PathsMiningGADMPregelAlgorithm, PregelResult, PathsMiningGADMPregel.PathsMiningGADMPregelConfig, Stream<PregelMutateResult>> computationResultConsumer(
            ) {
        return new PregelMutateComputationResultConsumer<>();
    }
}
