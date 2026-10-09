package task9;

import java.util.Optional;

@FunctionalInterface
public interface EnrollmentPolicy {
    Optional<String> violation(Student student, Course course);
}
