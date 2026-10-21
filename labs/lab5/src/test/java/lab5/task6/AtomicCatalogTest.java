package lab5.task6;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import lab5.Course;
import lab5.task1.TextCatalog;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import static org.junit.jupiter.api.Assertions.*;

class AtomicCatalogTest {
    @TempDir Path dir;

    @Test
    void replacesCatalogNormallyAndWithFallback() throws IOException {
        Path file = dir.resolve("catalog.txt");
        AtomicCatalog.write(file, List.of(new Course(1, "Старый")));
        var courses = List.of(new Course(2, "Новый"));
        var calls = new AtomicInteger();
        AtomicCatalog.write(file, courses, (source, target, options) -> {
            assertEquals(target.getParent(), source.getParent());
            assertEquals(courses, TextCatalog.read(source));
            if (calls.getAndIncrement() == 0) {
                assertArrayEquals(new CopyOption[]{StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING}, options);
                throw new AtomicMoveNotSupportedException(source.toString(), target.toString(), "test");
            }
            assertArrayEquals(new CopyOption[]{StandardCopyOption.REPLACE_EXISTING}, options);
            Files.move(source, target, options);
        });
        assertEquals(2, calls.get());
        assertEquals(courses, TextCatalog.read(file));
        assertOnlyTargetRemains(file);
    }

    @Test
    void moveFailurePreservesOldFileAndRemovesTemp() throws IOException {
        Path file = dir.resolve("catalog.txt");
        Files.writeString(file, "старый каталог");
        assertThrows(IOException.class, () -> AtomicCatalog.write(file, List.of(), (source, target, options) -> {
            throw new IOException("move failed");
        }));
        assertEquals("старый каталог", Files.readString(file));
        assertOnlyTargetRemains(file);
    }

    private void assertOnlyTargetRemains(Path target) throws IOException {
        try (var files = Files.list(dir)) {
            assertEquals(List.of(target), files.toList());
        }
    }
}
