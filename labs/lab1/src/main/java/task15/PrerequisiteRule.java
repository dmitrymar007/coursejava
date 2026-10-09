package task15;

import task15.RuleResult.Fail;
import task15.RuleResult.Pass;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public final class PrerequisiteRule implements EnrollmentRule {
    private final Map<Long, Set<Long>> prerequisitesByCourseId;

    public PrerequisiteRule(Map<Long, Set<Long>> prerequisitesByCourseId) {
        this.prerequisitesByCourseId = Map.copyOf(prerequisitesByCourseId);
    }

    @Override
    public RuleResult evaluate(EnrollmentRequest request) {
        Set<Long> missing = new TreeSet<>(prerequisitesByCourseId.getOrDefault(request.course().id(), Set.of()));
        missing.removeAll(request.student().completedCourseIds());
        return missing.isEmpty() ? new Pass() : new Fail("missing prerequisites " + missing);
    }
}
