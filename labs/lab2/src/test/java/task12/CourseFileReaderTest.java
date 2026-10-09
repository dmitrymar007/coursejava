package task12;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CourseFileReaderTest {

    // @TempDir: у каждого теста своя временная папка. Не зависим от текущей директории
    // запуска и не оставляем файлов в проекте.
    @TempDir
    Path dir;

    private final CourseFileReader reader = new CourseFileReader();

    @Test
    void readTitles_skipsBlankLinesAndTrims() throws IOException {
        // Arrange
        Path file = dir.resolve("courses.txt");
        Files.writeString(file, "Java\n\n  Kotlin  \n   \nSQL\n");

        // Act
        List<String> titles = reader.readTitles(file);

        // Assert
        assertEquals(List.of("Java", "Kotlin", "SQL"), titles);
    }

    @Test
    void readTitles_emptyFile_returnsEmptyList() throws IOException {
        // Arrange
        Path file = Files.createFile(dir.resolve("empty.txt"));

        // Act
        List<String> titles = reader.readTitles(file);

        // Assert
        assertEquals(List.of(), titles);
    }

    @Test
    void readTitles_missingFile_throwsNoSuchFile() {
        // Arrange
        Path missing = dir.resolve("missing.txt");

        // Act + Assert
        assertThrows(NoSuchFileException.class, () -> reader.readTitles(missing));
    }
}
