package lab5.task4;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class LazyLines {
    public static long count(Path path) throws IOException {
        try (var lines = Files.lines(path, StandardCharsets.UTF_8)) {
            return lines.filter(line -> !line.isBlank()).count();
        }
    }

    // Намеренная ошибка: возвращённый stream уже закрыт.
    public static Stream<String> brokenLines(Path path) throws IOException {
        try (var lines = Files.lines(path, StandardCharsets.UTF_8)) {
            return lines;
        }
    }

    public static void main(String[] args) throws IOException {
        Path file = Files.createTempFile("lines-", ".txt");
        try {
            Files.writeString(file, "Java\n\nSQL\n");
            System.out.println("Непустых строк: " + count(file));
            try (var lines = brokenLines(file)) {
                System.out.println(lines.count());
            } catch (IllegalStateException e) {
                System.out.println("Ожидаемая ошибка: stream уже закрыт");
            }
        } finally {
            Files.deleteIfExists(file);
        }
    }
}
