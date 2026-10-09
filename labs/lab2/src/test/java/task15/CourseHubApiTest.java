package task15;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CourseHubApiTest {
    private final CourseHubApi api = new CourseHubApi(new CourseHubService());

    @Test
    void importCourses_success_returnsOk() {
        ApiResponse response = api.importCourses(TestReader.healthy("1;Java;32;10\n"));

        assertTrue(response.success());
        assertEquals("OK", response.code());
    }

    @Test
    void importCourses_invalidRecord_returnsStableCodeWithRecordNumber() {
        ApiResponse response = api.importCourses(TestReader.healthy("1;Java;32;10\nbroken\n"));

        assertFalse(response.success());
        assertEquals("COURSE_IMPORT_INVALID_RECORD", response.code());
        assertTrue(response.message().contains("#2"), response.message());
    }

    @Test
    void importCourses_ioFailure_returnsStableCodeWithoutInternalDetails() {
        ApiResponse response = api.importCourses(TestReader.failingOnRead("1;Java;32;10\n"));

        assertEquals("COURSE_IMPORT_IO_ERROR", response.code());
        assertFalse(response.message().contains("read failed"), response.message());
    }

    @Test
    void enroll_unknownCourse_returnsStableCode() {
        ApiResponse response = api.enroll(99, new Student(1, Set.of()));

        assertEquals("ENROLLMENT_COURSE_NOT_FOUND", response.code());
    }

    @Test
    void enroll_nullStudent_programmingErrorIsNotConvertedToErrorCode() {
        assertThrows(NullPointerException.class, () -> api.enroll(10, null));
    }
}
