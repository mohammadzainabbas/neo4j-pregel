package com.neo4j.pregel;

import org.neo4j.gds.api.nodeproperties.ValueType;
import org.neo4j.gds.beta.pregel.Messages;
import org.neo4j.gds.beta.pregel.PregelComputation;
import org.neo4j.gds.beta.pregel.PregelSchema;
import org.neo4j.gds.beta.pregel.Reducer;
import org.neo4j.gds.beta.pregel.annotation.GDSMode;
import org.neo4j.gds.beta.pregel.annotation.PregelProcedure;
import org.neo4j.gds.beta.pregel.context.ComputeContext;

import java.util.Optional;

/**
 * Setting the value for each node to the level/iteration the node is discovered via BFS.
 */
@PregelProcedure(name = "esilv.pregel.bfs.level", modes = {GDSMode.STREAM}, description = "BFS (with level/iteration) with Pregel")
public class BFSLevelPregel implements PregelComputation<BFSPregelConfig> {

    private static final long NOT_FOUND = -1;
    public static final String LEVEL = "LEVEL";

    @Override
    public PregelSchema schema(BFSPregelConfig config) {
        return new PregelSchema.Builder().add(LEVEL, ValueType.LONG).build();
    }

    @Override
    public void compute(ComputeContext<BFSPregelConfig> context, Messages messages) {
        if (context.isInitialSuperstep()) {
            if (context.nodeId() == context.config().startNode()) {
                context.setNodeValue(LEVEL, 0);
                context.sendToNeighbors(1);
                context.voteToHalt();
            } else {
                context.setNodeValue(LEVEL, NOT_FOUND);
            }
        } else if (messages.iterator().hasNext()) {
            long level = context.longNodeValue(LEVEL);
            if (level == NOT_FOUND) {
                level = messages.iterator().next().longValue();

                context.setNodeValue(LEVEL, level);
                context.sendToNeighbors(level + 1);
            }
        }
        context.voteToHalt();
    }

    @Override
    public Optional<Reducer> reducer() {
        return Optional.of(new Reducer() {
            @Override
            public double identity() {
                return -1;
            }

            @Override
            public double reduce(double current, double message) {
                return message;
            }
        });
    }
}
