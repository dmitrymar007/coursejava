package lab6.task6;

import java.util.stream.Collector;
import lab6.Course;

public class DurationStatistics {
    // Для пустого потока используются те же min/max, что у summarizingInt.
    public record Stats(long count, long sum, int min, int max) {}

    private static class Accumulator {
        long count, sum;
        int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;

        void add(Course course) {
            count++;
            sum += course.duration();
            min = Math.min(min, course.duration());
            max = Math.max(max, course.duration());
        }

        Accumulator merge(Accumulator other) {
            count += other.count;
            sum += other.sum;
            min = Math.min(min, other.min);
            max = Math.max(max, other.max);
            return this;
        }

        Stats finish() {
            return new Stats(count, sum, min, max);
        }
    }

    public static Collector<Course, ?, Stats> collector() {
        return Collector.of(Accumulator::new, Accumulator::add, Accumulator::merge,
                Accumulator::finish, Collector.Characteristics.UNORDERED);
    }

    public static void main(String[] args) {
        System.out.println(Course.examples().stream().collect(collector()));
        System.out.println(Course.examples().stream()
                .collect(java.util.stream.Collectors.summarizingInt(Course::duration)));
    }
}
