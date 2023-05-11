package com.neo4j.pregel;

import org.neo4j.gds.annotation.Configuration;
import org.neo4j.gds.annotation.ValueClass;
import org.neo4j.gds.api.nodeproperties.ValueType;
import org.neo4j.gds.beta.pregel.Messages;
import org.neo4j.gds.beta.pregel.PregelComputation;
import org.neo4j.gds.beta.pregel.PregelProcedureConfig;
import org.neo4j.gds.beta.pregel.PregelSchema;
import org.neo4j.gds.beta.pregel.annotation.GDSMode;
import org.neo4j.gds.beta.pregel.annotation.PregelProcedure;
import org.neo4j.gds.beta.pregel.context.ComputeContext;
import org.neo4j.gds.beta.pregel.context.InitContext;
import org.neo4j.gds.core.CypherMapWrapper;

import static com.neo4j.pregel.SingleSourceShortestPathPregel.SingleSourceShortestPathPregelConfig;

@PregelProcedure(name = "esilv.pregel.sssp", modes = {GDSMode.STREAM}, description = "Single source shortest path (SSSP) with Pregel")
public class SingleSourceShortestPathPregel implements PregelComputation<SingleSourceShortestPathPregelConfig> {

    static final String DISTANCE = "DISTANCE";

    // Stores the internal node if of the user-defined source node id.
    private volatile long startNodeId;

    @Override
    public PregelSchema schema(SingleSourceShortestPathPregelConfig config) {
        return new PregelSchema.Builder().add(DISTANCE, ValueType.LONG).build();
    }

    @Override
    public void init(InitContext<SingleSourceShortestPathPregelConfig> context) {
        if (context.nodeId() == context.toInternalId(context.config().startNode())) {
            this.startNodeId = context.toInternalId(context.config().startNode());
            context.setNodeValue(DISTANCE, 0);
        } else {
            context.setNodeValue(DISTANCE, Long.MAX_VALUE);
        }
    }

    @Override
    public void compute(ComputeContext<SingleSourceShortestPathPregelConfig> context, Messages messages) {
        if (context.isInitialSuperstep() && context.nodeId() == this.startNodeId) {
            context.sendToNeighbors(1);
        } else {
            // This is basically the same message passing as WCC (except the new message)
            long newDistance = context.longNodeValue(DISTANCE);
            boolean hasChanged = false;

            for (var message : messages) {
                if (message < newDistance) {
                    newDistance = message.longValue();
                    hasChanged = true;
                }
            }

            if (hasChanged) {
                context.setNodeValue(DISTANCE, newDistance);
                context.sendToNeighbors(newDistance + 1);
            }

            context.voteToHalt();
        }

    }

    @ValueClass
    @Configuration("SingleSourceShortestPathPregelConfigImpl")
    @SuppressWarnings("immutables:subtype")
    interface SingleSourceShortestPathPregelConfig extends PregelProcedureConfig {
        long startNode();

        static SingleSourceShortestPathPregelConfig of(CypherMapWrapper userInput) {
            return new SingleSourceShortestPathPregelConfigImpl(userInput);
        }
    }
}
