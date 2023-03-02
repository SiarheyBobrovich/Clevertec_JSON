package ru.clevertec.json.reader;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class ArrayReaderTest {

    private ArrayReader reader;

    @BeforeEach
    void setUp() {
        reader = ArrayReader.getInstance();
    }
    @Test
    void checkGetObjectInteger() {
        String json = "[1,2,3,4,5]";
        Integer[] expected = {1,2,3,4,5};
        Integer[] actual = reader.getObject(json, Integer[].class, new ReaderFacadeImpl());

        assertThat(actual)
                .isEqualTo(expected);
    }
    @Test
    void checkGetObjectString() {
        String json = "[\"new String()\",\"String.builder().build()\"]";
        String String1 = "new String()";
        String String2 = "String.builder().build()";
        String[] expected = {String1, String2};

        String[] actual = reader.getObject(json, String[].class, new ReaderFacadeImpl());

        assertThat(actual)
                .isEqualTo(expected);
    }

    @Test
    void checkCanReadClassInteger() {
        assertFalse(reader.canReadClass(Integer.class));
    }
    @Test
    void checkCanReadClassList() {
        assertFalse(reader.canReadClass(List.class));
    }
    @Test
    void checkCanReadClassArrayInteger() {
        assertTrue(reader.canReadClass(Integer[].class));
    }
}
