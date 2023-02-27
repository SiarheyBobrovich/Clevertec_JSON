package ru.clevertec.json.util;

import java.util.*;

public class MultiObject extends Primitive {
    private long id;
    private String name;
    private Primitive[] primitives;
    private List<Primitive> primitiveList;
    private Set<Primitive> primitiveSet;
    private Map<String, Primitive> primitiveMap;

    private MultiObject() {
        super();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Primitive[] getPrimitives() {
        return primitives;
    }

    public void setPrimitives(Primitive[] primitives) {
        this.primitives = primitives;
    }

    public List<Primitive> getPrimitiveList() {
        return primitiveList;
    }

    public void setPrimitiveList(List<Primitive> primitiveList) {
        this.primitiveList = primitiveList;
    }

    public Set<Primitive> getPrimitiveSet() {
        return primitiveSet;
    }

    public void setPrimitiveSet(Set<Primitive> primitiveSet) {
        this.primitiveSet = primitiveSet;
    }

    public Map<String, Primitive> getPrimitiveMap() {
        return primitiveMap;
    }

    public void setPrimitiveMap(Map<String, Primitive> primitiveMap) {
        this.primitiveMap = primitiveMap;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MultiObject that)) return false;
        if (!super.equals(o)) return false;
        return id == that.id && Objects.equals(name, that.name) && Arrays.equals(primitives, that.primitives) && Objects.equals(primitiveList, that.primitiveList) && Objects.equals(primitiveSet, that.primitiveSet) && Objects.equals(primitiveMap, that.primitiveMap);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(super.hashCode(), id, name, primitiveList, primitiveSet, primitiveMap);
        result = 31 * result + Arrays.hashCode(primitives);
        return result;
    }

    @Override
    public String toString() {
        return "MultiObject{" +
                  "id=" + id +
                ", name='" + name + '\'' +
                ", primitives=" + Arrays.toString(primitives) +
                ", primitiveList=" + primitiveList +
                ", primitiveSet=" + primitiveSet +
                ", primitiveMap=" + primitiveMap +
                '}';
    }

    public static MultiObject random() {
        MultiObject multiObject = new MultiObject();

        multiObject.setaByte(RandomUtil.getByte());
        multiObject.setaShort(RandomUtil.getShort());
        multiObject.setaChar(RandomUtil.getChar());
        multiObject.setAnInt(RandomUtil.getInt());
        multiObject.setaLong(RandomUtil.getLong());
        multiObject.setaFloat(RandomUtil.getFloat());
        multiObject.setaDouble(RandomUtil.getDouble());
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
