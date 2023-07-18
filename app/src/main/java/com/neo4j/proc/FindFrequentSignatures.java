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
    @Description("Returns the frequency for all the signatures found in the given paths.")
    public FindFrequentSignaturesFunction find_signatures() {
        return new StatisticsFunction();
    }


    public static class FindFrequentSignaturesFunction {
    }

    
}
