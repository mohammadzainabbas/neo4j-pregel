package com.neo4j.proc;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Result;
import org.neo4j.graphdb.Transaction;

import org.neo4j.test.TestDatabaseManagementServiceFactory;


import org.neo4j.test.TestGraphDatabaseFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FindFrequentSignaturesTest {
    private GraphDatabaseService db;

    @BeforeEach
    void setUp() {
        db = new TestDatabaseManagementServiceFactory().newImpermanentDatabase();
    }

    @AfterEach
    void tearDown() {
        db.shutdown();
    }

    @Test
    void testFindSignatures() {
        // Prepare your graph data
        try (Transaction tx = db.beginTx()) {
            tx.execute("CREATE (a:Node {name: 'A'}), (b:Node {name: 'B'}), (c:Node {name: 'C'}), (d:Node {name: 'D'}), (e:Node {name: 'E'}), (a)-[:REL]->(b), (b)-[:REL]->(c), (c)-[:REL]->(d), (d)-[:REL]->(e), (e)-[:REL]->(a)");
            tx.commit();
        }

        // Register your procedure
        db.executeTransactionally("CALL dbms.procedures() YIELD name WHERE name = 'esilv.proc.find_signatures' CALL dbms.security.procedures.unrestricted(name) YIELD name RETURN name");

        // Test your procedure
        try (Transaction tx = db.beginTx()) {
            Result result = tx.execute("WITH -1 as identifier CALL esilv.pregel.find_paths.stream('countries_2018', {maxIterations: 5, identifier: identifier}) YIELD nodeId, values CALL esilv.proc.find_signatures(values.paths, identifier) YIELD signature, count RETURN signature, count ORDER BY count DESC");
            // assert your expected results here, e.g.
            // assertTrue(result.hasNext());
            // assertEquals(expectedSignature, result.next().get("signature"));
            tx.commit();
        }
    }
}
