package com.neo4j.proc;

import org.neo4j.gds.paths.PathResult;
import org.neo4j.procedure.Procedure;
import org.neo4j.procedure.Name;

public class PathsToSignatures {

    @Procedure("esilv.proc.find_signatures")
    public Stream<PathResult> find_signatures(@Name("nodes") List<Node> nodes, @Name(value = "config",defaultValue = "{}") Map<String, Object> config) {
     
    
}
