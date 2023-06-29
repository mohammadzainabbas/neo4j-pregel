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
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.neo4j.pregel.FrequentSubgraphMiningPregel.FSM;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@GdlExtension
class FrequentSubgraphMiningPregelAlgoTest {
    
    String PATH = "/Users/mohammadzainabbas/Masters/Thesis/Experiments/neo4j-pregel/queries/";
    String FILE_NAME = "graph_generation.cypher";

    @GdlGraph
    private static final String MY_TEST_GRAPH;
    static {
        String content = "";
        try {
            content = new String(Files.readAllBytes(Paths.get(PATH + FILE_NAME)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        MY_TEST_GRAPH = content;
    }
    
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
