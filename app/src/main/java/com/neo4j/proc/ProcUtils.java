package com.neo4j.proc;

import java.util.ArrayList;
import java.util.HashMap;

public class ProcUtils {
    static public String convertToSignature(ArrayList<Long> array) {
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
