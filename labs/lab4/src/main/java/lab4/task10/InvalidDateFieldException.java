package lab4.task10;

public class InvalidDateFieldException extends IllegalArgumentException {
    public InvalidDateFieldException(String field, String value, Throwable cause) {
        super("Invalid date in field '" + field + "': '" + value + "'", cause);
    }
}
