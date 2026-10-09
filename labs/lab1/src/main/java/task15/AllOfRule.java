package task15;

import task15.RuleResult.Pass;

import java.util.List;

final class AllOfRule extends CompositeRule {
    AllOfRule(EnrollmentRule... rules) {
        super(rules);
    }

    @Override
    protected RuleResult combine(List<RuleResult> results) {
        RuleResult fail = firstFail(results);
        if (fail != null) {
            return fail;
        }
        RuleResult wait = firstWaitList(results);
        return wait != null ? wait : new Pass();
    }
}
