package task8.after;
import task8.Course;


import java.util.List;
import java.util.Optional;

public interface CourseReader {
    Optional<Course> findById(long id);

    List<Course> findAll();
}
