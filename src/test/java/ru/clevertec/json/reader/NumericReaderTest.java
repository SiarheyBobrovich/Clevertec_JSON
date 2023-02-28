package ru.clevertec.json.reader;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;

class NumericReaderTest {

    private NumericReader numericReader;
    private ObjectMapper mapper;
    @BeforeEach
    void setUp() {
        numericReader = NumericReader.getInstance();
        mapper = new ObjectMapper();
    }

    @Test
    void checkWriteObjectAsStringNull() throws JsonProcessingException {
        Integer expected = null;
        String actualJson = mapper.writeValueAsString(expected);
        Number actual = numericReader.getObject(actualJson, Integer.class, null);

        assertThat(actual)
                .isEqualTo(expected);

    }
    @Test
    void checkWriteObjectAsStringBigDecimal() throws JsonProcessingException {
        BigDecimal expected = new BigDecimal("123");
        String actualJson = mapper.writeValueAsString(expected);
        BigDecimal actual = numericReader.getObject(actualJson, BigDecimal.class, null);

        assertThat(actual)
                .isEqualTo(expected);
    }
    @Test
    void checkWriteObjectAsStringByte() throws JsonProcessingException {
        byte expected = 125;
        String actualJson = mapper.writeValueAsString(expected);
        Number actual = numericReader.getObject(actualJson, Byte.class, null);

        assertThat(actual)
                .isEqualTo(expected);
    }
    @Test
    void checkWriteObjectAsStringShort() throws JsonProcessingException {
        short expected = 12412;
        String actualJson = mapper.writeValueAsString(expected);
        Number actual = numericReader.getObject(actualJson, Short.class, null);

        assertThat(actual)
                .isEqualTo(expected);
    }
    @Test
    void checkWriteObjectAsStringInt() throws JsonProcessingException {
        int expected = 23456789;
        String actualJson = mapper.writeValueAsString(expected);
        Number actual = numericReader.getObject(actualJson, Integer.class, null);

        assertThat(actual)
                .isEqualTo(expected);
    }
    @Test
    void checkWriteObjectAsStringLong() throws JsonProcessingException {
        long expected = 234567892352L;
        String actualJson = mapper.writeValueAsString(expected);
        Number actual = numericReader.getObject(actualJson, Long.class, null);

        assertThat(actual)
                .isEqualTo(expected);
    }
    @Test
    void checkWriteObjectAsStringFloat() throws JsonProcessingException {
        float expected = 123123.1232f;
        String actualJson = mapper.writeValueAsString(expected);
        Number actual = numericReader.getObject(actualJson, Float.class, null);

        assertThat(actual)
                .isEqualTo(expected);
    }
    @Test
    void checkWriteObjectAsStringDouble() throws JsonProcessingException {
        double expected = 41241241.5325235;
        String actualJson = mapper.writeValueAsString(expected);
        Number actual = numericReader.getObject(actualJson, Double.class, null);

        assertThat(actual)
                .isEqualTo(expected);
    }

    @Test
    void checkCanReadClassChar() {
        boolean canReadClass = numericReader.canReadClass(char.class);
        assertThat(canReadClass)
                .isFalse();
    }

    @Test
    void checkCanReadClassInt() {
        boolean canReadClass = numericReader.canReadClass(int.class);
        assertThat(canReadClass)
                .isTrue();
    }
    @Test
    void checkCanReadClassBigDecimal() {
        boolean canReadClass = numericReader.canReadClass(BigDecimal.class);
        assertThat(canReadClass)
                .isTrue();
    }
}
