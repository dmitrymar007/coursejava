package lab5.task8;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import lab5.Course;
import lab5.ImportError;

public class StreamingImporter {
    public record Summary(long lines, long accepted, long errors, List<ImportError> samples, boolean cancelled) {
        public Summary { samples = List.copyOf(samples); }
    }

    public static Summary read(Path path, int errorLimit, long cancelAfterErrors, Consumer<Course> sink)
            throws IOException {
        if (errorLimit < 0 || cancelAfterErrors <= 0) throw new IllegalArgumentException("Неверные лимиты");
        long lines = 0, accepted = 0, errors = 0;
        var samples = new ArrayList<ImportError>();
        try (var reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines++;
                Course course;
                try {
                    course = Course.parse(line);
                } catch (IllegalArgumentException e) {
                    errors++;
                    if (samples.size() < errorLimit) samples.add(new ImportError(lines, e.getMessage()));
                    if (errors >= cancelAfterErrors) return new Summary(lines, accepted, errors, samples, true);
                    continue;
                }
                sink.accept(course);
                accepted++;
            }
        }
        return new Summary(lines, accepted, errors, samples, false);
    }

    public static void main(String[] args) throws IOException {
        if (args.length > 0) {
            System.out.println(read(Path.of(args[0]), 3, Long.MAX_VALUE, course -> {}));
            return;
        }
        Path file = Files.createTempFile("stream-", ".txt");
        try {
            Files.writeString(file, "1;Java\nошибка\n2;SQL\nошибка\n3;C\n");
            System.out.println(read(file, 1, 2, System.out::println));
        } finally {
            Files.deleteIfExists(file);
        }
    }
}
