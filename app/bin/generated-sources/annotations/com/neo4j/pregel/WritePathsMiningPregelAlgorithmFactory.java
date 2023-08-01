package com.neo4j.pregel;

import javax.annotation.processing.Generated;
import org.neo4j.gds.GraphAlgorithmFactory;
import org.neo4j.gds.api.Graph;
import org.neo4j.gds.beta.pregel.Pregel;
import org.neo4j.gds.core.utils.mem.MemoryEstimation;
import org.neo4j.gds.core.utils.progress.tasks.ProgressTracker;
import org.neo4j.gds.core.utils.progress.tasks.Task;

@Generated("org.neo4j.gds.pregel.PregelProcessor")
public final class WritePathsMiningPregelAlgorithmFactory extends GraphAlgorithmFactory<WritePathsMiningPregelAlgorithm, WritePathsMiningPregel.WritePathsMiningPregelConfig> {
    @Override
    public WritePathsMiningPregelAlgorithm build(Graph graph,
            WritePathsMiningPregel.WritePathsMiningPregelConfig configuration,
            ProgressTracker progressTracker) {
        return new WritePathsMiningPregelAlgorithm(graph, configuration, progressTracker);
    }

    @Override
    public String taskName() {
        return WritePathsMiningPregelAlgorithm.class.getSimpleName();
    }

    @Override
    public Task progressTask(Graph graph,
            WritePathsMiningPregel.WritePathsMiningPregelConfig configuration) {
        return Pregel.progressTask(graph, configuration);
    }

    @Override
    public MemoryEstimation memoryEstimation(
            WritePathsMiningPregel.WritePathsMiningPregelConfig configuration) {
        var computation = new WritePathsMiningPregel();
        return Pregel.memoryEstimation(computation.schema(configuration), computation.reducer().isEmpty(), configuration.isAsynchronous());
    }
}
