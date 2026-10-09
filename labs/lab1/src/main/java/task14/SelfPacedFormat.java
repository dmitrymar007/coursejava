package task14;

public class SelfPacedFormat implements CourseFormat {
    @Override
    public String describe(Course course) {
        return "self-paced, " + course.durationHours() + "h at your own speed";
    }
}
