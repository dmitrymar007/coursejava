package task8.before;
import task8.Course;


import java.util.List;

public class CourseImporter {
    public static int importAll(CourseRepository repository, List<Course> courses) {
        for (Course course : courses) {
            repository.save(course);
        }
        return courses.size();
    }
}
