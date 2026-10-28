package lab6.task1;

import java.util.Comparator;
import java.util.List;
import lab6.Course;

public class CourseOrder {
    public static final Comparator<Course> ORDER = Comparator.comparing(Course::level)
            .thenComparingInt(Course::duration)
            .thenComparing(Course::code)
            .thenComparing(Course::modules, CourseOrder::compareModules);

    private static int compareModules(List<String> left, List<String> right) {
        for (int i = 0; i < Math.min(left.size(), right.size()); i++) {
            int comparison = left.get(i).compareTo(right.get(i));
            if (comparison != 0) return comparison;
        }
        return Integer.compare(left.size(), right.size());
    }

    public static List<Course> sorted(List<Course> courses) {
        return courses.stream().sorted(ORDER).toList();
    }

    public static void main(String[] args) {
        sorted(Course.examples()).forEach(System.out::println);
    }
}
