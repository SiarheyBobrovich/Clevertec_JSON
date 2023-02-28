package ru.clevertec.json.reader;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.*;

class CharacterReaderTest {

    private CharacterReader characterReader;
    private ObjectMapper mapper;
    @BeforeEach
    void setUp() {
        characterReader = CharacterReader.getInstance();
        mapper = new ObjectMapper();
    }
    @Test
    void checkWriteObjectAsStringNull() throws JsonProcessingException {
        Character expected = null;
        String actualJson = mapper.writeValueAsString(expected);
        Character actual = characterReader.getObject(actualJson, Character.class, null);

        assertThat(actual)
                .isEqualTo(expected);
    }
    @Test
    void checkWriteObjectAsStringChars() throws JsonProcessingException {
        List<Character> characterList =
                Stream.iterate(0, x -> x < Character.MAX_VALUE, x -> x + 1)
                        .map(integer -> (char) integer.intValue())
                        .toList();
        for (Character character: characterList) {
            String expected = mapper.writeValueAsString(character);
            Character actual = characterReader.getObject(expected, Character.class, null);

            assertThat(character)
                    .isEqualTo(actual);
        }
    }
}
