package task15;

import task15.RuleResult.Fail;
import task15.RuleResult.Pass;

public final class NoDuplicateRule implements EnrollmentRule {
    @Override
    public RuleResult evaluate(EnrollmentRequest request) {
        return request.alreadyEnrolled() ? new Fail("already enrolled") : new Pass();
    }
}
