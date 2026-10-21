package lab5.task9;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.CRC32;
import lab5.Course;

public class VersionedCatalog {
    private static final int MAGIC = 0x43415439;
    public record CourseV2(int id, String title, String category) {
        public CourseV2 {
            new Course(id, title);
            if (category == null || category.isBlank()) throw new IllegalArgumentException("Пустая категория");
        }
    }

    public static void write(Path path, List<CourseV2> courses) throws IOException {
        writeVersion(path, 2, courses);
    }

    public static void writeV1(Path path, List<Course> courses) throws IOException {
        writeVersion(path, 1, courses.stream().map(c -> new CourseV2(c.id(), c.title(), "Общее")).toList());
    }

    private static long checksum(int version, byte[] payload) {
        var crc = new CRC32();
        crc.update(version);
        crc.update(payload);
        return crc.getValue();
    }

    private static void writeVersion(Path path, int version, List<CourseV2> courses) throws IOException {
        var bytes = new ByteArrayOutputStream();
        try (var out = new DataOutputStream(bytes)) {
            out.writeInt(courses.size());
            for (var course : courses) {
                out.writeInt(course.id());
                out.writeUTF(course.title());
                if (version == 2) out.writeUTF(course.category());
            }
        }
        byte[] payload = bytes.toByteArray();
        try (var out = new DataOutputStream(Files.newOutputStream(path))) {
            out.writeInt(MAGIC);
            out.writeInt(version);
            out.writeLong(checksum(version, payload));
            out.write(payload);
        }
    }

    public static List<CourseV2> read(Path path) throws IOException {
        try (var in = new DataInputStream(Files.newInputStream(path))) {
            if (in.readInt() != MAGIC) throw new IOException("Неверный magic header");
            int version = in.readInt();
            if (version != 1 && version != 2) throw new IOException("Неизвестная schema version: " + version);
            long expected = in.readLong();
            byte[] payload = in.readAllBytes();
            if (checksum(version, payload) != expected) throw new IOException("Checksum не совпадает");
            try (var data = new DataInputStream(new ByteArrayInputStream(payload))) {
                int count = data.readInt();
                if (count < 0) throw new IOException("Отрицательное число записей");
                var courses = new ArrayList<CourseV2>();
                for (int i = 0; i < count; i++)
                    courses.add(new CourseV2(data.readInt(), data.readUTF(), version == 1 ? "Общее" : data.readUTF()));
                if (data.read() != -1) throw new IOException("Лишние данные");
                return List.copyOf(courses);
            }
        } catch (EOFException | IllegalArgumentException e) {
            throw new IOException("Обрезанный или неверный каталог", e);
        }
    }

    public static void main(String[] args) throws IOException {
        Path file = Files.createTempFile("versioned-", ".bin");
        try {
            writeV1(file, List.of(new Course(1, "Java")));
            var migrated = read(file);
            write(file, migrated);
            System.out.println(read(file));
        } finally {
            Files.deleteIfExists(file);
        }
    }
}
