package task8.before;
import task8.Course;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CourseRepository {
    private final Map<Long, Course> store = new HashMap<>();

    public Optional<Course> findById(long id) {
        return Optional.ofNullable(store.get(id));
    }

    public List<Course> findAll() {
        return new ArrayList<>(store.values());
    }

    public void save(Course course) {
        store.put(course.id(), course);
    }
}
