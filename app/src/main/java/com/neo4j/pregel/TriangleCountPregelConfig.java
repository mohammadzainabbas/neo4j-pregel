package com.neo4j.pregel;

import org.immutables.value.Value;
import org.neo4j.gds.annotation.Configuration;
import org.neo4j.gds.annotation.ValueClass;
import org.neo4j.gds.beta.pregel.PregelProcedureConfig;
import org.neo4j.gds.core.CypherMapWrapper;

@ValueClass
@Configuration("TriangleCountPregelConfigImpl")
@SuppressWarnings("immutables:subtype")
public interface TriangleCountPregelConfig extends PregelProcedureConfig {

    @Value.Default
    @Override
    default int maxIterations() {
        return 4;
    }

    static TriangleCountPregelConfig of(CypherMapWrapper userInput) {
        return new TriangleCountPregelConfigImpl(userInput);
    }
}
