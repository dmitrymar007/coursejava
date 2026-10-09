package task9;

import java.util.List;

public class EnrollmentService {
    private final List<EnrollmentPolicy> policies;
    private final Enrollments enrollments;

    public EnrollmentService(List<EnrollmentPolicy> policies, Enrollments enrollments) {
        this.policies = List.copyOf(policies);
        this.enrollments = enrollments;
    }

    public List<String> enroll(Student student, Course course) {
        List<String> violations = policies.stream()
                .map(policy -> policy.violation(student, course))
                .flatMap(java.util.Optional::stream)
                .toList();
        if (violations.isEmpty()) {
            enrollments.add(course, student);
        }
        return violations;
    }
}
