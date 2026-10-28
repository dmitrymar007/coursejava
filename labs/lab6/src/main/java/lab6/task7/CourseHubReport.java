package lab6.task7;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collector;
import java.util.stream.Stream;
import lab6.Course;
import lab6.Level;

public class CourseHubReport {
    public enum Status { ENROLLED, COMPLETED }

    // Некорректные входные значения допускаются, чтобы включить их в отчёт об аномалиях.
    public record Enrollment(long studentId, String courseCode, Status status) {}
    public record Anomaly(Enrollment event, String reason) {}

    public record CourseStats(String code, Level level, long enrolled, long completed) {
        public double completionRate() {
            return enrolled == 0 ? 0 : (double) completed / enrolled;
        }
    }

    public record Report(Set<Long> students, Map<String, CourseStats> courses,
                         List<CourseStats> top3, Map<Level, Long> byLevel, List<Anomaly> anomalies) {
        public Report {
            students = Collections.unmodifiableSet(new TreeSet<>(students));
            courses = Collections.unmodifiableMap(new TreeMap<>(courses));
            top3 = List.copyOf(top3);
            byLevel = Collections.unmodifiableMap(new TreeMap<>(byLevel));
            anomalies = List.copyOf(anomalies);
        }
    }

    private static final Comparator<CourseStats> RANK = Comparator
            .comparingDouble(CourseStats::completionRate).reversed()
            .thenComparing(Comparator.comparingLong(CourseStats::completed).reversed())
            .thenComparing(CourseStats::code);
    private static final Comparator<Enrollment> EVENT_ORDER = Comparator.nullsFirst(
            Comparator.comparingLong(Enrollment::studentId)
                    .thenComparing(Enrollment::courseCode, Comparator.nullsFirst(Comparator.naturalOrder()))
                    .thenComparing(Enrollment::status, Comparator.nullsFirst(Comparator.naturalOrder())));
    private static final Comparator<Anomaly> ANOMALY_ORDER = Comparator.comparing(Anomaly::reason)
            .thenComparing(Anomaly::event, EVENT_ORDER);

    private static class Accumulator {
        final Map<String, Course> catalog;
        final Map<String, Map<Long, Boolean>> progress = new HashMap<>();
        final List<Anomaly> anomalies = new ArrayList<>();

        Accumulator(Map<String, Course> catalog) {
            this.catalog = catalog;
        }

        void add(Enrollment event) {
            String error = error(event);
            if (error != null) {
                anomalies.add(new Anomaly(event, error));
                return;
            }
            progress.computeIfAbsent(event.courseCode(), key -> new HashMap<>())
                    .merge(event.studentId(), event.status() == Status.COMPLETED, Boolean::logicalOr);
        }

        String error(Enrollment event) {
            if (event == null) return "Отсутствует событие";
            if (event.studentId() <= 0) return "Некорректный studentId";
            if (event.courseCode() == null || event.courseCode().isBlank()) return "Пустой код курса";
            if (!catalog.containsKey(event.courseCode())) return "Неизвестный курс";
            if (event.status() == null) return "Отсутствует статус";
            return null;
        }

        Accumulator merge(Accumulator other) {
            other.progress.forEach((code, students) -> {
                var target = progress.computeIfAbsent(code, key -> new HashMap<>());
                students.forEach((id, completed) -> target.merge(id, completed, Boolean::logicalOr));
            });
            anomalies.addAll(other.anomalies);
            return this;
        }

        Report finish() {
            var students = new TreeSet<Long>();
            var courses = new TreeMap<String, CourseStats>();
            var levels = new TreeMap<Level, Long>();
            for (Level level : Level.values()) levels.put(level, 0L);
            // Цикл яснее нескольких потоков с общими изменяемыми коллекциями.
            for (Course course : catalog.values()) {
                var entries = progress.getOrDefault(course.code(), Map.of());
                students.addAll(entries.keySet());
                long completed = entries.values().stream().filter(Boolean::booleanValue).count();
                courses.put(course.code(), new CourseStats(course.code(), course.level(), entries.size(), completed));
                levels.merge(course.level(), (long) entries.size(), Long::sum);
            }
            var top = courses.values().stream().filter(c -> c.enrolled() > 0).sorted(RANK).limit(3).toList();
            return new Report(students, courses, top, levels,
                    anomalies.stream().sorted(ANOMALY_ORDER).toList());
        }
    }

    public static Report build(List<Course> courses, Stream<Enrollment> events) {
        var catalog = new TreeMap<String, Course>();
        for (Course course : courses) {
            if (catalog.putIfAbsent(course.code(), course) != null)
                throw new IllegalArgumentException("Повтор кода курса: " + course.code());
        }
        return events.collect(Collector.of(() -> new Accumulator(catalog), Accumulator::add,
                Accumulator::merge, Accumulator::finish));
    }

    public static void main(String[] args) {
        if (args.length > 0) {
            long size = Long.parseLong(args[0]);
            // Генератор не хранит входные события; можно запускать с маленьким heap.
            var report = build(Course.examples(), java.util.stream.LongStream.range(0, size)
                    .mapToObj(i -> new Enrollment(i % 1000 + 1, "JAVA",
                            i % 2 == 0 ? Status.COMPLETED : Status.ENROLLED)));
            System.out.println("Событий: " + size + ", студентов: " + report.students().size());
            System.out.println(report.courses().get("JAVA"));
            return;
        }
        var report = build(Course.examples(), Stream.of(
                new Enrollment(1, "JAVA", Status.ENROLLED),
                new Enrollment(1, "JAVA", Status.COMPLETED),
                new Enrollment(2, "JAVA", Status.ENROLLED),
                new Enrollment(2, "SQL", Status.COMPLETED),
                new Enrollment(3, "UNKNOWN", Status.ENROLLED)));
        System.out.println("Студенты: " + report.students());
        report.courses().values().forEach(c ->
                System.out.println(c + ", completion rate: " + c.completionRate()));
        System.out.println("Top-3: " + report.top3());
        System.out.println("По level: " + report.byLevel());
        System.out.println("Аномалии: " + report.anomalies());
    }
}
