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
        name = "esilv.pregel.pagerank.mutate",
        executionMode = ExecutionMode.MUTATE_NODE_PROPERTY,
        description = "Frequent Pattern Mining :: Neo4j - PageRank with Pregel"
)
@Generated("org.neo4j.gds.pregel.PregelProcessor")
public final class PageRankPregelMutateSpecification implements AlgorithmSpec<PageRankPregelAlgorithm, PregelResult, PageRankPregel.PageRankPregelConfig, Stream<PregelMutateResult>, PageRankPregelAlgorithmFactory> {
    @Override
    public String name() {
        return PageRankPregelAlgorithm.class.getSimpleName();
    }

    @Override
    public PageRankPregelAlgorithmFactory algorithmFactory(ExecutionContext executionContext) {
        return new PageRankPregelAlgorithmFactory();
    }

    @Override
    public NewConfigFunction<PageRankPregel.PageRankPregelConfig> newConfigFunction() {
        return (__, userInput) -> PageRankPregel.PageRankPregelConfig.of(userInput);
    }

    @Override
    public ComputationResultConsumer<PageRankPregelAlgorithm, PregelResult, PageRankPregel.PageRankPregelConfig, Stream<PregelMutateResult>> computationResultConsumer(
            ) {
        return new PregelMutateComputationResultConsumer<>();
    }
}
