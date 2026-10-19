package lab4.task10;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

class DateParserTest {
    @Test
    void acceptsLeapDate() {
        assertEquals(LocalDate.of(2024, 2, 29), DateParser.parse("startDate", "29.02.2024"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"29.02.2023", "31.04.2024", "01.13.2024", "1.01.2024", "01.01.2024x", " 01.01.2024", ""})
    void rejectsInvalidDateAndPreservesContext(String value) {
        var error = assertThrows(InvalidDateFieldException.class, () -> DateParser.parse("startDate", value));
        assertTrue(error.getMessage().contains("startDate"));
        assertTrue(error.getMessage().contains("'" + value + "'"));
        assertInstanceOf(DateTimeParseException.class, error.getCause());
    }

    @Test
    void nullHasFieldContextWithoutInventedCause() {
        var error = assertThrows(InvalidDateFieldException.class, () -> DateParser.parse("startDate", null));
        assertTrue(error.getMessage().contains("'null'"));
        assertNull(error.getCause());
    }
}
