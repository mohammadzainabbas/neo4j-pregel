package com.neo4j.proc;

import org.neo4j.gds.paths.PathResult;
import org.neo4j.procedure.Procedure;

public class PathsToSignatures {

    @Procedure("apoc.nodes.cycles")
    @Description("Detects all path cycles in the given node list.\n" +
            "This procedure can be limited on relationships as well.")
    public Stream<PathResult> cycles(@Name("nodes") List<Node> nodes, @Name(value = "config",defaultValue = "{}") Map<String, Object> config) {
     
    
}
