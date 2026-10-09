package task8.after;
import task8.Course;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryCourseRepository implements CourseRepository {
    private final Map<Long, Course> store = new HashMap<>();

    @Override
    public Optional<Course> findById(long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Course> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public void save(Course course) {
        store.put(course.id(), course);
    }
}
