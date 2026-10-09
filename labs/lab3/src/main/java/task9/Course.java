package task9;

public class Course {
    private final String title;

    public Course(String title) {
        this.title = title;
    }

    public String title() {
        return title;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" + title + ")";
    }
}
