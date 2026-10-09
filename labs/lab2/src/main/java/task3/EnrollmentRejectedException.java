package task3;

public class EnrollmentRejectedException extends Exception {
    private final String studentId;
    private final String courseId;
    private final String reasonCode;

    public EnrollmentRejectedException(String studentId, String courseId, String reasonCode, Throwable cause) {
        // Сообщение без id и без текста cause, чтобы не раскрывать лишнего
        super("Enrollment rejected: " + reasonCode, cause);
        this.studentId = studentId;
        this.courseId = courseId;
        this.reasonCode = reasonCode;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getCourseId() {
        return courseId;
    }

    public String getReasonCode() {
        return reasonCode;
    }
}
