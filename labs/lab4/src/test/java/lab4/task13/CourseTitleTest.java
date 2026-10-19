package lab4.task13;

import java.util.Locale;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CourseTitleTest {
    @Test
    void differentDisplayValuesCanHaveSameSearchKey() {
        var a = new CourseTitle("  Java\t BASICS  ");
        var b = new CourseTitle("java\u00a0basics");
        assertNotEquals(a.displayValue(), b.displayValue());
        assertEquals("  Java\t BASICS  ", a.displayValue());
        assertEquals("java basics", a.searchKey());
        assertEquals(a.searchKey(), b.searchKey());
        assertEquals("java", new CourseTitle("\u00a0JAVA\u00a0").searchKey());
    }

    @Test
    void fixedLocaleAndEmptyTitleContract() {
        var original = Locale.getDefault();
        try {
            Locale.setDefault(Locale.forLanguageTag("tr-TR"));
            assertEquals("intro", new CourseTitle("INTRO").searchKey());
        } finally {
            Locale.setDefault(original);
        }
        assertThrows(IllegalArgumentException.class, () -> new CourseTitle(" \t\u00a0"));
    }
}
