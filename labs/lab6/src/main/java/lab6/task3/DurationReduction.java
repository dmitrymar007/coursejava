package lab6.task3;

import java.util.stream.Stream;
import lab6.Course;

public class DurationReduction {
    public record Duration(long minutes) {
        public static final Duration ZERO = new Duration(0);

        public Duration {
            if (minutes < 0) throw new IllegalArgumentException("Отрицательная длительность");
        }

        public Duration plus(Duration other) {
            return new Duration(Math.addExact(minutes, other.minutes));
        }
    }

    public static long sum(Stream<Course> courses) {
        return courses.map(c -> (long) c.duration()).reduce(0L, Math::addExact);
    }

    public static Duration combine(Stream<Duration> durations) {
        return durations.reduce(Duration.ZERO, Duration::plus);
    }

    // Намеренно неверно: 10 не является нейтральным элементом сложения.
    public static long wrongSum(Stream<Course> courses) {
        return courses.map(c -> (long) c.duration()).reduce(10L, Long::sum);
    }

    public static void main(String[] args) {
        var courses = Course.examples();
        System.out.println("Сумма: " + sum(courses.stream()));
        System.out.println("Value objects: " + combine(courses.stream().map(c -> new Duration(c.duration()))));
        System.out.println("Неверная identity, sequential: " + wrongSum(courses.stream()));
        System.out.println("Неверная identity, parallel: " + wrongSum(courses.parallelStream()));
    }
}
