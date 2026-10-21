package lab5.task2;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class BufferedText {
    public record Stats(long lines, long chars) {}

    public static Stats copy(Path source, Path target) throws IOException {
        long lines = 0, chars = 0;
        try (var reader = Files.newBufferedReader(source, StandardCharsets.UTF_8);
             var writer = Files.newBufferedWriter(target, StandardCharsets.UTF_8)) {
            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line);
                writer.newLine();
                lines++;
                chars += line.length();
            }
        }
        return new Stats(lines, chars);
    }

    public static void main(String[] args) throws IOException {
        Path source = Files.createTempFile("source-", ".txt");
        Path target = Files.createTempFile("copy-", ".txt");
        try {
            Files.writeString(source, "1;Основы Java\n2;Базы данных");
            System.out.println(copy(source, target));
        } finally {
            Files.deleteIfExists(source);
            Files.deleteIfExists(target);
        }
    }
}
