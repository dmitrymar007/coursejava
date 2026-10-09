package task15;

import task15.RuleResult.Fail;
import task15.RuleResult.Pass;
import task15.RuleResult.WaitList;

public final class CapacityRule implements EnrollmentRule {
    private final int seats;
    private final int waitListLimit;

    public CapacityRule(int seats, int waitListLimit) {
        this.seats = seats;
        this.waitListLimit = waitListLimit;
    }

    @Override
    public RuleResult evaluate(EnrollmentRequest request) {
        if (request.enrolledCount() < seats) {
            return new Pass();
        }
        if (request.waitListCount() < waitListLimit) {
            return new WaitList("no seats left", request.waitListCount() + 1);
        }
        return new Fail("course and waiting list are full");
    }
}
