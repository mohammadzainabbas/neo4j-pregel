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
 * Setting the value for each node to the node-id of its parent.
 * If there are multiple parents at the discovery level/iteration, the parent with the minimum id is chosen.
 */
@PregelProcedure(name = "esilv.pregel.bfs.parent", modes = {GDSMode.STREAM}, description = "BFS (parent with minimum id) with Pregel")
public class BFSParentPregel implements PregelComputation<BFSPregelConfig> {

    public static final long NOT_FOUND = Long.MAX_VALUE;
    public static final String PARENT = "parent";

    @Override
    public PregelSchema schema(BFSPregelConfig config) {
        return new PregelSchema.Builder().add(PARENT, ValueType.LONG).build();
    }

    @Override
    public void compute(ComputeContext<BFSPregelConfig> context, Messages messages) {
        long nodeId = context.nodeId();

        if (context.isInitialSuperstep()) {
            if (nodeId == context.config().startNode()) {
                context.setNodeValue(PARENT, nodeId);
                context.sendToNeighbors(nodeId);
                context.voteToHalt();
            } else {
                context.setNodeValue(PARENT, NOT_FOUND);
            }
        } else {
            long currentParent = context.longNodeValue(PARENT);

            if (currentParent == NOT_FOUND) {
                for (var msg : messages) {
                    currentParent = Long.min(currentParent, msg.longValue());
                }

                // only send message if a parent existed
                if (currentParent != NOT_FOUND) {
                    context.setNodeValue(PARENT, currentParent);
                    context.sendToNeighbors(nodeId);
                }
            }
            context.voteToHalt();
        }
    }

    @Override
    public Optional<Reducer> reducer() {
        return Optional.of(new Reducer.Min());
    }
}

