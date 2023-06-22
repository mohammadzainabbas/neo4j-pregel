
## API Reference

## ComputeContext Methods

| Context         | Method                                                       |
|-----------------|--------------------------------------------------------------|
| ComputeContext  | `double doubleNodeValue(java.lang.String key)`                 |
| ComputeContext  | `long longNodeValue(java.lang.String key)`                     |
| ComputeContext  | `long[] longArrayNodeValue(java.lang.String key)`              |
| ComputeContext  | `long[] longArrayNodeValue(java.lang.String key, long id)`     |
| ComputeContext  | `double[] doubleArrayNodeValue(java.lang.String key)`          |
| ComputeContext  | `void voteToHalt()`                                            |
| ComputeContext  | `boolean isInitialSuperstep()`                                 |
| ComputeContext  | `int superstep()`                                              |
| ComputeContext  | `void sendToNeighbors(double message)`                         |
| ComputeContext  | `void sendTo(long targetNodeId, double message)`               |
| ComputeContext  | `void sendToNeighbors(long sourceNodeId, double message)`      |
| ComputeContext  | `void sendToNeighborsWeighted(long sourceNodeId, double message)` |
| ComputeContext  | `boolean hasSentMessage()`                                     |
| ComputeContext  | `boolean lambda$sendToNeighborsWeighted$1(double message, long ignored, long targetNodeId, double weight)` |
| ComputeContext  | `boolean lambda$sendToNeighbors$0(double message, long ignored, long targetNodeId)` |


### NodeCentricContext (ComputeContext's superclass) Methods

| Context           | Method                                                                                                  |
|-------------------|---------------------------------------------------------------------------------------------------------|
| NodeCentricContext | `boolean isMultiGraph()`                                                                                |
| NodeCentricContext | `long nodeCount()`                                                                                      |


### PregelContext (NodeCentricContext's superclass) Methods

| Context         | Method                                              |
|-----------------|-----------------------------------------------------|
| PregelContext   | `CONFIG config()`                                   |
| PregelContext   | `void logDebug(java.lang.String message)`           |
| PregelContext   | `void logWarning(java.lang.String message)`         |
| PregelContext   | `void logMessage(java.lang.String message)`         |
| PregelContext   | `abstract boolean isMultiGraph()`                   |
| PregelContext   | `abstract long nodeCount()`                         |
| PregelContext   | `abstract long relationshipCount()`                 |
