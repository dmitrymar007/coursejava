package task12;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/** Реестр курсов. Состояние хранится в экземпляре, а не в static-поле, чтобы тесты не делили его. */
public class CourseRegistry {
    private final Map<Long, Course> courses = new HashMap<>();

    public void add(Course course) {
        if (courses.putIfAbsent(course.id(), course) != null) {
            throw new IllegalArgumentException("course already registered: id=" + course.id());
        }
    }

    public Optional<Course> find(long id) {
        return Optional.ofNullable(courses.get(id));
    }

    public int size() {
        return courses.size();
    }
}
