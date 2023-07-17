package com.neo4j.pregel;

import javax.annotation.processing.Generated;
import org.neo4j.gds.GraphAlgorithmFactory;
import org.neo4j.gds.api.Graph;
import org.neo4j.gds.beta.pregel.Pregel;
import org.neo4j.gds.core.utils.mem.MemoryEstimation;
import org.neo4j.gds.core.utils.progress.tasks.ProgressTracker;
import org.neo4j.gds.core.utils.progress.tasks.Task;

@Generated("org.neo4j.gds.pregel.PregelProcessor")
public final class BidirectionalPathsMiningPregelAlgorithmFactory extends GraphAlgorithmFactory<BidirectionalPathsMiningPregelAlgorithm, BidirectionalPathsMiningPregel.BidirectionalPathsMiningPregelConfig> {
    @Override
    public BidirectionalPathsMiningPregelAlgorithm build(Graph graph,
            BidirectionalPathsMiningPregel.BidirectionalPathsMiningPregelConfig configuration,
            ProgressTracker progressTracker) {
        return new BidirectionalPathsMiningPregelAlgorithm(graph, configuration, progressTracker);
    }

    @Override
    public String taskName() {
        return BidirectionalPathsMiningPregelAlgorithm.class.getSimpleName();
    }

    @Override
    public Task progressTask(Graph graph,
            BidirectionalPathsMiningPregel.BidirectionalPathsMiningPregelConfig configuration) {
        return Pregel.progressTask(graph, configuration);
    }

    @Override
    public MemoryEstimation memoryEstimation(
            BidirectionalPathsMiningPregel.BidirectionalPathsMiningPregelConfig configuration) {
        var computation = new BidirectionalPathsMiningPregel();
        return Pregel.memoryEstimation(computation.schema(configuration), computation.reducer().isEmpty(), configuration.isAsynchronous());
    }
}
