package task3;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/** Индекс CourseId -> Course. Повторный id - ошибка, а не молчаливая перезапись. */
public class CourseIndex {
    private final Map<CourseId, Course> byId = new HashMap<>();

    /** putIfAbsent: одна операция вместо containsKey+put, возвращает прежнее значение. */
    public void add(Course course) {
        Course previous = byId.putIfAbsent(course.id(), course);
        if (previous != null) {
            throw new DuplicateCourseIdException(course.id());
        }
    }

    public void addAll(Collection<Course> courses) {
        for (Course course : courses) {
            add(course);
        }
    }

    public Optional<Course> find(CourseId id) {
        return Optional.ofNullable(byId.get(id));
    }

    public boolean contains(CourseId id) {
        return byId.containsKey(id);
    }

    public int size() {
        return byId.size();
    }
}
