package task15;

import java.io.Reader;
import java.util.Objects;

/**
 * Публичный слой. Превращает CourseHubException в стабильные коды, не показывая клиенту cause.
 * Ошибки программиста (RuntimeException) не перехватываются: они доходят до самой внешней границы процесса.
 */
public class CourseHubApi {
    private final CourseHubService service;

    public CourseHubApi(CourseHubService service) {
        this.service = Objects.requireNonNull(service, "service");
    }

    public ApiResponse importCourses(Reader reader) {
        try {
            return ApiResponse.ok("Imported " + service.importCourses(reader) + " courses");
        } catch (CourseHubException e) {
            String suffix = e.recordNumber() > 0 ? " (record #" + e.recordNumber() + ")" : "";
            return ApiResponse.error(e.errorCode(), e.errorCode().publicMessage() + suffix);
        }
    }

    public ApiResponse enroll(long courseId, Student student) {
        return switch (service.enroll(courseId, student)) {
            case ENROLLED -> ApiResponse.ok("Enrolled");
            case COURSE_NOT_FOUND -> error(ErrorCode.COURSE_NOT_FOUND);
            case COURSE_NOT_OPEN -> error(ErrorCode.COURSE_NOT_OPEN);
            case ALREADY_APPLIED -> error(ErrorCode.ALREADY_APPLIED);
            case PREREQUISITE_NOT_MET -> error(ErrorCode.PREREQUISITE_NOT_MET);
            case NO_SEATS -> error(ErrorCode.NO_SEATS);
        };
    }

    private static ApiResponse error(ErrorCode code) {
        return ApiResponse.error(code, code.publicMessage());
    }
}
