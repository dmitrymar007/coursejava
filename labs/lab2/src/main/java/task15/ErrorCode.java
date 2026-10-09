package task15;

/** Стабильные коды ошибок публичного слоя. Строки кодов менять нельзя: на них опираются клиенты. */
public enum ErrorCode {
    INVALID_RECORD("COURSE_IMPORT_INVALID_RECORD", "Import data contains an invalid record"),
    IMPORT_IO_ERROR("COURSE_IMPORT_IO_ERROR", "Import source could not be read"),
    COURSE_NOT_FOUND("ENROLLMENT_COURSE_NOT_FOUND", "Course not found"),
    COURSE_NOT_OPEN("ENROLLMENT_COURSE_NOT_OPEN", "Course is not open for enrollment"),
    ALREADY_APPLIED("ENROLLMENT_ALREADY_APPLIED", "Student has already applied to this course"),
    PREREQUISITE_NOT_MET("ENROLLMENT_PREREQUISITE_NOT_MET", "Course prerequisites are not met"),
    NO_SEATS("ENROLLMENT_NO_SEATS", "No seats available");

    private final String code;
    private final String publicMessage;

    ErrorCode(String code, String publicMessage) {
        this.code = code;
        this.publicMessage = publicMessage;
    }

    public String code() {
        return code;
    }

    public String publicMessage() {
        return publicMessage;
    }
}
