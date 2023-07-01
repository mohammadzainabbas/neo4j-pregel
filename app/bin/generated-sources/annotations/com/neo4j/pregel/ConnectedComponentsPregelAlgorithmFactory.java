package com.neo4j.pregel;

import javax.annotation.processing.Generated;
import org.neo4j.gds.GraphAlgorithmFactory;
import org.neo4j.gds.api.Graph;
import org.neo4j.gds.beta.pregel.Pregel;
import org.neo4j.gds.core.utils.mem.MemoryEstimation;
import org.neo4j.gds.core.utils.progress.tasks.ProgressTracker;
import org.neo4j.gds.core.utils.progress.tasks.Task;

@Generated("org.neo4j.gds.pregel.PregelProcessor")
public final class ConnectedComponentsPregelAlgorithmFactory extends GraphAlgorithmFactory<ConnectedComponentsPregelAlgorithm, ConnectedComponentsConfig> {
    @Override
    public ConnectedComponentsPregelAlgorithm build(Graph graph,
            ConnectedComponentsConfig configuration, ProgressTracker progressTracker) {
        return new ConnectedComponentsPregelAlgorithm(graph, configuration, progressTracker);
    }

    @Override
    public String taskName() {
        return ConnectedComponentsPregelAlgorithm.class.getSimpleName();
    }

    @Override
    public Task progressTask(Graph graph, ConnectedComponentsConfig configuration) {
        return Pregel.progressTask(graph, configuration);
    }

    @Override
    public MemoryEstimation memoryEstimation(ConnectedComponentsConfig configuration) {
        var computation = new ConnectedComponentsPregel();
        return Pregel.memoryEstimation(computation.schema(configuration), computation.reducer().isEmpty(), configuration.isAsynchronous());
    }
}
