package task11;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Неуспешная команда completeHours должна (1) бросить исключение нужного типа
 * с понятным сообщением и (2) не изменить состояние курса.
 * В lambda передаётся только сам вызов completeHours: создание объекта и проверки вынесены наружу,
 * чтобы исключение не могло прийти из другого места.
 */
class CourseCompleteHoursFailureTest {

    private static final int DURATION = 32;
    private static final int ALREADY_COMPLETED = 10;

    private static Course courseInProgress() {
        Course course = new Course(1, "Java", DURATION);
        course.completeHours(ALREADY_COMPLETED);
        return course;
    }

    @Test
    @DisplayName("часов больше, чем осталось: исключение с данными и состояние не меняется")
    void completeHours_exceedsRemaining_throwsAndKeepsState() {
        // Arrange
        Course course = courseInProgress();
        int remainingBefore = course.remainingHours();   // 22
        int tooMany = remainingBefore + 1;

        // Act
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> course.completeHours(tooMany));

        // Assert
        assertEquals("hours exceed remaining: requested 23, remaining 22", e.getMessage());
        assertEquals(ALREADY_COMPLETED, course.completedHours());
        assertEquals(remainingBefore, course.remainingHours());
        assertFalse(course.isCompleted());
    }

    @ParameterizedTest(name = "[{index}] hours = {0}: исключение и состояние не меняется")
    @ValueSource(ints = {0, -1, -100})
    void completeHours_nonPositive_throwsAndKeepsState(int hours) {
        // Arrange
        Course course = courseInProgress();

        // Act
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> course.completeHours(hours));

        // Assert
        assertEquals("hours must be positive, got " + hours, e.getMessage());
        assertEquals(ALREADY_COMPLETED, course.completedHours());
        assertEquals(DURATION - ALREADY_COMPLETED, course.remainingHours());
    }

    @Test
    @DisplayName("ровно остаток часов принимается, курс завершается (граница отказа)")
    void completeHours_exactlyRemaining_completesCourse() {
        // Arrange
        Course course = courseInProgress();

        // Act
        course.completeHours(course.remainingHours());

        // Assert
        assertEquals(DURATION, course.completedHours());
        assertEquals(0, course.remainingHours());
    }
}
