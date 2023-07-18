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

import static com.neo4j.pregel.PathsMiningPregel.PATH;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@GdlExtension
class PathsMiningPregelAlgoTest {

    public enum TestingGraph {
        DUMMY_GRAPH {
            public String toString() {
                return "dummy_graph_generation.cypher";
            }
        },
        SMALL_GRAPH {
            public String toString() {
                return "small_graph_generation.cypher";
            }
        },
        BIG_GRAPH {
            public String toString() {
                return "graph_generation.cypher";
            }
        }
    }
    
    static String PATH = "/Users/mohammadzainabbas/Masters/Thesis/Experiments/neo4j-pregel/queries/";
    static String FILE_NAME = TestingGraph.SMALL_GRAPH.toString();

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
    void runPathsMiningPregel() {
        int maxIterations = 10;
        boolean isEncodedOutput = false;
        long identifier = -1;

        var config = ImmutablePathsMiningPregelConfig.builder()
            .maxIterations(maxIterations)
            .isEncodedOutput(isEncodedOutput)
            .identifier(identifier)
            .build();

        var pregelJob = Pregel.create(
            graph,
            config,
            new PathsMiningPregel(),
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
