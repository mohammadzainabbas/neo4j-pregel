package com.neo4j.pregel;

import org.immutables.value.Value;
import org.neo4j.gds.annotation.Configuration;
import org.neo4j.gds.annotation.ValueClass;
import org.neo4j.gds.beta.pregel.PregelProcedureConfig;
import org.neo4j.gds.config.SeedConfig;
import org.neo4j.gds.core.CypherMapWrapper;

@ValueClass
@Configuration
@SuppressWarnings("immutables:subtype")
public interface ConnectedComponentsConfig extends PregelProcedureConfig, SeedConfig {

    @Value.Default
    @Override
    default boolean isAsynchronous() {
        return true;
    }

    static ConnectedComponentsConfig of(CypherMapWrapper userInput) {
        return new ConnectedComponentsConfigImpl(userInput);
    }
}
