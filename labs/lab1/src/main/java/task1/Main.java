package task1;

public class Main {
    public static void main(String[] args) {
        Course course = new Course(10, "Java Fundamentals", 32);
        course.completeHours(6);

        CourseFormatter formatter = new CompactCourseFormatter();
        System.out.println(formatter.format(course));

        formatter = new DetailedCourseFormatter();
        System.out.println(formatter.format(course));

        if (formatter instanceof DetailedCourseFormatter detailed) {
            System.out.println(detailed.legend());
        }
    }
}
