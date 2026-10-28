package lab6.task3;

import java.util.List;
import java.util.stream.Stream;
import lab6.Course;
import lab6.Level;
import lab6.task3.DurationReduction.Duration;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DurationReductionTest {
    @Test
    void sumsWithoutIntOverflowSequentiallyAndInParallel() {
        var input = List.of(new Course("A", Level.BEGINNER, Integer.MAX_VALUE, List.of()),
                new Course("B", Level.BEGINNER, Integer.MAX_VALUE, List.of()));
        assertEquals(4_294_967_294L, DurationReduction.sum(input.stream()));
        assertEquals(DurationReduction.sum(input.stream()), DurationReduction.sum(input.parallelStream()));
        assertEquals(0, DurationReduction.sum(Stream.empty()));
        assertEquals(Duration.ZERO, DurationReduction.combine(Stream.empty()));
    }

    @Test
    void valueObjectsHaveIdentityAndAssociativeAddition() {
        var a = new Duration(10);
        var b = new Duration(20);
        var c = new Duration(30);
        assertEquals(a, Duration.ZERO.plus(a));
        assertEquals(a, a.plus(Duration.ZERO));
        assertEquals(a.plus(b).plus(c), a.plus(b.plus(c)));
        assertEquals(new Duration(60), DurationReduction.combine(Stream.of(a, b, c).parallel()));
        assertEquals(new Duration(10), a);
        assertThrows(IllegalArgumentException.class, () -> new Duration(-1));
        assertThrows(ArithmeticException.class, () -> new Duration(Long.MAX_VALUE).plus(a));
    }

    @Test
    void demonstratesNonNeutralIdentityEvenOnEmptyInput() {
        assertEquals(10, DurationReduction.wrongSum(Stream.empty()));
        assertEquals(DurationReduction.sum(Course.examples().stream()) + 10,
                DurationReduction.wrongSum(Course.examples().stream()));
        assertNotEquals(DurationReduction.sum(Course.examples().parallelStream()),
                DurationReduction.wrongSum(Course.examples().parallelStream()));
    }
}
