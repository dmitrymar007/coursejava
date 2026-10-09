package task3;

public class JsonCourseFormatter implements CourseFormatter {
    @Override
    public String format(Course course) {
        return "{\"id\":" + course.id()
                + ",\"title\":\"" + course.title() + "\""
                + ",\"durationHours\":" + course.durationHours()
                + ",\"completedHours\":" + course.completedHours() + "}";
    }
}
