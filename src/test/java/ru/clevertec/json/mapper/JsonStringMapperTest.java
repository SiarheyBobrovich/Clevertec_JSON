package ru.clevertec.json.mapper;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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
    void checkGetNumberValueObject1Field() throws JsonFormatException {
        String json = "{123}";
        String value = mapper.getNumber(json);

        Assertions.assertThat(value).isEqualTo("123");
    }
    @Test
    void checkGetNumberValueObjectNull() throws JsonFormatException {
        String json = "{null,\"anInt\":321}";
        String value = mapper.getNumber(json);

        Assertions.assertThat(value).isEqualTo("null");
    }
    @Test
    void checkGetCharValueDigit() throws JsonFormatException {
        String json = "{321,\"aByte\":null}";
        String value = mapper.getCharValue(json);

        Assertions.assertThat(value).isEqualTo("321");
    }
    @Test
    void checkGetCharValueHex() throws JsonFormatException {
        String json = "{\"\\u0032\",\"aByte\":null}";
        String value = mapper.getCharValue(json);

        Assertions.assertThat(value).isEqualTo("\"\\u0032\"");
    }
    @Test
    void checkGetCharValueSpice() throws JsonFormatException {
        String json = "{\"\\b\",\"aByte\":null}";
        String value = mapper.getCharValue(json);

        Assertions.assertThat(value).isEqualTo("\"\\b\"");
    }
    @Test
    void checkGetCharValueNull() throws JsonFormatException {
        String json = "{\"aChar\":null,\"aByte\":null}";
        String value = mapper.getCharValue(json);

        Assertions.assertThat(value).isEqualTo("null");
    }
    @Test
    void checkGetCharValueAnySymbol() throws JsonFormatException {
        String json = "{\"ﾯ\",\"aByte\":null}";
        String value = mapper.getCharValue(json);

        Assertions.assertThat(value).isEqualTo("\"ﾯ\"");
    }

    @Test
    void checkGetStringValue() throws JsonFormatException {
        String json = "{\"Some String\",\"aByte\":null}";
        String value = mapper.getStringValue(json);

        Assertions.assertThat(value).isEqualTo("\"Some String\"");
    }

    @Test
    void checkGetStringValueWithQuote() throws JsonFormatException {
        String json = "{\"Some\"\" String\",\"aByte\":null}";
        String value = mapper.getStringValue(json);

        Assertions.assertThat(value).isEqualTo("\"Some\"\" String\"");
    }

    @Test
    void checkGetStringValueWithQuotes() throws JsonFormatException {
        String json = "\"Some\"\"String\"\"\",\"aByte\":null";
        String value = mapper.getStringValue(json);

        Assertions.assertThat(value).isEqualTo("\"Some\"\"String\"\"\"");
    }
    @Nested
    class Array {
        @Test
        void checkGetArrayValue1() throws JsonFormatException {
            String json = "[123,324,3123]";
            String value = mapper.getArrayString(json, 1);

            Assertions.assertThat(value).isEqualTo("[123,324,3123]");
        }
        @Test
        void checkGetArrayValue2() throws JsonFormatException {
            String json = "[[\"One\",\"Two\",\"Three\"],[\"Four\",\"Five\",\"Six\"]]";
            String value = mapper.getArrayString(json, 2);

            Assertions.assertThat(value)
                    .isEqualTo("[[\"One\",\"Two\",\"Three\"],[\"Four\",\"Five\",\"Six\"]]");
        }
        @Test
        void checkGetArrayValue3() throws JsonFormatException {
            String json = "[[[\"0\",\"1\"],[\"2\",\"3\"]],[[\"4\",\"5\"],[\"6\",\"7\"]]]";
            String value = mapper.getArrayString(json, 3);

            Assertions.assertThat(value).isEqualTo("[[[\"0\",\"1\"],[\"2\",\"3\"]],[[\"4\",\"5\"],[\"6\",\"7\"]]]");
        }
        @Test
        void checkGetArrayObjectValue1() throws JsonFormatException {
            String json = "{\"aBytes\":[123,324,3123]}";
            String value = mapper.getArrayString(json, 1);

            Assertions.assertThat(value).isEqualTo("[123,324,3123]");
        }
        @Test
        void checkGetArrayObjectValue2() throws JsonFormatException {
            String json = "{\"aByte\":123,\"aBytes\":[[\"One\",\"Two\",\"Three\"],[\"Four\",\"Five\",\"Six\"]]}";
            String value = mapper.getArrayString(json, 2);

            Assertions.assertThat(value)
                    .isEqualTo("[[\"One\",\"Two\",\"Three\"],[\"Four\",\"Five\",\"Six\"]]");
        }
        @Test
        void checkGetArrayObjectValue3() throws JsonFormatException {
            String json = "{[[[\"0\",\"1\"],[\"2\",\"3\"]],[[\"4\",\"5\"],[\"6\",\"7\"]]]}";
            String value = mapper.getArrayString(json, 3);

            Assertions.assertThat(value)
                    .isEqualTo("[[[\"0\",\"1\"],[\"2\",\"3\"]],[[\"4\",\"5\"],[\"6\",\"7\"]]]");
        }
    }
}
