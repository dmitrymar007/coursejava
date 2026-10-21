package lab5.task9;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import lab5.Course;
import lab5.task9.VersionedCatalog.CourseV2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import static org.junit.jupiter.api.Assertions.*;

class VersionedCatalogTest {
    @TempDir Path dir;

    @Test
    void readsV2AndMigratesV1ToV2() throws IOException {
        Path file = dir.resolve("catalog.bin");
        var courses = List.of(new CourseV2(1, "Java", "Разработка"));
        VersionedCatalog.write(file, courses);
        assertEquals(courses, VersionedCatalog.read(file));
        VersionedCatalog.writeV1(file, List.of(new Course(2, "SQL")));
        var migrated = VersionedCatalog.read(file);
        assertEquals(List.of(new CourseV2(2, "SQL", "Общее")), migrated);
        VersionedCatalog.write(file, migrated);
        assertEquals(migrated, VersionedCatalog.read(file));
        VersionedCatalog.write(file, List.of());
        assertEquals(List.of(), VersionedCatalog.read(file));
    }

    @Test
    void rejectsFutureVersionCorruptionAndEveryTruncation() throws IOException {
        Path file = dir.resolve("bad.bin");
        VersionedCatalog.write(file, List.of(new CourseV2(1, "Java", "Разработка")));
        byte[] original = Files.readAllBytes(file);
        for (int offset : new int[]{0, 4, 8, original.length - 1}) {
            byte[] changed = original.clone();
            changed[offset] ^= 1;
            Files.write(file, changed);
            assertThrows(IOException.class, () -> VersionedCatalog.read(file));
        }
        byte[] future = original.clone();
        ByteBuffer.wrap(future).putInt(4, 3);
        Files.write(file, future);
        assertTrue(assertThrows(IOException.class, () -> VersionedCatalog.read(file)).getMessage().contains("version"));
        for (int length = 0; length < original.length; length++) {
            Files.write(file, Arrays.copyOf(original, length));
            assertThrows(IOException.class, () -> VersionedCatalog.read(file), "length=" + length);
        }
        VersionedCatalog.writeV1(file, List.of());
        byte[] switched = Files.readAllBytes(file);
        ByteBuffer.wrap(switched).putInt(4, 2);
        Files.write(file, switched);
        assertTrue(assertThrows(IOException.class, () -> VersionedCatalog.read(file)).getMessage().contains("Checksum"));
    }
}
