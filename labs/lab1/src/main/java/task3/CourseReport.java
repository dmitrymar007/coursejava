package task3;

public final class CourseReport {
    private final CourseFormatter[] formatters;

    public CourseReport(CourseFormatter... formatters) {
        this.formatters = formatters.clone();
    }

    public String[] render(Course course) {
        String[] lines = new String[formatters.length];
        for (int i = 0; i < formatters.length; i++) {
            lines[i] = formatters[i].format(course);
        }
        return lines;
    }
}
