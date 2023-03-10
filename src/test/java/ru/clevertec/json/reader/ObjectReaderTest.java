package ru.clevertec.json.reader;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.clevertec.json.JsonMapperImpl;
import ru.clevertec.json.util.ObjectWithList;
import ru.clevertec.json.util.ObjectWithMap;
import ru.clevertec.json.util.Primitive;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;

class ObjectReaderTest {

    private ObjectReader objectReader;
    private ReaderFacadeImpl readerFacade;

    @BeforeEach
    void setUp() {
        objectReader = ObjectReader.getInstance();
        readerFacade = new ReaderFacadeImpl();
    }
    @Test
    void checkGetObjectList() {
        List<Integer> list = List.of(1, 2, 41, 523);
        ObjectWithList expected = ObjectWithList.builder().integers(list).build();

        String json = "{\"integers\":[1,2,41,523]}";
        ObjectWithList actual = objectReader.getObject(json, ObjectWithList.class, readerFacade);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void checkGetObjectSet() {
        Set<Integer> set = Set.of(1, 2, 41, 523);
        ObjectWithList expected = ObjectWithList.builder().integerSet(set).build();

        String json = "{\"integerSet\":[1,2,41,523]}";
        ObjectWithList actual = objectReader.getObject(json, ObjectWithList.class, readerFacade);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void checkGetObjectListObject() {
        Primitive p1 = Primitive.builder().aByte((byte)-127).build();
        Primitive p2 = Primitive.builder().string("Some string").build();
        Primitive p3 = Primitive.builder().aDouble(123123.123123).build();
        List<Primitive> primitivesList = List.of(p1, p2, p3);

        ObjectWithList expected = ObjectWithList.builder().primitivesList(primitivesList).build();

        String json = new JsonMapperImpl().writeObjectAsString(expected);
        ObjectWithList actual = objectReader.getObject(json, ObjectWithList.class, readerFacade);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void checkGetObjectSetOfObject() {
        Set<Primitive> set = Set.of(Primitive.random(), Primitive.random());
        ObjectWithList expected = ObjectWithList.builder().primitiveSet(set).build();

        String json = new JsonMapperImpl().writeObjectAsString(expected);
        ObjectWithList actual = objectReader.getObject(json, ObjectWithList.class, readerFacade);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void checkGetObjectMap() {
        Map<String, Integer> map = Map.of("One", 1, "Two", 2);
        ObjectWithMap expected = ObjectWithMap.builder()
                .map(map)
                .build();

        String json = "{\"map\":{\"Two\":2,\"One\":1}}";
        ObjectWithMap actual = objectReader.getObject(json, ObjectWithMap.class, readerFacade);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void checkGetObjectMapObjects() {
        Primitive random1 = Primitive.random();
        Primitive random2 = Primitive.random();
        Map<Integer, Primitive> map = Map.of(
                random1.getAnInt(), Primitive.random(),
                random2.getAnInt(), Primitive.random());
        ObjectWithMap expected = ObjectWithMap.builder()
                .primitiveMap(map)
                .build();

        String json = new JsonMapperImpl().writeObjectAsString(expected);
        ObjectWithMap actual = objectReader.getObject(json, ObjectWithMap.class, readerFacade);

        assertThat(actual).isEqualTo(expected);
    }
}
