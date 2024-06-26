// package com.neo4j.pregel;

// import org.immutables.value.Value;
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

// import java.util.Set;
// import java.util.HashSet;
// import java.util.Optional;

// @PregelProcedure(name = "esilv.pregel.fsm", modes = { GDSMode.STREAM, GDSMode.MUTATE }, description = "Frequent Pattern Mining :: Neo4j - Approximate Frequent Subgraph Mining with Pregel")
// public class FrequentSubgraphMiningPregel implements PregelComputation<FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig> {

//     private static final String FSM = "fsm";
//     private static final String F = "F";
//     private static final String F1 = "F1";
//     private static final String Sk = "Sk";
//     private static final String Ext = "Ext";
//     private static final String Fk1 = "Fk1";
//     private static final String Fw_i = "Fw_i";

//     private static boolean weighted;

//     /* Each node will have this value-schema during pregel computation */
//     @Override
//     public PregelSchema schema(FrequentSubgraphMiningPregelConfig config) {
//         return new PregelSchema.Builder()
//                 .add(FSM, ValueType.DOUBLE)
//                 .add(F, ValueType.LONG)
//                 .add(F1, ValueType.LONG)
//                 .add(Sk, ValueType.LONG)
//                 .add(Ext, ValueType.LONG)
//                 .add(Fk1, ValueType.LONG)
//                 .add(Fw_i, ValueType.LONG)
//                 .build();
//     }
    
//     /* Called in the beginning of the first superstep of the Pregel computation and allows initializing node values */
//     @Override
//     public void init(InitContext<FrequentSubgraphMiningPregelConfig> context) {
//         var initialValue = context.config().seedProperty() != null
//                 ? context.nodeProperties(context.config().seedProperty()).doubleValue(context.nodeId())
//                 : 1.0 / context.nodeCount();
//         context.setNodeValue(FSM, initialValue);

//         weighted = context.config().hasRelationshipWeightProperty();
//     }

//     public long calculateFrequentSize1Subgraphs(ComputeContext<FrequentSubgraphMiningPregelConfig> context) {
//         return 0;
//     }

//     public Set<Subgraph> ApFSMEXTEND(Set<Subgraph> Fk, Graph Gprime, Set<Embedding> S_Fk) {
//         Set<Subgraph> Fk1 = new HashSet<>();

//         for (Embedding e : S_Fk) {
//             Subgraph sPrime = e.getSubgraph();
//             Set<Subgraph> Ck1 = getCandidateSubgraphs(); // Replace with actual logic

//             for (Subgraph c_k1 : Ck1) {
//                 if (isParentOf(Fk, c_k1)) { // Replace with actual logic
//                     for (Extension ext : getExtensions(Gprime, e)) { // Replace with actual logic
//                         if (ext.isEdgeExtension()) {
//                             Vertex v = ext.getVertex();
//                             if (isFrequent(v, sPrime)) { // Replace with actual logic
//                                 Ck1.add(ext.getExtendedSubgraph());
//                             }
//                         } else if (ext.isVertexExtension()) {
//                             Vertex v_sPrime = ext.getVertex();
//                             Ck1.add(ext.getExtendedSubgraph());
//                         }
//                     }
//                 }
//                 Fk1.addAll(Ck1);
//             }
//         }
//         return Fk1; // All size k+1 subgraphs
//     }

//     /* Called for each node in every superstep */
//     @Override
//     public void compute(ComputeContext<FrequentSubgraphMiningPregelConfig> context, Messages messages) {
//         if (context.isInitialSuperstep()) {
//             // Initialization step
//             context.setNodeValue(F, 0);
//             context.setNodeValue(F1, calculateFrequentSize1Subgraphs(context));
//             context.setNodeValue(Sk, calculateEmbeddings(context.getNodeValue(F1)));
//         } else {
//             // Main computation step
//             context.setNodeValue(Sk, calculateEmbeddings(context.getNodeValue(Fk1)));
//             context.setNodeValue(Ext, calculateExtensions(context.getNodeValue(Sk)));
//             context.setNodeValue(Fk1, ApFSMEXTEND(context.getNodeValue(Fk1), context.getNodeValue(Sk)));

//             while (context.getNodeValue(Fk1) != 0) {
//                 context.setNodeValue(Fw_i, LocalPrunning(context.getNodeValue(Fk1)));
//             }

//             context.setNodeValue(Fk1, calculateDistinctSubgraphImages(context.getNodeValue(Fw_i)));
//         }
//     }

//     @Override
//     public Optional<Reducer> reducer() {
//         return Optional.of(new Reducer.Sum());
//     }

//     @Override
//     public double applyRelationshipWeight(double nodeValue, double relationshipWeight) {
//         // ! assuming normalized relationshipWeights (sum of outgoing edge weights = 1
//         // and none negative weights)
//         return nodeValue * relationshipWeight;
//     }

//     @Override
//     public boolean masterCompute(MasterComputeContext<FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig> context) {
//         while (context.getNodeValue(Fk1) != 0) {
//             context.setNodeValue(F, GlobalPrunning(context.getNodeValue(Fk1)));
//         }
//         return false; // continue computation
//     }

//     @ValueClass
//     @Configuration("FrequentSubgraphMiningPregelConfigImpl")
//     @SuppressWarnings("immutables:subtype")
//     public interface FrequentSubgraphMiningPregelConfig extends PregelProcedureConfig, SeedConfig {
//         @Value.Default
//         default double dampingFactor() {
//             return 0.85;
//         }

//         static FrequentSubgraphMiningPregelConfig of(CypherMapWrapper userInput) {
//             return new FrequentSubgraphMiningPregelConfigImpl(userInput);
//         }
//     }
// }
