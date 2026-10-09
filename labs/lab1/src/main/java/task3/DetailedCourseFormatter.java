package task3;

public class DetailedCourseFormatter implements CourseFormatter {
    @Override
    public String format(Course course) {
        return "Course #" + course.id() + " \"" + course.title() + "\": "
                + course.completedHours() + "/" + course.durationHours() + " h done, "
                + course.remainingHours() + " h left, completed=" + course.isCompleted();
    }

    public String legend() {
        return "id, title, done/total, left, completed";
    }
}
