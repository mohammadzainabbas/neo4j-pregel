package com.neo4j.pregel;

import javax.annotation.processing.Generated;
import org.neo4j.gds.GraphAlgorithmFactory;
import org.neo4j.gds.api.Graph;
import org.neo4j.gds.beta.pregel.Pregel;
import org.neo4j.gds.core.utils.mem.MemoryEstimation;
import org.neo4j.gds.core.utils.progress.tasks.ProgressTracker;
import org.neo4j.gds.core.utils.progress.tasks.Task;

@Generated("org.neo4j.gds.pregel.PregelProcessor")
public final class PageRankTolAlgorithmFactory extends GraphAlgorithmFactory<PageRankTolAlgorithm, PageRankTol.PrTolConfig> {
    @Override
    public PageRankTolAlgorithm build(Graph graph, PageRankTol.PrTolConfig configuration,
            ProgressTracker progressTracker) {
        return new PageRankTolAlgorithm(graph, configuration, progressTracker);
    }

    @Override
    public String taskName() {
        return PageRankTolAlgorithm.class.getSimpleName();
    }

    @Override
    public Task progressTask(Graph graph, PageRankTol.PrTolConfig configuration) {
        return Pregel.progressTask(graph, configuration);
    }

    @Override
    public MemoryEstimation memoryEstimation(PageRankTol.PrTolConfig configuration) {
        var computation = new PageRankTol();
        return Pregel.memoryEstimation(computation.schema(configuration), computation.reducer().isEmpty(), configuration.isAsynchronous());
    }
}
