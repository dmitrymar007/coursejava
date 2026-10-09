package task15;

import task15.RuleResult.Fail;
import task15.RuleResult.Pass;
import task15.RuleResult.WaitList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class EnrollmentEngine {
    private final EnrollmentRule rule;
    private final Map<Long, List<String>> enrolled = new HashMap<>();
    private final Map<Long, List<String>> waiting = new HashMap<>();

    public EnrollmentEngine(EnrollmentRule rule) {
        this.rule = rule;
    }

    public RuleResult enroll(Student student, Course course) {
        List<String> enrolledNames = enrolled.computeIfAbsent(course.id(), id -> new ArrayList<>());
        List<String> waitingNames = waiting.computeIfAbsent(course.id(), id -> new ArrayList<>());

        RuleResult result = rule.evaluate(new EnrollmentRequest(
                student, course, enrolledNames.size(), waitingNames.size(),
                enrolledNames.contains(student.name())));

        switch (result) {
            case Pass p -> enrolledNames.add(student.name());
            case WaitList w -> waitingNames.add(student.name());
            case Fail f -> { }
        }
        return result;
    }
}
