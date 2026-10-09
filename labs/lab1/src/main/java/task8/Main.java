package task8;

import task8.after.CourseCatalogReport;
import task8.after.CourseImporter;
import task8.after.InMemoryCourseRepository;
import task8.after.ReadOnlyCourseRepository;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Course> courses = List.of(
                new Course(1, "Java Fundamentals", 32),
                new Course(2, "Spring Deep Dive", 80));

        task8.before.CourseRepository legacy = new task8.before.ReadOnlyCourseRepository();
        try {
            task8.before.CourseImporter.importAll(legacy, courses);
        } catch (UnsupportedOperationException e) {
            System.out.println("LSP violated: " + e.getMessage());
        }

        InMemoryCourseRepository writable = new InMemoryCourseRepository();
        System.out.println(CourseImporter.importAll(writable, courses));

        ReadOnlyCourseRepository readOnly = new ReadOnlyCourseRepository(writable);
        System.out.println(CourseCatalogReport.titles(readOnly));
        System.out.println(CourseCatalogReport.titles(writable));

    }
}
