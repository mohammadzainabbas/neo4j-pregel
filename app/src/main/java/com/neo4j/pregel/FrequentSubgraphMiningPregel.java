// package com.neo4j.pregel;

// import org.immutables.value.Value;
// import org.apache.commons.lang3.mutable.MutableLong;
// import org.neo4j.gds.annotation.Configuration;
// import org.neo4j.gds.annotation.ValueClass;
// import org.neo4j.gds.api.nodeproperties.ValueType;
// import org.neo4j.gds.beta.pregel.Messages;
// import org.neo4j.gds.beta.pregel.PregelComputation;
// import org.neo4j.gds.beta.pregel.PregelProcedureConfig;
// import org.neo4j.gds.beta.pregel.PregelSchema;
// import org.neo4j.gds.beta.pregel.Reducer;
// import org.neo4j.gds.beta.pregel.annotation.GDSMode;
// import org.neo4j.gds.beta.pregel.annotation.PregelProcedure;
// import org.neo4j.gds.beta.pregel.context.ComputeContext;
// import org.neo4j.gds.beta.pregel.context.InitContext;
// import org.neo4j.gds.beta.pregel.context.MasterComputeContext;
// import org.neo4j.gds.config.SeedConfig;
// import org.neo4j.gds.core.CypherMapWrapper;

// import java.util.ArrayList;
// import java.util.List;
// import java.util.Arrays;
// import java.util.Collections;
// import java.util.HashMap;
// import java.util.Optional;
// import java.util.stream.Collectors;

// @PregelProcedure(name = "esilv.pregel.fsm", modes = { GDSMode.STREAM, GDSMode.MUTATE }, description = "Frequent Pattern Mining :: Neo4j - Approximate Frequent Subgraph Mining with Pregel")
// public class FrequentSubgraphMiningPregel implements PregelComputation<FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig> {

//     public static final String FSM = "fsm";
//     public static final String G_ID = "gid";
//     public static final String POS_X = "pos_x";
//     public static final String POS_Y = "pos_y";
//     public static final String RATING = "rating";
//     public static final String NODE_INFO = "node_info";
    
//     public static final long IDENTIFIER = -1;

    
//     public long encode(long value1, long value2) {
//         // Combine the two longs into one
//         return (value1 << 32) | (value2 & 0xFFFFFFFFL);  
//     }
    
//     public long[] decode(long result) {
//         // Extract the two longs
//         long result1 = result >> 32;
//         long result2 = result & 0xFFFFFFFFL;
//         return new long[] {result1, result2};
//     }
    
//     // convert ArrayList<Long> back to long[]
//     public long[] arrayListToNativeArray(ArrayList<Long> arrayList) {
//         return arrayList.stream().mapToLong(Long::longValue).toArray();
//     }

//     // long[] to ArrayList<Long> (for dynamic array)
//     public ArrayList<Long> nativeArrayToArrayList(long[] nativeArray) {
//         return Arrays.stream(nativeArray).boxed().collect(Collectors.toCollection(ArrayList::new));
//     }
    
//     /* Each node will have this value-schema during pregel computation */
//     @Override
//     public PregelSchema schema(FrequentSubgraphMiningPregelConfig config) {

//         var schema = new PregelSchema.Builder()
//             .add(NODE_INFO, ValueType.LONG_ARRAY) // [degree, orginal_id] 
//             .add(G_ID, ValueType.LONG)
//             .add(POS_X, ValueType.DOUBLE)
//             .add(POS_Y, ValueType.DOUBLE)
//             .add(RATING, ValueType.DOUBLE);

//         for (var i = 0; i < config.maxIterations(); i++) {
//             schema.add(FSM + i, ValueType.LONG_ARRAY); // every step has its own FSM
//         }

//         return schema.build();
//     }
    
//     /* Called in the beginning of the first superstep of the Pregel computation and allows initializing node values */
//     @Override
//     public void init(InitContext<FrequentSubgraphMiningPregelConfig> context) {

//         long[] nodeInfo = {context.degree(), context.toOriginalId()};
//         context.setNodeValue(NODE_INFO, nodeInfo);
        
//         var nodeId = context.nodeId();
//         var nodeProperties = new String[] { "pos_x", "pos_y", "rating" };
        
//         for (var property: nodeProperties) {
//             var _property = context.nodeProperties(property);
//             if (_property == null) {
//                 context.logMessage("Property '" + property + "' not found");
//                 continue;
//             }
//             context.setNodeValue(property, _property.doubleValue(nodeId));
//         }
        
//         if (context.nodeProperties(G_ID) != null) {
//             context.setNodeValue(G_ID, context.nodeProperties(G_ID).longValue(nodeId));
//         }

//         long[] empty_fsm_array = {};
//         context.setNodeValue(FSM, empty_fsm_array);
//     }

//     /* Called for each node in every superstep */
//     @Override
//     public void compute(ComputeContext<FrequentSubgraphMiningPregelConfig> context, Messages messages) {
//         var nodeId = context.nodeId();
//         var nodeOriginalId = context.toOriginalId(); // for showing correct IDs in the output
//         int superstep = context.superstep();
//         var stepKey = FSM + superstep;

//         // First superstep
//         if (context.isInitialSuperstep()) {
//             context.setNodeValue(stepKey, new long[] {nodeOriginalId, IDENTIFIER});
//         } 
//         else {
//             // iterate over all messages (coming from all the neighbors) and add them all to FSM 
//             // (NOTE: each superstep is separated via some unique identifier)

//             var previousKey = FSM + (superstep - 1);
            
//             HashMap<Long, ArrayList<Long>> messages_map = new HashMap<Long, ArrayList<Long>>();
            
//             var messages_list = new ArrayList<Long>();
//             for (var msg: messages) { // @TODO: recheck this logic (to build the correct message list)
//                 long[] message = decode(msg.longValue());
//                 var from_node_id = message[0];
//                 var to_node_id = message[1];
                
//                 messages_map.computeIfAbsent(from_node_id, k -> new ArrayList<Long>()).add(to_node_id); // add to the hashmap's array list against the key
//             }
            
//             var new_fsm = new ArrayList<Long>();
//             var fsm_buffer = new ArrayList<Long>();
//             var previous_messages = context.longArrayNodeValue(previousKey);

//             for (int i = 0; i < previous_messages.length; i++) {
//                 long previous_message = previous_messages[i];
                
//                 if (previous_messages[i + 1] == IDENTIFIER) {
//                     long[] decoded_previous_message = decode(previous_message);
//                     long previous_message_from_node = decoded_previous_message[0];
//                     long previous_message_to_node = decoded_previous_message[1];

//                     var message_list = messages_map.get(previous_message_to_node);
//                     if (message_list != null) {
                        
//                         var temp = new ArrayList<Long>();
                        
//                         for (var message: message_list) {
                            
//                             temp.addAll(fsm_buffer);

//                             long value = encode(previous_message_to_node, message);

//                             temp.add(value);
//                             temp.add(IDENTIFIER);
//                         }

//                         new_fsm.addAll(temp);
//                         fsm_buffer.clear();
//                         temp.clear();
//                         continue;
//                     }
//                 }                
//                 fsm_buffer.add(previous_message);
//                 if (previous_message == IDENTIFIER) {
//                     new_fsm.addAll(fsm_buffer);
//                     fsm_buffer.clear();
//                 }
//         }

//         if (newMessage) {
//             // convert ArrayList<Long> back to long[]
//             long[] new_fsms = arrayListToNativeArray(new_fsm);
//             context.setNodeValue(stepKey, new_fsms); // update paths internally (for each node)
//             for (var message: new_fsms) {
//                 context.sendToNeighbors(message); // send node_id to all neighbors (to let them know where they got this message from)
//             }
//             context.sendToNeighbors(nodeId); // send node_id to all neighbors (to let them know where they got this message from)
//         } 
//         else {
//             context.voteToHalt();
//         }
//     }

//     // @Override
//     // public Optional<Reducer> reducer() {
//     //     return Optional.of(new Reducer.Sum());
//     // }

//     // @Override
//     // public boolean masterCompute(MasterComputeContext<FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig> context) {
//     //     return context.superstep() >= 2; // stop after 2 supersteps
//     // }

//     @ValueClass
//     @Configuration("FrequentSubgraphMiningPregelConfigImpl")
//     @SuppressWarnings("immutables:subtype")
//     public interface FrequentSubgraphMiningPregelConfig extends PregelProcedureConfig, SeedConfig {

//         @Override
//         default boolean isAsynchronous() {
//             return true;
//         }

//         @Value.Default
//         @Configuration.Key("maxRepeatNodes")
//         default long maxRepeatNodes() {
//             return 0;
//         }

//         @Value.Default
//         @Configuration.Key("withRepeition")
//         default boolean withRepeition() {
//             return false;
//         }

//         static FrequentSubgraphMiningPregelConfig of(CypherMapWrapper userInput) {
//             return new FrequentSubgraphMiningPregelConfigImpl(userInput);
//         }
//     }
// }
