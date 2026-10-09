package task15;

/** Ответ публичного слоя: стабильный код и безопасное сообщение без деталей реализации. */
public record ApiResponse(boolean success, String code, String message) {
    public static ApiResponse ok(String message) {
        return new ApiResponse(true, "OK", message);
    }

    public static ApiResponse error(ErrorCode errorCode, String message) {
        return new ApiResponse(false, errorCode.code(), message);
    }
}
