package task14;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * Импорт курсов из Reader, формат строки: id;title;durationHours.
 * Результат публикуется всё или ничего: пока чтение и закрытие не завершились успешно,
 * в published() ничего не попадает.
 */
public class CourseImporter {
    private final List<Course> published = new ArrayList<>();

    public List<Course> published() {
        return List.copyOf(published);
    }

    public int importCourses(Reader reader) throws CourseImportException {
        List<Course> parsed = new ArrayList<>();
        try (BufferedReader in = new BufferedReader(reader)) {
            String line;
            int lineNumber = 0;
            while ((line = in.readLine()) != null) {
                lineNumber++;
                if (line.isBlank()) {
                    continue;
                }
                parsed.add(parse(line, lineNumber, parsed.size()));
            }
        } catch (IOException e) {
            throw new CourseImportException("I/O error during import", 0, parsed.size(), e);
        }
        published.addAll(parsed);
        return parsed.size();
    }

    private static Course parse(String line, int lineNumber, int processedSoFar) throws CourseImportException {
        try {
            String[] parts = line.split(";", -1);
            if (parts.length != 3) {
                throw new IllegalArgumentException("expected 3 fields, got " + parts.length);
            }
            return new Course(Long.parseLong(parts[0].trim()), parts[1], Integer.parseInt(parts[2].trim()));
        } catch (IllegalArgumentException e) { // включает NumberFormatException
            throw new CourseImportException("malformed record #" + lineNumber, lineNumber, processedSoFar, e);
        }
    }
}
