package com.neo4j.proc;

import org.neo4j.gds.paths.PathResult;
import org.HdrHistogram.DoubleHistogram;
import org.HdrHistogram.Histogram;
import org.neo4j.procedure.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Arrays.asList;

import java.util.ArrayList;

public class FindFrequentSignatures {

    @UserAggregationFunction("esilv.proc.find_frequent_signatures")
    @Description("Returns the frequency for all the signatures found in the given paths.")
    public FindFrequentSignaturesFunction find_signatures() {
        return new FindFrequentSignaturesFunction();
    }


    public static class FindFrequentSignaturesFunction {

        private final ConcurrentHashMap<String, Long> signature_count_map = new ConcurrentHashMap<String, Long>();
        
        public String toSignature(List<Long> path) {
            String signature = "";
            for (Long node : path) {
                signature += node.toString();
            }
            return signature;
        }

        @UserAggregationUpdate
        public void aggregate(@Name(value = "paths", defaultValue = "[1, -1]") List<Long> paths) {
            ArrayList<Long> path = new ArrayList<Long>();
            for (Long subPaths : paths) {
                String signature = toSignature(subPaths);
                if (signature_count_map.containsKey(signature)) {
                    signature_count_map.put(signature, signature_count_map.get(signature) + 1);
                } else {
                    signature_count_map.put(signature, 1L);
                }
            }
        }

        @UserAggregationResult
        public Map<String, Number> result() {
            long totalCount = values != null ? values.getTotalCount() : doubles.getTotalCount();
            boolean empty = totalCount == 0;
            Map<String, Number> result = new LinkedHashMap<>(percentiles.size() + 6);
            result.put("min", minValue);
            result.put("minNonZero", values != null ? values.getMinNonZeroValue() : doubles.getMinNonZeroValue());
            result.put("max", maxValue);
            result.put("total", totalCount);
            result.put("mean", values != null ? values.getMean() : doubles.getMean());
            result.put("stdev", values != null ? values.getStdDeviation() : doubles.getStdDeviation());

            for (Double percentile : percentiles) {
                if (percentile != null && !empty) {
                    if (values != null) {
                        result.put(percentile.toString(), values.getValueAtPercentile(percentile * 100D));
                    } else {
                        result.put(percentile.toString(), doubles.getValueAtPercentile(percentile * 100D));
                    }
                }
            }
            return result;
        }

    }

    
}
