package com.neo4j.proc;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.neo4j.harness.Neo4j;
import org.neo4j.harness.Neo4jBuilders;
import org.neo4j.driver.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
