package task14;

public class HybridFormat implements CourseFormat {
    private final String room;

    public HybridFormat(String room) {
        this.room = room;
    }

    @Override
    public String describe(Course course) {
        int inPerson = course.durationHours() / 2;
        return "hybrid, " + (course.durationHours() - inPerson) + "h online + " + inPerson + "h in " + room;
    }
}
