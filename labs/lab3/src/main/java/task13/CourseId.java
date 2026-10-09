package task13;

import java.util.Objects;

public record CourseId(String value) {
    public CourseId {
        Objects.requireNonNull(value, "value");
    }
}
