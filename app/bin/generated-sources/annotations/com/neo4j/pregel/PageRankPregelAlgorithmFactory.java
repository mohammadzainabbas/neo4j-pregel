package com.neo4j.pregel;

import javax.annotation.processing.Generated;
import org.neo4j.gds.GraphAlgorithmFactory;
import org.neo4j.gds.api.Graph;
import org.neo4j.gds.beta.pregel.Pregel;
import org.neo4j.gds.core.utils.mem.MemoryEstimation;
import org.neo4j.gds.core.utils.progress.tasks.ProgressTracker;
import org.neo4j.gds.core.utils.progress.tasks.Task;

@Generated("org.neo4j.gds.pregel.PregelProcessor")
public final class PageRankPregelAlgorithmFactory extends GraphAlgorithmFactory<PageRankPregelAlgorithm, PageRankPregel.PageRankPregelConfig> {
    @Override
    public PageRankPregelAlgorithm build(Graph graph,
            PageRankPregel.PageRankPregelConfig configuration, ProgressTracker progressTracker) {
        return new PageRankPregelAlgorithm(graph, configuration, progressTracker);
    }

    @Override
    public String taskName() {
        return PageRankPregelAlgorithm.class.getSimpleName();
    }

    @Override
    public Task progressTask(Graph graph, PageRankPregel.PageRankPregelConfig configuration) {
        return Pregel.progressTask(graph, configuration);
    }

    @Override
    public MemoryEstimation memoryEstimation(PageRankPregel.PageRankPregelConfig configuration) {
        var computation = new PageRankPregel();
        return Pregel.memoryEstimation(computation.schema(configuration), computation.reducer().isEmpty(), configuration.isAsynchronous());
    }
}
