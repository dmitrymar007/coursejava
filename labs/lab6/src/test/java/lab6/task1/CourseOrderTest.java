package lab6.task1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import lab6.Course;
import lab6.Level;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CourseOrderTest {
    @Test
    void explicitlyOrdersAllFieldsAndPreservesDuplicates() {
        var a = new Course("A", Level.BEGINNER, 10, List.of("a"));
        var b = new Course("A", Level.BEGINNER, 10, List.of("a", "b"));
        var c = new Course("A", Level.BEGINNER, 10, List.of("b"));
        var d = new Course("B", Level.BEGINNER, 10, List.of());
        var e = new Course("A", Level.BEGINNER, 20, List.of());
        var f = new Course("A", Level.ADVANCED, 0, List.of());
        var expected = List.of(a, a, b, c, d, e, f);
        var input = new ArrayList<>(expected);
        var random = new Random(6);
        for (int i = 0; i < 40; i++) {
            Collections.shuffle(input, random);
            var snapshot = List.copyOf(input);
            assertEquals(expected, CourseOrder.sorted(input));
            assertEquals(snapshot, input);
        }
    }

    @Test
    void handlesEmptyInputAndReturnsImmutableList() {
        assertEquals(List.of(), CourseOrder.sorted(List.of()));
        assertThrows(UnsupportedOperationException.class, () -> CourseOrder.sorted(Course.examples()).clear());
    }
}
