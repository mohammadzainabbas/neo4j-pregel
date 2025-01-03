//Cypher Projection CountriesAll
CALL gds.graph.drop("countries_all");
CALL gds.graph.project.cypher(
                "countries_all",
                "MATCH (a1:Location) RETURN id(a1) AS id",
                "MATCH (n:Location) -[r:trip]-> (m)
                 WHERE not r.country in ['MEL', 'HdF', 'not_HdF']
                 RETURN id(n) as source, id(m) as target, sum(r.NB) as NB"
) YIELD graphName AS graph, nodeCount AS nodes, relationshipCount AS rels;

//Cypher Projection CountriesYear
CALL gds.graph.drop("countries_<<YEAR>>");
CALL gds.graph.project.cypher(
                "countries_<<YEAR>>",
                "MATCH (a1:Location) RETURN id(a1) AS id",
                "MATCH (n:Location) -[r:trip{year:<<YEAR>>}]-> (m)
                 WHERE not r.country in ['MEL', 'HdF', 'not_HdF']
                 RETURN id(n) as source, id(m) as target, sum(r.NB) as NB"
) YIELD graphName AS graph, nodeCount AS nodes, relationshipCount AS rels;

//Cypher Projection CountryAll
CALL gds.graph.drop("<<COUNTRY>>_all");
CALL gds.graph.project.cypher(
                "<<COUNTRY>>_all",
                "MATCH (a1:Location) RETURN id(a1) AS id",
                "MATCH (n:Location) -[r:trip{country:'<<COUNTRY>>'}]-> (m)
                 RETURN id(n) as source, id(m) as target, sum(r.NB) as NB"
) YIELD graphName AS graph, nodeCount AS nodes, relationshipCount AS rels;

//Cypher Projection CountryYear
CALL gds.graph.drop("<<COUNTRY>>_<<YEAR>>");
CALL gds.graph.project.cypher(
                "<<COUNTRY>>_<<YEAR>>",
                "MATCH (a1:Location) RETURN id(a1) AS id",
                "MATCH (n:Location) -[r:trip{country:'<<COUNTRY>>', year:<<YEAR>>}]-> (m)
                 RETURN id(n) as source, id(m) as target, sum(r.NB) as NB"
) YIELD graphName AS graph, nodeCount AS nodes, relationshipCount AS rels;

//Cypher Projection reverse CountriesAll
CALL gds.graph.drop("countries_reverse_all");
CALL gds.graph.project.cypher(
                "countries_reverse_all",
                "MATCH (a1:Location) RETURN id(a1) AS id",
                "MATCH (n:Location) -[r:trip]-> (m)
                 WHERE not r.country in ['MEL', 'HdF', 'not_HdF']
                 RETURN id(m) as source, id(n) as target, sum(r.NB) as NB"
) YIELD graphName AS graph, nodeCount AS nodes, relationshipCount AS rels;

//Cypher Projection reverse CountriesYear
CALL gds.graph.drop("countries_reverse_<<YEAR>>");
CALL gds.graph.project.cypher(
                "countries_reverse_<<YEAR>>",
                "MATCH (a1:Location) RETURN id(a1) AS id",
                "MATCH (n:Location) -[r:trip{year:<<YEAR>>}]-> (m)
                 WHERE not r.country in ['MEL', 'HdF', 'not_HdF']
                 RETURN id(m) as source, id(n) as target, sum(r.NB) as NB"
) YIELD graphName AS graph, nodeCount AS nodes, relationshipCount AS rels;

//Cypher Projection reverse CountryAll
CALL gds.graph.drop("<<COUNTRY>>_reverse_all");
CALL gds.graph.project.cypher(
                "<<COUNTRY>>_reverse_all",
                "MATCH (a1:Location) RETURN id(a1) AS id",
                "MATCH (n:Location) -[r:trip{country:'<<COUNTRY>>'}]-> (m)
                 RETURN id(m) as source, id(n) as target, sum(r.NB) as NB"
) YIELD graphName AS graph, nodeCount AS nodes, relationshipCount AS rels;

//Cypher Projection reverse CountryYear
CALL gds.graph.drop("<<COUNTRY>>_reverse_<<YEAR>>");
CALL gds.graph.project.cypher(
                "<<COUNTRY>>_reverse_<<YEAR>>",
                "MATCH (a1:Location) RETURN id(a1) AS id",
                "MATCH (n:Location) -[r:trip{country:'<<COUNTRY>>', year:<<YEAR>>}]-> (m)
                 RETURN id(m) as source, id(n) as target, sum(r.NB) as NB"
) YIELD graphName AS graph, nodeCount AS nodes, relationshipCount AS rels;

//-----------------------

CALL gds.graph.project.cypher(
                "countries_2019",
                "MATCH (a1:Location) RETURN id(a1) AS id",
                "MATCH (n:Location) -[r:trip{year:2019}]-> (m)
                 WHERE not r.country in ['MEL', 'HdF', 'not_HdF']
                 RETURN id(n) as source, id(m) as target, sum(r.NB) as NB"
) YIELD graphName AS graph, nodeCount AS nodes, relationshipCount AS rels;

CALL gds.graph.project.cypher(
                "countries_2018",
                "MATCH (a1:Location) RETURN id(a1) AS id",
                "MATCH (n:Location) -[r:trip{year:2018}]-> (m)
                 WHERE not r.country in ['MEL', 'HdF', 'not_HdF']
                 RETURN id(n) as source, id(m) as target, sum(r.NB) as NB"
) YIELD graphName AS graph, nodeCount AS nodes, relationshipCount AS rels;

CALL gds.graph.project.cypher(
                "countries_2017",
                "MATCH (a1:Location) RETURN id(a1) AS id",
                "MATCH (n:Location) -[r:trip{year:2017}]-> (m)
                 WHERE not r.country in ['MEL', 'HdF', 'not_HdF']
                 RETURN id(n) as source, id(m) as target, sum(r.NB) as NB"
) YIELD graphName AS graph, nodeCount AS nodes, relationshipCount AS rels;

//-----

// check all projections
CALL gds.graph.list()

// check all projections (just the names, memoryUsage, nodeCount, relationshipCount)
CALL gds.graph.list() YIELD graphName, memoryUsage, nodeCount, relationshipCount

// check all functions with a prefix "esilv"
SHOW PROCEDURES yield name, description, signature where name starts with "esilv"

// drop all projections
CALL gds.graph.list() YIELD graphName
CALL gds.graph.drop(graphName)
YIELD database
RETURN graphName as dropped_graph

//----- Visualise in-memory (projected) graph in Neo4j Browser

DROP DATABASE temp IF EXISTS;
CALL gds.graph.export("countries_2018", { dbName: "temp" });
CREATE DATABASE temp IF NOT EXISTS;
MATCH (n) RETURN n LIMIT 15;

//-----
// https://neo4j.com/labs/apoc/4.1/export/cypher/
// Export graph in database to cypher statements

CALL apoc.export.cypher.all(null, {
    streamStatements: true,
    format: "plain",
    useOptimizations: {type: "NONE"}
})
YIELD nodes, relationships, properties, cypherStatements
RETURN nodes, relationships, properties, cypherStatements;

//-----

CALL esilv.pregel.pagerank.stream("countries_2018", {maxIterations: 10})
YIELD nodeId, values
RETURN gds.util.asNode(nodeId).name AS name, values.pagerank as pagerank
ORDER BY pagerank DESC

CALL esilv.pregel.cc.stream("countries_2018", {maxIterations: 10})
YIELD nodeId, values
RETURN gds.util.asNode(nodeId).name AS name, values.component as component
ORDER BY component ASC



---

MATCH (s:Location) -[r:trip{year:2018}]-> (t:Location)
WHERE not r.country in ['MEL', 'HdF', 'not_HdF']
WITH gds.graph.project(
    "countries_2018",
    s,
    t,
    {
        sourceNodeProperties: 
        s {
            name: coalesce(s.name, ""),
            rating: coalesce(s.rating, tofloat(0)),
            pos_x: coalesce(s.position.x, 0),
            pos_y: coalesce(s.position.y, 0),
            hotelType: coalesce(s.hotelType, "")
        },
        targetNodeProperties: 
        t {
            name: coalesce(t.name, ""),
            rating: coalesce(t.rating, tofloat(0)),
            pos_x: coalesce(t.position.x, 0),
            pos_y: coalesce(t.position.y, 0),
            hotelType: coalesce(t.hotelType, "")
        },
        relationshipProperties: 
        r {
            .country,
            .year,
            .NbPerMaxDurationDays_1,
            .NbPerMaxDurationDays_3,
            .NbPerMaxDurationDays_7,
            .NbPerMaxDurationDays_14,
            .NbPerMaxDurationDays_inf
        }
    }
) as g
RETURN g.graphName as graph, g.nodeCount as nodes, g.relationshipCount as rels;

---

MATCH (s:Location) -[r:trip{year:2018}]-> (t:Location)
WHERE not r.country in ['MEL', 'HdF', 'not_HdF']
WITH gds.graph.project(
    "countries_2018",
    s,
    t,
    {
        sourceNodeProperties: 
        s {
            rating: coalesce(tofloat(s.rating), tofloat(0)),
            pos_x: coalesce(toInteger(s.position.x), 0),
            pos_y: coalesce(toInteger(s.position.y), 0)
        },
        targetNodeProperties: 
        t {
            rating: coalesce(tofloat(t.rating), tofloat(0)),
            pos_x: coalesce(toInteger(t.position.x), 0),
            pos_y: coalesce(toInteger(t.position.y), 0)
        },
        relationshipProperties: 
        r {
            .country,
            .year,
            .NbPerMaxDurationDays_1,
            .NbPerMaxDurationDays_3,
            .NbPerMaxDurationDays_7,
            .NbPerMaxDurationDays_14,
            .NbPerMaxDurationDays_inf
        }
    }
) as g
RETURN g.graphName as graph, g.nodeCount as nodes, g.relationshipCount as rels;


---

MATCH (s:Location) -[r:trip{year:2018}]-> (t:Location)
WHERE not r.country in ['MEL', 'HdF', 'not_HdF']
WITH gds.graph.project(
    "countries_2018",
    s,
    t,
    {}
) as g
RETURN g.graphName as graph, g.nodeCount as nodes, g.relationshipCount as rels;

---

MATCH (s:Location) -[r:trip{year:2018}]-> (t:Location)
WHERE not r.country in ['MEL', 'HdF', 'not_HdF']
WITH gds.graph.project(
    "countries_2018",
    s,
    t,
    {
        sourceNodeProperties: s {
            rating: coalesce(tofloat(s.rating), tofloat(0)),
            pos_x: coalesce(toInteger(s.position.x), 0),
            pos_y: coalesce(toInteger(s.position.y), 0)
        },
        targetNodeProperties: t {
            rating: coalesce(tofloat(t.rating), tofloat(0)),
            pos_x: coalesce(toInteger(t.position.x), 0),
            pos_y: coalesce(toInteger(t.position.y), 0)
        }
    }
) as g
RETURN g.graphName as graph, g.nodeCount as nodes, g.relationshipCount as rels;