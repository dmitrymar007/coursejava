package task15;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CourseHubServiceTest {
    private static final Student STUDENT = new Student(1, Set.of());

    private final CourseHubService service = new CourseHubService();

    @Test
    void import_validData_registersCoursesAndClosesReader() throws CourseHubException {
        TestReader reader = TestReader.healthy("1;Java;32;10\n2;SQL;16;5\n");

        int count = service.importCourses(reader);

        assertEquals(2, count);
        assertEquals(EnrollmentResult.ENROLLED, service.enroll(1, STUDENT));
        assertEquals(1, reader.closeCalls());
    }

    @ParameterizedTest(name = "[{index}] битая запись \"{0}\"")
    @ValueSource(strings = {"x;SQL;16;5", "2;SQL;16", "2;SQL;0;5"})
    void import_invalidRecord_reportsRecordNumberKeepsCauseAndRegistersNothing(String badLine) {
        TestReader reader = TestReader.healthy("1;Java;32;10\n" + badLine + "\n");

        CourseHubException e = assertThrows(CourseHubException.class, () -> service.importCourses(reader));

        assertEquals(ErrorCode.INVALID_RECORD, e.errorCode());
        assertEquals(2, e.recordNumber());
        assertEquals(1, reader.closeCalls());
        // валидная первая запись не должна остаться зарегистрированной
        assertEquals(EnrollmentResult.COURSE_NOT_FOUND, service.enroll(1, STUDENT));
    }

    @Test
    void import_readFailure_isIoErrorWithOriginalCause() {
        TestReader reader = TestReader.failingOnRead("1;Java;32;10\n");

        CourseHubException e = assertThrows(CourseHubException.class, () -> service.importCourses(reader));

        assertEquals(ErrorCode.IMPORT_IO_ERROR, e.errorCode());
        assertInstanceOf(IOException.class, e.getCause());
        assertEquals("read failed", e.getCause().getMessage());
        assertEquals(1, reader.closeCalls());
    }

    @Test
    void import_closeFailureOnly_failsImportAndRegistersNothing() {
        TestReader reader = TestReader.failingOnClose("1;Java;32;10\n");

        CourseHubException e = assertThrows(CourseHubException.class, () -> service.importCourses(reader));

        assertEquals(ErrorCode.IMPORT_IO_ERROR, e.errorCode());
        assertEquals("close failed", e.getCause().getMessage());
        assertEquals(EnrollmentResult.COURSE_NOT_FOUND, service.enroll(1, STUDENT));
    }

    @Test
    void import_invalidRecordAndCloseFailure_keepsPrimaryAndSuppressed() {
        TestReader reader = TestReader.failingOnClose("1;Java;32;10\nbroken\n");

        CourseHubException e = assertThrows(CourseHubException.class, () -> service.importCourses(reader));

        assertEquals(ErrorCode.INVALID_RECORD, e.errorCode());
        assertEquals(1, e.getSuppressed().length);
        assertEquals("close failed", e.getSuppressed()[0].getMessage());
    }

    @Test
    void enroll_unknownCourse_returnsCourseNotFound() {
        assertEquals(EnrollmentResult.COURSE_NOT_FOUND, service.enroll(99, STUDENT));
    }
}
