package lab6;

import java.util.List;
import java.util.Objects;

public record Course(String code, Level level, int duration, List<String> modules) {
    public Course {
        if (code == null || code.isBlank() || duration < 0)
            throw new IllegalArgumentException("Нужны код курса и неотрицательная длительность в минутах");
        code = code.strip();
        Objects.requireNonNull(level, "level");
        modules = List.copyOf(modules);
    }

    public static List<Course> examples() {
        return List.of(
                new Course("JAVA", Level.BEGINNER, 120, List.of(" Java ", "Streams", "java")),
                new Course("SQL", Level.BEGINNER, 90, List.of("SQL", " Databases ")),
                new Course("STREAM", Level.INTERMEDIATE, 180, List.of("streams", "Collectors")),
                new Course("JVM", Level.ADVANCED, 180, List.of("Java", "Memory")));
    }
}
