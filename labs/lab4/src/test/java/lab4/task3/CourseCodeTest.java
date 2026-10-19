package lab4.task3;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

class CourseCodeTest {
    @Test
    void extractsGroupsIncludingLeadingZero() {
        assertEquals(new CourseCode("JAVA", "101"), CourseCode.parse("JAVA-101"));
        assertEquals(new CourseCode("SQL", "007"), CourseCode.parse("SQL-007"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"java-101", "ЯВА-101", "JAVA-١٠١", "JAVA-１０１", " JAVA-101",
            "JAVA-101 ", "JAVA-101\n", "JAVA-10", "JAVA-1001", "xJAVA-101x", "JAVA–101"})
    void rejectsInvalidCode(String value) {
        assertThrows(IllegalArgumentException.class, () -> CourseCode.parse(value));
    }
}
