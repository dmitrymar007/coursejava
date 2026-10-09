package task8.after;
import task8.Course;


public interface CourseRepository extends CourseReader {
    void save(Course course);
}
