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
public final class WritePathsMiningPregelMutateProc extends BaseProc {
    @Procedure(
            name = "esilv.pregel.find_paths_with_write.mutate",
            mode = Mode.READ
    )
    @Description("Paths Mining with Pregel (find all paths of length 'max_iteration'). Write to disk while finding the paths - Frequent Pattern Mining :: Neo4j")
    public Stream<PregelMutateResult> mutate(@Name("graphName") String graphName,
            @Name(value = "configuration", defaultValue = "{}") Map<String, Object> configuration) {
        var specification = new WritePathsMiningPregelMutateSpecification();
        var executor = new ProcedureExecutor<>(specification, executionContext());
        return executor.compute(graphName, configuration);
    }

    @Procedure(
            name = "esilv.pregel.find_paths_with_write.mutate.estimate",
            mode = Mode.READ
    )
    @Description(BaseProc.ESTIMATE_DESCRIPTION)
    public Stream<MemoryEstimateResult> estimate(
            @Name("graphNameOrConfiguration") Object graphNameOrConfiguration,
            @Name("algoConfiguration") Map<String, Object> algoConfiguration) {
        var specification = new WritePathsMiningPregelMutateSpecification();
        var executor = new MemoryEstimationExecutor<>(specification, executionContext(), transactionContext());
        return executor.computeEstimate(graphNameOrConfiguration, algoConfiguration);
    }
}