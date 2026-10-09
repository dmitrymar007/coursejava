package task15;

public sealed interface RuleResult
        permits RuleResult.Pass, RuleResult.Fail, RuleResult.WaitList {

    record Pass() implements RuleResult {
    }

    record Fail(String reason) implements RuleResult {
    }

    record WaitList(String reason, int position) implements RuleResult {
    }
}
