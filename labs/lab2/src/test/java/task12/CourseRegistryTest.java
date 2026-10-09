package task12;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CourseRegistryTest {

    // Новый реестр на каждый тест: нет общего состояния, порядок и параллельность не важны.
    private CourseRegistry registry;

    @BeforeEach
    void setUp() {
        registry = new CourseRegistry();
    }

    @Test
    void newRegistry_isEmpty() {
        assertEquals(0, registry.size());
    }

    @Test
    void add_newCourse_isFoundById() {
        // Arrange
        Course course = new Course(1, "Java", 32);

        // Act
        registry.add(course);

        // Assert
        assertEquals(course, registry.find(1).orElseThrow());
        assertEquals(1, registry.size());
    }

    @Test
    void add_duplicateId_throwsAndKeepsRegistry() {
        // Arrange
        Course first = new Course(1, "Java", 32);
        Course duplicate = new Course(1, "Other", 10);
        registry.add(first);

        // Act
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> registry.add(duplicate));

        // Assert
        assertEquals("course already registered: id=1", e.getMessage());
        assertEquals(first, registry.find(1).orElseThrow());
        assertEquals(1, registry.size());
    }

    @Test
    void find_unknownId_isEmpty() {
        assertTrue(registry.find(42).isEmpty());
    }
}
