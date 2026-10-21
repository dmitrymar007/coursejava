package lab5.task4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import static org.junit.jupiter.api.Assertions.*;

class LazyLinesTest {
    @TempDir Path dir;

    @Test
    void consumesInsideOwnerScopeAndReproducesClosedStream() throws IOException {
        Path file = dir.resolve("lines.txt");
        Files.writeString(file, "Java\n\n \nSQL\n");
        assertEquals(2, LazyLines.count(file));
        try (var stream = LazyLines.brokenLines(file)) {
            assertThrows(IllegalStateException.class, stream::count);
        }
    }
}
