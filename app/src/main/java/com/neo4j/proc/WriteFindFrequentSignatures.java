package com.neo4j.proc;

import org.neo4j.procedure.*;

import com.neo4j.Constants;
import com.neo4j.pregel.WritePathsMiningPregel.WritePathsMiningPregelConfig;
import com.neo4j.proc.FindFrequentSignatures.SignatureCount;

import org.neo4j.gds.beta.pregel.context.ComputeContext;
import org.neo4j.logging.Log;
import java.util.stream.Stream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class WriteFindFrequentSignatures {
    // @Context
    // public Log log;

    @Procedure(value = "esilv.proc.find_signatures_with_write", mode = Mode.READ)
    @Description("Returns the frequency for all the signatures found in the given paths files.")
    public Stream<SignatureCount> find_signatures_with_write(@Name("nodeIds") List<Long> nodeIds, @Name("identifier") Long identifier, @Name(value = "writePath") String writePath) {
        String pathDir = !writePath.isBlank() ? writePath : Constants.PATHS_DIR;

        WriteFindFrequentSignaturesFunction function = new WriteFindFrequentSignaturesFunction();
        function.aggregate(nodeIds, pathDir, identifier);
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

    public class WriteFindFrequentSignaturesFunction {
        private final ConcurrentHashMap<String, Long> signature_count_map = new ConcurrentHashMap<String, Long>();

        public ArrayList<Long> readPathsFromFile(String filePath) {
            var paths = new ArrayList<Long>();
            try (DataInputStream dis = new DataInputStream(new FileInputStream(filePath))) {
                int length = dis.readInt(); // Read the length of the array first
                for (int i = 0; i < length; i++) {
                    paths.add(dis.readLong());
                }
            } catch (IOException e) {
                // e.printStackTrace();
            }
            return paths;
        }

        public void aggregate(List<Long> nodeIds, String pathDir, Long identifier) {
            for (Long nodeId: nodeIds) {
                String filePath = pathDir + File.separator + nodeId + ".txt";
                File file = new File(filePath);
                if (!file.exists()) { continue; }

                var paths = readPathsFromFile(filePath);
                ArrayList<Long> _paths = new ArrayList<Long>();
                for (Long el: paths) {
                    if (el.equals(identifier)) {
                        String signature = ProcUtils.convertToSignature(_paths);
                        signature_count_map.put(signature, signature_count_map.getOrDefault(signature, 0L) + 1);
                        _paths.clear();
                    } else {
                        _paths.add(el);
                    }
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
