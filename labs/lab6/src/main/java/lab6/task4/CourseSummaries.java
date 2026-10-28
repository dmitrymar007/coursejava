package lab6.task4;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Stream;
import lab6.Course;
import lab6.Level;
import lab6.task1.CourseOrder;
import static java.util.stream.Collectors.*;

public class CourseSummaries {
    public record Summary(List<Course> courses, long count, double averageDuration) {
        public Summary {
            courses = List.copyOf(courses);
        }
    }

    public static Map<Level, Summary> byLevel(Stream<Course> courses) {
        return Collections.unmodifiableMap(courses.collect(groupingBy(Course::level, TreeMap::new,
                teeing(toList(), summarizingInt(Course::duration),
                        (group, stats) -> new Summary(CourseOrder.sorted(group),
                                stats.getCount(), stats.getAverage())))));
    }

    public static void main(String[] args) {
        byLevel(Course.examples().stream()).forEach((level, summary) ->
                System.out.println(level + ": " + summary));
    }
}
