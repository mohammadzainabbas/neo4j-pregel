package com.neo4j.proc;

import org.neo4j.gds.paths.PathResult;
import org.HdrHistogram.DoubleHistogram;
import org.HdrHistogram.Histogram;
import org.neo4j.procedure.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;

public class FindFrequentSignatures {

    @UserAggregationFunction("esilv.proc.find_frequent_signatures")
    @Description("Returns the following statistics on the numerical values in the given collection: percentiles, min, minNonZero, max, total, mean, stdev.")
    public StatisticsFunction statistics() {
        return new StatisticsFunction();
    }

    @Procedure("esilv.proc.find_signatures")
    public Stream<PathResult> find_signatures(@Name("nodes") List<Node> nodes, @Name(value = "config",defaultValue = "{}") Map<String, Object> config) {
     
    
}
