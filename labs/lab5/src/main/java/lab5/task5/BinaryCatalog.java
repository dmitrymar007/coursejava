package lab5.task5;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import lab5.Course;

public class BinaryCatalog {
    private static final int MAGIC = 0x43415435, VERSION = 1;

    public static void write(Path path, List<Course> courses) throws IOException {
        try (var out = new DataOutputStream(new BufferedOutputStream(Files.newOutputStream(path)))) {
            out.writeInt(MAGIC);
            out.writeInt(VERSION);
            out.writeInt(courses.size());
            for (var course : courses) {
                out.writeInt(course.id());
                out.writeUTF(course.title());
            }
        }
    }

    public static List<Course> read(Path path) throws IOException {
        try (var in = new DataInputStream(new BufferedInputStream(Files.newInputStream(path)))) {
            if (in.readInt() != MAGIC) throw new IOException("Неверный magic header");
            if (in.readInt() != VERSION) throw new IOException("Неизвестная версия");
            int count = in.readInt();
            if (count < 0) throw new IOException("Отрицательное число записей");
            var courses = new ArrayList<Course>();
            for (int i = 0; i < count; i++) courses.add(new Course(in.readInt(), in.readUTF()));
            if (in.read() != -1) throw new IOException("Лишние данные");
            return List.copyOf(courses);
        } catch (EOFException | IllegalArgumentException e) {
            throw new IOException("Обрезанный или неверный каталог", e);
        }
    }

    public static void main(String[] args) throws IOException {
        Path file = Files.createTempFile("catalog-", ".bin");
        try {
            write(file, List.of(new Course(1, "Java")));
            System.out.println(read(file));
        } finally {
            Files.deleteIfExists(file);
        }
    }
}
