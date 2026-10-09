package task9;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Контракт: длительность курса целая и лежит в [MIN_DURATION_HOURS, MAX_DURATION_HOURS],
 * иначе конструктор бросает IllegalArgumentException.
 *
 * Одного «обычного» примера (например, 32) мало: он проходит и при проверке
 * {@code d > 0}, и при {@code d >= 1 && d < 300}, и при {@code d != 0}. Ошибки вида
 * {@code <} вместо {@code <=} или off-by-one живут только на границах, поэтому
 * только значения вокруг границ однозначно фиксируют, какие числа допустимы.
 */
class CourseDurationTest {

    private static Course courseWithDuration(int hours) {
        return new Course(1, "Java", hours);
    }

    @Test
    @DisplayName("ниже минимума (0) отвергается")
    void duration_belowMin_throws() {
        // Arrange
        int belowMin = Course.MIN_DURATION_HOURS - 1;

        // Act
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> courseWithDuration(belowMin));

        // Assert
        assertEquals("durationHours must be in [1, 300], got 0", e.getMessage());
    }

    @Test
    @DisplayName("минимум (1) принимается")
    void duration_min_accepted() {
        // Arrange
        int min = Course.MIN_DURATION_HOURS;

        // Act
        Course course = courseWithDuration(min);

        // Assert
        assertEquals(1, course.durationHours());
    }

    @Test
    @DisplayName("максимум (300) принимается")
    void duration_max_accepted() {
        // Arrange
        int max = Course.MAX_DURATION_HOURS;

        // Act
        Course course = courseWithDuration(max);

        // Assert
        assertEquals(300, course.durationHours());
    }

    @Test
    @DisplayName("выше максимума (301) отвергается")
    void duration_aboveMax_throws() {
        // Arrange
        int aboveMax = Course.MAX_DURATION_HOURS + 1;

        // Act
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> courseWithDuration(aboveMax));

        // Assert
        assertEquals("durationHours must be in [1, 300], got 301", e.getMessage());
    }
}
