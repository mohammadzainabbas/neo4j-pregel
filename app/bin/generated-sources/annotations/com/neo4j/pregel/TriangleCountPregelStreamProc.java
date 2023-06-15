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
        name = "esilv.pregel.triangleCount.stream",
        executionMode = ExecutionMode.STREAM,
        description = "Triangle counts with Pregel"
)
@Generated("org.neo4j.gds.beta.pregel.PregelProcessor")
public final class TriangleCountPregelStreamProc extends PregelStreamProc<TriangleCountPregelAlgorithm, TriangleCountPregelConfig> {
    @Procedure(
            name = "esilv.pregel.triangleCount.stream",
            mode = Mode.READ
    )
    @Description("Triangle counts with Pregel")
    public Stream<PregelStreamResult> stream(@Name("graphName") String graphName,
            @Name(value = "configuration", defaultValue = "{}") Map<String, Object> configuration) {
        return stream(compute(graphName, configuration));
    }

    @Procedure(
            name = "esilv.pregel.triangleCount.stream.estimate",
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
    protected TriangleCountPregelConfig newConfig(String username, CypherMapWrapper config) {
        return TriangleCountPregelConfig.of(config);
    }

    @Override
    public GraphAlgorithmFactory<TriangleCountPregelAlgorithm, TriangleCountPregelConfig> algorithmFactory(
            ) {
        return new GraphAlgorithmFactory<TriangleCountPregelAlgorithm, TriangleCountPregelConfig>() {
            @Override
            public TriangleCountPregelAlgorithm build(Graph graph,
                    TriangleCountPregelConfig configuration, ProgressTracker progressTracker) {
                return new TriangleCountPregelAlgorithm(graph, configuration, progressTracker);
            }

            @Override
            public String taskName() {
                return TriangleCountPregelAlgorithm.class.getSimpleName();
            }

            @Override
            public Task progressTask(Graph graph, TriangleCountPregelConfig configuration) {
                return Pregel.progressTask(graph, configuration);
            }

            @Override
            public MemoryEstimation memoryEstimation(TriangleCountPregelConfig configuration) {
                var computation = new TriangleCountPregel();
                return Pregel.memoryEstimation(computation.schema(configuration), computation.reducer().isEmpty(), configuration.isAsynchronous());
            }
        };
    }
}
