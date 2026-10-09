package task15;

/** Нарушение доменных правил во входных данных. */
public class ValidationException extends Exception {
    public ValidationException(String message) {
        super(message);
    }
}
