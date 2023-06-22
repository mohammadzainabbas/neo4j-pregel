<a id="algorithms-pregel-api" />

# Pregel API

This chapter provides documentation for the Pregel API in the Neo4j Graph Data Science library.

<a id="algorithms-pregel-api-intro" />

## Introduction

Pregel is a vertex-centric computation model to define your own algorithms via a user-defined _compute_ function.
Node values can be updated within the compute function and represent the algorithm result.
The input graph contains default node values or node values from a graph projection.

The compute function is executed in multiple iterations, also called _supersteps_.
In each superstep, the compute function runs for each node in the graph.
Within that function, a node can receive messages from other nodes, typically its neighbors.
Based on the received messages and its currently stored value, a node can compute a new value.
A node can also send messages to other nodes, typically its neighbors, which are received in the next superstep.
The algorithm terminates after a fixed number of supersteps or if no messages are being sent between nodes.

A Pregel computation is executed in parallel.
Each thread executes the compute function for a batch of nodes.

For more information about Pregel, have a look at the [original paper](https://kowshik.github.io/JPregel/pregel_paper.pdf).

To implement your own Pregel algorithm, the Graph Data Science library provides a Java API, which is described below.

The introduction of a new Pregel algorithm can be separated in two main steps.
First, we need to implement the algorithm using the Pregel Java API.
Second, we need to expose the algorithm via a Cypher procedure to make use of it.

For an example on how to expose a custom Pregel computation via a Neo4j procedure, have a look at the [Pregel examples](https://github.com/neo4j/graph-data-science/tree/master/examples/pregel-example/src/main/java/org/neo4j/gds/beta/pregel).

<a id="algorithms-pregel-api-java" />

## Pregel Java API

The Pregel Java API allows us to easily build our own algorithm by implementing several interfaces.

<a id="algorithms-pregel-api-java-computation" />

### Computation

The first step is to implement the `org.neo4j.gds.beta.pregel.PregelComputation` interface.
It is the main interface to express user-defined logic using the Pregel framework.

> The Pregel computation

```java
public interface PregelComputation<C extends PregelConfig> {
    // The schema describes the node property layout.
    PregelSchema schema();
    // Called in the first superstep and allows initializing node state.
    default void init(PregelContext.InitContext<C> context) {}
    // Called in each superstep for each node and contains the main logic.
    void compute(PregelContext.ComputeContext<C> context, Pregel.Messages messages);
    // Called exactly once at the end of each superstep by a single thread.
    default void masterCompute(MasterComputeContext<C> context) {}
    // Used to combine all messages sent to a node to a single value.
    default Optional<Reducer> reducer() {
        return Optional.empty();
    }
    // Used to apply a relationship weight on a message.
    default double applyRelationshipWeight(double message, double relationshipWeight);
    // Used to close any opened resources, such as ThreadLocals
    default void close() {}
}
```

Pregel node values are composite values.
The `schema` describes the layout of that composite value.
Each element of the schema can represent either a primitive long or double value as well as arrays of those.
The element is uniquely identified by a key, which is used to access the value during the computation.
Details on schema declaration can be found in the [dedicated section](#algorithms-pregel-api-schema)

The `init` method is called in the beginning of the first superstep of the Pregel computation and allows initializing node values.
The interface defines an abstract `compute` method, which is called for each node in every superstep.
Algorithm-specific logic is expressed within the `compute` method.
The context parameter provides access to node properties of the projected graph and the algorithm configuration.

The `compute` method is called individually for each node in every superstep as long as the node receives messages or has not voted to halt yet.
Since an implementation of `PregelComputation` is stateless, a node can only communicate with other nodes via messages.
In each superstep, a node receives `messages` and can send new messages via the `context` parameter.
Messages can be sent to neighbor nodes or any node if its identifier is known.

The `masterCompute` method is called exactly once at the end of each superstep.
It is executed by a single thread and can be used to modify a global state based on the current computation state.
Details on using a master computation can be found in the [dedicated section](#algorithms-pregel-api-master-compute).

An optional `reducer` can be used to define a function that is being applied on messages sent to a single node.
It takes two arguments, the current value and a message value, and produces a new value.
The function is called repeatedly, once for each message that is sent to a node.
Eventually, only one message will be received by the node in the next superstep.
By defining a reducer, memory consumption and computation runtime can be improved significantly.
Check the [dedicated section](#algorithms-pregel-api-reducer) for more details.

The `applyRelationshipWeight` method can be used to modify the message based on a relationship property.
If the input graph has no relationship properties, i.e. is unweighted, the method is skipped.

The `close` method can be used to close any resources opened as part of the implementation.
This includes ThreadLocals, file handles, network connections, or anything else that should not be kept alive after the algorithm has finished computing.

<a id="algorithms-pregel-api-schema" />

### Pregel schema

In Pregel, each node is associated with a value which can be accessed from within the `compute` method.
The value is typically used to represent intermediate computation state and eventually the computation result.
To represent complex state, the node value is a composite type which consists of one or more named values.
From the perspective of the `compute` function, each of these values can be accessed by its name.

When implementing a `PregelComputation`, one must override the `schema()` method.
The following example shows the simplest possible example:

```java
PregelSchema schema() {
    return PregelSchema.Builder().add("foobar", ValueType.LONG).build();
}
```

The node value consists of a single value named `foobar` which is of type `long`.
A node value can be of any GDS-supported type, i.e. `long`, `double`, `long[]`, `double[]` and `float[]`.

We can add an arbitrary number of values to the schema:

```java
PregelSchema schema() {
    return PregelSchema.Builder()
        .add("foobar", ValueType.LONG)
        .add("baz", ValueType.DOUBLE)
        .build();
}
```

Note, that each property consumes additional memory when executing the algorithm, which typically amounts to the number of nodes multiplied by the size of a single value (e.g. 64 Bit for a `long` or `double`).

The `add` method on the builder takes a third argument: `Visibility`.
There are two possible values: `PUBLIC` (default) and `PRIVATE`.
The visibility is considered during xref:algorithms/pregel-api.adoc#algorithms-pregel-api-procedure[procedure code generation] to indicate if the value is part of the Pregel result or not.
Any value that has visibility `PUBLIC` will be part of the computation result and included in the result of the procedure, e.g., streamed to the caller, mutated to the in-memory graph or written to the database.

The following shows a schema where one value is used as result and a second value is only used during computation:

```java
PregelSchema schema() {
    return PregelSchema.Builder()
        .add("result", ValueType.LONG, Visiblity.PUBLIC)
        .add("tempValue", ValueType.DOUBLE, Visiblity.PRIVATE)
        .build();
}
```

<a id="algorithms-pregel-api-java-context" />

### Init context and compute context

The main purpose of the two context objects is to enable the computation to communicate with the Pregel framework.
A context is stateful, and all its methods are subject to the current superstep and the currently processed node.
Both context objects share a set of methods, e.g., to access the config and node state.
Additionally, each context adds context-specific methods.

The `org.neo4j.gds.beta.pregel.PregelContext.InitContext` is available in the `init` method of a Pregel computation.
It provides access to node properties stored in the in-memory graph.
We can set the initial node state to a fixed value, e.g. the node id, or use graph properties and the user-defined configuration to initialize a context-dependent state.

> The InitContext

```java
public final class InitContext {
    // The currently processed node id.
    public long nodeId();
    // User-defined Pregel configuration
    public PregelConfig config();
    // Sets a double node value for the given schema key.
    public void setNodeValue(String key, double value);
    // Sets a long node value for the given schema key.
    public void setNodeValue(String key, long value);
    // Sets a double array node value for the given schema key.
    public void setNodeValue(String key, double[] value);
    // Sets a long array node value for the given schema key.
    public void setNodeValue(String key, long[] value);
    // Number of nodes in the input graph.
    public long nodeCount();
    // Number of relationships in the input graph.
    public long relationshipCount();
    // Number of relationships of the current node.
    public int degree();
    // Available node property keys in the input graph.
    public Set<String> nodePropertyKeys();
    // Node properties stored in the input graph.
    public NodeProperties nodeProperties(String key);
}
```

In contrast, `org.neo4j.gds.beta.pregel.PregelContext.ComputeContext` can be accessed inside the `compute` method.
The context provides methods to access the computation state, e.g. the current superstep, and to send messages to other nodes in the graph.

> The ComputeContext
```java
public final class ComputeContext {
    // The currently processed node id.
    public long nodeId();
    // User-defined Pregel configuration
    public PregelConfig config();
    // Sets a double node value for the given schema key.
    public void setNodeValue(String key, double value);
    // Sets a long node value for the given schema key.
    public void setNodeValue(String key, long value);
    // Number of nodes in the input graph.
    public long nodeCount();
    // Number of relationships in the input graph.
    public long relationshipCount();
    // Indicates whether the input graph is a multi-graph.
    public boolean isMultiGraph();
    // Number of relationships of the current node.
    public int degree();
    // Double value for the given node schema key.
    public double doubleNodeValue(String key);
    // Double value for the given node schema key.
    public long longNodeValue(String key);
    // Double array value for the given node schema key.
    public double[] doubleArrayNodeValue(String key);
    // Long array value for the given node schema key.
    public long[] longArrayNodeValue(String key);
    // Notify the framework that the node intends to stop its computation.
    public void voteToHalt();
    // Indicates whether this is superstep 0.
    public boolean isInitialSuperstep();
    // 0-based superstep identifier.
    public int superstep();
    // Sends the given message to all neighbors of the node.
    public void sendToNeighbors(double message);
    // Sends the given message to the target node.
    public void sendTo(long targetNodeId, double message);
    // Stream of neighbor ids of the current node.
    public LongStream getNeighbours();
}
```

<a id="algorithms-pregel-api-master-compute" />

### Master Computation

Some Pregel programs may require logic that is executed after all threads have finished the current superstep, for example, to reset or evaluate a global data structure.
This can be achieved by overriding the `org.neo4j.gds.beta.pregel.PregelComputation.masterCompute` function of the `PregelComputation`.
This function will be called at the end of each superstep after all compute threads have finished.
The master compute function will be called by a single thread.

The `masterCompute` function has access to the `org.neo4j.gds.beta.pregel.PregelContext.MasterComputeContext`.
That context is similar to the `ComputeContext` but is not tied to a specific node and does not allow sending messages.
Furthermore, the `MasterComputeContext` allows to run a function for every node in the graph and has access to the computation state of all nodes.

> The MasterComputeContext
```java
public final class MasterComputeContext {
    // User-defined Pregel configuration
    public PregelConfig config();
    // Number of nodes in the input graph.
    public long nodeCount();
    // Number of relationships in the input graph.
    public long relationshipCount();
    // Indicates whether the input graph is a multi-graph.
    public boolean isMultiGraph();
    // Run the given consumer for every node in the graph.
    public void forEachNode(LongPredicate consumer);
    // Double value for the given node schema key.
    public double doubleNodeValue(long nodeId, String key);
    // Double value for the given node schema key.
    public long longNodeValue(long nodeId, String key);
    // Double array value for the given node schema key.
    public double[] doubleArrayNodeValue(long nodeId, String key);
    // Long array value for the given node schema key.
    public long[] longArrayNodeValue(long nodeId, String key);
    // Sets a double node value for the given schema key.
    public void setNodeValue(long nodeId, String key, double value);
    // Sets a long node value for the given schema key.
    public void setNodeValue(long nodeId, String key, long value);
    // Sets a double array node value for the given schema key.
    public void setNodeValue(long nodeId, String key, double[] value);
    // Sets a long array node value for the given schema key.
    public void setNodeValue(long nodeId, String key, long[] value);
    // Indicates whether this is superstep 0.
    public boolean isInitialSuperstep();
    // 0-based superstep identifier.
    public int superstep();
}
```

<a id="algorithms-pregel-api-java-context" />

[[algorithms-pregel-api-reducer]]
### Message reducer

Many Pregel computations rely on computing a single value from all messages being sent to a node.
For example, the page rank algorithm computes the sum of all messages being sent to a single node.
In those cases, a reducer can be used to combine all messages to a single value.
If applicable, this optimization improves memory consumption and computation runtime.

By default, a Pregel computation does not make use of a reducer.
All messages sent to a node are stored in a queue and received in the next superstep.
To enable message reduction, one needs to implement the `reducer` method and provide either a custom or a pre-defined reducer.

> The Reducer interface that needs to be implemented.

```java
public interface Reducer {
    // The identity element is used as the initial value.
    double identity();
    // Computes a new value based on the current value and the message.
    double reduce(double current, double message);
}
```

The identity value is used as the initial value for the `current` argument in the `reduce` function.
All subsequent calls use the result of the previous call as `current` value.

The framework already provides implementations for computing the minimum, maximum, sum and count of messages.
The default implementations are part of the `Reducer` interface and can be applied as follows:

> Applying the sum reducer in a custom computation.
```java
public class CustomComputation implements PregelComputation<PregelConfig> {

    @Override
    public void compute(PregelContext.ComputeContext<CustomConfig> context, Pregel.Messages messages) {
        // ...
        for (var message : messages) {
            // ...
        }
    }

    @Override
    public Optional<Reducer> reducer() {
        return Optional.of(new Reducer.Sum());
    }
}
```

The implementation of the compute method does not need to be adapted.
If a reducer is present, the `messages` iterator contains either zero or one message.
Note, that defining a reducer precludes running the computation with asynchronous messaging.
The `isAsynchronous` flag at the config is ignored in that case.

<a id="algorithms-pregel-api-java-context" />

[[algorithms-pregel-api-java-config]]
### Configuration

To configure the execution of a custom Pregel computation, the framework requires a configuration.
The `org.neo4j.gds.beta.pregel.PregelConfig` provides the minimum set of options to execute a computation.
The configuration options also map to the parameters that can later be set via a custom procedure.
This is equivalent to all the other algorithms within the GDS library.

.Pregel Configuration
| Name                                                                             | Type      | Default       | Description                                                                                      |
|----------------------------------------------------------------------------------|-----------|---------------|--------------------------------------------------------------------------------------------------|
| [maxIterations](https://neo4j.com/docs/graph-data-science/current/common-usage/running-algos/#common-configuration-max-iterations)                            | Integer   | -             | Maximum number of supersteps after which the computation will terminate.                          |
| isAsynchronous                                                                   | Boolean   | false         | Flag indicating if messages can be sent and received in the same superstep.                       |
| partitioning                                                                     | String    | "range"       | Selects the partitioning of the input graph, can be either "range", "degree" or "auto".           |
| [relationshipWeightProperty](https://neo4j.com/docs/graph-data-science/current/common-usage/running-algos/#common-configuration-relationship-weight-property) | String    | null          | Name of the relationship property to use as weights. If unspecified, the algorithm runs unweighted.|
| [concurrency](https://neo4j.com/docs/graph-data-science/current/common-usage/running-algos/#common-configuration-concurrency)                                 | Integer   | 4             | Concurrency used when executing the Pregel computation.                                           |
| [writeConcurrency](https://neo4j.com/docs/graph-data-science/current/common-usage/running-algos/#common-configuration-write-concurrency)                      | Integer   | concurrency   | Concurrency used when writing computation results to Neo4j.                                       |
| [writeProperty](https://neo4j.com/docs/graph-data-science/current/common-usage/running-algos/#common-configuration-write-property)                            | String    | "pregel_"     | Prefix string that is prepended to node schema keys in write mode.                               |
| mutateProperty                                                                   | String    | "pregel_"     | Prefix string that is prepended to node schema keys in mutate mode.                              |


For some algorithms, we want to specify additional configuration options.

Typically, these options are algorithm specific arguments, such as thresholds.
Another reason for a custom config relates to the initialization phase of the computation.
If we want to init the node state based on a graph property, we need to access that property via its key.
Since those keys are dynamic properties of the graph, we need to provide them to the computation.
We can achieve that by declaring an option to set that key in a custom configuration.

If a user-defined Pregel computation requires custom options a custom configuration can be created by extending the `PregelConfig`.

.A custom configuration and how it can be used in the init phase.
[source, java]
----
@ValueClass
@Configuration
public interface CustomConfig extends PregelConfig {
    // A property key that refers to a seed property.
    String seedProperty();
    // An algorithm specific parameter.
    int minDegree();
}

public class CustomComputation implements PregelComputation<CustomConfig> {

    @Override
    public void init(PregelContext.InitContext<CustomConfig> context) {
        // Use the custom config key to access a graph property.
        var seedProperties = context.nodeProperties(context.config().seedProperty());
        // Init the node state with the graph property for that node.
        context.setNodeValue("state", seedProperties.doubleValue(context.nodeId()));
    }

    @Override
    public void compute(PregelContext.ComputeContext<CustomConfig> context, Pregel.Messages messages) {
        if (context.degree() >= context.config().minDegree()) {
            // ...
        }
    }

    // ...
}
----

[[algorithms-pregel-api-bidirectional]]
=== Traversing incoming relationships

Some algorithms implemented in Pregel might require or benefit from the ability to access and send messages to all incoming relationships of the current context node.
GDS supports the creation of inverse indexes for relationship types, which enables the traversal of incoming relationships for directed relationship types.

A Pregel algorithm can access this index by implementing the `org.neo4j.gds.beta.pregel.BidirectionalPregelComputation` interface instead of the `PregelComputation` interface.
Implementing this interface has the following consequences:

* The Pregel framework will make sure that all relationships passed into the algorithm are inverse indexed.
  If no such index exists, an error will be thrown.
* The signature of the `init` and `compute` functions now accept a `org.neo4j.gds.beta.pregel.context.InitContext.BidirectionalInitContext` and `org.neo4j.gds.beta.pregel.context.ComputeContext.BidirectionalComputeContext` respectively.
* Algorithms annotated with the `@PregelProcedure` annotation will automatically create all required inverse indexes.

The `BidirectionalInitContext` and `BidirectionalComputeContexts` expose the following new methods in addition to the methods defined by `InitContext` and `ComputeContext`:

[source, java]
----
//Returns the incoming degree (number of relationships) of the currently processed node.
public int incomingDegree();
// Calls the consumer for each incoming neighbor of the currently processed node.
public void forEachIncomingNeighbor(LongConsumer targetConsumer);
// Calls the consumer for each incoming neighbor of the given node.
public void forEachIncomingNeighbor(long nodeId, LongConsumer targetConsumer);
// Calls the consumer once for each incoming neighbor of the currently processed node.
public void forEachDistinctIncomingNeighbor(LongConsumer targetConsumer);
// Calls the consumer once for each incoming neighbor of the given node.
public void forEachDistinctIncomingNeighbor(long nodeId, LongConsumer targetConsumer);
----

In addition, the `BidirectionalComputeContext` also exposes the following function:

[source, java]
----
// Sends the given message to all neighbors of the node.
public void sendToIncomingNeighbors(double message);
----


[[algorithms-pregel-api-logging]]
=== Logging

The following methods are available for all contexts (`InitContext`, `ComputeContext`, `MasterComputeContext`) to inject custom messages into the progress log of the algorithm execution.

.The log methods can be used in Pregel contexts
[source, java]
----
// All contexts inherit from PregelContext
public abstract class PregelContext<CONFIG extends PregelConfig> {

    // Log a debug message to the Neo4j log.
    public void logDebug(String message) {
        progressTracker.logDebug(message);
    }

    // Log a warning message to the Neo4j log.
    public void logWarning(String message) {
        progressTracker.logWarning(message);
    }

    // Log a info message to the Neo4j log
    public void logMessage(String message) {
        progressTracker.logMessage(message);
    }

}
----


[[algorithms-pregel-api-id-mapping]]
=== Node id space translation

Some algorithms require nodes as input from the user.
For example, a shortest path algorithm needs to know about the start and the end node.
In GDS, there are two node id spaces: the original id space and the internal id space.
The original id space are the node ids of the graph the in-memory graph has been projected from.
Typically, these are Neo4j node identifiers.
The internal id space represents the node ids of the in-memory graph and is always a consecutive space starting at id `0`.
A Pregel computation uses the internal node id space, e.g., `ComputeContext#nodeId()` returns the internal id of the currently processed node.
In order to translate from the original to the internal node id space and vice versa, all context classes provide the following methods:

.Methods to translate between id spaces which can be used in all Pregel contexts
[source, java]
----
// All contexts inherit from PregelContext
public abstract class PregelContext<CONFIG extends PregelConfig> {
    // Maps the given internal node to its original counterpart.
    public long toOriginalNodeId(long internalNodeId);
    // Maps the given original node to its internal counterpart.
    public long toInternalNodeId(long originalNodeId);
}
----

[[algorithms-pregel-api-procedure]]
== Run Pregel via Cypher

To make a custom Pregel computation accessible via Cypher, it needs to be exposed via the procedure API.
The Pregel framework in GDS provides an easy way to generate procedures for all the default modes.

[[algorithms-pregel-api-procedure-generation]]
=== Procedure generation

To generate procedures for a computation, it needs to be annotated with the `@org.neo4j.gds.beta.pregel.annotation.PregelProcedure` annotation.
In addition, the config parameter of the custom computation must be a subtype of `org.neo4j.gds.beta.pregel.PregelProcedureConfig`.

.Using the `@PregelProcedure` annotation to configure code generation.
[source, java]
----
@PregelProcedure(
    name = "custom.pregel.proc",
    modes = {GDSMode.STREAM, GDSMode.WRITE},
    description = "My custom Pregel algorithm"
)
public class CustomComputation implements PregelComputation<PregelProcedureConfig> {
    // ...
}
----

The annotation provides a number of configuration options for the code generation.

.Configuration
[opts="header",cols="1,1,1,6"]
|===
| Name                      | Type      | Default                           | Description
| name                      | String    | -                                 | The prefix of the generated procedure name. It is appended by the mode.
| modes                     | List      | `[STREAM, WRITE, MUTATE, STATS]`  | A procedure is generated for each of the specified modes.
| description               | String    | `""`                              | Procedure description that is printed in `dbms.listProcedures()`.
|===

For the above Code snippet, we generate four procedures:

* `custom.pregel.proc.stream`
* `custom.pregel.proc.stream.estimate`
* `custom.pregel.proc.write`
* `custom.pregel.proc.write.estimate`

Note that by default, all values specified in the `PregelSchema` are included in the procedure results.
To change that behaviour, we can change the visibility for individual parts of the schema.
For more details, please refer to the xref:algorithms/pregel-api.adoc#algorithms-pregel-api-schema[dedicated documentation section].


[[algorithms-pregel-api-plugin]]
=== Building and installing a Neo4j plugin

In order to use a Pregel algorithm in Neo4j via a procedure, we need to package it as Neo4j plugin.
The https://github.com/neo4j/graph-data-science/tree/master/examples/pregel-bootstrap[pregel-bootstrap] project is a good starting point.
The `build.gradle` file within the project contains all the dependencies necessary to implement a Pregel algorithm and to generate corresponding procedures.

Make sure to change the `gdsVersion` and `neo4jVersion` according to your setup.
GDS and Neo4j are runtime dependencies.
Therefore, GDS needs to be installed as a plugin on the Neo4j server.

To build the project and create a plugin jar, just run:

[source, bash]
----
./gradlew shadowJar
----

You can find the `pregel-bootstrap.jar` in `build/libs`.
The jar needs to be placed in the `plugins` directory within your Neo4j installation alongside a GDS plugin jar.
In order to have access to the procedure in Cypher, its namespace potentially needs to be added to the `neo4j.conf` file.

.Enabling an example procedure in `neo4j.conf`
[source, bash]
----
dbms.security.procedures.unrestricted=custom.pregel.proc.*
dbms.security.procedures.allowlist=custom.pregel.proc.*
----


[[algorithms-pregel-api-example]]
== Examples

The https://github.com/neo4j/graph-data-science/tree/master/examples/pregel-example[pregel-examples] module contains a set of examples for Pregel algorithms.
The algorithm implementations demonstrate the usage of the Pregel API.
Along with each example, we provide test classes that can be used as a guideline on how to write tests for custom algorithms.
To play around, we recommend copying one of the algorithms into the `pregel-bootstrap` project, build it and setup the plugin in Neo4j.