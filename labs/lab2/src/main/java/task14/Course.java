package task14;

public record Course(long id, String title, int durationHours) {
    public Course {
        if (id <= 0) throw new IllegalArgumentException("id must be positive, got " + id);
        if (title == null || title.isBlank()) throw new IllegalArgumentException("title must not be blank");
        if (durationHours < 1 || durationHours > 300) {
            throw new IllegalArgumentException("durationHours must be in [1, 300], got " + durationHours);
        }
        title = title.trim();
    }
}
