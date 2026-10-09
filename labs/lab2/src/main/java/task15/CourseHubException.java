package task15;

/**
 * Прикладная ошибка импорта: плохие данные (INVALID_RECORD) или сбой ввода-вывода (IMPORT_IO_ERROR).
 * Всегда хранит исходную причину. recordNumber равен 0, если ошибка не связана с конкретной записью.
 */
public class CourseHubException extends Exception {
    private final ErrorCode errorCode;
    private final int recordNumber;

    public CourseHubException(ErrorCode errorCode, String message, int recordNumber, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
        this.recordNumber = recordNumber;
    }

    public ErrorCode errorCode() {
        return errorCode;
    }

    public int recordNumber() {
        return recordNumber;
    }
}
