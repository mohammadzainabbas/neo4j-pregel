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
import static org.mockito.Answers.values;

import java.util.ArrayList;
import java.util.HashMap;

public class FindFrequentSignatures {

    @UserAggregationFunction("esilv.proc.find_frequent_signatures")
    @Description("Returns the frequency for all the signatures found in the given paths.")
    public FindFrequentSignaturesFunction find_signatures() {
        return new FindFrequentSignaturesFunction();
    }


    public static class FindFrequentSignaturesFunction {

        private final ConcurrentHashMap<String, Long> signature_count_map = new ConcurrentHashMap<String, Long>();
        
        public static String convertToSignature(ArrayList<Long> array) {
            // Map to store each number and its corresponding character.
            HashMap<Long, Character> map = new HashMap<>();
            char currentChar = 'A';
            StringBuilder signature = new StringBuilder();

            for (long number : array) {
                // If the number is not already in the map, add it with the current character
                // as its value, and increment the current character.
                if (!map.containsKey(number)) {
                    map.put(number, currentChar);
                    currentChar++;
                }
                // Append the character corresponding to this number to the signature.
                signature.append(map.get(number));
            }

            return signature.toString();
        }

        @UserAggregationUpdate
        public void aggregate(@Name(value = "paths", defaultValue = "[1, -1]") List<Long> paths, @Name(value = "identifier", defaultValue = "-1") Long identifier) {
            ArrayList<Long> _paths = new ArrayList<Long>();
            for (Long el: paths) {
                if (el == identifier) {
                    String signature = convertToSignature(_paths);
                    if (signature_count_map.containsKey(signature)) {
                        signature_count_map.put(signature, signature_count_map.get(signature) + 1);
                    } else {
                        signature_count_map.put(signature, 1L);
                    }
                    _paths.clear();
                } else {
                    _paths.add(el);
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
