package task10;

import java.util.Objects;

/** Намеренно ошибочный вариант: изменяемое поле title входит в equals и hashCode. */
public class BadCourse {
    private final long id;
    private String title;

    public BadCourse(long id, String title) {
        this.id = id;
        this.title = Objects.requireNonNull(title);
    }

    public void rename(String newTitle) {
        this.title = Objects.requireNonNull(newTitle);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof BadCourse other && id == other.id && title.equals(other.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title);
    }

    @Override
    public String toString() {
        return "BadCourse#" + id + "(" + title + ")";
    }
}
