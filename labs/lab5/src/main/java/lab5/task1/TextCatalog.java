package lab5.task1;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import lab5.Course;

public class TextCatalog {
    public static void write(Path path, List<Course> courses) throws IOException {
        Files.writeString(path, courses.stream().map(Course::toLine).collect(Collectors.joining("\n")),
                StandardCharsets.UTF_8);
    }

    public static List<Course> read(Path path) throws IOException {
        return Files.readString(path, StandardCharsets.UTF_8).lines().map(Course::parse).toList();
    }

    public static void main(String[] args) throws IOException {
        Path file = Files.createTempFile("courses-", ".txt");
        try {
            write(file, List.of(new Course(1, "Основы Java")));
            System.out.println(read(file));
        } finally {
            Files.deleteIfExists(file);
        }
    }
}
