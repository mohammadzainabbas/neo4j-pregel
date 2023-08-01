package com.neo4j.proc;

public class ProcUtils {
    public static String convertToSignature(ArrayList<Long> array) {
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
}
