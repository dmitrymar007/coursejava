package task12;

import task12.EnrollmentResult.Accepted;
import task12.EnrollmentResult.PendingPayment;
import task12.EnrollmentResult.Rejected;
import task12.EnrollmentResult.WaitListed;

import java.util.List;

public class Main {
    static String describe(EnrollmentResult result) {
        return switch (result) {
            case Accepted a -> a.student() + " enrolled in #" + a.courseId();
            case Rejected(String student, long courseId, String reason) ->
                    student + " rejected from #" + courseId + ": " + reason;
            case WaitListed w -> w.student() + " is #" + w.position() + " in the waiting list for #" + w.courseId();
            case PendingPayment p -> p.student() + " must pay " + p.amount() + " for #" + p.courseId();
        };
    }

    public static void main(String[] args) {
        List<EnrollmentResult> results = List.of(
                new Accepted("Anna", 10),
                new Rejected("Boris", 10, "too young"),
                new WaitListed("Clara", 10, 2),
                new PendingPayment("Dan", 10, 500));

        for (EnrollmentResult result : results) {
            System.out.println(describe(result));
        }

    }
}
