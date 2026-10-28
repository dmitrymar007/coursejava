package lab6.task6;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lab6.Course;
import lab6.Level;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DurationStatisticsTest {
    @Test
    void matchesStandardCollectorIncludingEmptyAndLargeSum() {
        var random = new Random(6);
        var input = new ArrayList<Course>();
        for (int i = 0; i < 10_000; i++)
            input.add(new Course("C" + i, Level.BEGINNER, random.nextInt(Integer.MAX_VALUE), List.of()));
        var standard = input.stream().collect(Collectors.summarizingInt(Course::duration));
        var expected = new DurationStatistics.Stats(standard.getCount(), standard.getSum(),
                standard.getMin(), standard.getMax());
        assertTrue(expected.sum() > Integer.MAX_VALUE);
        assertEquals(expected, input.stream().collect(DurationStatistics.collector()));
        assertEquals(expected, input.parallelStream().collect(DurationStatistics.collector()));
        assertEquals(new DurationStatistics.Stats(0, 0, Integer.MAX_VALUE, Integer.MIN_VALUE),
                Stream.<Course>empty().collect(DurationStatistics.collector()));
        assertEquals(new DurationStatistics.Stats(1, 0, 0, 0),
                Stream.of(new Course("ZERO", Level.BEGINNER, 0, List.of())).collect(DurationStatistics.collector()));
    }

    @Test
    void combinerSupportsExplicitPartitionsAndEmptyIdentity() {
        verifyCombiner(DurationStatistics.collector());
    }

    private static <A> void verifyCombiner(Collector<Course, A, DurationStatistics.Stats> collector) {
        var courses = Course.examples();
        A left = collector.supplier().get(), right = collector.supplier().get();
        courses.subList(0, 2).forEach(c -> collector.accumulator().accept(left, c));
        courses.subList(2, 4).forEach(c -> collector.accumulator().accept(right, c));
        A merged = collector.combiner().apply(left, right);
        var expected = courses.stream().collect(collector);
        assertEquals(expected, collector.finisher().apply(merged));
        assertEquals(expected, collector.finisher().apply(
                collector.combiner().apply(collector.supplier().get(), merged)));
        assertEquals(expected, collector.finisher().apply(
                collector.combiner().apply(merged, collector.supplier().get())));
        assertEquals(expected, courses.stream().collect(collector));
        assertFalse(collector.characteristics().contains(Collector.Characteristics.CONCURRENT));
        assertFalse(collector.characteristics().contains(Collector.Characteristics.IDENTITY_FINISH));
    }
}
