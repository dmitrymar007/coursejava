package task8.before;
import task8.Course;


public class ReadOnlyCourseRepository extends CourseRepository {
    @Override
    public void save(Course course) {
        throw new UnsupportedOperationException("repository is read-only");
    }
}
