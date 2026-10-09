package task14;

public class OnlineFormat implements CourseFormat {
    @Override
    public String describe(Course course) {
        return "online, " + course.durationHours() + "h of video";
    }
}
