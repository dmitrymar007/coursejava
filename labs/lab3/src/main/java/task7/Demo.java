package task7;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Demo {
    record Course(String id, String title) {
    }

    public static void main(String[] args) {
        List<String> names = new ArrayList<>(List.of("a", "b", "c"));
        System.out.println("firstOrThrow: " + Generics.firstOrThrow(names));
        Generics.swap(names, 0, 2);
        System.out.println("swap(0,2):    " + names);

        List<Course> courses = List.of(new Course("J1", "Java"), new Course("S1", "SQL"), new Course("J1", "Java 2"));
        try {
            Generics.indexBy(courses, Course::id);
        } catch (IllegalStateException e) {
            System.out.println("indexBy без политики: " + e.getMessage());
        }
        Map<String, Course> keepFirst = Generics.indexBy(courses, Course::id, (old, nu) -> old);
        Map<String, Course> keepLast = Generics.indexBy(courses, Course::id, (old, nu) -> nu);
        System.out.println("keepFirst J1: " + keepFirst.get("J1").title() + ", keepLast J1: " + keepLast.get("J1").title());
    }
}
