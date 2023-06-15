package com.neo4j.pregel;

import java.util.Map;
import java.util.stream.Stream;
import javax.annotation.processing.Generated;
import org.neo4j.gds.BaseProc;
import org.neo4j.gds.GraphAlgorithmFactory;
import org.neo4j.gds.api.Graph;
import org.neo4j.gds.beta.pregel.Pregel;
import org.neo4j.gds.beta.pregel.PregelResult;
import org.neo4j.gds.core.CypherMapWrapper;
import org.neo4j.gds.core.utils.mem.MemoryEstimation;
import org.neo4j.gds.core.utils.progress.tasks.ProgressTracker;
import org.neo4j.gds.core.utils.progress.tasks.Task;
import org.neo4j.gds.executor.ComputationResult;
import org.neo4j.gds.executor.ExecutionContext;
import org.neo4j.gds.executor.ExecutionMode;
import org.neo4j.gds.executor.GdsCallable;
import org.neo4j.gds.pregel.proc.PregelMutateProc;
import org.neo4j.gds.pregel.proc.PregelMutateResult;
import org.neo4j.gds.result.AbstractResultBuilder;
import org.neo4j.gds.results.MemoryEstimateResult;
import org.neo4j.procedure.Description;
import org.neo4j.procedure.Mode;
import org.neo4j.procedure.Name;
import org.neo4j.procedure.Procedure;

@GdsCallable(
        name = "esilv.pregel.pagerank.mutate",
        executionMode = ExecutionMode.MUTATE_NODE_PROPERTY,
        description = "Frequent Pattern Mining :: Neo4j - PageRank with Pregel"
)
@Generated("org.neo4j.gds.beta.pregel.PregelProcessor")
public final class PageRankPregelMutateProc extends PregelMutateProc<PageRankPregelAlgorithm, PageRankPregel.PageRankPregelConfig> {
    @Procedure(
            name = "esilv.pregel.pagerank.mutate",
            mode = Mode.READ
    )
    @Description("Frequent Pattern Mining :: Neo4j - PageRank with Pregel")
    public Stream<PregelMutateResult> mutate(@Name("graphName") String graphName,
            @Name(value = "configuration", defaultValue = "{}") Map<String, Object> configuration) {
        return mutate(compute(graphName, configuration));
    }

    @Procedure(
            name = "esilv.pregel.pagerank.mutate.estimate",
            mode = Mode.READ
    )
    @Description(BaseProc.ESTIMATE_DESCRIPTION)
    public Stream<MemoryEstimateResult> estimate(
            @Name("graphNameOrConfiguration") Object graphNameOrConfiguration,
            @Name("algoConfiguration") Map<String, Object> algoConfiguration) {
        return computeEstimate(graphNameOrConfiguration, algoConfiguration);
    }

    @Override
    protected AbstractResultBuilder<PregelMutateResult> resultBuilder(
            ComputationResult<PageRankPregelAlgorithm, PregelResult, PageRankPregel.PageRankPregelConfig> computeResult,
            ExecutionContext executionContext) {
        var ranIterations = computeResult.result().ranIterations();
        var didConverge = computeResult.result().didConverge();
        return new PregelMutateResult.Builder().withRanIterations(ranIterations).didConverge(didConverge);
    }

    @Override
    protected PageRankPregel.PageRankPregelConfig newConfig(String username,
            CypherMapWrapper config) {
        return PageRankPregel.PageRankPregelConfig.of(config);
    }

    @Override
    public GraphAlgorithmFactory<PageRankPregelAlgorithm, PageRankPregel.PageRankPregelConfig> algorithmFactory(
            ) {
        return new GraphAlgorithmFactory<PageRankPregelAlgorithm, PageRankPregel.PageRankPregelConfig>() {
            @Override
            public PageRankPregelAlgorithm build(Graph graph,
                    PageRankPregel.PageRankPregelConfig configuration,
                    ProgressTracker progressTracker) {
                return new PageRankPregelAlgorithm(graph, configuration, progressTracker);
            }

            @Override
            public String taskName() {
                return PageRankPregelAlgorithm.class.getSimpleName();
            }

            @Override
            public Task progressTask(Graph graph,
                    PageRankPregel.PageRankPregelConfig configuration) {
                return Pregel.progressTask(graph, configuration);
            }

            @Override
            public MemoryEstimation memoryEstimation(
                    PageRankPregel.PageRankPregelConfig configuration) {
                var computation = new PageRankPregel();
                return Pregel.memoryEstimation(computation.schema(configuration), computation.reducer().isEmpty(), configuration.isAsynchronous());
            }
        };
    }
}
