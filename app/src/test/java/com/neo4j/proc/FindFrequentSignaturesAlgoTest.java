package com.neo4j.proc;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Result;
import org.neo4j.graphdb.Transaction;
import org.neo4j.test.TestGraphDatabaseFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FindFrequentSignaturesAlgoTest {

    private GraphDatabaseService db;

    @BeforeEach
    void setUp() {
        db = new TestGraphDatabaseFactory().newImpermanentDatabase();
    }

    @AfterEach
    void tearDown() {
        db.shutdown();
    }
    
}
