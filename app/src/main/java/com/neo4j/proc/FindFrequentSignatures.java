package com.neo4j.proc;

import org.neo4j.procedure.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import java.util.ArrayList;
import java.util.HashMap;

public class FindFrequentSignatures {

    @UserAggregationFunction("esilv.proc.find_signatures")
    @Description("Returns the frequency for all the signatures found in the given paths.")
    public FindFrequentSignaturesFunction find_signatures() {
        return new FindFrequentSignaturesFunction();
    }

    public class SignatureResult {
        public String signature;
        public long count;
    
        public SignatureResult(String signature, long count) {
            this.signature = signature;
            this.count = count;
        }
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
        public List<SignatureResult> result() {

            // Convert the ConcurrentHashMap to a list of Map.Entry
            List<Map.Entry<String, Long>> entryList = new ArrayList<>(signature_count_map.entrySet());

            // Sort the entryList based on values using a Comparator
            entryList.sort(Map.Entry.comparingByValue());

            // Convert each Map.Entry to a SignatureCount and add it to the result list
            List<SignatureResult> resultList = new ArrayList<>();
            for (Map.Entry<String, Long> entry : entryList) {
                resultList.add(new SignatureResult(entry.getKey(), entry.getValue()));
            }

            // // Create a new LinkedHashMap to preserve the sorted order
            // LinkedHashMap<String, Long> sortedMap = new LinkedHashMap<>();

            // // Populate the sortedMap with sorted entries
            // for (Map.Entry<String, Long> entry : entryList) {
            //     sortedMap.put(entry.getKey(), entry.getValue());
            // }

            return resultList;
        }
    }
}
