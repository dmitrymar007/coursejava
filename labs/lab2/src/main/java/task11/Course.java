package task11;

public class Course {
    public static final int MIN_DURATION_HOURS = 1;
    public static final int MAX_DURATION_HOURS = 300;

    private long id;
    private String title;
    private int durationHours;
    private int completedHours;

    Course(long id, String title, int durationHours, int completedHours) {
        if (id > 0) this.id = id;
        else System.out.println("ERR");

        if (!title.isBlank() && !title.isEmpty()) this.title = title.trim();

        this.durationHours = validDuration(durationHours);

        if (completedHours < durationHours && completedHours >= 0) this.completedHours = completedHours;
    }

    public Course(long id, String title, int durationHours) {
        if (id > 0) this.id = id;
        else System.out.println("ERR");

        if (!title.isBlank() && !title.isEmpty()) this.title = title.trim();

        this.durationHours = validDuration(durationHours);

        this.completedHours = 0;
    }

    private static int validDuration(int durationHours) {
        if (durationHours < MIN_DURATION_HOURS || durationHours > MAX_DURATION_HOURS) {
            throw new IllegalArgumentException("durationHours must be in ["
                    + MIN_DURATION_HOURS + ", " + MAX_DURATION_HOURS + "], got " + durationHours);
        }
        return durationHours;
    }

    public long id() {
        return this.id;
    }
    public String title() {
        return this.title;
    }
    public int durationHours() {
        return this.durationHours;
    }
    public int completedHours() {
        return this.completedHours;
    }
    public int remainingHours() {
        return durationHours - completedHours;
    }
    public boolean isCompleted() {
        return remainingHours() == 0;
    }
    public void completeHours(int hours) {
        if (hours <= 0) {
            throw new IllegalArgumentException("hours must be positive, got " + hours);
        }
        if (hours > remainingHours()) {
            throw new IllegalArgumentException("hours exceed remaining: requested "
                    + hours + ", remaining " + remainingHours());
        }
        this.completedHours += hours;
    }

    public static void main(String[] args) {
        Course course = new Course(10, "Java Fundamentals", 32);
        course.completeHours(6);

        System.out.println(course.completedHours());
        System.out.println(course.remainingHours());
        System.out.println(course.isCompleted());
    }
}
