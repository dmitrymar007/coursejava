package lab6.task5;

import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Stream;
import lab6.Course;
import lab6.task1.CourseOrder;

public class LongestCourse {
    public static Optional<Course> find(Stream<Course> courses) {
        return courses.max(Comparator.comparingInt(Course::duration).thenComparing(CourseOrder.ORDER));
    }

    public static String describe(Stream<Course> courses) {
        return find(courses).map(c -> c.code() + ": " + c.duration() + " мин.")
                .orElse("Курсов нет");
    }

    public static void main(String[] args) {
        System.out.println(describe(Course.examples().stream()));
        System.out.println(describe(Stream.empty()));
    }
}
