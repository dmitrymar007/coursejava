package task13;

import java.util.Objects;
import java.util.Set;

/** Курс с набором тегов. Набор копируется при создании, поэтому изменить его снаружи нельзя. */
public record Course(CourseId id, Set<Tag> tags) {
    public Course {
        Objects.requireNonNull(id, "id");
        tags = Set.copyOf(tags);
    }
}
