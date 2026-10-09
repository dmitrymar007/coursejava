package task10;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CourseCodeTest {

    @ParameterizedTest(name = "[{index}] \"{0}\" нормализуется в \"{1}\"")
    @CsvSource(delimiter = '|', quoteCharacter = '"', value = {
            "JAVA-101     | JAVA-101",   // уже нормальный вид
            "java-101     | JAVA-101",   // нижний регистр
            "JaVa-101     | JAVA-101",   // смешанный регистр
            "\"  JAVA-101 \" | JAVA-101", // пробелы по краям
            "cs-007       | CS-007",     // короткий префикс (2 буквы) и ведущие нули
    })
    void of_validInput_returnsNormalizedCode(String raw, String expected) {
        // Act
        CourseCode code = CourseCode.of(raw);

        // Assert
        assertEquals(expected, code.value());
    }

    @ParameterizedTest(name = "[{index}] недопустимый код: \"{0}\"")
    @NullAndEmptySource
    @ValueSource(strings = {
            "   ",         // только пробелы
            "JAVA101",     // нет дефиса
            "JAVA_101",    // другой разделитель
            "J-101",       // слишком короткий префикс
            "JAVAA-101",   // слишком длинный префикс
            "JAVA-10",     // слишком мало цифр
            "JAVA-1010",   // слишком много цифр
            "JAVA-ABC",    // вместо цифр буквы
            "JA VA-101",   // пробел внутри
            "ЯВА-101",     // кириллица
            "JAVA-١٢٣",    // не-ASCII цифры
    })
    void of_invalidInput_throwsIllegalArgument(String raw) {
        assertThrows(IllegalArgumentException.class, () -> CourseCode.of(raw));
    }

    @Test
    void of_equalAfterNormalization_areEqual() {
        // Arrange
        CourseCode a = CourseCode.of("java-101");
        CourseCode b = CourseCode.of(" JAVA-101 ");

        // Assert
        assertEquals(a, b);
    }
}
