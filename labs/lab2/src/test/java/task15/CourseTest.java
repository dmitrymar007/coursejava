package task15;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CourseTest {

    @ParameterizedTest(name = "[{index}] id={0}, title=\"{1}\", hours={2}, capacity={3}: нарушено поле {4}")
    @CsvSource(quoteCharacter = '"', value = {
            "0, Java,  32,  10, id",
            "1, \" \", 32,  10, title",
            "1, Java,  301, 10, durationHours",
            "1, Java,  32,  0,  capacity",
    })
    void constructor_invalidData_throwsValidationExceptionNamingField(
            long id, String title, int hours, int capacity, String field) {
        ValidationException e = assertThrows(ValidationException.class,
                () -> new Course(id, title, hours, capacity, true, Set.of()));

        assertTrue(e.getMessage().startsWith(field), e.getMessage());
    }

    @Test
    void constructor_nullPrerequisites_isProgrammingErrorNotValidation() {
        assertThrows(NullPointerException.class,
                () -> new Course(1, "Java", 32, 10, true, null));
    }
}
