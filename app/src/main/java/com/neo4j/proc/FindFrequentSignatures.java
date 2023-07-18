package com.neo4j.proc;

import org.neo4j.gds.paths.PathResult;
import org.neo4j.procedure.Procedure;
import org.neo4j.procedure.Name;
import org.HdrHistogram.DoubleHistogram;
import org.HdrHistogram.Histogram;
import org.neo4j.procedure.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;

public class FindFrequentSignatures {

    @Procedure("esilv.proc.find_signatures")
    public Stream<PathResult> find_signatures(@Name("nodes") List<Node> nodes, @Name(value = "config",defaultValue = "{}") Map<String, Object> config) {
     
    
}
