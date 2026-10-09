package task15;

import task15.RuleResult.Fail;
import task15.RuleResult.Pass;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        Course spring = new Course(11, "Spring Deep Dive", 80);
        Map<Long, Set<Long>> prerequisites = Map.of(11L, Set.of(10L));

        Student anna = new Student("Anna", 20, Set.of(10L), true, false);
        Student boris = new Student("Boris", 16, Set.of(), false, false);
        Student clara = new Student("Clara", 25, Set.of(10L), false, true);
        Student dan = new Student("Dan", 30, Set.of(10L), true, false);
        Student eve = new Student("Eve", 22, Set.of(10L), true, false);

        EnrollmentRequest request = new EnrollmentRequest(clara, spring, 2, 0, false);
        List<EnrollmentRule> rules = List.of(
                new AgeRule(18),
                new PrerequisiteRule(prerequisites),
                new CapacityRule(2, 1),
                new PaymentRule(),
                new ScholarshipRule(),
                new NoDuplicateRule(),
                EnrollmentRule.anyOf(new PaymentRule(), new ScholarshipRule()),
                EnrollmentRule.allOf(new AgeRule(18), new CapacityRule(2, 1)));
        for (EnrollmentRule rule : rules) {
            System.out.println(rule.name() + " -> " + rule.evaluate(request));
        }

        EnrollmentRule policy = EnrollmentRule.allOf(
                new NoDuplicateRule(),
                new AgeRule(18),
                new PrerequisiteRule(prerequisites),
                EnrollmentRule.anyOf(new PaymentRule(), new ScholarshipRule()),
                new CapacityRule(2, 1));
        EnrollmentEngine engine = new EnrollmentEngine(policy);

        System.out.println(engine.enroll(anna, spring));
        System.out.println(engine.enroll(boris, spring));
        System.out.println(engine.enroll(clara, spring));
        System.out.println(engine.enroll(dan, spring));
        System.out.println(engine.enroll(eve, spring));
        System.out.println(engine.enroll(anna, spring));

        EnrollmentRule notBlacklisted = r ->
                r.student().name().equals("Frank") ? new Fail("blacklisted") : new Pass();
        EnrollmentEngine stricter = new EnrollmentEngine(EnrollmentRule.allOf(policy, notBlacklisted));
        Student frank = new Student("Frank", 30, Set.of(10L), true, false);
        System.out.println(stricter.enroll(frank, spring));
        System.out.println(stricter.enroll(anna, spring));
    }
}
