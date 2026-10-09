package task9;

import java.util.Optional;

public class AgePolicy implements EnrollmentPolicy {
    private final int minAge;

    public AgePolicy(int minAge) {
        this.minAge = minAge;
    }

    @Override
    public Optional<String> violation(Student student, Course course) {
        return student.age() >= minAge
                ? Optional.empty()
                : Optional.of("age " + student.age() + " < required " + minAge);
    }
}
