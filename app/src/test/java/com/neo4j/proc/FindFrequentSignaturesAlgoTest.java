package com.neo4j.proc;

import org.junit.jupiter.api.Test;
import org.neo4j.gds.TestSupport;
import org.neo4j.gds.beta.pregel.Pregel;
import org.neo4j.gds.core.concurrency.Pools;
import org.neo4j.gds.core.utils.progress.tasks.ProgressTracker;
import org.neo4j.gds.extension.GdlExtension;
import org.neo4j.gds.extension.GdlGraph;
import org.neo4j.gds.extension.Inject;
import org.neo4j.gds.extension.TestGraph;

import com.neo4j.pregel.ImmutablePathsMiningPregelConfig;
import com.neo4j.pregel.PathsMiningPregel;

import java.util.HashMap;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.neo4j.pregel.PathsMiningPregel.PATHS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@GdlExtension
class FindFrequentSignaturesAlgoTest {

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
    void runFindSignatures() {
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

        var __graph = graph.innerGraph();

        // assertTrue(result.didConverge(), "Algorithm did not converge.");
        // assertEquals(0, result.ranIterations());

        var expected = new HashMap<String, Long>();
        expected.put("alice", 0L);
        expected.put("bob", 1L);
        expected.put("eve", 2L);

        // TestSupport.assertLongValues(graph, (nodeId) -> result.nodeValues().longValue(FSM, nodeId), expected);
    }
    
}
















// package com.neo4j.proc;

// import org.junit.jupiter.api.AfterEach;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.neo4j.graphdb.GraphDatabaseService;
// import org.neo4j.graphdb.Result;
// import org.neo4j.graphdb.Transaction;

// import org.neo4j.test.TestDatabaseManagementServiceFactory;


// import org.neo4j.test.TestGraphDatabaseFactory;

// import static org.junit.jupiter.api.Assertions.assertEquals;

// class FindFrequentSignaturesTest {
//     private GraphDatabaseService db;

//     @BeforeEach
//     void setUp() {
//         db = new TestDatabaseManagementServiceFactory().newImpermanentDatabase();
//     }

//     @AfterEach
//     void tearDown() {
//         db.shutdown();
//     }

//     @Test
//     void testFindSignatures() {
//         // Prepare your graph data
//         try (Transaction tx = db.beginTx()) {
//             tx.execute("CREATE (a:Node {name: 'A'}), (b:Node {name: 'B'}), (c:Node {name: 'C'}), (d:Node {name: 'D'}), (e:Node {name: 'E'}), (a)-[:REL]->(b), (b)-[:REL]->(c), (c)-[:REL]->(d), (d)-[:REL]->(e), (e)-[:REL]->(a)");
//             tx.commit();
//         }

//         // Register your procedure
//         db.executeTransactionally("CALL dbms.procedures() YIELD name WHERE name = 'esilv.proc.find_signatures' CALL dbms.security.procedures.unrestricted(name) YIELD name RETURN name");

//         // Test your procedure
//         try (Transaction tx = db.beginTx()) {
//             Result result = tx.execute("WITH -1 as identifier CALL esilv.pregel.find_paths.stream('countries_2018', {maxIterations: 5, identifier: identifier}) YIELD nodeId, values CALL esilv.proc.find_signatures(values.paths, identifier) YIELD signature, count RETURN signature, count ORDER BY count DESC");
//             // assert your expected results here, e.g.
//             // assertTrue(result.hasNext());
//             // assertEquals(expectedSignature, result.next().get("signature"));
//             tx.commit();
//         }
//     }
// }
