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
        name = "esilv.pregel.find_paths_with_write.mutate",
        executionMode = ExecutionMode.MUTATE_NODE_PROPERTY,
        description = "Paths Mining with Pregel (find all paths of length 'max_iteration'). Write to disk while finding the paths - Frequent Pattern Mining :: Neo4j"
)
@Generated("org.neo4j.gds.pregel.PregelProcessor")
public final class WritePathsMiningPregelMutateSpecification implements AlgorithmSpec<WritePathsMiningPregelAlgorithm, PregelResult, WritePathsMiningPregel.WritePathsMiningPregelConfig, Stream<PregelMutateResult>, WritePathsMiningPregelAlgorithmFactory> {
    @Override
    public String name() {
        return WritePathsMiningPregelAlgorithm.class.getSimpleName();
    }

    @Override
    public WritePathsMiningPregelAlgorithmFactory algorithmFactory(
            ExecutionContext executionContext) {
        return new WritePathsMiningPregelAlgorithmFactory();
    }

    @Override
    public NewConfigFunction<WritePathsMiningPregel.WritePathsMiningPregelConfig> newConfigFunction(
            ) {
        return (__, userInput) -> WritePathsMiningPregel.WritePathsMiningPregelConfig.of(userInput);
    }

    @Override
    public ComputationResultConsumer<WritePathsMiningPregelAlgorithm, PregelResult, WritePathsMiningPregel.WritePathsMiningPregelConfig, Stream<PregelMutateResult>> computationResultConsumer(
            ) {
        return new PregelMutateComputationResultConsumer<>();
    }
}
