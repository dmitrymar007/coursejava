package task13;

import java.util.HashSet;
import java.util.Set;

public class Course {
    private final long id;
    private final CourseStatus status;
    private final int capacity;
    private final Set<Long> prerequisiteIds;
    private final Set<Long> enrolledStudentIds;

    public Course(long id, CourseStatus status, int capacity,
                  Set<Long> prerequisiteIds, Set<Long> enrolledStudentIds) {
        this.id = id;
        this.status = status;
        this.capacity = capacity;
        this.prerequisiteIds = Set.copyOf(prerequisiteIds);
        this.enrolledStudentIds = new HashSet<>(enrolledStudentIds);
    }

    public long id() {
        return id;
    }

    public int enrolledCount() {
        return enrolledStudentIds.size();
    }

    /**
     * Порядок проверок задаёт приоритет отказов (см. таблицу решений в ANSWER.md):
     * статус, повторная заявка, prerequisite, места.
     */
    public EnrollmentResult enroll(Student student) {
        if (status != CourseStatus.OPEN) {
            return EnrollmentResult.COURSE_NOT_OPEN;
        }
        if (enrolledStudentIds.contains(student.id())) {
            return EnrollmentResult.ALREADY_APPLIED;
        }
        if (!student.completedCourseIds().containsAll(prerequisiteIds)) {
            return EnrollmentResult.PREREQUISITE_NOT_MET;
        }
        if (enrolledStudentIds.size() >= capacity) {
            return EnrollmentResult.NO_SEATS;
        }
        enrolledStudentIds.add(student.id());
        return EnrollmentResult.ENROLLED;
    }
}
