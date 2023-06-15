package com.neo4j.pregel;

import com.carrotsearch.hppc.LongHashSet;
import com.carrotsearch.hppc.procedures.LongProcedure;
import org.apache.commons.lang3.mutable.MutableLong;
import org.neo4j.gds.api.nodeproperties.ValueType;
import org.neo4j.gds.beta.pregel.Messages;
import org.neo4j.gds.beta.pregel.PregelComputation;
import org.neo4j.gds.beta.pregel.PregelSchema;
import org.neo4j.gds.beta.pregel.Reducer;
import org.neo4j.gds.beta.pregel.annotation.GDSMode;
import org.neo4j.gds.beta.pregel.annotation.PregelProcedure;
import org.neo4j.gds.beta.pregel.context.ComputeContext;

import java.util.Optional;
import java.util.function.LongConsumer;

/**
 * ! Assuming an unweighted graph
 */
@PregelProcedure(name = "esilv.pregel.triangleCount", modes = {GDSMode.STREAM}, description = "Triangle counts with Pregel")
public class TriangleCountPregel implements PregelComputation<TriangleCountPregelConfig> {

    public static final String TRIANGLE_COUNT = "TRIANGLES";

    @Override
    public PregelSchema schema(TriangleCountPregelConfig config) {
        return new PregelSchema.Builder()
            .add(TRIANGLE_COUNT, ValueType.LONG)
            .build();
    }

    @Override
    public void compute(ComputeContext<TriangleCountPregelConfig> context, Messages messages) {
        if (context.isInitialSuperstep()) {
            context.setNodeValue(TRIANGLE_COUNT, 0);
        } else if (context.superstep() == Phase.MERGE_NEIGHBORS.step) {
            var neighborsOfA = new LongHashSet(context.degree());
            context.forEachDistinctNeighbor(neighborsOfA::add);

            long nodeA = context.nodeId();
            var trianglesFromNodeA = new MutableLong();

            neighborsOfA.forEach((LongProcedure) nodeB -> {
                if (nodeB > nodeA) {
                    LongConsumer findTriangles = nodeC -> {
                        // find common neighbors of A
                        // check indexed neighbors of A
                        if (nodeC > nodeB && neighborsOfA.contains(nodeC)) {
                            trianglesFromNodeA.increment();
                            context.sendTo(nodeB, 1);
                            context.sendTo(nodeC, 1);
                        }
                    };
                    if (context.isMultiGraph()) {
                        context.forEachDistinctNeighbor(nodeB, findTriangles);
                    } else {
                        context.forEachNeighbor(nodeB, findTriangles);
                    }
                }
            });
            context.setNodeValue(TRIANGLE_COUNT, trianglesFromNodeA.longValue());
        } else if (context.superstep() == Phase.COUNT_TRIANGLES.step) {
            long triangles = context.longNodeValue(TRIANGLE_COUNT);
            if (!messages.isEmpty()) {
                triangles += messages.doubleIterator().nextDouble();
            }

            context.setNodeValue(TRIANGLE_COUNT, triangles);
            context.voteToHalt();
        }
    }

    @Override
    public Optional<Reducer> reducer() {
        return Optional.of(new Reducer.Count());
    }

    enum Phase {
        MERGE_NEIGHBORS(1),
        COUNT_TRIANGLES(2);

        final long step;

        Phase(int i) {
            step = i;
        }
    }
}
