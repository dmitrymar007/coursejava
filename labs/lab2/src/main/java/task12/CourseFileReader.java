package task12;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class CourseFileReader {

    /** Названия курсов из файла: по одному на строку, пустые строки пропускаются. */
    public List<String> readTitles(Path file) throws IOException {
        return Files.readAllLines(file).stream()
                .map(String::trim)
                .filter(line -> !line.isEmpty())
                .toList();
    }
}
