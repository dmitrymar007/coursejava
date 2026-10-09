package task8;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CourseTest {

    @Test
    @DisplayName("completeHours: допустимое число часов увеличивает completedHours")
    void completeHours_withinRemaining_increasesCompletedHours() {
        // Arrange
        Course course = new Course(10, "Java Fundamentals", 32);

        // Act
        course.completeHours(6);

        // Assert
        assertEquals(6, course.completedHours());
    }
}
