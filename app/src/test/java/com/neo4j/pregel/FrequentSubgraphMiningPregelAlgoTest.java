package com.neo4j.pregel;

import org.junit.jupiter.api.Test;
import org.neo4j.gds.TestSupport;
import org.neo4j.gds.beta.pregel.Pregel;
import org.neo4j.gds.core.concurrency.Pools;
import org.neo4j.gds.core.utils.progress.tasks.ProgressTracker;
import org.neo4j.gds.extension.GdlExtension;
import org.neo4j.gds.extension.GdlGraph;
import org.neo4j.gds.extension.Inject;
import org.neo4j.gds.extension.TestGraph;

import java.util.HashMap;

import static com.neo4j.pregel.FrequentSubgraphMiningPregel.FSM;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@GdlExtension
class FrequentSubgraphMiningPregelAlgoTest {

    @GdlGraph
    private static final String MY_TEST_GRAPH =
        "CREATE" +
        "  (alice)" +
        ", (bob)" +
        ", (eve)" +
        ", (alice)-[:LIKES]->(bob)" +
        ", (bob)-[:LIKES]->(alice)" +
        ", (eve)-[:DISLIKES]->(alice)" +
        ", (eve)-[:DISLIKES]->(bob)";

    @Inject
    private TestGraph graph;

    @Test
    void runFrequentSubgraphMiningPregel() {
        int maxIterations = 10;

        var config = ImmutableFrequentSubgraphMiningPregelConfig.builder()
            .maxIterations(maxIterations)
            .build();

        var pregelJob = Pregel.create(
            graph,
            config,
            new FrequentSubgraphMiningPregel(),
            Pools.DEFAULT,
            ProgressTracker.NULL_TRACKER
        );

        var result = pregelJob.run();

        assertTrue(result.didConverge(), "Algorithm did not converge.");
        // assertEquals(0, result.ranIterations());

        var expected = new HashMap<String, Long>();
        expected.put("alice", 0L);
        expected.put("bob", 1L);
        expected.put("eve", 2L);

        // TestSupport.assertLongValues(graph, (nodeId) -> result.nodeValues().longValue(FSM, nodeId), expected);
    }
    
}