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
import java.util.stream.Collectors;
import java.util.stream.Stream;

@PregelProcedure(name = "esilv.pregel.find_paths", modes = { GDSMode.STREAM, GDSMode.MUTATE }, description = "Paths Mining with Pregel (find all paths of length 'max_iteration') - Frequent Pattern Mining :: Neo4j")
public class PathsMiningPregel implements PregelComputation<PathsMiningPregel.PathsMiningPregelConfig> {

    // INTERNALS
    public static final boolean USE_ORIGINAL_IDS = false;
    public static final String PATH = "path_";
    public static final String G_ID = "gid";
    public static final String POS_X = "pos_x";
    public static final String POS_Y = "pos_y";
    public static final String RATING = "rating";
    public static final String NODE_INFO = "node_info";
    public static final String NEIGHBORS_IDS = "neighbor_ids"; // to save all neighbors (incoming + outgoing)
    
    //@TODO: maybe add one check for CONTAIN_SELF_LOOP and one variable for NO_OF_SELF_LOOPS
    // Might be helpful to add this (self-loop) info at the end when we make the paths
    // Can be done in master-compute function

    public static final long IDENTIFIER = -1;
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
        // return list.stream().distinct().collect(Collectors.toList());
    }
     
    /* Each node will have this value-schema during pregel computation */
    @Override
    public PregelSchema schema(PathsMiningPregelConfig config) {

        var schema = new PregelSchema.Builder()
            .add(NODE_INFO, ValueType.LONG_ARRAY) // [degree, orginal_id] 
            .add(G_ID, ValueType.LONG)
            .add(POS_X, ValueType.DOUBLE)
            .add(POS_Y, ValueType.DOUBLE)
            .add(RATING, ValueType.DOUBLE)
            .add(NEIGHBORS_IDS, ValueType.LONG_ARRAY);

        for (var i = 0; i < config.maxIterations(); i++) {
            schema.add(PATH + i, ValueType.LONG_ARRAY); // every step has its own PATH
        }

        return schema.build();
    }
    
    /* Called in the beginning of the first superstep of the Pregel computation and allows initializing node values */
    @Override
    public void init(InitContext<PathsMiningPregelConfig> context) {

        long[] nodeInfo = {context.degree(), context.toOriginalId()};
        context.setNodeValue(NODE_INFO, nodeInfo);
        
        var nodeId = context.nodeId();
        var nodeProperties = new String[] { "pos_x", "pos_y", "rating" };
        
        for (var property: nodeProperties) {
            var _property = context.nodeProperties(property);
            if (_property == null) {
                context.logMessage("Property '" + property + "' not found");
                continue;
            }
            context.setNodeValue(property, _property.doubleValue(nodeId));
        }
        
        if (context.nodeProperties(G_ID) != null) {
            context.setNodeValue(G_ID, context.nodeProperties(G_ID).longValue(nodeId));
        }

        for (var i = 0; i < context.config().maxIterations(); i++) {
            context.setNodeValue(PATH + i, new long[]{}); // initialize all paths to empty array
        }
        context.setNodeValue(NEIGHBORS_IDS, new long[]{});
    }

    public void sentToAllNeighbors(ComputeContext<PathsMiningPregelConfig> context, ArrayList<Long> messages) {
        long[] neighbors = context.longArrayNodeValue(NEIGHBORS_IDS);

        for (var neighbor: neighbors) {
            for (var message: messages) {
                context.sendTo(neighbor, message);
            }
        }
    }

    public ArrayList<ArrayList<Long>> extractPreviousPaths(long[] previous_messages) {
        var previous_paths = new ArrayList<ArrayList<Long>>();
        var path_buffer = new ArrayList<Long>();
        for (var previous_message: previous_messages) {
            if (previous_message == IDENTIFIER && !path_buffer.isEmpty()) {
                previous_paths.add(path_buffer.stream().collect(Collectors.toCollection(ArrayList::new)));
                path_buffer.clear();
                continue;
            }
            path_buffer.add(previous_message);
        }
        return previous_paths;
    }

    public ArrayList<String> printEncodedMessageList(ArrayList<Long> messageList) {
        var _messageList = new ArrayList<String>();
        String temp = "";
        boolean isIdentifier = false;
        for (var message: messageList) {
            if (message == IDENTIFIER) {
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

    /* Called for each node in every superstep */
    @Override
    public void compute(ComputeContext<PathsMiningPregelConfig> context, Messages messages) {
        var nodeId = context.nodeId();
        var nodeOriginalId = context.toOriginalId(); // for showing correct IDs in the output
        int superstep = context.superstep();
        var stepKey = PATH + superstep;

        // First superstep
        if (context.isInitialSuperstep() && superstep == PathFindingPhase.INIT_PATH.step) {
            context.setNodeValue(stepKey, new long[] {nodeOriginalId, IDENTIFIER});

            // unique messages (to avoid duplicate paths)
            // neighbors.forEach((LongProcedure) neighbor_node_id -> context.sendTo(neighbor_node_id, nodeOriginalId));
            context.sendToNeighbors(nodeId); // send node_id to all neighbors (to let them know where they got this message from)
        } 
        else if (superstep == PathFindingPhase.CONNECT_NEIGHBORS_PATH.step) {
            var _messages = new ArrayList<Long>();
            for (var msg: messages) { _messages.add(msg.longValue()); }
            // _messages = removeDuplicates(_messages);

            var outGoingNeighbors = new ArrayList<Long>(); // outgoing neighbors
            context.forEachNeighbor(outGoingNeighbors::add);
            var incomingNeighbors = _messages.stream().map(Long::valueOf).collect(Collectors.toCollection(ArrayList::new)); // incoming neighbors
            
            var neighbors = new ArrayList<Long>(); // all neighbors
            neighbors.addAll(outGoingNeighbors);
            neighbors.addAll(incomingNeighbors);

            neighbors = removeDuplicates(neighbors);
            neighbors.removeIf(neighbor_node_id -> neighbor_node_id == nodeId); // remove self-loop

            context.setNodeValue(NEIGHBORS_IDS, arrayListToNativeArray(neighbors)); // save all neighbors (incoming + outgoing)

            var messages_list = new ArrayList<Long>();
            // Remove duplicate messages (multiple links between two nodes i.e: 0 -> 1, 0 -> 1)
            // Add only one link in case of duplicate links (since duplicate links doesn't effect the path)
            // _messages = removeDuplicates(_messages);
            for (var neighbor_id: neighbors) {
                var from_node_id = USE_ORIGINAL_IDS ? nodeOriginalId : nodeId;
                var to_node_id = USE_ORIGINAL_IDS ? context.toOriginalId(neighbor_id) : neighbor_id;

                var value = encode(from_node_id, to_node_id);
                messages_list.add(value);
            }

            if (messages_list.isEmpty()) { // no neighbors
                context.voteToHalt();
                return;
            }

            // send encoded (from_node_id, to_node_id) to all neighbors (to let them know where they got this message from
            sentToAllNeighbors(context, messages_list);
            // separate each message (path) with a unique identifier
            messages_list = messages_list.stream().flatMap(n -> Stream.of(n, IDENTIFIER)).collect(Collectors.toCollection(ArrayList::new));
            context.setNodeValue(stepKey, arrayListToNativeArray(messages_list)); // update paths internally (for each node)
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
            //@TODO: do we have to remove the duplicates here as well?
            sentToAllNeighbors(context, _messages); // simply forward all messages that you received to all neighbors

            var previousKey = PATH + (superstep - 1);
            HashMap<Long, ArrayList<Long>> messages_map = new HashMap<Long, ArrayList<Long>>(); // to keep track of [where this message was sent from] -> [to which node]
            
            for (var msg: _messages) {
                long[] message = decode(msg);
                var from_node_id = message[0];
                var to_node_id = message[1];
                
                messages_map.computeIfAbsent(from_node_id, k -> new ArrayList<Long>()).add(to_node_id); // add to the hashmap's array list against the key
            }
            
            var previous_messages = context.longArrayNodeValue(previousKey);
            var previous_paths = extractPreviousPaths(previous_messages);
            
            var new_path = new ArrayList<Long>();

            for (var previous_path: previous_paths) {
                
                var last_element = previous_path.get(previous_path.size() - 1);
                long[] decoded_last_element = decode(last_element);
                long last_element_from_node = decoded_last_element[0];
                long last_element_to_node = decoded_last_element[1];
                
                var message_list = messages_map.get(last_element_to_node);
                if (message_list != null) {
                    message_list = removeDuplicates(message_list); // remove duplicates (same neighbor info)
                    
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

            if (new_path.isEmpty()) {
                context.voteToHalt();
                return;
            }

            context.setNodeValue(stepKey, arrayListToNativeArray(new_path)); // update paths internally (for each node)
        }
    }

    @Override
    public boolean masterCompute(MasterComputeContext<PathsMiningPregel.PathsMiningPregelConfig> context) {
        return context.superstep() >= 4; // stop after 2 supersteps
    }

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
    @Configuration("PathsMiningPregelConfigImpl")
    @SuppressWarnings("immutables:subtype")
    public interface PathsMiningPregelConfig extends PregelProcedureConfig, SeedConfig {

        @Override
        default boolean isAsynchronous() {
            return false;
        }

        @Value.Default
        @Configuration.Key("useOriginalIds")
        default boolean useOriginalIds() {
            return false;
        }

        @Value.Default
        @Configuration.Key("identifier")
        default long identifier() {
            return -1;
        }

        static PathsMiningPregelConfig of(CypherMapWrapper userInput) {
            return new PathsMiningPregelConfigImpl(userInput);
        }
    }
}
