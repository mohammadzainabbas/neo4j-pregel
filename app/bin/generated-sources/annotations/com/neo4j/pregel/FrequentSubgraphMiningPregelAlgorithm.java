package com.neo4j.pregel;

import javax.annotation.processing.Generated;
import org.neo4j.gds.Algorithm;
import org.neo4j.gds.api.Graph;
import org.neo4j.gds.beta.pregel.Pregel;
import org.neo4j.gds.beta.pregel.PregelResult;
import org.neo4j.gds.core.concurrency.Pools;
import org.neo4j.gds.core.utils.TerminationFlag;
import org.neo4j.gds.core.utils.progress.tasks.ProgressTracker;

@Generated("org.neo4j.gds.pregel.PregelProcessor")
public final class FrequentSubgraphMiningPregelAlgorithm extends Algorithm<PregelResult> {
    private final Pregel<FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig> pregelJob;

    FrequentSubgraphMiningPregelAlgorithm(Graph graph,
            FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig configuration,
            ProgressTracker progressTracker) {
        super(progressTracker);
        var computation = new FrequentSubgraphMiningPregel();
        this.pregelJob = Pregel.create(graph, configuration, computation, Pools.DEFAULT, progressTracker);
    }

    @Override
    public void setTerminationFlag(TerminationFlag terminationFlag) {
        super.setTerminationFlag(terminationFlag);
        pregelJob.setTerminationFlag(terminationFlag);
    }

    @Override
    public PregelResult compute() {
        return pregelJob.run();
    }
}
