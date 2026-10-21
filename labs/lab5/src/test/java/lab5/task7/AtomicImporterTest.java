package lab5.task7;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import lab5.Course;
import lab5.ImportError;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import static org.junit.jupiter.api.Assertions.*;

class AtomicImporterTest {
    @TempDir Path dir;

    @Test
    void publishesAllOrOnlyErrorsWithLineNumbers() throws IOException {
        Path file = dir.resolve("import.txt");
        Files.writeString(file, "1;Java\nошибка\n2;SQL\n0;Bad\n3;\n");
        var failure = assertInstanceOf(AtomicImporter.Failure.class, AtomicImporter.read(file));
        assertEquals(List.of(2L, 4L, 5L), failure.errors().stream().map(ImportError::line).toList());
        assertTrue(failure.errors().stream().allMatch(e -> !e.message().isBlank()));
        assertThrows(UnsupportedOperationException.class, () -> failure.errors().clear());
        Files.writeString(file, "1;Java\n2;SQL\n");
        var success = assertInstanceOf(AtomicImporter.Success.class, AtomicImporter.read(file));
        assertEquals(List.of(new Course(1, "Java"), new Course(2, "SQL")), success.courses());
        assertThrows(UnsupportedOperationException.class, () -> success.courses().clear());
        Files.writeString(file, "");
        assertEquals(new AtomicImporter.Success(List.of()), AtomicImporter.read(file));
    }
}
