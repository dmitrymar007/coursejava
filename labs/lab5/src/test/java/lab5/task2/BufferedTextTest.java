package lab5.task2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import static org.junit.jupiter.api.Assertions.*;

class BufferedTextTest {
    @TempDir Path dir;

    @Test
    void countsLinesAndUtf16UnitsWithoutSeparators() throws IOException {
        Path source = dir.resolve("in.txt"), target = dir.resolve("out.txt");
        Files.writeString(source, "Я😀\r\n\r\nSQL");
        assertEquals(new BufferedText.Stats(3, 6), BufferedText.copy(source, target));
        assertEquals("Я😀" + System.lineSeparator() + System.lineSeparator() + "SQL" + System.lineSeparator(),
                Files.readString(target));
        Files.writeString(source, "");
        assertEquals(new BufferedText.Stats(0, 0), BufferedText.copy(source, target));
        assertEquals(0, Files.size(target));
    }
}
