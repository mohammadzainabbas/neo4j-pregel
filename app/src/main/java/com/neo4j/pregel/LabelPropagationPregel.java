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
import org.neo4j.gds.config.SeedConfig;
import org.neo4j.gds.core.CypherMapWrapper;

import java.util.Arrays;

/**
 * Basic implementation potentially suffering from oscillating vertex states due to synchronous computation.
 */
@PregelProcedure(name = "esilv.pregel.lp", modes = {GDSMode.STREAM}, description = "Label Propagation with Pregel")
public class LabelPropagationPregel implements PregelComputation<LabelPropagationPregel.LabelPropagationPregelConfig> {

    public static final String LABEL_KEY = "label";

    @Override
    public PregelSchema schema(LabelPropagationPregel.LabelPropagationPregelConfig config) {
        return new PregelSchema.Builder().add(LABEL_KEY, ValueType.LONG).build();
    }

    @Override
    public void init(InitContext<LabelPropagationPregel.LabelPropagationPregelConfig> context) {
        context.setNodeValue(LABEL_KEY, context.nodeId());
    }

    @Override
    public void compute(ComputeContext<LabelPropagationPregel.LabelPropagationPregelConfig> context, Messages messages) {
        if (context.isInitialSuperstep()) {
            context.sendToNeighbors(context.nodeId());
        } else {
            if (messages != null) {
                long oldValue = context.longNodeValue(LABEL_KEY);
                long newValue = oldValue;

                // TODO: could be shared across compute functions per thread
                // We receive at most |degree| messages
                long[] buffer = new long[context.degree()];

                int messageCount = 0;

                for (var message : messages) {
                    buffer[messageCount++] = message.longValue();
                }

                int maxOccurences = 1;
                if (messageCount > 1) {
                    // Sort to compute the most frequent id
                    Arrays.sort(buffer, 0, messageCount);
                    int currentOccurences = 1;
                    for (int i = 1; i < messageCount; i++) {
                        if (buffer[i] == buffer[i - 1]) {
                            currentOccurences++;
                            if (currentOccurences > maxOccurences) {
                                maxOccurences = currentOccurences;
                                newValue = buffer[i];
                            }
                        } else {
                            currentOccurences = 1;
                        }
                    }
                }

                // All with same frequency, pick smallest id
                if (maxOccurences == 1) {
                    newValue = Math.min(oldValue, buffer[0]);
                }

                if (newValue != oldValue) {
                    context.setNodeValue(LABEL_KEY, newValue);
                    context.sendToNeighbors(newValue);
                }
            }
        }
        context.voteToHalt();
    }

    @ValueClass
    @Configuration("LabelPropagationPregelConfigImpl")
    @SuppressWarnings("immutables:subtype")
    public interface LabelPropagationPregelConfig extends PregelProcedureConfig, SeedConfig {

        static LabelPropagationPregelConfig of(CypherMapWrapper userInput) {
            return new LabelPropagationPregelConfigImpl(userInput);
        }
    }
}
