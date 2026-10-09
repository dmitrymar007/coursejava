package task8.after;
import task8.Course;


import java.util.List;

public class CourseCatalogReport {
    public static List<String> titles(CourseReader reader) {
        return reader.findAll().stream().map(Course::title).sorted().toList();
    }
}
