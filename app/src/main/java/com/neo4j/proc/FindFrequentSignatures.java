package com.neo4j.proc;

import org.neo4j.procedure.*;

import org.neo4j.logging.Log;

import java.util.stream.Stream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import java.util.ArrayList;
import java.util.HashMap;

public class FindFrequentSignatures {
    // @Context
    // public Log log;

    @Procedure(value = "esilv.proc.find_signatures", mode = Mode.READ)
    @Description("Returns the frequency for all the signatures found in the given paths.")
    public Stream<SignatureCount> find_signatures(@Name("paths") List<Long> paths, @Name("identifier") Long identifier) {
        FindFrequentSignaturesFunction function = new FindFrequentSignaturesFunction();
        function.aggregate(paths, identifier);
        return function.result().stream();
    }

    public class SignatureCount {
        public String signature;
        public Long count;

        public SignatureCount(String signature, Long count) {
            this.signature = signature;
            this.count = count;
        }
    }

    public class FindFrequentSignaturesFunction {
        private final ConcurrentHashMap<String, Long> signature_count_map = new ConcurrentHashMap<String, Long>();

        public String convertToSignature(ArrayList<Long> array) {
            HashMap<Long, Character> map = new HashMap<>();
            char currentChar = 'A';
            StringBuilder signature = new StringBuilder();

            for (long number : array) {
                if (!map.containsKey(number)) {
                    map.put(number, currentChar);
                    currentChar++;
                }
                signature.append(map.get(number));
            }

            return signature.toString();
        }

        public void aggregate(List<Long> paths, Long identifier) {
            ArrayList<Long> _paths = new ArrayList<Long>();
            for (Long el: paths) {
                if (el.equals(identifier)) {
                    String signature = convertToSignature(_paths);
                    signature_count_map.put(signature, signature_count_map.getOrDefault(signature, 0L) + 1);
                    _paths.clear();
                } else {
                    _paths.add(el);
                }
            }
        }

        public List<SignatureCount> result() {
            List<SignatureCount> resultList = new ArrayList<>();
            for (Map.Entry<String, Long> entry : signature_count_map.entrySet()) {
                resultList.add(new SignatureCount(entry.getKey(), entry.getValue()));
            }
            resultList.sort((o1, o2) -> Long.compare(o2.count, o1.count));
            return resultList;
        }
    }
}
