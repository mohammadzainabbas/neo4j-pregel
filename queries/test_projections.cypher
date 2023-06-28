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
        relationshipTypes: 'trip',
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
        },
    }
) as g
RETURN g.graphName as graph_name, g.nodeCount as nodes, g.relationshipCount as rels;
CALL gds.graph.export("countries_2018", { dbName: "temp" });
CREATE DATABASE temp IF NOT EXISTS;
// -------
