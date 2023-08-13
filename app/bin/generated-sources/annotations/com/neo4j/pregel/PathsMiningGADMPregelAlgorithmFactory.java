package com.neo4j.pregel;

import javax.annotation.processing.Generated;
import org.neo4j.gds.GraphAlgorithmFactory;
import org.neo4j.gds.api.Graph;
import org.neo4j.gds.beta.pregel.Pregel;
import org.neo4j.gds.core.utils.mem.MemoryEstimation;
import org.neo4j.gds.core.utils.progress.tasks.ProgressTracker;
import org.neo4j.gds.core.utils.progress.tasks.Task;

@Generated("org.neo4j.gds.pregel.PregelProcessor")
public final class PathsMiningGADMPregelAlgorithmFactory extends GraphAlgorithmFactory<PathsMiningGADMPregelAlgorithm, PathsMiningGADMPregel.PathsMiningGADMPregelConfig> {
    @Override
    public PathsMiningGADMPregelAlgorithm build(Graph graph,
            PathsMiningGADMPregel.PathsMiningGADMPregelConfig configuration,
            ProgressTracker progressTracker) {
        return new PathsMiningGADMPregelAlgorithm(graph, configuration, progressTracker);
    }

    @Override
    public String taskName() {
        return PathsMiningGADMPregelAlgorithm.class.getSimpleName();
    }

    @Override
    public Task progressTask(Graph graph,
            PathsMiningGADMPregel.PathsMiningGADMPregelConfig configuration) {
        return Pregel.progressTask(graph, configuration);
    }

    @Override
    public MemoryEstimation memoryEstimation(
            PathsMiningGADMPregel.PathsMiningGADMPregelConfig configuration) {
        var computation = new PathsMiningGADMPregel();
        return Pregel.memoryEstimation(computation.schema(configuration), computation.reducer().isEmpty(), configuration.isAsynchronous());
    }
}
