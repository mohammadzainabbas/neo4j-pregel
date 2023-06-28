DROP DATABASE temp IF EXISTS;
MATCH (s:Location) -[r:trip{year:2018}]-> (t:Location)
WHERE not r.country in ['MEL', 'HdF', 'not_HdF']
WITH gds.graph.project(
    "countries_2018",
    s,
    t,
    {
        sourceNodeLabels: "Location",
        targetNodeLabels: "Location",
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
            distance: coalesce(tofloat(r.distance), tofloat(0)),
            duration: coalesce(tofloat(r.duration), tofloat(0)),
            cost: coalesce(tofloat(r.cost), tofloat(0)),
            rating: coalesce(tofloat(r.rating), tofloat(0)),
            pos_x: coalesce(toInteger(r.position.x), 0),
            pos_y: coalesce(toInteger(r.position.y), 0)
        },
        relationshipTypes: "trip",
    },
    {undirectedRelationshipTypes: ['*']}
) as g
RETURN g.graphName as graph, g.nodeCount as nodes, g.relationshipCount as rels;