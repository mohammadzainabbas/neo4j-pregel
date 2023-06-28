package com.neo4j.pregel;

import java.util.stream.Stream;
import javax.annotation.processing.Generated;
import org.neo4j.gds.beta.pregel.PregelResult;
import org.neo4j.gds.executor.AlgorithmSpec;
import org.neo4j.gds.executor.ComputationResultConsumer;
import org.neo4j.gds.executor.ExecutionContext;
import org.neo4j.gds.executor.ExecutionMode;
import org.neo4j.gds.executor.GdsCallable;
import org.neo4j.gds.executor.NewConfigFunction;
import org.neo4j.gds.pregel.proc.PregelStreamComputationResultConsumer;
import org.neo4j.gds.pregel.proc.PregelStreamResult;

@GdsCallable(
        name = "esilv.pregel.lp.stream",
        executionMode = ExecutionMode.STREAM,
        description = "Label Propagation with Pregel"
)
@Generated("org.neo4j.gds.pregel.PregelProcessor")
public final class LabelPropagationPregelStreamSpecification implements AlgorithmSpec<LabelPropagationPregelAlgorithm, PregelResult, LabelPropagationPregel.LabelPropagationPregelConfig, Stream<PregelStreamResult>, LabelPropagationPregelAlgorithmFactory> {
    @Override
    public String name() {
        return LabelPropagationPregelAlgorithm.class.getSimpleName();
    }

    @Override
    public LabelPropagationPregelAlgorithmFactory algorithmFactory(
            ExecutionContext executionContext) {
        return new LabelPropagationPregelAlgorithmFactory();
    }

    @Override
    public NewConfigFunction<LabelPropagationPregel.LabelPropagationPregelConfig> newConfigFunction(
            ) {
        return (__, userInput) -> LabelPropagationPregel.LabelPropagationPregelConfig.of(userInput);
    }

    @Override
    public ComputationResultConsumer<LabelPropagationPregelAlgorithm, PregelResult, LabelPropagationPregel.LabelPropagationPregelConfig, Stream<PregelStreamResult>> computationResultConsumer(
            ) {
        return new PregelStreamComputationResultConsumer<>();
    }
}
