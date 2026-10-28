package lab6.task5;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;
import lab6.Course;
import lab6.Level;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LongestCourseTest {
    @Test
    void findsTransformsAndFallsBackWithoutGet() {
        assertEquals("JVM: 180 мин.", LongestCourse.describe(Course.examples().stream()));
        assertTrue(LongestCourse.find(Stream.empty()).isEmpty());
        assertEquals("Курсов нет", LongestCourse.describe(Stream.empty()));
        var only = new Course("ONLY", Level.BEGINNER, 0, List.of());
        assertEquals(only, LongestCourse.find(Stream.of(only)).orElseThrow());
    }

    @Test
    void resolvesEqualDurationsDeterministically() {
        var input = new ArrayList<>(Course.examples());
        var expected = LongestCourse.find(input.stream());
        Collections.reverse(input);
        assertEquals(expected, LongestCourse.find(input.stream()));
        assertEquals(expected, LongestCourse.find(input.parallelStream()));
    }
}
