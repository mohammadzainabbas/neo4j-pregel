package com.neo4j.pregel;

import javax.annotation.processing.Generated;
import org.neo4j.gds.GraphAlgorithmFactory;
import org.neo4j.gds.api.Graph;
import org.neo4j.gds.beta.pregel.Pregel;
import org.neo4j.gds.core.utils.mem.MemoryEstimation;
import org.neo4j.gds.core.utils.progress.tasks.ProgressTracker;
import org.neo4j.gds.core.utils.progress.tasks.Task;

@Generated("org.neo4j.gds.pregel.PregelProcessor")
public final class SingleSourceShortestPathPregelAlgorithmFactory extends GraphAlgorithmFactory<SingleSourceShortestPathPregelAlgorithm, SingleSourceShortestPathPregel.SingleSourceShortestPathPregelConfig> {
    @Override
    public SingleSourceShortestPathPregelAlgorithm build(Graph graph,
            SingleSourceShortestPathPregel.SingleSourceShortestPathPregelConfig configuration,
            ProgressTracker progressTracker) {
        return new SingleSourceShortestPathPregelAlgorithm(graph, configuration, progressTracker);
    }

    @Override
    public String taskName() {
        return SingleSourceShortestPathPregelAlgorithm.class.getSimpleName();
    }

    @Override
    public Task progressTask(Graph graph,
            SingleSourceShortestPathPregel.SingleSourceShortestPathPregelConfig configuration) {
        return Pregel.progressTask(graph, configuration);
    }

    @Override
    public MemoryEstimation memoryEstimation(
            SingleSourceShortestPathPregel.SingleSourceShortestPathPregelConfig configuration) {
        var computation = new SingleSourceShortestPathPregel();
        return Pregel.memoryEstimation(computation.schema(configuration), computation.reducer().isEmpty(), configuration.isAsynchronous());
    }
}
