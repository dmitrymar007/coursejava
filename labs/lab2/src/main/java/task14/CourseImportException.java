package task14;

public class CourseImportException extends Exception {
    private final int recordNumber;
    private final int processedRecords;

    /**
     * @param recordNumber     номер строки с ошибкой, 0 если ошибка не связана с конкретной записью
     * @param processedRecords сколько записей успешно разобрано до ошибки
     */
    public CourseImportException(String message, int recordNumber, int processedRecords, Throwable cause) {
        super(message, cause);
        this.recordNumber = recordNumber;
        this.processedRecords = processedRecords;
    }

    public int recordNumber() {
        return recordNumber;
    }

    public int processedRecords() {
        return processedRecords;
    }
}
