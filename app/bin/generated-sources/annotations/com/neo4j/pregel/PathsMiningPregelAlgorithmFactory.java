package com.neo4j.pregel;

import javax.annotation.processing.Generated;
import org.neo4j.gds.GraphAlgorithmFactory;
import org.neo4j.gds.api.Graph;
import org.neo4j.gds.beta.pregel.Pregel;
import org.neo4j.gds.core.utils.mem.MemoryEstimation;
import org.neo4j.gds.core.utils.progress.tasks.ProgressTracker;
import org.neo4j.gds.core.utils.progress.tasks.Task;

@Generated("org.neo4j.gds.pregel.PregelProcessor")
public final class PathsMiningPregelAlgorithmFactory extends GraphAlgorithmFactory<PathsMiningPregelAlgorithm, PathsMiningPregel.PathsMiningPregelConfig> {
    @Override
    public PathsMiningPregelAlgorithm build(Graph graph,
            PathsMiningPregel.PathsMiningPregelConfig configuration,
            ProgressTracker progressTracker) {
        return new PathsMiningPregelAlgorithm(graph, configuration, progressTracker);
    }

    @Override
    public String taskName() {
        return PathsMiningPregelAlgorithm.class.getSimpleName();
    }

    @Override
    public Task progressTask(Graph graph, PathsMiningPregel.PathsMiningPregelConfig configuration) {
        return Pregel.progressTask(graph, configuration);
    }

    @Override
    public MemoryEstimation memoryEstimation(
            PathsMiningPregel.PathsMiningPregelConfig configuration) {
        var computation = new PathsMiningPregel();
        return Pregel.memoryEstimation(computation.schema(configuration), computation.reducer().isEmpty(), configuration.isAsynchronous());
    }
}
