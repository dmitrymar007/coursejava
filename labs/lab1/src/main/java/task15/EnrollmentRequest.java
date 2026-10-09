package task15;

public record EnrollmentRequest(Student student, Course course,
                                int enrolledCount, int waitListCount, boolean alreadyEnrolled) {
}
