package com.neo4j.pregel;

import org.immutables.value.Value;
import org.neo4j.gds.annotation.Configuration;
import org.neo4j.gds.annotation.ValueClass;
import org.neo4j.gds.api.nodeproperties.ValueType;
import org.neo4j.gds.beta.pregel.Messages;
import org.neo4j.gds.beta.pregel.PregelComputation;
import org.neo4j.gds.beta.pregel.PregelProcedureConfig;
import org.neo4j.gds.beta.pregel.PregelSchema;
import org.neo4j.gds.beta.pregel.annotation.GDSMode;
import org.neo4j.gds.beta.pregel.annotation.PregelProcedure;
import org.neo4j.gds.beta.pregel.context.ComputeContext;
import org.neo4j.gds.beta.pregel.context.InitContext;
import org.neo4j.gds.beta.pregel.context.MasterComputeContext;
import org.neo4j.gds.config.SeedConfig;
import org.neo4j.gds.core.CypherMapWrapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

@PregelProcedure(name = "esilv.pregel.find_paths_with_write", modes = { GDSMode.STREAM, GDSMode.MUTATE }, description = "Paths Mining with Pregel (find all paths of length 'max_iteration'). Write to disk while finding the paths - Frequent Pattern Mining :: Neo4j")
public class WritePathsMiningPregel implements PregelComputation<WritePathsMiningPregel.WritePathsMiningPregelConfig> {

    // INTERNALS
    public static final String PATHS = "paths";
    
    // to save all neighbors (incoming + outgoing)
    // to keep track of [where this message was sent from] -> [to which node]
    // tells neighbors given a node_id 
    private final ConcurrentHashMap<Long, ArrayList<Long>> neighbors_map = new ConcurrentHashMap<Long, ArrayList<Long>>();
    
    //@TODO: maybe add one check for CONTAIN_SELF_LOOP and one variable for NO_OF_SELF_LOOPS
    // Might be helpful to add this (self-loop) info at the end when we make the paths
    // Can be done in master-compute function

    /*
     * Combine the two longs into one
     */
    public long encode(long value1, long value2) {
        return (value1 << 32) | (value2 & 0xFFFFFFFFL);  
    }
    
    /*
     * Extract the two longs
     */
    public long[] decode(long result) {
        long result1 = result >> 32;
        long result2 = result & 0xFFFFFFFFL;
        return new long[] {result1, result2};
    }

    /*
     * Compare two encoded values
     */
    public boolean sameEncodedValue(long value1, long value2) {

        long[] decoded_value1 = decode(value1);
        long[] decoded_value2 = decode(value2);

        boolean exactly_same = decoded_value1[0] == decoded_value2[0] && decoded_value1[1] == decoded_value2[1]; // (0_1) == (0_1)
        boolean exchanged_same = decoded_value1[1] == decoded_value2[0] && decoded_value1[0] == decoded_value2[1]; // (0_1) == (1_0)

        return exactly_same || exchanged_same; // (0_1) == (0_1) || (0_1) == (1_0)
    }
    
    /*
     * Convert ArrayList<Long> to long[]
     */
    public long[] arrayListToNativeArray(ArrayList<Long> arrayList) {
        return arrayList.stream().mapToLong(Long::longValue).toArray();
    }

    /*
     * Convert long[] to ArrayList<Long> (for dynamic array)
     */
    public ArrayList<Long> nativeArrayToArrayList(long[] nativeArray) {
        return Arrays.stream(nativeArray).boxed().collect(Collectors.toCollection(ArrayList::new));
    }

    /*
     * Remove duplicates from ArrayList<T>
     */
    public <T> ArrayList<T> removeDuplicates(ArrayList<T> list) {
        return list.stream().distinct().collect(Collectors.toCollection(ArrayList::new));
    }
     
    /* Each node will have this value-schema during pregel computation */
    @Override
    public PregelSchema schema(WritePathsMiningPregelConfig config) {

        var schema = new PregelSchema.Builder()
            .add(PATHS, ValueType.LONG_ARRAY);

        return schema.build();
    }
    
    /* Called in the beginning of the first superstep of the Pregel computation and allows initializing node values */
    @Override
    public void init(InitContext<WritePathsMiningPregelConfig> context) {
        context.setNodeValue(PATHS, new long[]{});
    }

    public void sentToAllNeighbors(ComputeContext<WritePathsMiningPregelConfig> context, ArrayList<Long> messages) {
        var neighbors = neighbors_map.get(context.nodeId());

        for (var neighbor: neighbors) {
            for (var message: messages) {
                context.sendTo(neighbor, message);
            }
        }
    }

    public ArrayList<ArrayList<Long>> extractPreviousPaths(long[] previous_messages, long identifier) {
        var previous_paths = new ArrayList<ArrayList<Long>>();
        var path_buffer = new ArrayList<Long>();
        for (var previous_message: previous_messages) {
            if (previous_message == identifier && !path_buffer.isEmpty()) {
                previous_paths.add(path_buffer.stream().collect(Collectors.toCollection(ArrayList::new)));
                path_buffer.clear();
                continue;
            }
            path_buffer.add(previous_message);
        }
        return previous_paths;
    }

    public ArrayList<String> printEncodedMessageList(ArrayList<Long> messageList, long identifier) {
        var _messageList = new ArrayList<String>();
        String temp = "";
        boolean isIdentifier = false;
        for (var message: messageList) {
            if (message == identifier) {
                _messageList.add(temp + ", -1, ");
                temp = "";
                isIdentifier = true;
                continue;
            }
            var decoded_message = decode(message);
            temp += decoded_message[0];
            temp += decoded_message[1];
        }
        if (!isIdentifier) {_messageList.add(temp);}
        return _messageList;
    }

    public void writeToFile(ComputeContext<WritePathsMiningPregelConfig> context, long[] paths) {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(filePath))) {
            dos.writeInt(paths.length); // Write the length of the array first
            for (long value : paths) {
                dos.writeLong(value);
            }
        } catch (IOException e) {
            context.logDebug("Error while writing to file: " + e.getMessage());
        }
    }

    /* Called for each node in every superstep */
    @Override
    public void compute(ComputeContext<WritePathsMiningPregelConfig> context, Messages messages) {
        var nodeId = context.nodeId();
        var nodeOriginalId = context.toOriginalId(); // for showing correct IDs in the output
        int superstep = context.superstep();
        var IDENTIFIER = context.config().identifier();
        var IS_ENCODED_OUTPUT = context.config().isEncodedOutput();

        File file = new File(context.config().writePath(), nodeId + ".txt");

        // First superstep
        if (context.isInitialSuperstep() && superstep == PathFindingPhase.INIT_PATH.step) {
            context.setNodeValue(PATHS, new long[] {nodeOriginalId, IDENTIFIER});
            context.sendToNeighbors(nodeId); // send node_id to all neighbors (to let them know where they got this message from)

            try {
                if (file.exists()) { file.delete(); } else { file.getParentFile().mkdirs(); file.createNewFile(); }
            } catch (IOException e) {
                context.logDebug("Error while creating file: " + e.getMessage());
            }
        } 
        else if (superstep == PathFindingPhase.CONNECT_NEIGHBORS_PATH.step) {
            var _messages = new ArrayList<Long>();
            for (var msg: messages) { _messages.add(msg.longValue()); }

            var outGoingNeighbors = new ArrayList<Long>(); // outgoing neighbors
            context.forEachNeighbor(outGoingNeighbors::add);
            var incomingNeighbors = _messages.stream().map(Long::valueOf).collect(Collectors.toCollection(ArrayList::new)); // incoming neighbors
            
            var neighbors = new ArrayList<Long>(); // all neighbors
            neighbors.addAll(outGoingNeighbors);
            neighbors.addAll(incomingNeighbors);

            neighbors = removeDuplicates(neighbors);
            neighbors.removeIf(neighbor_node_id -> neighbor_node_id == nodeId); // remove self-loop

            neighbors_map.put(nodeId, neighbors); // save all neighbors (incoming + outgoing) in a hashmap
            
            var path_list = new ArrayList<Long>();
            if (IS_ENCODED_OUTPUT) {
                for (var neighbor_id: neighbors) {
                    var from_node_id = nodeId;
                    var to_node_id = neighbor_id;
                    path_list.add(encode(from_node_id, to_node_id));
                }
                // separate each message (path) with a unique identifier
                path_list = path_list.stream().flatMap(n -> Stream.of(n, IDENTIFIER)).collect(Collectors.toCollection(ArrayList::new));
            } else {
                for (var neighbor_id: neighbors) {
                    path_list.add(nodeOriginalId);
                    path_list.add(context.toOriginalId(neighbor_id));
                    path_list.add(IDENTIFIER);
                }
            }

            if (neighbors.isEmpty()) { // no neighbors
                context.voteToHalt();
                return;
            }

            sentToAllNeighbors(context, neighbors);
            context.setNodeValue(PATHS, arrayListToNativeArray(path_list)); // update paths internally (for each node)
        } else if (superstep >= PathFindingPhase.COMPUTE_PATH.step) {
            // if no message is received, then halt
            if (messages.isEmpty()) {
                context.voteToHalt();
                return;
            }

            // iterate over all messages (coming from all the neighbors) and add them all to PATH 
            // (NOTE: each superstep is separated via some unique identifier)
            var _messages = new ArrayList<Long>();
            for (var msg: messages) { _messages.add(msg.longValue()); }
            //@TODO: do we have to remove the duplicates here as well? I don't think so !!!
            sentToAllNeighbors(context, _messages); // simply forward all messages that you received to all neighbors
            
            var previous_messages = context.longArrayNodeValue(PATHS);
            var previous_paths = extractPreviousPaths(previous_messages, IDENTIFIER);
            
            var new_path = new ArrayList<Long>();

            if (IS_ENCODED_OUTPUT) {
                for (var previous_path: previous_paths) {
                    
                    var last_element = previous_path.get(previous_path.size() - 1);
                    long[] decoded_last_element = decode(last_element);
                    long last_element_from_node = decoded_last_element[0];
                    long last_element_to_node = decoded_last_element[1];
                    
                    var message_list = neighbors_map.get(last_element_to_node);
                    if (message_list != null) {
                        
                        var temp = new ArrayList<Long>();
                        
                        for (var message: message_list) {
                            // A-B-A => disallow this path since this doesn't make any sense for a pattern p.o.v
                            if (message == last_element_from_node) { continue; }
                            long value = encode(last_element_to_node, message);
                            temp.addAll(previous_path);
                            temp.add(value);
                            temp.add(IDENTIFIER);
                        }
    
                        new_path.addAll(temp);
                        temp.clear();
                    }
                }
            } else {
                for (var previous_path: previous_paths) {
                    
                    if (previous_path.size() < 2) {continue;} // invalid path -> at least, it should contain 2 elements 

                    var last_element = previous_path.get(previous_path.size() - 1);
                    var second_last_element = previous_path.get(previous_path.size() - 2);

                    var message_list = neighbors_map.get(context.toInternalId(last_element));


                    
                    if (message_list != null) {
                        
                        var temp = new ArrayList<Long>();
                        
                        for (var msg: message_list) {
                            var message = context.toOriginalId(msg);
                            // A-B-A => disallow this path since this doesn't make any sense for a pattern p.o.v
                            if (message == second_last_element) { continue; }
                            temp.addAll(previous_path);
                            temp.add(message);
                            temp.add(IDENTIFIER);
                        }
    
                        new_path.addAll(temp);
                        temp.clear();
                    }
                }
            }

            if (new_path.isEmpty()) {
                context.voteToHalt();
                return;
            }

            context.setNodeValue(PATHS, arrayListToNativeArray(new_path)); // update paths internally (for each node)
        }
    }

    // @Override
    // public boolean masterCompute(MasterComputeContext<WritePathsMiningPregel.WritePathsMiningPregelConfig> context) {
    //     return context.superstep() >= 4; // stop after 2 supersteps
    // }

    enum PathFindingPhase {
        INIT_PATH(0),
        CONNECT_NEIGHBORS_PATH(1),
        COMPUTE_PATH(2);

        final int step;

        PathFindingPhase(int i) {
            step = i;
        }
    }

    @ValueClass
    @Configuration("WritePathsMiningPregelConfigImpl")
    @SuppressWarnings("immutables:subtype")
    public interface WritePathsMiningPregelConfig extends PregelProcedureConfig, SeedConfig {

        @Override
        default boolean isAsynchronous() {
            return false;
        }

        @Value.Default
        @Configuration.Key("isEncodedOutput")
        default boolean isEncodedOutput() {
            return false;
        }
        
        @Value.Default
        @Configuration.Key("identifier")
        default long identifier() {
            return -1;
        }

        @Value.Default
        @Configuration.Key("writePath")
        default String writePath() {
            return "/Users/mohammadzainabbas/Desktop/paths";
        }
        
        static WritePathsMiningPregelConfig of(CypherMapWrapper userInput) {
            return new WritePathsMiningPregelConfigImpl(userInput);
        }
    }
}
