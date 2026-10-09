package task9;

import java.util.Optional;

public class NoDuplicatePolicy implements EnrollmentPolicy {
    private final Enrollments enrollments;

    public NoDuplicatePolicy(Enrollments enrollments) {
        this.enrollments = enrollments;
    }

    @Override
    public Optional<String> violation(Student student, Course course) {
        return enrollments.isEnrolled(course, student)
                ? Optional.of("already enrolled")
                : Optional.empty();
    }
}
