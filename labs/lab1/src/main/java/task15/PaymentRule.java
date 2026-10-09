package task15;

import task15.RuleResult.Fail;
import task15.RuleResult.Pass;

public final class PaymentRule implements EnrollmentRule {
    @Override
    public RuleResult evaluate(EnrollmentRequest request) {
        return request.student().paid() ? new Pass() : new Fail("not paid");
    }
}
