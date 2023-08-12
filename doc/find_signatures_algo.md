Input: List of path "L_v" for each V, identifier
Ouput: List of frequent signatures "FS"

Function find_signatures(L_v, identifier) {
    
    signature_count_map = initialise an empty hashmap
    temp_path = initialise an empty list

    for each element in L_v {

        if element is equal to identifier {
            signature = convertToSignature(temp_path)
            if signature_count_map.containsKey(signature) {
                signature_count_map[signature] += 1
            } else {
                signature_count_map[signature] = 1
            }
            temp_path = clear list
        }
        else
        {
            temp_path.add(element)
        }
    }

    return signature_count_map
}
