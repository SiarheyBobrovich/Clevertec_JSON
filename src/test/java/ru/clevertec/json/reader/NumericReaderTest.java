package ru.clevertec.json.reader;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NumericReaderTest {

    private NumericReader numericReader;
    private ObjectMapper mapper;
    @BeforeEach
    void setUp() {
        numericReader = new NumericReader();
        mapper = new ObjectMapper();
    }


    @Test
    void checkWriteObjectAsStringNull() throws JsonProcessingException {
        Integer expected = null;
        String actualJson = mapper.writeValueAsString(expected);
        Number actual = numericReader.readStringToObject(actualJson, Integer.class);

        Assertions.assertThat(actual)
                .isEqualTo(expected);
    }
    @Test
    void checkWriteObjectAsStringByte() throws JsonProcessingException {
        byte expected = 125;
        String actualJson = mapper.writeValueAsString(expected);
        Number actual = numericReader.readStringToObject(actualJson, Byte.class);

        Assertions.assertThat(actual)
                .isEqualTo(expected);
    }
    @Test
    void checkWriteObjectAsStringShort() throws JsonProcessingException {
        short expected = 12412;
        String actualJson = mapper.writeValueAsString(expected);
        Number actual = numericReader.readStringToObject(actualJson, Short.class);

        Assertions.assertThat(actual)
                .isEqualTo(expected);
    }
    @Test
    void checkWriteObjectAsStringInt() throws JsonProcessingException {
        int expected = 23456789;
        String actualJson = mapper.writeValueAsString(expected);
        Number actual = numericReader.readStringToObject(actualJson, Integer.class);

        Assertions.assertThat(actual)
                .isEqualTo(expected);
    }
    @Test
    void checkWriteObjectAsStringLong() throws JsonProcessingException {
        long expected = 234567892352L;
        String actualJson = mapper.writeValueAsString(expected);
        Number actual = numericReader.readStringToObject(actualJson, Long.class);

        Assertions.assertThat(actual)
                .isEqualTo(expected);
    }
    @Test
    void checkWriteObjectAsStringFloat() throws JsonProcessingException {
        float expected = 123123.1232f;
        String actualJson = mapper.writeValueAsString(expected);
        Number actual = numericReader.readStringToObject(actualJson, Float.class);

        Assertions.assertThat(actual)
                .isEqualTo(expected);
    }
    @Test
    void checkWriteObjectAsStringDouble() throws JsonProcessingException {
        double expected = 41241241.5325235;
        String actualJson = mapper.writeValueAsString(expected);
        Number actual = numericReader.readStringToObject(actualJson, Double.class);

        Assertions.assertThat(actual)
                .isEqualTo(expected);
    }
}
