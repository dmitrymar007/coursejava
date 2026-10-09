package task13;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class Demo {
    static Course course(String id, String... tags) {
        return new Course(new CourseId(id), Arrays.stream(tags).map(Tag::new).collect(Collectors.toSet()));
    }

    public static void main(String[] args) {
        TagIndex index = new TagIndex();
        index.add(course("J1", "java", "backend"));
        index.add(course("J2", "java", "web"));
        index.add(course("S1", "sql", "backend"));

        System.out.println("java:           " + index.findByAll(Set.of(new Tag("java"))));
        System.out.println("java + backend: " + index.findByAll(Set.of(new Tag("java"), new Tag("backend"))));
        System.out.println("java + sql:     " + index.findByAll(Set.of(new Tag("java"), new Tag("sql"))));
        System.out.println("Неизвестный тег: " + index.findByAll(Set.of(new Tag("java"), new Tag("rust"))));
        System.out.println("Теги: " + index.tags());

        System.out.println("remove(J2): " + index.remove(new CourseId("J2")) + ", повторно: " + index.remove(new CourseId("J2")));
        System.out.println("Тег web исчез (пустой set не хранится): " + index.tags());

        // Повторный add того же id заменяет курс: старые теги не остаются в индексе
        index.add(course("J1", "kotlin"));
        System.out.println("J1 переписан на kotlin: java=" + index.findByAll(Set.of(new Tag("java")))
                + ", kotlin=" + index.findByAll(Set.of(new Tag("kotlin"))));
        System.out.println("Теги: " + index.tags());
    }
}
