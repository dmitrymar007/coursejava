package lab5.task6;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.List;
import lab5.Course;

public class AtomicCatalog {
    @FunctionalInterface
    interface Move {
        void move(Path source, Path target, CopyOption... options) throws IOException;
    }

    public static void write(Path target, List<Course> courses) throws IOException {
        write(target, courses, Files::move);
    }

    static void write(Path target, List<Course> courses, Move move) throws IOException {
        target = target.toAbsolutePath();
        Path temp = Files.createTempFile(target.getParent(), ".catalog-", ".tmp");
        try {
            try (var writer = Files.newBufferedWriter(temp, StandardCharsets.UTF_8)) {
                for (var course : courses) {
                    writer.write(course.toLine());
                    writer.newLine();
                }
                writer.flush();
            }
            try {
                move.move(temp, target, StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);
            } catch (AtomicMoveNotSupportedException e) {
                move.move(temp, target, StandardCopyOption.REPLACE_EXISTING);
            }
        } finally {
            Files.deleteIfExists(temp);
        }
    }

    public static void main(String[] args) throws IOException {
        Path file = Files.createTempFile("catalog-", ".txt");
        try {
            write(file, List.of(new Course(1, "Java")));
            System.out.print(Files.readString(file));
        } finally {
            Files.deleteIfExists(file);
        }
    }
}
