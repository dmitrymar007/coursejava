package task15;

@FunctionalInterface
public interface EnrollmentRule {
    RuleResult evaluate(EnrollmentRequest request);

    default String name() {
        return getClass().getSimpleName();
    }

    static EnrollmentRule allOf(EnrollmentRule... rules) {
        return new AllOfRule(rules);
    }

    static EnrollmentRule anyOf(EnrollmentRule... rules) {
        return new AnyOfRule(rules);
    }
}
