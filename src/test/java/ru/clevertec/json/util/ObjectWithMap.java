package ru.clevertec.json.util;

import lombok.*;

import java.util.Map;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
public class ObjectWithMap {
    private Map<String, Integer> map;
    private Map<Integer, Primitive> primitiveMap;
}
