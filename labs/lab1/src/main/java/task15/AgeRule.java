package task15;

import task15.RuleResult.Fail;
import task15.RuleResult.Pass;

public final class AgeRule implements EnrollmentRule {
    private final int minAge;

    public AgeRule(int minAge) {
        this.minAge = minAge;
    }

    @Override
    public RuleResult evaluate(EnrollmentRequest request) {
        int age = request.student().age();
        return age >= minAge ? new Pass() : new Fail("age " + age + " < " + minAge);
    }
}
