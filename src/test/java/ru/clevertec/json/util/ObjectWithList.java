package ru.clevertec.json.util;

import lombok.*;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Data
public class ObjectWithList {
    private List<Integer> integers;
    private Set<Integer> integerSet;
    private List<Primitive> primitivesList;
    private Set<Primitive> primitiveSet;
}

