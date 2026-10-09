package task10;

import java.util.Locale;
import java.util.regex.Pattern;

/**
 * Код курса: 2–4 латинские буквы, дефис, три цифры (например, JAVA-101).
 * Перед проверкой вход нормализуется: обрезаются пробелы по краям, буквы переводятся в верхний регистр.
 */
public final class CourseCode {
    private static final Pattern FORMAT = Pattern.compile("[A-Z]{2,4}-\\d{3}");

    private final String value;

    private CourseCode(String value) {
        this.value = value;
    }

    public static CourseCode of(String raw) {
        if (raw == null) {
            throw new IllegalArgumentException("course code must not be null");
        }
        String normalized = raw.trim().toUpperCase(Locale.ROOT);
        if (!FORMAT.matcher(normalized).matches()) {
            throw new IllegalArgumentException("course code must match " + FORMAT.pattern());
        }
        return new CourseCode(normalized);
    }

    public String value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof CourseCode other && value.equals(other.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return value;
    }
}
