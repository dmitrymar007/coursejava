package task3;

import java.util.Objects;

public record Course(CourseId id, String title) {
    public Course {
        Objects.requireNonNull(id, "id");
        Objects.requireNonNull(title, "title");
    }
}
