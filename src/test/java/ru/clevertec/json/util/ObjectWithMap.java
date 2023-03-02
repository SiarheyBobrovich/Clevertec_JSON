package ru.clevertec.json.util;

import lombok.*;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ObjectWithMap {
    private Map<String, Integer> map;
    private Map<Integer, Primitive> primitiveMap;
}
