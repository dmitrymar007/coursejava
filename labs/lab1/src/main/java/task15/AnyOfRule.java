package task15;

import task15.RuleResult.Fail;
import task15.RuleResult.Pass;
//import task15.RuleResult.WaitList;

import java.util.List;

final class AnyOfRule extends CompositeRule {
    AnyOfRule(EnrollmentRule... rules) {
        super(rules);
    }

    @Override
    protected RuleResult combine(List<RuleResult> results) {
        if (anyPass(results)) {
            return new Pass();
        }
        RuleResult wait = firstWaitList(results);
        if (wait != null) {
            return wait;
        }
        String reasons = results.stream()
                .map(r -> ((Fail) r).reason())
                .reduce((a, b) -> a + "; " + b)
                .orElse("");
        return new Fail("none of: " + reasons);
    }
}
