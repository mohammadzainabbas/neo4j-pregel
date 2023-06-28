package com.neo4j.pregel;

import javax.annotation.processing.Generated;
import org.neo4j.gds.GraphAlgorithmFactory;
import org.neo4j.gds.api.Graph;
import org.neo4j.gds.beta.pregel.Pregel;
import org.neo4j.gds.core.utils.mem.MemoryEstimation;
import org.neo4j.gds.core.utils.progress.tasks.ProgressTracker;
import org.neo4j.gds.core.utils.progress.tasks.Task;

@Generated("org.neo4j.gds.pregel.PregelProcessor")
public final class BFSLevelPregelAlgorithmFactory extends GraphAlgorithmFactory<BFSLevelPregelAlgorithm, BFSPregelConfig> {
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
}
