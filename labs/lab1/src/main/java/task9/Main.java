package task9;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        Course spring = new Course(11, "Spring Deep Dive", 80);

        Student anna = new Student("Anna", 20, Set.of(10L));
        Student boris = new Student("Boris", 16, Set.of());

        Enrollments enrollments = new Enrollments();
        EnrollmentService service = new EnrollmentService(List.of(
                new AgePolicy(18),
                new PrerequisitePolicy(Map.of(11L, Set.of(10L))),
                new CapacityPolicy(1, enrollments)), enrollments);

        System.out.println(service.enroll(anna, spring));
        System.out.println(service.enroll(boris, spring));

        EnrollmentPolicy nameRequired = (student, course) ->
                student.name().isBlank() ? Optional.of("name is blank") : Optional.empty();
        EnrollmentService stricter = new EnrollmentService(List.of(
                new AgePolicy(18),
                new PrerequisitePolicy(Map.of(11L, Set.of(10L))),
                new CapacityPolicy(1, enrollments),
                new NoDuplicatePolicy(enrollments),
                nameRequired), enrollments);

        System.out.println(stricter.enroll(anna, spring));
    }
}
