package task13;

import java.util.Set;

public record Student(long id, Set<Long> completedCourseIds) {
    public Student {
        completedCourseIds = Set.copyOf(completedCourseIds);
    }
}
