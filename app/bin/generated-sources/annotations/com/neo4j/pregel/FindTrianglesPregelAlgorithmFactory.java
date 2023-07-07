package com.neo4j.pregel;

import javax.annotation.processing.Generated;
import org.neo4j.gds.GraphAlgorithmFactory;
import org.neo4j.gds.api.Graph;
import org.neo4j.gds.beta.pregel.Pregel;
import org.neo4j.gds.core.utils.mem.MemoryEstimation;
import org.neo4j.gds.core.utils.progress.tasks.ProgressTracker;
import org.neo4j.gds.core.utils.progress.tasks.Task;

@Generated("org.neo4j.gds.pregel.PregelProcessor")
public final class FindTrianglesPregelAlgorithmFactory extends GraphAlgorithmFactory<FindTrianglesPregelAlgorithm, FindTrianglesPregel.FindTrianglesPregelConfig> {
    @Override
    public FindTrianglesPregelAlgorithm build(Graph graph,
            FindTrianglesPregel.FindTrianglesPregelConfig configuration,
            ProgressTracker progressTracker) {
        return new FindTrianglesPregelAlgorithm(graph, configuration, progressTracker);
    }

    @Override
    public String taskName() {
        return FindTrianglesPregelAlgorithm.class.getSimpleName();
    }

    @Override
    public Task progressTask(Graph graph,
            FindTrianglesPregel.FindTrianglesPregelConfig configuration) {
        return Pregel.progressTask(graph, configuration);
    }

    @Override
    public MemoryEstimation memoryEstimation(
            FindTrianglesPregel.FindTrianglesPregelConfig configuration) {
        var computation = new FindTrianglesPregel();
        return Pregel.memoryEstimation(computation.schema(configuration), computation.reducer().isEmpty(), configuration.isAsynchronous());
    }
}
