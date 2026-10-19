package lab4.task13;

import java.util.Locale;
import java.util.Objects;
import java.util.regex.Pattern;

public record CourseTitle(String displayValue) {
    private static final Pattern SPACES = Pattern.compile("[\\s\\p{Z}]+", Pattern.UNICODE_CHARACTER_CLASS);

    public CourseTitle {
        Objects.requireNonNull(displayValue);
        if (normalize(displayValue).isEmpty()) throw new IllegalArgumentException("Empty title");
    }

    public String searchKey() {
        return normalize(displayValue);
    }

    private static String normalize(String value) {
        return SPACES.matcher(value.strip()).replaceAll(" ").strip().toLowerCase(Locale.ROOT);
    }
}
