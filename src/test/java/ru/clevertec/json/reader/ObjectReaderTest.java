package ru.clevertec.json.reader;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.clevertec.json.JsonMapperImpl;
import ru.clevertec.json.util.ObjectWithList;
import ru.clevertec.json.util.Primitive;

import java.util.List;
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
    void checkGetObjectListOfSet() {
        Set<Integer> set = Set.of(1, 2, 41, 523);
        ObjectWithList expected = ObjectWithList.builder().integerSet(set).build();

        String json = "{\"integerSet\":[1,2,41,523]}";
        ObjectWithList actual = objectReader.getObject(json, ObjectWithList.class, readerFacade);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void checkGetObjectListObject() {
        List<Primitive> primitivesList = List.of(Primitive.random(), Primitive.random(), Primitive.random());
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
}