package com.neo4j.pregel;

import java.util.Map;
import java.util.stream.Stream;
import javax.annotation.processing.Generated;
import org.neo4j.gds.BaseProc;
import org.neo4j.gds.GraphAlgorithmFactory;
import org.neo4j.gds.api.Graph;
import org.neo4j.gds.api.properties.nodes.NodePropertyValues;
import org.neo4j.gds.beta.pregel.Pregel;
import org.neo4j.gds.core.CypherMapWrapper;
import org.neo4j.gds.core.utils.mem.MemoryEstimation;
import org.neo4j.gds.core.utils.progress.tasks.ProgressTracker;
import org.neo4j.gds.core.utils.progress.tasks.Task;
import org.neo4j.gds.executor.ExecutionMode;
import org.neo4j.gds.executor.GdsCallable;
import org.neo4j.gds.pregel.proc.PregelStreamProc;
import org.neo4j.gds.pregel.proc.PregelStreamResult;
import org.neo4j.gds.results.MemoryEstimateResult;
import org.neo4j.procedure.Description;
import org.neo4j.procedure.Mode;
import org.neo4j.procedure.Name;
import org.neo4j.procedure.Procedure;

@GdsCallable(
        name = "esilv.pregel.bfs.level.stream",
        executionMode = ExecutionMode.STREAM,
        description = "BFS (with level/iteration) with Pregel"
)
@Generated("org.neo4j.gds.beta.pregel.PregelProcessor")
public final class BFSLevelPregelStreamProc extends PregelStreamProc<BFSLevelPregelAlgorithm, BFSPregelConfig> {
    @Procedure(
            name = "esilv.pregel.bfs.level.stream",
            mode = Mode.READ
    )
    @Description("BFS (with level/iteration) with Pregel")
    public Stream<PregelStreamResult> stream(@Name("graphName") String graphName,
            @Name(value = "configuration", defaultValue = "{}") Map<String, Object> configuration) {
        return stream(compute(graphName, configuration));
    }

    @Procedure(
            name = "esilv.pregel.bfs.level.stream.estimate",
            mode = Mode.READ
    )
    @Description(BaseProc.ESTIMATE_DESCRIPTION)
    public Stream<MemoryEstimateResult> estimate(
            @Name("graphNameOrConfiguration") Object graphNameOrConfiguration,
            @Name("algoConfiguration") Map<String, Object> algoConfiguration) {
        return computeEstimate(graphNameOrConfiguration, algoConfiguration);
    }

    @Override
    protected PregelStreamResult streamResult(long originalNodeId, long internalNodeId,
            NodePropertyValues nodePropertyValues) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected BFSPregelConfig newConfig(String username, CypherMapWrapper config) {
        return BFSPregelConfig.of(config);
    }

    @Override
    public GraphAlgorithmFactory<BFSLevelPregelAlgorithm, BFSPregelConfig> algorithmFactory() {
        return new GraphAlgorithmFactory<BFSLevelPregelAlgorithm, BFSPregelConfig>() {
            @Override
            public BFSLevelPregelAlgorithm build(Graph graph, BFSPregelConfig configuration,
                    ProgressTracker progressTracker) {
                return new BFSLevelPregelAlgorithm(graph, configuration, progressTracker);
            }

            @Override
            public String taskName() {
                return BFSLevelPregelAlgorithm.class.getSimpleName();
            }

            @Override
            public Task progressTask(Graph graph, BFSPregelConfig configuration) {
                return Pregel.progressTask(graph, configuration);
            }

            @Override
            public MemoryEstimation memoryEstimation(BFSPregelConfig configuration) {
                var computation = new BFSLevelPregel();
                return Pregel.memoryEstimation(computation.schema(configuration), computation.reducer().isEmpty(), configuration.isAsynchronous());
            }
        };
    }
}
