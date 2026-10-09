package task9;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

public class PrerequisitePolicy implements EnrollmentPolicy {
    private final Map<Long, Set<Long>> prerequisitesByCourseId;

    public PrerequisitePolicy(Map<Long, Set<Long>> prerequisitesByCourseId) {
        this.prerequisitesByCourseId = Map.copyOf(prerequisitesByCourseId);
    }

    @Override
    public Optional<String> violation(Student student, Course course) {
        Set<Long> missing = new TreeSet<>(prerequisitesByCourseId.getOrDefault(course.id(), Set.of()));
        missing.removeAll(student.completedCourseIds());
        return missing.isEmpty()
                ? Optional.empty()
                : Optional.of("missing prerequisites: " + missing);
    }
}
