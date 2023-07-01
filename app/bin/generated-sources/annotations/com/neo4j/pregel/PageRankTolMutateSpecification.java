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
        name = "esilv.pregel.prtol.mutate",
        executionMode = ExecutionMode.MUTATE_NODE_PROPERTY
)
@Generated("org.neo4j.gds.pregel.PregelProcessor")
public final class PageRankTolMutateSpecification implements AlgorithmSpec<PageRankTolAlgorithm, PregelResult, PageRankTol.PrTolConfig, Stream<PregelMutateResult>, PageRankTolAlgorithmFactory> {
    @Override
    public String name() {
        return PageRankTolAlgorithm.class.getSimpleName();
    }

    @Override
    public PageRankTolAlgorithmFactory algorithmFactory(ExecutionContext executionContext) {
        return new PageRankTolAlgorithmFactory();
    }

    @Override
    public NewConfigFunction<PageRankTol.PrTolConfig> newConfigFunction() {
        return (__, userInput) -> PageRankTol.PrTolConfig.of(userInput);
    }

    @Override
    public ComputationResultConsumer<PageRankTolAlgorithm, PregelResult, PageRankTol.PrTolConfig, Stream<PregelMutateResult>> computationResultConsumer(
            ) {
        return new PregelMutateComputationResultConsumer<>();
    }
}
