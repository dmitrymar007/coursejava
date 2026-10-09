package task15;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

/** Таблица решений зачисления (приоритет: открыт ли курс, повторная заявка, prerequisite, места). */
class CourseEnrollTest {
    private static final long STUDENT_ID = 1;
    private static final long OTHER_STUDENT_ID = 2;
    private static final long PREREQUISITE_ID = 100;

    private static final Student WITH_PREREQUISITE = new Student(STUDENT_ID, Set.of(PREREQUISITE_ID));
    private static final Student WITHOUT_PREREQUISITE = new Student(STUDENT_ID, Set.of());

    static Stream<Arguments> decisionTable() {
        return Stream.of(
                Arguments.of("курс закрыт", false, Set.of(), WITHOUT_PREREQUISITE, EnrollmentResult.COURSE_NOT_OPEN),
                Arguments.of("повторная заявка", true, Set.of(STUDENT_ID), WITHOUT_PREREQUISITE, EnrollmentResult.ALREADY_APPLIED),
                Arguments.of("нет prerequisite", true, Set.of(OTHER_STUDENT_ID), WITHOUT_PREREQUISITE, EnrollmentResult.PREREQUISITE_NOT_MET),
                Arguments.of("нет мест", true, Set.of(OTHER_STUDENT_ID), WITH_PREREQUISITE, EnrollmentResult.NO_SEATS),
                Arguments.of("всё выполнено", true, Set.of(), WITH_PREREQUISITE, EnrollmentResult.ENROLLED));
    }

    @ParameterizedTest(name = "[{index}] {0} -> {4}")
    @MethodSource("decisionTable")
    void enroll_followsDecisionTable(String scenario, boolean open, Set<Long> enrolledBefore,
                                     Student student, EnrollmentResult expected) throws ValidationException {
        // Arrange: курс на одно место; заранее записанные студенты имеют prerequisite
        Course course = new Course(10, "Java", 32, 1, open, Set.of(PREREQUISITE_ID));
        enrolledBefore.forEach(id -> course.enroll(new Student(id, Set.of(PREREQUISITE_ID))));

        // Act
        EnrollmentResult result = course.enroll(student);

        // Assert
        assertEquals(expected, result);
    }
}
