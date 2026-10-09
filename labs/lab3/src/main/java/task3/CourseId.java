package task3;

public record CourseId(String value) {
    public CourseId {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("id курса пуст");
        }
    }
}
