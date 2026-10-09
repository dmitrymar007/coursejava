package task3;

public class Demo {
    public static void main(String[] args) {
        try {
            try {
                throw new IllegalStateException("db error: jdbc:postgresql://internal-host, user=admin"); //лишние данные которые не надо раскрывать
            } catch (IllegalStateException e) {
                throw new EnrollmentRejectedException("S-1001", "CS-101", "STORAGE_FAILURE", e);
            }
        } catch (EnrollmentRejectedException e) {
            System.out.println("message:  " + e.getMessage());
            System.out.println("toString: " + e);
            System.out.println("ids/code: " + e.getStudentId() + ", " + e.getCourseId() + ", " + e.getReasonCode());
            System.out.println("cause:    " + e.getCause().getClass().getSimpleName());
        }
    }
}
