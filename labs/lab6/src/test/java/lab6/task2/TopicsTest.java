package lab6.task2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import lab6.Course;
import lab6.Level;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TopicsTest {
    @Test
    void normalizesFlattensAndBuildsUniqueReverseIndex() {
        var a = new Course("A", Level.BEGINNER, 1,
                List.of(" JAVA ", "java", "\tDATA\u00a0  BASES\n", " ", "I"));
        var b = new Course("B", Level.BEGINNER, 1, List.of("Java"));
        var input = List.of(b, a, a);
        assertEquals(Set.of("java", "data bases", "i"), Topics.unique(input));
        assertEquals(List.of("data bases", "i", "java"), new ArrayList<>(Topics.index(input).keySet()));
        assertEquals(List.of(a, b), Topics.index(input).get("java"));
        assertEquals(List.of(a), Topics.index(input).get("data bases"));
        var reversed = new ArrayList<>(input);
        Collections.reverse(reversed);
        assertEquals(Topics.index(input), Topics.index(reversed));
    }

    @Test
    void handlesEmptyModulesAndProtectsResults() {
        var empty = List.of(new Course("A", Level.BEGINNER, 0, List.of()));
        assertTrue(Topics.unique(empty).isEmpty());
        assertTrue(Topics.index(empty).isEmpty());
        assertTrue(Topics.index(List.of()).isEmpty());
        assertThrows(UnsupportedOperationException.class, () -> Topics.unique(Course.examples()).clear());
        var index = Topics.index(Course.examples());
        assertThrows(UnsupportedOperationException.class, index::clear);
        assertThrows(UnsupportedOperationException.class, () -> index.get("java").clear());
    }
}
