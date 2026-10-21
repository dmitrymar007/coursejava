package lab5.task8;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import lab5.Course;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import static org.junit.jupiter.api.Assertions.*;

class StreamingImporterTest {
    @TempDir Path dir;

    @Test
    void boundsErrorsCountsAndCancelsAtThreshold() throws IOException {
        Path file = dir.resolve("import.txt");
        Files.writeString(file, "1;Java\nbad\n2;SQL\nbad\nbad\n3;C\n");
        var received = new ArrayList<Course>();
        var result = StreamingImporter.read(file, 1, 2, received::add);
        assertEquals(List.of(new Course(1, "Java"), new Course(2, "SQL")), received);
        assertEquals(4, result.lines());
        assertEquals(2, result.accepted());
        assertEquals(2, result.errors());
        assertEquals(1, result.samples().size());
        assertEquals(2, result.samples().getFirst().line());
        assertTrue(result.cancelled());
        var complete = StreamingImporter.read(file, 0, 10, course -> {});
        assertEquals(new StreamingImporter.Summary(6, 3, 3, List.of(), false), complete);
        assertThrows(IllegalArgumentException.class, () -> StreamingImporter.read(file, -1, 1, course -> {}));
        assertThrows(IllegalArgumentException.class, () -> StreamingImporter.read(file, 1, 0, course -> {}));
    }

    @Test
    void processesFileLargerThanHeap() throws Exception {
        Path file = dir.resolve("large.txt");
        try (var writer = Files.newBufferedWriter(file)) {
            for (int i = 0; i < 700_000; i++) writer.write("1;Java — потоковый импорт\n");
        }
        assertTrue(Files.size(file) > 16 * 1024 * 1024);
        Path log = dir.resolve("process.log");
        var process = new ProcessBuilder(Path.of(System.getProperty("java.home"), "bin", "java").toString(),
                "-Xmx16m", "-cp", System.getProperty("java.class.path"), StreamingImporter.class.getName(), file.toString())
                .redirectErrorStream(true).redirectOutput(log.toFile()).start();
        try {
            assertTrue(process.waitFor(30, TimeUnit.SECONDS), "Импорт не завершился");
            String output = Files.readString(log);
            assertEquals(0, process.exitValue(), output);
            assertTrue(output.contains("lines=700000, accepted=700000, errors=0"), output);
        } finally {
            process.destroyForcibly();
        }
    }

    @Test
    void sinkFailureIsNotMistakenForBadInput() throws IOException {
        Path file = dir.resolve("valid.txt");
        Files.writeString(file, "1;Java");
        var failure = new IllegalArgumentException("sink failed");
        assertSame(failure, assertThrows(IllegalArgumentException.class,
                () -> StreamingImporter.read(file, 1, 2, course -> { throw failure; })));
    }
}
