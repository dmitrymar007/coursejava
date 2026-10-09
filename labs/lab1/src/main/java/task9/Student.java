package task9;

import java.util.Set;

public record Student(String name, int age, Set<Long> completedCourseIds) {
}
