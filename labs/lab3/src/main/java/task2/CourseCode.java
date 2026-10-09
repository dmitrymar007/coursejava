package task2;

import java.util.Locale;
import java.util.Objects;

/** Код курса. Равенство по значению: "java101" и " JAVA101 " - один и тот же код. */
public final class CourseCode {
    private final String value;

    public CourseCode(String raw) {
        Objects.requireNonNull(raw, "raw");
        String normalized = raw.trim().toUpperCase(Locale.ROOT);
        if (normalized.isEmpty()) {
            throw new IllegalArgumentException("код курса пуст");
        }
        this.value = normalized;
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
