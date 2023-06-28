package com.neo4j.pregel;

import javax.annotation.processing.Generated;
import org.neo4j.gds.GraphAlgorithmFactory;
import org.neo4j.gds.api.Graph;
import org.neo4j.gds.beta.pregel.Pregel;
import org.neo4j.gds.core.utils.mem.MemoryEstimation;
import org.neo4j.gds.core.utils.progress.tasks.ProgressTracker;
import org.neo4j.gds.core.utils.progress.tasks.Task;

@Generated("org.neo4j.gds.pregel.PregelProcessor")
public final class LabelPropagationPregelAlgorithmFactory extends GraphAlgorithmFactory<LabelPropagationPregelAlgorithm, LabelPropagationPregel.LabelPropagationPregelConfig> {
    @Override
    public LabelPropagationPregelAlgorithm build(Graph graph,
            LabelPropagationPregel.LabelPropagationPregelConfig configuration,
            ProgressTracker progressTracker) {
        return new LabelPropagationPregelAlgorithm(graph, configuration, progressTracker);
    }

    @Override
    public String taskName() {
        return LabelPropagationPregelAlgorithm.class.getSimpleName();
    }

    @Override
    public Task progressTask(Graph graph,
            LabelPropagationPregel.LabelPropagationPregelConfig configuration) {
        return Pregel.progressTask(graph, configuration);
    }

    @Override
    public MemoryEstimation memoryEstimation(
            LabelPropagationPregel.LabelPropagationPregelConfig configuration) {
        var computation = new LabelPropagationPregel();
        return Pregel.memoryEstimation(computation.schema(configuration), computation.reducer().isEmpty(), configuration.isAsynchronous());
    }
}
