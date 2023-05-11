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
import org.neo4j.gds.pregel.proc.PregelStatsProc;
import org.neo4j.gds.pregel.proc.PregelStatsResult;
import org.neo4j.gds.result.AbstractResultBuilder;
import org.neo4j.gds.results.MemoryEstimateResult;
import org.neo4j.procedure.Description;
import org.neo4j.procedure.Mode;
import org.neo4j.procedure.Name;
import org.neo4j.procedure.Procedure;

@GdsCallable(
        name = "esilv.pregel.cc.stats",
        executionMode = ExecutionMode.STATS,
        description = "Connected Components with Pregel"
)
@Generated("org.neo4j.gds.beta.pregel.PregelProcessor")
public final class ConnectedComponentsPregelStatsProc extends PregelStatsProc<ConnectedComponentsPregelAlgorithm, ConnectedComponentsConfig> {
    @Procedure(
            name = "esilv.pregel.cc.stats",
            mode = Mode.READ
    )
    @Description("Connected Components with Pregel")
    public Stream<PregelStatsResult> stats(@Name("graphName") String graphName,
            @Name(value = "configuration", defaultValue = "{}") Map<String, Object> configuration) {
        return stats(compute(graphName, configuration));
    }

    @Procedure(
            name = "esilv.pregel.cc.stats.estimate",
            mode = Mode.READ
    )
    @Description(BaseProc.ESTIMATE_DESCRIPTION)
    public Stream<MemoryEstimateResult> estimate(
            @Name("graphNameOrConfiguration") Object graphNameOrConfiguration,
            @Name("algoConfiguration") Map<String, Object> algoConfiguration) {
        return computeEstimate(graphNameOrConfiguration, algoConfiguration);
    }

    @Override
    protected AbstractResultBuilder<PregelStatsResult> resultBuilder(
            ComputationResult<ConnectedComponentsPregelAlgorithm, PregelResult, ConnectedComponentsConfig> computeResult,
            ExecutionContext executionContext) {
        var ranIterations = computeResult.result().ranIterations();
        var didConverge = computeResult.result().didConverge();
        return new PregelStatsResult.Builder().withRanIterations(ranIterations).didConverge(didConverge);
    }

    @Override
    protected ConnectedComponentsConfig newConfig(String username, CypherMapWrapper config) {
        return ConnectedComponentsConfig.of(config);
    }

    @Override
    public GraphAlgorithmFactory<ConnectedComponentsPregelAlgorithm, ConnectedComponentsConfig> algorithmFactory(
            ) {
        return new GraphAlgorithmFactory<ConnectedComponentsPregelAlgorithm, ConnectedComponentsConfig>() {
            @Override
            public ConnectedComponentsPregelAlgorithm build(Graph graph,
                    ConnectedComponentsConfig configuration, ProgressTracker progressTracker) {
                return new ConnectedComponentsPregelAlgorithm(graph, configuration, progressTracker);
            }

            @Override
            public String taskName() {
                return ConnectedComponentsPregelAlgorithm.class.getSimpleName();
            }

            @Override
            public Task progressTask(Graph graph, ConnectedComponentsConfig configuration) {
                return Pregel.progressTask(graph, configuration);
            }

            @Override
            public MemoryEstimation memoryEstimation(ConnectedComponentsConfig configuration) {
                var computation = new ConnectedComponentsPregel();
                return Pregel.memoryEstimation(computation.schema(configuration), computation.reducer().isEmpty(), configuration.isAsynchronous());
            }
        };
    }
}
