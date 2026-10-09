package task10;

import java.util.Objects;

/** Сущность: идентичность - id (неизменяемый), равенство и hashCode только по нему. Название изменяемо. */
public class Course {
    private final long id;
    private String title;

    public Course(long id, String title) {
        this.id = id;
        this.title = Objects.requireNonNull(title);
    }

    public long id() {
        return id;
    }

    public String title() {
        return title;
    }

    public void rename(String newTitle) {
        this.title = Objects.requireNonNull(newTitle);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Course other && id == other.id;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(id);
    }

    @Override
    public String toString() {
        return "Course#" + id + "(" + title + ")";
    }
}
