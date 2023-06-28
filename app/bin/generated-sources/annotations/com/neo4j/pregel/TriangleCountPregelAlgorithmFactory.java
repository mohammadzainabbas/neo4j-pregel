package com.neo4j.pregel;

import javax.annotation.processing.Generated;
import org.neo4j.gds.GraphAlgorithmFactory;
import org.neo4j.gds.api.Graph;
import org.neo4j.gds.beta.pregel.Pregel;
import org.neo4j.gds.core.utils.mem.MemoryEstimation;
import org.neo4j.gds.core.utils.progress.tasks.ProgressTracker;
import org.neo4j.gds.core.utils.progress.tasks.Task;

@Generated("org.neo4j.gds.pregel.PregelProcessor")
public final class TriangleCountPregelAlgorithmFactory extends GraphAlgorithmFactory<TriangleCountPregelAlgorithm, TriangleCountPregelConfig> {
    @Override
    public TriangleCountPregelAlgorithm build(Graph graph, TriangleCountPregelConfig configuration,
            ProgressTracker progressTracker) {
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
}
