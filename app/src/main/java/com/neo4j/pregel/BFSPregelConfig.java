package com.neo4j.pregel;

import org.neo4j.gds.annotation.Configuration;
import org.neo4j.gds.annotation.ValueClass;
import org.neo4j.gds.beta.pregel.PregelProcedureConfig;
import org.neo4j.gds.core.CypherMapWrapper;

@ValueClass
@Configuration
@SuppressWarnings("immutables:subtype")
public interface BFSPregelConfig extends PregelProcedureConfig {
    long startNode();

    static BFSPregelConfig of(CypherMapWrapper userInput) {
        return new BFSPregelConfigImpl(userInput);
    }
}
