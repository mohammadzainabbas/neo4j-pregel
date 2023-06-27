package com.neo4j.pregel;

import org.neo4j.gds.api.nodeproperties.ValueType;
import org.neo4j.gds.beta.pregel.Messages;
import org.neo4j.gds.beta.pregel.PregelComputation;
import org.neo4j.gds.beta.pregel.PregelSchema;
import org.neo4j.gds.beta.pregel.annotation.PregelProcedure;
import org.neo4j.gds.beta.pregel.context.ComputeContext;
import org.neo4j.gds.beta.pregel.context.InitContext;
import org.neo4j.gds.beta.pregel.annotation.GDSMode;

@PregelProcedure(
    name = "esilv.pregel.cc",
    description = "Connected Components with Pregel",
    modes = { GDSMode.STREAM, GDSMode.MUTATE, GDSMode.STATS }
)
public class ConnectedComponentsPregel implements PregelComputation<ConnectedComponentsConfig> {

    public static final String COMPONENT = "component";

    @Override
    public PregelSchema schema(ConnectedComponentsConfig config) {
        return new PregelSchema.Builder().add(COMPONENT, ValueType.LONG).build();
    }

    @Override
    public void init(InitContext<ConnectedComponentsConfig> context) {
        var initialValue = context.config().seedProperty() != null
            ? context.nodeProperties(context.config().seedProperty()).longValue(context.nodeId())
            : context.nodeId();
        context.setNodeValue(COMPONENT, initialValue);
    }

    @Override
    public void compute(ComputeContext<ConnectedComponentsConfig> context, Messages messages) {
        long oldComponentId = context.longNodeValue(COMPONENT);
        long newComponentId = oldComponentId;

        for (var nextComponentId : messages) {
            if (nextComponentId.longValue() < newComponentId) {
                newComponentId = nextComponentId.longValue();
            }
        }

        if (context.isInitialSuperstep() || newComponentId != oldComponentId) {
            context.setNodeValue(COMPONENT, newComponentId);
            context.sendToNeighbors(newComponentId);
        }

        context.voteToHalt();
    }
}
