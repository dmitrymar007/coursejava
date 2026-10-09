package task12;

public record Course(long id, String title, int durationHours) {
    public Course {
        if (id <= 0) throw new IllegalArgumentException("id must be positive, got " + id);
        if (title == null || title.isBlank()) throw new IllegalArgumentException("title must not be blank");
        if (durationHours < 1) throw new IllegalArgumentException("durationHours must be positive, got " + durationHours);
        title = title.trim();
    }
}
