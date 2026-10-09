package task12;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CourseTest {

    @Test
    void constructor_trimsTitle() {
        // Act
        Course course = new Course(1, "  Java  ", 32);

        // Assert
        assertEquals("Java", course.title());
    }

    @Test
    void toString_containsIdentifyingData() {
        // Arrange
        Course course = new Course(7, "Java", 32);

        // Act
        String text = course.toString();

        // Assert: проверяем значимое содержимое, а не точный формат и порядок полей.
        assertTrue(text.contains("Java"), text);
        assertTrue(text.contains("7"), text);
    }
}
