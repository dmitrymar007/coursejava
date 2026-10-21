package lab5.task7;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import lab5.Course;
import lab5.ImportError;

public class AtomicImporter {
    public sealed interface Result permits Success, Failure {}
    public record Success(List<Course> courses) implements Result {
        public Success { courses = List.copyOf(courses); }
    }
    public record Failure(List<ImportError> errors) implements Result {
        public Failure { errors = List.copyOf(errors); }
    }

    public static Result read(Path path) throws IOException {
        var courses = new ArrayList<Course>();
        var errors = new ArrayList<ImportError>();
        try (var reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String line;
            long number = 0;
            while ((line = reader.readLine()) != null) {
                number++;
                try {
                    courses.add(Course.parse(line));
                } catch (IllegalArgumentException e) {
                    errors.add(new ImportError(number, e.getMessage()));
                }
            }
        }
        return errors.isEmpty() ? new Success(courses) : new Failure(errors);
    }

    public static void main(String[] args) throws IOException {
        Path file = Files.createTempFile("import-", ".txt");
        try {
            Files.writeString(file, "1;Java\nошибка\n2;SQL\n");
            System.out.println(read(file));
        } finally {
            Files.deleteIfExists(file);
        }
    }
}
