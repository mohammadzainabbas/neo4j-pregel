DROP DATABASE temp IF EXISTS;
MATCH (s:Location) -[r:trip{year:2018}]-> (t:Location)
WHERE not r.country in ['MEL', 'HdF', 'not_HdF']
WITH gds.graph.project(
    "countries_2018",
    s,
    t,
    {
        sourceNodeLabels: 'Location',
        targetNodeLabels: 'Location',
        relationshipType: 'trip',
        sourceNodeProperties: s {
            rating: coalesce(tofloat(s.rating), tofloat(0)),
            pos_x: coalesce(toInteger(s.position.x), 0),
            pos_y: coalesce(toInteger(s.position.y), 0)
        },
        targetNodeProperties: t {
            rating: coalesce(tofloat(t.rating), tofloat(0)),
            pos_x: coalesce(toInteger(t.position.x), 0),
            pos_y: coalesce(toInteger(t.position.y), 0)
        },
        relationshipProperties: {
            year: coalesce(toInteger(r.year), toInteger(0)),
            NbPerMaxDurationDays_1: coalesce(toInteger(r.NbPerMaxDurationDays_1), toInteger(0))
        }
    }
) as g
RETURN g.graphName as graph_name, g.nodeCount as nodes, g.relationshipCount as rels;
CALL gds.graph.export("countries_2018", { dbName: "temp" });
CREATE DATABASE temp IF NOT EXISTS;
// -------

WITH -1 as identifier
CALL esilv.pregel.find_paths.stream("countries_2018", {maxIterations: 8, identifier: identifier})
YIELD nodeId, values
UNWIND values.paths AS paths
WITH collect(paths) as path, identifier
CALL esilv.proc.find_signatures(path, identifier) YIELD signature, count
RETURN signature, count
ORDER BY count DESC

//------------

WITH -1 as identifier
CALL esilv.pregel.find_paths.stream("countries_2018_small", {maxIterations: 4, identifier: identifier})
YIELD nodeId, values
WITH nodeId, values.paths AS paths
LIMIT 10
UNWIND paths AS _paths
WITH collect(_paths) as path, -1 as identifier
CALL esilv.proc.find_signatures(path, identifier) YIELD signature, count
RETURN signature, count
ORDER BY count DESC

//------------

CREATE CONSTRAINT unique_id IF NOT EXISTS FOR (n:User) REQUIRE n.id IS UNIQUE;

LOAD CSV FROM 'file:///Users/mohammadzainabbas/Downloads/soc-LiveJournal1.txt' AS line FIELDTERMINATOR '\t'
WITH line
LIMIT 10000
MERGE (from:User {id: toInteger(line[0])})
MERGE (to:User {id: toInteger(line[1])})
MERGE (from)-[:FRIEND]->(to);

MATCH (s)-[r]-> (t)
WITH s, r, t
LIMIT 100
WITH gds.graph.project(
    "livejournal",
    s,
    t
) as g
RETURN g.graphName as graph_name, g.nodeCount as nodes, g.relationshipCount as rels;

WITH -1 as identifier
CALL esilv.pregel.find_paths.stream("livejournal", {maxIterations: 5, identifier: identifier})
YIELD nodeId, values
UNWIND values.paths AS paths
WITH collect(paths) as path, identifier
CALL esilv.proc.find_signatures(path, identifier) YIELD signature, count
RETURN signature, count
ORDER BY count DESC

//------------

CREATE CONSTRAINT twitter_unique_id IF NOT EXISTS FOR (n:TwitterUser) REQUIRE n.id IS UNIQUE;

LOAD CSV FROM 'file:///Users/mohammadzainabbas/Downloads/twitter-2010.txt' AS line FIELDTERMINATOR ' '
WITH line
LIMIT 10000
MERGE (from:TwitterUser {id: toInteger(line[0])})
MERGE (to:TwitterUser {id: toInteger(line[1])})
MERGE (from)-[:FOLLOWS]->(to);
