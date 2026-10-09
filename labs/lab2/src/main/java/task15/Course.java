package task15;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Course {
    private final long id;
    private final int capacity;
    private final boolean open;
    private final Set<Long> prerequisiteIds;
    private final Set<Long> enrolledStudentIds = new HashSet<>();

    /**
     * Неверные данные дают ValidationException. null вместо prerequisiteIds это ошибка программиста,
     * она даёт NullPointerException и не смешивается с ошибками данных.
     */
    public Course(long id, String title, int durationHours, int capacity,
                  boolean open, Set<Long> prerequisiteIds) throws ValidationException {
        Objects.requireNonNull(prerequisiteIds, "prerequisiteIds");
        if (id <= 0) {
            throw new ValidationException("id must be positive");
        }
        if (title == null || title.isBlank()) {
            throw new ValidationException("title must not be blank");
        }
        if (durationHours < 1 || durationHours > 300) {
            throw new ValidationException("durationHours must be in [1, 300]");
        }
        if (capacity < 1) {
            throw new ValidationException("capacity must be positive");
        }
        this.id = id;
        this.capacity = capacity;
        this.open = open;
        this.prerequisiteIds = Set.copyOf(prerequisiteIds);
    }

    public long id() {
        return id;
    }

    public int enrolledCount() {
        return enrolledStudentIds.size();
    }

    /** Приоритет отказов: открыт ли курс, повторная заявка, prerequisite, места. */
    public EnrollmentResult enroll(Student student) {
        Objects.requireNonNull(student, "student");
        if (!open) {
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
