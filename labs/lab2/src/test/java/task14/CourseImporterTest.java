package task14;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CourseImporterTest {

    private static final String VALID = "1;Java;32\n2;SQL;16\n";

    private final CourseImporter importer = new CourseImporter();

    @Test
    @DisplayName("успех: записи опубликованы, reader закрыт один раз")
    void import_validData_publishesAndClosesReader() throws CourseImportException {
        // Arrange
        TestReader reader = new TestReader(VALID, false);

        // Act
        int count = importer.importCourses(reader);

        // Assert
        assertEquals(2, count);
        assertEquals(List.of(new Course(1, "Java", 32), new Course(2, "SQL", 16)), importer.published());
        assertEquals(1, reader.closeCalls());
    }

    @ParameterizedTest(name = "[{index}] битая запись \"{0}\": основное исключение с номером записи, ничего не опубликовано")
    @ValueSource(strings = {"x;SQL;16", "2;SQL", "2;SQL;abc", "2;SQL;0", "2; ;16"})
    void import_malformedRecord_throwsAndPublishesNothing(String badLine) {
        // Arrange: первая запись корректна, вторая битая
        TestReader reader = new TestReader("1;Java;32\n" + badLine + "\n3;Go;8\n", false);

        // Act
        CourseImportException e = assertThrows(CourseImportException.class,
                () -> importer.importCourses(reader));

        // Assert
        assertEquals(2, e.recordNumber());
        assertEquals(1, e.processedRecords());
        assertInstanceOf(IllegalArgumentException.class, e.getCause());
        assertEquals(0, e.getSuppressed().length);
        assertEquals(1, reader.closeCalls());
        assertTrue(importer.published().isEmpty());
    }

    @Test
    @DisplayName("битая запись и отказ close: основное исключение сохранено, close-ошибка в suppressed")
    void import_malformedRecordAndCloseFailure_keepsPrimaryAndSuppressed() {
        // Arrange
        TestReader reader = new TestReader("1;Java;32\nbroken\n", true);

        // Act
        CourseImportException e = assertThrows(CourseImportException.class,
                () -> importer.importCourses(reader));

        // Assert: основное исключение описывает запись, а не закрытие
        assertEquals("malformed record #2", e.getMessage());
        assertEquals(2, e.recordNumber());
        assertEquals(1, e.processedRecords());
        assertEquals(1, e.getSuppressed().length);
        assertInstanceOf(IOException.class, e.getSuppressed()[0]);
        assertEquals("close failed", e.getSuppressed()[0].getMessage());
        assertEquals(1, reader.closeCalls());
        assertTrue(importer.published().isEmpty());
    }

    @Test
    @DisplayName("данные корректны, но close упал: импорт неуспешен, ничего не опубликовано")
    void import_closeFailureOnly_failsWithoutPartialResult() {
        // Arrange
        TestReader reader = new TestReader(VALID, true);

        // Act
        CourseImportException e = assertThrows(CourseImportException.class,
                () -> importer.importCourses(reader));

        // Assert: обе записи разобраны, но результат не опубликован
        assertEquals(0, e.recordNumber());
        assertEquals(2, e.processedRecords());
        assertInstanceOf(IOException.class, e.getCause());
        assertEquals("close failed", e.getCause().getMessage());
        assertEquals(1, reader.closeCalls());
        assertTrue(importer.published().isEmpty());
    }
}
