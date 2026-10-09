package task12;

public sealed interface EnrollmentResult
        permits EnrollmentResult.Accepted,
                EnrollmentResult.Rejected,
                EnrollmentResult.WaitListed,
                EnrollmentResult.PendingPayment {

    record Accepted(String student, long courseId) implements EnrollmentResult {
    }

    record Rejected(String student, long courseId, String reason) implements EnrollmentResult {
    }

    record WaitListed(String student, long courseId, int position) implements EnrollmentResult {
    }

    record PendingPayment(String student, long courseId, int amount) implements EnrollmentResult {
    }
}
