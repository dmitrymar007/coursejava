package task9;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Enrollments {
    private final Map<Long, Set<String>> byCourse = new HashMap<>();

    public int count(Course course) {
        return byCourse.getOrDefault(course.id(), Set.of()).size();
    }

    public boolean isEnrolled(Course course, Student student) {
        return byCourse.getOrDefault(course.id(), Set.of()).contains(student.name());
    }

    public void add(Course course, Student student) {
        byCourse.computeIfAbsent(course.id(), id -> new HashSet<>()).add(student.name());
    }
}
