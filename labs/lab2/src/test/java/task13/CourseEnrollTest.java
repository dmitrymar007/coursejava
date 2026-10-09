package task13;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CourseEnrollTest {

    private static final long STUDENT_ID = 1;
    private static final long OTHER_STUDENT_ID = 2;
    private static final long PREREQUISITE_ID = 100;

    private static Student studentWithPrerequisite() {
        return new Student(STUDENT_ID, Set.of(PREREQUISITE_ID));
    }

    private static Student studentWithoutPrerequisite() {
        return new Student(STUDENT_ID, Set.of());
    }

    private static Course course(CourseStatus status, Set<Long> enrolled) {
        return new Course(10, status, 1, Set.of(PREREQUISITE_ID), enrolled);
    }

    @Test
    @DisplayName("Правило 1: курс не открыт, остальное неважно -> COURSE_NOT_OPEN")
    void enroll_courseNotOpen_isRejected() {
        // Arrange: курс закрыт, мест нет, prerequisite нет, студент уже записан
        Course course = course(CourseStatus.CLOSED, Set.of(STUDENT_ID));

        // Act
        EnrollmentResult result = course.enroll(studentWithoutPrerequisite());

        // Assert
        assertEquals(EnrollmentResult.COURSE_NOT_OPEN, result);
        assertEquals(1, course.enrolledCount());
    }

    @Test
    @DisplayName("Правило 2: повторная заявка на открытый курс -> ALREADY_APPLIED")
    void enroll_repeatedApplication_isRejected() {
        // Arrange: курс открыт, мест нет, prerequisite нет, студент уже записан
        Course course = course(CourseStatus.OPEN, Set.of(STUDENT_ID));

        // Act
        EnrollmentResult result = course.enroll(studentWithoutPrerequisite());

        // Assert
        assertEquals(EnrollmentResult.ALREADY_APPLIED, result);
        assertEquals(1, course.enrolledCount());
    }

    @Test
    @DisplayName("Правило 3: новая заявка без prerequisite -> PREREQUISITE_NOT_MET")
    void enroll_prerequisiteNotMet_isRejected() {
        // Arrange: курс открыт, мест нет, студент новый и без prerequisite
        Course course = course(CourseStatus.OPEN, Set.of(OTHER_STUDENT_ID));

        // Act
        EnrollmentResult result = course.enroll(studentWithoutPrerequisite());

        // Assert
        assertEquals(EnrollmentResult.PREREQUISITE_NOT_MET, result);
        assertEquals(1, course.enrolledCount());
    }

    @Test
    @DisplayName("Правило 4: все условия выполнены, но мест нет -> NO_SEATS")
    void enroll_noSeats_isRejected() {
        // Arrange: курс открыт, студент новый с prerequisite, место занято другим
        Course course = course(CourseStatus.OPEN, Set.of(OTHER_STUDENT_ID));

        // Act
        EnrollmentResult result = course.enroll(studentWithPrerequisite());

        // Assert
        assertEquals(EnrollmentResult.NO_SEATS, result);
        assertEquals(1, course.enrolledCount());
    }

    @Test
    @DisplayName("Правило 5: все условия выполнены и место есть -> ENROLLED")
    void enroll_allConditionsMet_enrollsStudent() {
        // Arrange
        Course course = course(CourseStatus.OPEN, Set.of());

        // Act
        EnrollmentResult result = course.enroll(studentWithPrerequisite());

        // Assert
        assertEquals(EnrollmentResult.ENROLLED, result);
        assertEquals(1, course.enrolledCount());
    }
}
