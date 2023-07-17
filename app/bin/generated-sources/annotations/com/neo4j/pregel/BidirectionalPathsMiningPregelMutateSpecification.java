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
import org.neo4j.gds.executor.validation.ValidationConfiguration;
import org.neo4j.gds.pregel.proc.PregelBaseProc;
import org.neo4j.gds.pregel.proc.PregelMutateComputationResultConsumer;
import org.neo4j.gds.pregel.proc.PregelMutateResult;

@GdsCallable(
        name = "esilv.pregel.bi_find_paths.mutate",
        executionMode = ExecutionMode.MUTATE_NODE_PROPERTY,
        description = "Paths Mining with Pregel (find all paths of length 'max_iteration') - Frequent Pattern Mining :: Neo4j"
)
@Generated("org.neo4j.gds.pregel.PregelProcessor")
public final class BidirectionalPathsMiningPregelMutateSpecification implements AlgorithmSpec<BidirectionalPathsMiningPregelAlgorithm, PregelResult, BidirectionalPathsMiningPregel.BidirectionalPathsMiningPregelConfig, Stream<PregelMutateResult>, BidirectionalPathsMiningPregelAlgorithmFactory> {
    @Override
    public String name() {
        return BidirectionalPathsMiningPregelAlgorithm.class.getSimpleName();
    }

    @Override
    public BidirectionalPathsMiningPregelAlgorithmFactory algorithmFactory(
            ExecutionContext executionContext) {
        return new BidirectionalPathsMiningPregelAlgorithmFactory();
    }

    @Override
    public NewConfigFunction<BidirectionalPathsMiningPregel.BidirectionalPathsMiningPregelConfig> newConfigFunction(
            ) {
        return (__, userInput) -> BidirectionalPathsMiningPregel.BidirectionalPathsMiningPregelConfig.of(userInput);
    }

    @Override
    public ComputationResultConsumer<BidirectionalPathsMiningPregelAlgorithm, PregelResult, BidirectionalPathsMiningPregel.BidirectionalPathsMiningPregelConfig, Stream<PregelMutateResult>> computationResultConsumer(
            ) {
        return new PregelMutateComputationResultConsumer<>();
    }

    @Override
    public ValidationConfiguration<BidirectionalPathsMiningPregel.BidirectionalPathsMiningPregelConfig> validationConfig(
            ExecutionContext executionContext) {
        return PregelBaseProc.ensureIndexValidation(executionContext.log(), executionContext.taskRegistryFactory());
    }
}
