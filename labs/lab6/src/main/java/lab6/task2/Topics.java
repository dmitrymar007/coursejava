package lab6.task2;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Stream;
import lab6.Course;
import lab6.task1.CourseOrder;
import static java.util.stream.Collectors.*;

public class Topics {
    private static Stream<String> topics(Course course) {
        return course.modules().stream()
                .map(s -> s.replaceAll("(?U)\\s+", " ").strip().toLowerCase(Locale.ROOT))
                .filter(s -> !s.isEmpty()).distinct();
    }

    public static Set<String> unique(List<Course> courses) {
        var result = courses.stream().flatMap(Topics::topics).collect(toCollection(TreeSet::new));
        return Collections.unmodifiableSet(result);
    }

    public static Map<String, List<Course>> index(List<Course> courses) {
        return Collections.unmodifiableMap(courses.stream().distinct()
                .flatMap(course -> topics(course).map(topic -> Map.entry(topic, course)))
                .collect(groupingBy(Map.Entry::getKey, TreeMap::new,
                        mapping(Map.Entry::getValue,
                                collectingAndThen(toList(), CourseOrder::sorted)))));
    }

    public static void main(String[] args) {
        System.out.println(unique(Course.examples()));
        index(Course.examples()).forEach((topic, courses) ->
                System.out.println(topic + " -> " + courses.stream().map(Course::code).toList()));
    }
}
