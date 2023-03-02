package ru.clevertec.json.mapper;

import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import ru.clevertec.json.exception.JsonFormatException;

class JsonStringMapperTest {

    private JsonStringMapper mapper;
    @BeforeEach
    void setUp() {
        mapper = JsonStringMapper.getInstance();
    }

    @Test
    void checkGetNumberValueObject1Field() {
        String json = "{123}";
        String value = mapper.getNumber(json);

        assertThat(value).isEqualTo("123");
    }
    @Test
    void checkGetNumberNegativeValue() {
        String json = "dkajwhdka-123.42342E12dawd";
        String value = mapper.getNumber(json);

        assertThat(value).isEqualTo("-123.42342E12");
    }
    @Test
    void checkGetNumberValueObjectNull() {
        String json = "{null,\"anInt\":321}";
        String value = mapper.getNumber(json);

        assertThat(value).isEqualTo("null");
    }

    @Test
    void checkGetNumberValueNull() {
        assertThatThrownBy(() -> mapper.getNumber(null))
                .isInstanceOf(JsonFormatException.class);
    }
    @Test
    void checkGetCharValueDigit() {
        String json = "{321,\"aByte\":null}";
        String value = mapper.getCharValue(json);

        assertThat(value).isEqualTo("321");
    }
    @Test
    void checkGetCharValueHex() {
        String json = "{\"\\u0032\",\"aByte\":null}";
        String value = mapper.getCharValue(json);

        assertThat(value).isEqualTo("\"\\u0032\"");
    }
    @Test
    void checkGetCharValueSpice() {
        String json = "{\"\\b\",\"aByte\":null}";
        String value = mapper.getCharValue(json);

        assertThat(value).isEqualTo("\"\\b\"");
    }
    @Test
    void checkGetCharValueRealNull() {
        assertThatThrownBy(() -> mapper.getCharValue(null))
                .isInstanceOf(JsonFormatException.class);
    }
    @Test
    @DisplayName("\"null\"")
    void checkGetCharValueNull() {
        String json = "{\"aChar\":null,\"aByte\":null}";
        String value = mapper.getCharValue(json);

        assertThat(value).isEqualTo("null");
    }

    @Test
    void checkGetCharValueAnySymbol() {
        String json = "{\"ﾯ\",\"aByte\":null}";
        String value = mapper.getCharValue(json);

        assertThat(value).isEqualTo("\"ﾯ\"");
    }



    @Test
    void checkGetStringValue() {
        String json = "{\"Some String\",\"aByte\":null}";
        String value = mapper.getStringValue(json);

        assertThat(value).isEqualTo("\"Some String\"");
    }

    @Test
    void checkGetStringNull() {
        assertThatThrownBy(() -> mapper.getStringValue(null))
                .isInstanceOf(JsonFormatException.class);
    }

    @Test
    void checkGetStringValueWithQuote() {
        String json = "{\"Some\"\" String\",\"aByte\":null}";
        String value = mapper.getStringValue(json);

        assertThat(value).isEqualTo("\"Some\"\" String\"");
    }

    @Test
    void checkGetStringValueWithQuotes() {
        String json = "\"Some\"\"String\"\"\",\"aByte\":null";
        String value = mapper.getStringValue(json);

        assertThat(value).isEqualTo("\"Some\"\"String\"\"\"");
    }
    @Nested
    class Array {
        @Test
        void checkGetArrayNull() {
            assertThatThrownBy(() -> mapper.getArrayString(null, 1))
                    .isInstanceOf(JsonFormatException.class);
        }
        @Test
        void checkGetArrayBracketsZero() {
            assertThatThrownBy(() -> mapper.getArrayString("[]", 0))
                    .isInstanceOf(JsonFormatException.class);
        }
        @Test
        void checkGetArrayNegativeBrackets() {
            assertThatThrownBy(() -> mapper.getArrayString("[[]]", -1))
                    .isInstanceOf(JsonFormatException.class);
        }
        @Test
        @DisplayName("Integer[]")
        void checkGetArrayValue1() {
            String json = "[123,324,3123]";
            String value = mapper.getArrayString(json, 1);

            assertThat(value).isEqualTo("[123,324,3123]");
        }
        @Test
        @DisplayName("String[][]")
        void checkGetArrayValue2() {
            String json = "[[\"One\",\"Two\",\"Three\"],[\"Four\",\"Five\",\"Six\"]]";
            String value = mapper.getArrayString(json, 2);

            assertThat(value)
                    .isEqualTo("[[\"One\",\"Two\",\"Three\"],[\"Four\",\"Five\",\"Six\"]]");
        }
        @Test
        @DisplayName("String[][][]")
        void checkGetArrayValue3() {
            String json = "[[[\"0\",\"1\"],[\"2\",\"3\"]],[[\"4\",\"5\"],[\"6\",\"7\"]]]";
            String value = mapper.getArrayString(json, 3);

            assertThat(value)
                    .isEqualTo("[[[\"0\",\"1\"],[\"2\",\"3\"]],[[\"4\",\"5\"],[\"6\",\"7\"]]]");
        }
        @Test
        @DisplayName("field = Integer[]")
        void checkGetArrayInObject1() {
            String json = "{\"aBytes\":[123,324,3123]}";
            String value = mapper.getArrayString(json, 1);

            assertThat(value).isEqualTo("[123,324,3123]");
        }
        @Test
        @DisplayName("field = String[][]")
        void checkGetArrayInObject2() {
            String json = "{\"aByte\":123,\"aBytes\":[[\"One\",\"Two\",\"Three\"],[\"Four\",\"Five\",\"Six\"]]}";
            String value = mapper.getArrayString(json, 2);

            assertThat(value)
                    .isEqualTo("[[\"One\",\"Two\",\"Three\"],[\"Four\",\"Five\",\"Six\"]]");
        }
        @Test
        @DisplayName("Array = String[][][]")
        void checkGetArrayObject3() {
            String json = "{[[[\"0\",\"1\"],[\"2\",\"3\"]],[[\"4\",\"5\"],[\"6\",\"7\"]]]}";
            String value = mapper.getArrayString(json, 3);

            assertThat(value)
                    .isEqualTo("[[[\"0\",\"1\"],[\"2\",\"3\"]],[[\"4\",\"5\"],[\"6\",\"7\"]]]");
        }
    }

    @Test
    void checkGetObjectNull() {
        String actual = mapper.getObject("null");
        assertThat(actual)
                .isEqualTo("null");
    }

    @Test
    void checkGetObject() {
        String actual = mapper.getObject("{\"integer\":123,\"other\":{\"bytes:[1,2,3]\"}}");
        assertThat(actual)
                .isEqualTo("{\"integer\":123,\"other\":{\"bytes:[1,2,3]\"}}");
    }
    @Test
    @DisplayName("Not start with {")
    void checkGetObjectThrow() {
        String json = "\"integer\":123,\"other\":{\"bytes:[1,2,3]\"}";
        assertThatThrownBy(() -> mapper.getObject(json))
                .isInstanceOf(JsonFormatException.class);
    }

    @Test
    @DisplayName("Incorrect {} count")
    void checkGetObjectThrow2() {
        String json = "{\"integer\":123,\"other\":{\"bytes:[1,2,3]\"}";
        assertThatThrownBy(() -> mapper.getObject(json))
                .isInstanceOf(JsonFormatException.class);
    }
    @Test
    void checkGetObjectJsonNull() {
        assertThatThrownBy(() -> mapper.getObject(null))
                .isInstanceOf(JsonFormatException.class);
    }
}
