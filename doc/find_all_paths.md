Input: In-memory projection of circulation graph "G(V,E)"
Output: List of path "L_v" for each V

For each V, initialise an empty list "L_v"
neighbor_map, initialise an empty hashmap

While (no. of supersteps <= maxIteration) {

    Function compute(superstep, msgs, identifier) {

        if (superstep == 0) // initial (0th) super-step
        {
            send current node's nodeId to all neighbors
        } 
        else if (superstep == 1) // 1st super-step
        {
            incoming-neighbors = all received neighbors' nodeIds from msgs 
            outgoing-neighbors = iterate and store all nodeIds for neighboring nodes
            neighbors = incoming-neighborrs + outgoing-neighbors
            neighbors = remove_duplicates(neighbors)
            neighbor_map.put(nodeId, neighbors) // add list of neighbors against each nodeId for each node

            add current node's nodeId to path list "L_v"
            add identifier to path list "L_v"

            send all the received neighbors' nodeIds to all neighbors // forward the message to all neighbors


        }
        else if (superstep > 1) // subsequent super-steps
        {
            previous_path = L_v
            previous_paths = split the previous_path by the identifier

            neighbors = neighbor_map.get(nodeId) // get list of neighbors for current node

            new_paths = empty list

            for each path in previous_paths
            {
                neighbors_of_last_node = get neighbors of last_node in path

                temp = empty list

                for each msg in msgs
                {
                    if (criteria(msg, path)) // this criteria method can be defined to further filter the messages
                    {
                        temp = path + msg + identifier
                    }

                    new_paths = new_paths + temp
                    temp = clear list
                }
            }

            if new_paths is empty
            {
                vote to halt
            }
            else
            {
                L_v = new_paths
            }

            send all the received neighbors' nodeIds to all neighbors // forward the message to all neighbors
        }
    }
}

Return L_v for each V
