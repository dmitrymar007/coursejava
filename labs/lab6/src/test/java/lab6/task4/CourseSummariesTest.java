package lab6.task4;

import java.util.List;
import java.util.stream.Stream;
import lab6.Course;
import lab6.Level;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CourseSummariesTest {
    @Test
    void groupsCountsAndAveragesInOneCollection() {
        var result = CourseSummaries.byLevel(Course.examples().stream());
        var beginner = result.get(Level.BEGINNER);
        assertEquals(2, beginner.count());
        assertEquals(105.0, beginner.averageDuration());
        assertEquals(List.of("SQL", "JAVA"), beginner.courses().stream().map(Course::code).toList());
        assertEquals(1, result.get(Level.ADVANCED).count());
        assertEquals(180.0, result.get(Level.ADVANCED).averageDuration());
        assertEquals(result, CourseSummaries.byLevel(Course.examples().parallelStream()));
        assertEquals(List.of(Level.BEGINNER, Level.INTERMEDIATE, Level.ADVANCED), List.copyOf(result.keySet()));
    }

    @Test
    void emptyInputAndImmutableResults() {
        assertTrue(CourseSummaries.byLevel(Stream.empty()).isEmpty());
        var result = CourseSummaries.byLevel(Course.examples().stream());
        assertThrows(UnsupportedOperationException.class, result::clear);
        assertThrows(UnsupportedOperationException.class, () -> result.get(Level.BEGINNER).courses().clear());
    }
}
