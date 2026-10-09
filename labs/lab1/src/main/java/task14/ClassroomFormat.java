package task14;

public class ClassroomFormat implements CourseFormat {
    private final String room;

    public ClassroomFormat(String room) {
        this.room = room;
    }

    @Override
    public String describe(Course course) {
        return "classroom " + room + ", " + course.durationHours() + "h in person";
    }
}
