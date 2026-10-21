package lab5.task5;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import lab5.Course;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import static org.junit.jupiter.api.Assertions.*;

class BinaryCatalogTest {
    @TempDir Path dir;

    @Test
    void roundTripAndRejectsEveryTruncation() throws IOException {
        Path file = dir.resolve("catalog.bin");
        var courses = List.of(new Course(1, "Основы Java"), new Course(2, "SQL"));
        BinaryCatalog.write(file, courses);
        assertEquals(courses, BinaryCatalog.read(file));
        byte[] bytes = Files.readAllBytes(file);
        for (int length = 0; length < bytes.length; length++) {
            Files.write(file, Arrays.copyOf(bytes, length));
            assertThrows(IOException.class, () -> BinaryCatalog.read(file), "length=" + length);
        }
        BinaryCatalog.write(file, List.of());
        assertEquals(List.of(), BinaryCatalog.read(file));
    }

    @Test
    void rejectsBadHeaderVersionCountAndTrailingBytes() throws IOException {
        Path file = dir.resolve("bad.bin");
        BinaryCatalog.write(file, List.of());
        byte[] original = Files.readAllBytes(file);
        for (int offset : new int[]{0, 4, 8}) {
            byte[] changed = original.clone();
            ByteBuffer.wrap(changed).putInt(offset, -1);
            Files.write(file, changed);
            assertThrows(IOException.class, () -> BinaryCatalog.read(file));
        }
        Files.write(file, Arrays.copyOf(original, original.length + 1));
        assertThrows(IOException.class, () -> BinaryCatalog.read(file));
    }
}
