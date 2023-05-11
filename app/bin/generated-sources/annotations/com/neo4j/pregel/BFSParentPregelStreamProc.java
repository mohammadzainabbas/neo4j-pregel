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
        name = "esilv.pregel.bfs.parent.stream",
        executionMode = ExecutionMode.STREAM,
        description = "BFS (parent with minimum id) with Pregel"
)
@Generated("org.neo4j.gds.beta.pregel.PregelProcessor")
public final class BFSParentPregelStreamProc extends PregelStreamProc<BFSParentPregelAlgorithm, BFSPregelConfig> {
    @Procedure(
            name = "esilv.pregel.bfs.parent.stream",
            mode = Mode.READ
    )
    @Description("BFS (parent with minimum id) with Pregel")
    public Stream<PregelStreamResult> stream(@Name("graphName") String graphName,
            @Name(value = "configuration", defaultValue = "{}") Map<String, Object> configuration) {
        return stream(compute(graphName, configuration));
    }

    @Procedure(
            name = "esilv.pregel.bfs.parent.stream.estimate",
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
    public GraphAlgorithmFactory<BFSParentPregelAlgorithm, BFSPregelConfig> algorithmFactory() {
        return new GraphAlgorithmFactory<BFSParentPregelAlgorithm, BFSPregelConfig>() {
            @Override
            public BFSParentPregelAlgorithm build(Graph graph, BFSPregelConfig configuration,
                    ProgressTracker progressTracker) {
                return new BFSParentPregelAlgorithm(graph, configuration, progressTracker);
            }

            @Override
            public String taskName() {
                return BFSParentPregelAlgorithm.class.getSimpleName();
            }

            @Override
            public Task progressTask(Graph graph, BFSPregelConfig configuration) {
                return Pregel.progressTask(graph, configuration);
            }

            @Override
            public MemoryEstimation memoryEstimation(BFSPregelConfig configuration) {
                var computation = new BFSParentPregel();
                return Pregel.memoryEstimation(computation.schema(configuration), computation.reducer().isEmpty(), configuration.isAsynchronous());
            }
        };
    }
}
