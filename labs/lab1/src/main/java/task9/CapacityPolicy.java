package task9;

import java.util.Optional;

public class CapacityPolicy implements EnrollmentPolicy {
    private final int seats;
    private final Enrollments enrollments;

    public CapacityPolicy(int seats, Enrollments enrollments) {
        this.seats = seats;
        this.enrollments = enrollments;
    }

    @Override
    public Optional<String> violation(Student student, Course course) {
        int taken = enrollments.count(course);
        return taken < seats
                ? Optional.empty()
                : Optional.of("no seats left: " + taken + "/" + seats);
    }
}
