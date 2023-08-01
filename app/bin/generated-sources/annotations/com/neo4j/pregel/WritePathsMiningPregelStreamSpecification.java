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
        name = "esilv.pregel.find_paths_with_write.stream",
        executionMode = ExecutionMode.STREAM,
        description = "Paths Mining with Pregel (find all paths of length 'max_iteration') - Frequent Pattern Mining :: Neo4j"
)
@Generated("org.neo4j.gds.pregel.PregelProcessor")
public final class WritePathsMiningPregelStreamSpecification implements AlgorithmSpec<WritePathsMiningPregelAlgorithm, PregelResult, WritePathsMiningPregel.WritePathsMiningPregelConfig, Stream<PregelStreamResult>, WritePathsMiningPregelAlgorithmFactory> {
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
    public ComputationResultConsumer<WritePathsMiningPregelAlgorithm, PregelResult, WritePathsMiningPregel.WritePathsMiningPregelConfig, Stream<PregelStreamResult>> computationResultConsumer(
            ) {
        return new PregelStreamComputationResultConsumer<>();
    }
}
