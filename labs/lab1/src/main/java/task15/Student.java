package task15;

import java.util.Set;

public record Student(String name, int age, Set<Long> completedCourseIds, boolean paid, boolean scholarship) {
}
