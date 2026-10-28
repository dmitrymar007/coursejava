package lab6.task7;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;
import lab6.Course;
import lab6.Level;
import lab6.task7.CourseHubReport.Enrollment;
import static lab6.task7.CourseHubReport.Status.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import static org.junit.jupiter.api.Assertions.*;

class CourseHubReportTest {
    @TempDir Path dir;

    @Test
    void deduplicatesStudentsAndEnrollmentsAndKeepsCompletion() {
        var input = List.of(new Enrollment(1, "JAVA", COMPLETED),
                new Enrollment(1, "JAVA", ENROLLED), new Enrollment(1, "JAVA", COMPLETED),
                new Enrollment(2, "JAVA", ENROLLED), new Enrollment(1, "SQL", COMPLETED));
        var read = new AtomicInteger();
        var report = CourseHubReport.build(Course.examples(), input.stream().peek(e -> read.incrementAndGet()));
        assertEquals(input.size(), read.get());
        assertEquals(Set.of(1L, 2L), report.students());
        assertEquals(new CourseHubReport.CourseStats("JAVA", Level.BEGINNER, 2, 1), report.courses().get("JAVA"));
        assertEquals(0.5, report.courses().get("JAVA").completionRate());
        assertEquals(1.0, report.courses().get("SQL").completionRate());
        assertEquals(0.0, report.courses().get("JVM").completionRate());
        assertEquals(List.of("SQL", "JAVA"), report.top3().stream().map(CourseHubReport.CourseStats::code).toList());
        assertEquals(Map.of(Level.BEGINNER, 3L, Level.INTERMEDIATE, 0L, Level.ADVANCED, 0L), report.byLevel());
        assertTrue(report.anomalies().isEmpty());
    }

    @Test
    void ranksByRateThenCompletedCountThenCodeAndLimitsToThree() {
        var catalog = Stream.of("A", "B", "C", "D", "E")
                .map(code -> new Course(code, Level.BEGINNER, 1, List.of())).toList();
        var report = CourseHubReport.build(catalog, Stream.of(
                new Enrollment(1, "A", COMPLETED), new Enrollment(1, "B", COMPLETED),
                new Enrollment(2, "B", COMPLETED), new Enrollment(1, "C", COMPLETED),
                new Enrollment(1, "D", ENROLLED), new Enrollment(2, "D", COMPLETED)));
        assertEquals(List.of("B", "A", "C"), report.top3().stream().map(CourseHubReport.CourseStats::code).toList());
    }

    @Test
    void recordsEveryInvalidEventWithoutPollutingMetrics() {
        var events = Arrays.asList(null, new Enrollment(0, "JAVA", ENROLLED),
                new Enrollment(1, null, ENROLLED), new Enrollment(1, " ", ENROLLED),
                new Enrollment(1, "UNKNOWN", ENROLLED), new Enrollment(1, "JAVA", null),
                new Enrollment(1, "UNKNOWN", ENROLLED));
        var report = CourseHubReport.build(Course.examples(), events.stream());
        assertEquals(events.size(), report.anomalies().size());
        assertEquals(2, report.anomalies().stream().filter(a -> a.reason().equals("Неизвестный курс")).count());
        assertTrue(report.students().isEmpty());
        assertTrue(report.top3().isEmpty());
        assertTrue(report.courses().values().stream().allMatch(c -> c.enrolled() == 0));
    }

    @Test
    void isDeterministicAcrossPermutationsAndParallelPartitions() {
        var events = new ArrayList<Enrollment>();
        for (int i = 0; i < 1000; i++) {
            String code = Course.examples().get(i % 4).code();
            events.add(new Enrollment(i % 27 + 1, code, i % 3 == 0 ? COMPLETED : ENROLLED));
        }
        events.addAll(Arrays.asList(null, new Enrollment(-1, null, null), new Enrollment(1, "JAVA", null)));
        var catalog = new ArrayList<>(Course.examples());
        var expected = CourseHubReport.build(catalog, events.stream());
        var random = new Random(6);
        for (int i = 0; i < 15; i++) {
            Collections.shuffle(events, random);
            Collections.shuffle(catalog, random);
            assertEquals(expected, CourseHubReport.build(catalog, events.stream()));
            assertEquals(expected, CourseHubReport.build(catalog, events.parallelStream()));
            assertEquals(List.of("JAVA", "JVM", "SQL", "STREAM"), List.copyOf(expected.courses().keySet()));
        }
    }

    @Test
    void handlesEmptyInputAndRejectsAmbiguousCatalog() {
        var empty = CourseHubReport.build(List.of(), Stream.empty());
        assertTrue(empty.students().isEmpty());
        assertTrue(empty.courses().isEmpty());
        assertTrue(empty.top3().isEmpty());
        assertTrue(empty.anomalies().isEmpty());
        assertEquals(Map.of(Level.BEGINNER, 0L, Level.INTERMEDIATE, 0L, Level.ADVANCED, 0L), empty.byLevel());
        var course = Course.examples().getFirst();
        assertThrows(IllegalArgumentException.class,
                () -> CourseHubReport.build(List.of(course, course), Stream.empty()));
        var report = CourseHubReport.build(List.of(), Stream.of(new Enrollment(1, "JAVA", COMPLETED)));
        assertEquals(1, report.anomalies().size());
    }

    @Test
    void resultIsImmutableAndIndependentOfLaterCalls() {
        var report = CourseHubReport.build(Course.examples(), Stream.of(new Enrollment(1, "JAVA", COMPLETED)));
        assertThrows(UnsupportedOperationException.class, () -> report.students().clear());
        assertThrows(UnsupportedOperationException.class, () -> report.courses().clear());
        assertThrows(UnsupportedOperationException.class, () -> report.top3().clear());
        assertThrows(UnsupportedOperationException.class, () -> report.byLevel().clear());
        assertThrows(UnsupportedOperationException.class, () -> report.anomalies().clear());
        assertTrue(CourseHubReport.build(Course.examples(), Stream.empty()).students().isEmpty());
        assertEquals(Set.of(1L), report.students());
    }

    @Test
    void processesTwoMillionEventsWith16MiBHeap() throws Exception {
        Path log = dir.resolve("large-report.log");
        var process = new ProcessBuilder(Path.of(System.getProperty("java.home"), "bin", "java").toString(),
                "-Xmx16m", "-Dstdout.encoding=UTF-8", "-Dstderr.encoding=UTF-8",
                "-cp", System.getProperty("java.class.path"),
                CourseHubReport.class.getName(), "2000000")
                .redirectErrorStream(true).redirectOutput(log.toFile()).start();
        try {
            assertTrue(process.waitFor(30, TimeUnit.SECONDS), "Обработка потока не завершилась");
            String output = Files.readString(log);
            assertEquals(0, process.exitValue(), output);
            assertTrue(output.contains("2000000"), output);
            assertTrue(output.contains("1000"), output);
            assertTrue(output.contains("enrolled=1000, completed=500"), output);
        } finally {
            process.destroyForcibly();
        }
    }
}
