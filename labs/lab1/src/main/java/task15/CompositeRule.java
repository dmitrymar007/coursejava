package task15;

import task15.RuleResult.Fail;
import task15.RuleResult.Pass;
import task15.RuleResult.WaitList;

import java.util.List;

abstract class CompositeRule implements EnrollmentRule {
    private final List<EnrollmentRule> children;

    protected CompositeRule(EnrollmentRule... rules) {
        if (rules.length == 0) {
            throw new IllegalArgumentException("composite needs at least one rule");
        }
        this.children = List.of(rules);
    }

    @Override
    public final RuleResult evaluate(EnrollmentRequest request) {
        List<RuleResult> results = children.stream().map(rule -> rule.evaluate(request)).toList();
        return combine(results);
    }

    protected abstract RuleResult combine(List<RuleResult> results);

    protected static RuleResult firstFail(List<RuleResult> results) {
        return results.stream().filter(r -> r instanceof Fail).findFirst().orElse(null);
    }

    protected static RuleResult firstWaitList(List<RuleResult> results) {
        return results.stream().filter(r -> r instanceof WaitList).findFirst().orElse(null);
    }

    protected static boolean anyPass(List<RuleResult> results) {
        return results.stream().anyMatch(r -> r instanceof Pass);
    }
}
