package ru.clevertec.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import ru.clevertec.json.util.MultiObject;
import ru.clevertec.json.util.Primitive;

import java.util.Random;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

class JsonMapperImplTest {
    private ObjectMapper mapper;
    private JsonMapperImpl jsonMapper;

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
        jsonMapper = new JsonMapperImpl();
    }

    @Nested
    class Write {
        @Test
        void checkWriteObjectAsStringNull() throws JsonProcessingException {
            String actualJson = jsonMapper.writeObjectAsString(null);
            Integer actual = mapper.readValue(actualJson, Integer.class);

            assertThat(actual)
                    .isEqualTo(null);
        }

        @Test
        void checkWriteObjectAsStringByte() throws JsonProcessingException {
            byte expected = 125;
            String actualJson = jsonMapper.writeObjectAsString(expected);
            byte actual = mapper.readValue(actualJson, Byte.class);

            assertThat(actual)
                    .isEqualTo(expected);
        }

        @Test
        void checkWriteObjectAsStringShort() throws JsonProcessingException {
            short expected = 12412;
            String actualJson = jsonMapper.writeObjectAsString(expected);
            short actual = mapper.readValue(actualJson, Short.class);

            assertThat(actual)
                    .isEqualTo(expected);
        }

        @Test
        void checkWriteObjectAsStringChars() {
            Stream.iterate(0, x -> x < Character.MAX_VALUE, x -> x + 1)
                    .map(integer -> (char) integer.intValue())
                    .forEach(character -> {
                        String expected = jsonMapper.writeObjectAsString(character);
                        try {
                            char actual = mapper.readValue(expected, Character.class);
                            assertThat(character)
                                    .isEqualTo(actual);
                        } catch (JsonProcessingException e) {
                            throw new RuntimeException(e);
                        }
                    });
        }

        @Test
        void checkWriteObjectAsStringInt() throws JsonProcessingException {
            int expected = 23456789;
            String actualJson = jsonMapper.writeObjectAsString(expected);
            int actual = mapper.readValue(actualJson, Integer.class);

            assertThat(actual)
                    .isEqualTo(expected);
        }

        @Test
        void checkWriteObjectAsStringLong() throws JsonProcessingException {
            long expected = 234567892352L;
            String actualJson = jsonMapper.writeObjectAsString(expected);
            long actual = mapper.readValue(actualJson, Long.class);

            assertThat(actual)
                    .isEqualTo(expected);
        }

        @Test
        void checkWriteObjectAsStringFloat() throws JsonProcessingException {
            float expected = 123123.1232f;
            String actualJson = jsonMapper.writeObjectAsString(expected);
            float actual = mapper.readValue(actualJson, Float.class);

            assertThat(actual)
                    .isEqualTo(expected);
        }

        @Test
        void checkWriteObjectAsStringDouble() throws JsonProcessingException {
            double expected = 41241241.5325235;
            String actualJson = jsonMapper.writeObjectAsString(expected);
            double actual = mapper.readValue(actualJson, Double.class);

            assertThat(actual)
                    .isEqualTo(expected);
        }

        @Test
        void checkWriteObjectAsStringString() throws JsonProcessingException {
            String expected = "new String()";
            String actualJson = jsonMapper.writeObjectAsString(expected);
            String actual = mapper.readValue(actualJson, String.class);

            assertThat(actual)
                    .isEqualTo(expected);
        }

        @Test
        void checkWriteObjectAsStringArray1() throws JsonProcessingException {
            String[] expected = {"One", "Two", "Three"};
            String actualJson = jsonMapper.writeObjectAsString(expected);
            String[] actual = mapper.readValue(actualJson, String[].class);

            assertThat(actual)
                    .isEqualTo(expected);
        }

        @Test
        void checkWriteObjectAsStringMultiArray() throws JsonProcessingException {
            String[][] expected = {{"One", "Two", "Three"}, {"Four", "Five", "Six"}};
            String actualJson = jsonMapper.writeObjectAsString(expected);
            String[][] actual = mapper.readValue(actualJson, String[][].class);

            assertThat(actual)
                    .isEqualTo(expected);
        }

        @Test
        @DisplayName("byte, short, char, int, long, float, double, String")
        void checkWriteObjectAsStringPrimitives() throws JsonProcessingException {
            Primitive expected = Primitive.random();
            String actualJson = jsonMapper.writeObjectAsString(expected);

            Primitive actual = mapper.readValue(actualJson, Primitive.class);

            assertThat(actual)
                    .isEqualTo(expected);
        }

        @Test
        @DisplayName("long, String, Array, List, Set, Map")
        void checkWriteObjectAsStringMultiObject() throws JsonProcessingException {
            MultiObject expected = MultiObject.random();
            String actualJson = jsonMapper.writeObjectAsString(expected);
            MultiObject actual = mapper.readValue(actualJson, MultiObject.class);

            assertThat(expected)
                    .isEqualTo(actual);
        }
    }

    @Nested
    class Read {

        @Test
        void checkWriteObjectAsStringString() throws JsonProcessingException {
            String expected = "new String()";
            String actualJson = mapper.writeValueAsString(expected);
            String actual = jsonMapper.readStringToObject(actualJson, String.class);

            assertThat(actual)
                    .isEqualTo(expected);
        }

        @Test
        void checkWriteObjectAsStringArray1() throws JsonProcessingException {
            String[] expected = {"One", "Two", "Three"};
            String actualJson = mapper.writeValueAsString(expected);
            String[] actual = jsonMapper.readStringToObject(actualJson, String[].class);

            assertThat(actual)
                    .isEqualTo(expected);
        }

        @Test
        void checkWriteObjectAsStringMultiArray2() throws JsonProcessingException {
            String[][] expected = {{"One", "Two", "Three"}, {"Four", "Five", "Six"}};
            String actualJson = mapper.writeValueAsString(expected);
            String[][] actual = jsonMapper.readStringToObject(actualJson, String[][].class);

            assertThat(actual)
                    .isEqualTo(expected);
        }

        @Test
        void checkWriteObjectAsStringMultiArray3() throws JsonProcessingException {
            Integer[][][] expected = new Integer[2][2][2];
            Random random = new Random();

            for (int i = 0; i < expected.length; i++) {
                for (int i1 = 0; i1 < expected.length; i1++) {
                    for (int i2 = 0; i2 < expected.length; i2++) {
                        expected[i][i1][i2] = random.nextInt(Integer.MAX_VALUE);
                    }
                }
            }
            String actualJson = mapper.writeValueAsString(expected);
            Integer[][][] actual = jsonMapper.readStringToObject(actualJson, Integer[][][].class);

            assertThat(actual)
                    .isEqualTo(expected);
        }

        @Test
        void checkWriteObjectAsStringPrimitives() throws JsonProcessingException {
            Primitive expected = Primitive.random();
            String actualJson = mapper.writeValueAsString(expected);
            Primitive actual = jsonMapper.readStringToObject(actualJson, Primitive.class);

            assertThat(actual)
                    .isEqualTo(expected);
        }
        @Test
        void checkWriteObjectAsStringArrayPrimitives() throws JsonProcessingException {
            Primitive[] expected ={Primitive.random(), Primitive.random(), Primitive.random()};
            String actualJson = mapper.writeValueAsString(expected);
            Primitive[] actual = jsonMapper.readStringToObject(actualJson, Primitive[].class);

            assertThat(actual)
                    .isEqualTo(expected);
        }
    }
}
