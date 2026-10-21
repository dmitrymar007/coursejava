package lab5.task1;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.List;
import lab5.Course;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import static org.junit.jupiter.api.Assertions.*;

class TextCatalogTest {
    @TempDir Path dir;

    @Test
    void roundTripAndEmptyCatalog() throws IOException {
        Path file = dir.resolve("courses.txt");
        var courses = List.of(new Course(1, "Основы Java ☕"), new Course(2, "SQL"));
        TextCatalog.write(file, courses);
        assertArrayEquals("1;Основы Java ☕\n2;SQL".getBytes(StandardCharsets.UTF_8), Files.readAllBytes(file));
        assertEquals(courses, TextCatalog.read(file));
        TextCatalog.write(file, List.of());
        assertEquals(List.of(), TextCatalog.read(file));
    }

    @Test
    void fileErrorsReachCaller() {
        assertThrows(NoSuchFileException.class, () -> TextCatalog.read(dir.resolve("missing")));
        assertThrows(IOException.class, () -> TextCatalog.read(dir));
        assertThrows(IOException.class, () -> TextCatalog.write(dir, List.of()));
    }
}
