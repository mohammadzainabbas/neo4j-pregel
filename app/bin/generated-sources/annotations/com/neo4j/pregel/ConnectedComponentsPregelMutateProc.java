package com.neo4j.pregel;

import java.util.Map;
import java.util.stream.Stream;
import javax.annotation.processing.Generated;
import org.neo4j.gds.BaseProc;
import org.neo4j.gds.executor.MemoryEstimationExecutor;
import org.neo4j.gds.executor.ProcedureExecutor;
import org.neo4j.gds.pregel.proc.PregelMutateResult;
import org.neo4j.gds.results.MemoryEstimateResult;
import org.neo4j.procedure.Description;
import org.neo4j.procedure.Mode;
import org.neo4j.procedure.Name;
import org.neo4j.procedure.Procedure;

@Generated("org.neo4j.gds.pregel.PregelProcessor")
public final class ConnectedComponentsPregelMutateProc extends BaseProc {
    @Procedure(
            name = "esilv.pregel.cc.mutate",
            mode = Mode.READ
    )
    @Description("Connected Components with Pregel")
    public Stream<PregelMutateResult> mutate(@Name("graphName") String graphName,
            @Name(value = "configuration", defaultValue = "{}") Map<String, Object> configuration) {
        var specification = new ConnectedComponentsPregelMutateSpecification();
        var executor = new ProcedureExecutor<>(specification, executionContext());
        return executor.compute(graphName, configuration);
    }

    @Procedure(
            name = "esilv.pregel.cc.mutate.estimate",
            mode = Mode.READ
    )
    @Description(BaseProc.ESTIMATE_DESCRIPTION)
    public Stream<MemoryEstimateResult> estimate(
            @Name("graphNameOrConfiguration") Object graphNameOrConfiguration,
            @Name("algoConfiguration") Map<String, Object> algoConfiguration) {
        var specification = new ConnectedComponentsPregelMutateSpecification();
        var executor = new MemoryEstimationExecutor<>(specification, executionContext(), transactionContext());
        return executor.computeEstimate(graphNameOrConfiguration, algoConfiguration);
    }
}
