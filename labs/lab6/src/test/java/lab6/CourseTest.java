package lab6;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CourseTest {
    @Test
    void validatesAndDefensivelyCopiesModules() {
        var modules = new ArrayList<>(List.of("Java"));
        var course = new Course(" JAVA ", Level.BEGINNER, 0, modules);
        modules.clear();
        assertEquals("JAVA", course.code());
        assertEquals(List.of("Java"), course.modules());
        assertThrows(UnsupportedOperationException.class, () -> course.modules().clear());
        assertThrows(IllegalArgumentException.class, () -> new Course(" ", Level.BEGINNER, 1, List.of()));
        assertThrows(IllegalArgumentException.class, () -> new Course(null, Level.BEGINNER, 1, List.of()));
        assertThrows(IllegalArgumentException.class, () -> new Course("A", Level.BEGINNER, -1, List.of()));
        assertThrows(NullPointerException.class, () -> new Course("A", null, 1, List.of()));
        assertThrows(NullPointerException.class, () -> new Course("A", Level.BEGINNER, 1, null));
    }
}
