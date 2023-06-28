package com.neo4j.pregel;

import javax.annotation.processing.Generated;
import org.neo4j.gds.GraphAlgorithmFactory;
import org.neo4j.gds.api.Graph;
import org.neo4j.gds.beta.pregel.Pregel;
import org.neo4j.gds.core.utils.mem.MemoryEstimation;
import org.neo4j.gds.core.utils.progress.tasks.ProgressTracker;
import org.neo4j.gds.core.utils.progress.tasks.Task;

@Generated("org.neo4j.gds.pregel.PregelProcessor")
public final class FrequentSubgraphMiningPregelAlgorithmFactory extends GraphAlgorithmFactory<FrequentSubgraphMiningPregelAlgorithm, FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig> {
    @Override
    public FrequentSubgraphMiningPregelAlgorithm build(Graph graph,
            FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig configuration,
            ProgressTracker progressTracker) {
        return new FrequentSubgraphMiningPregelAlgorithm(graph, configuration, progressTracker);
    }

    @Override
    public String taskName() {
        return FrequentSubgraphMiningPregelAlgorithm.class.getSimpleName();
    }

    @Override
    public Task progressTask(Graph graph,
            FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig configuration) {
        return Pregel.progressTask(graph, configuration);
    }

    @Override
    public MemoryEstimation memoryEstimation(
            FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig configuration) {
        var computation = new FrequentSubgraphMiningPregel();
        return Pregel.memoryEstimation(computation.schema(configuration), computation.reducer().isEmpty(), configuration.isAsynchronous());
    }
}
