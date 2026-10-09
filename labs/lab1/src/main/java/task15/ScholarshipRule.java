package task15;

import task15.RuleResult.Fail;
import task15.RuleResult.Pass;

public final class ScholarshipRule implements EnrollmentRule {
    @Override
    public RuleResult evaluate(EnrollmentRequest request) {
        return request.student().scholarship() ? new Pass() : new Fail("no scholarship");
    }
}
