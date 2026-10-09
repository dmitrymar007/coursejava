package task6;

import java.util.Objects;

/** Результат операции: либо значение типа T, либо сообщение об ошибке. */
public final class Result<T> {
    private final T value;
    private final String error;

    private Result(T value, String error) {
        this.value = value;
        this.error = error;
    }

    public static <T> Result<T> success(T value) {
        return new Result<>(Objects.requireNonNull(value, "value"), null);
    }

    public static <T> Result<T> failure(String error) {
        if (error == null || error.isBlank()) {
            throw new IllegalArgumentException("текст ошибки обязателен");
        }
        return new Result<>(null, error);
    }

    public boolean isSuccess() {
        return error == null;
    }

    public T getValue() {
        if (!isSuccess()) {
            throw new IllegalStateException("результат - ошибка: " + error);
        }
        return value;
    }

    public String getError() {
        if (isSuccess()) {
            throw new IllegalStateException("результат успешен, ошибки нет");
        }
        return error;
    }

    @Override
    public String toString() {
        return isSuccess() ? "Success[" + value + "]" : "Failure[" + error + "]";
    }
}
