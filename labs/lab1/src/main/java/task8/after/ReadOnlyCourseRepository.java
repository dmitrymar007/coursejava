package task8.after;
import task8.Course;


import java.util.List;
import java.util.Optional;

public class ReadOnlyCourseRepository implements CourseReader {
    private final CourseReader source;

    public ReadOnlyCourseRepository(CourseReader source) {
        this.source = source;
    }

    @Override
    public Optional<Course> findById(long id) {
        return source.findById(id);
    }

    @Override
    public List<Course> findAll() {
        return source.findAll();
    }
}
