package ru.clevertec.json.util;

import lombok.*;

import java.util.*;

@ToString
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class MultiObject extends Primitive {
    private long id;
    private String name;
    private Primitive[] primitives;
    private List<Primitive> primitiveList;
    private Set<Primitive> primitiveSet;
    private Map<String, Primitive> primitiveMap;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Primitive[] getPrimitives() {
        return primitives;
    }

    public List<Primitive> getPrimitiveList() {
        return primitiveList;
    }

    public Set<Primitive> getPrimitiveSet() {
        return primitiveSet;
    }

    public Map<String, Primitive> getPrimitiveMap() {
        return primitiveMap;
    }

    public static MultiObject random() {
        MultiObject multiObject = new MultiObject();

        multiObject.setAByte(RandomUtil.getByte());
        multiObject.setAShort(RandomUtil.getShort());
        multiObject.setAChar(RandomUtil.getChar());
        multiObject.setAnInt(RandomUtil.getInt());
        multiObject.setALong(RandomUtil.getLong());
        multiObject.setAFloat(RandomUtil.getFloat());
        multiObject.setADouble(RandomUtil.getDouble());
        multiObject.setString(RandomUtil.getString());

        Primitive primitive1 = Primitive.random();
        Primitive primitive2 = Primitive.random();
        Primitive primitive3 = Primitive.random();

        multiObject.id = RandomUtil.getLong();
        multiObject.name = RandomUtil.getString();
        multiObject.primitives = new Primitive[]{Primitive.random(), Primitive.random(), Primitive.random()};
        multiObject.primitiveList = List.of(Primitive.random(), Primitive.random(), Primitive.random());
        multiObject.primitiveSet = Set.of(Primitive.random(), Primitive.random(), Primitive.random());

        multiObject.primitiveMap = new HashMap<>();
        multiObject.primitiveMap.put(primitive1.getString(), primitive1);
        multiObject.primitiveMap.put(primitive2.getString(), primitive2);
        multiObject.primitiveMap.put(primitive3.getString(), primitive3);
        return multiObject;
    }
}
