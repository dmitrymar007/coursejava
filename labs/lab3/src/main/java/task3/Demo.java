package task3;

import java.util.HashMap;
import java.util.Map;

public class Demo {
    public static void main(String[] args) {
        CourseIndex index = new CourseIndex();
        index.add(new Course(new CourseId("J1"), "Java"));
        try {
            index.add(new Course(new CourseId("J1"), "Другой курс"));
        } catch (DuplicateCourseIdException e) {
            System.out.println("Повтор отклонён: " + e.getMessage());
        }
        System.out.println("В индексе осталось: " + index.find(new CourseId("J1")).orElseThrow());

        // Сравнение методов Map
        Map<String, Integer> m = new HashMap<>();
        m.put("a", 1);
        m.put("nul", null);

        System.out.println("get(\"zzz\")            = " + m.get("zzz") + "   (нет ключа -> null)");
        System.out.println("get(\"nul\")            = " + m.get("nul") + "   (ключ есть, значение null - то же null!)");
        System.out.println("containsKey(\"nul\")    = " + m.containsKey("nul") + "  (различает 'нет ключа' и 'значение null')");

        System.out.println("putIfAbsent(\"a\", 99)  = " + m.putIfAbsent("a", 99) + "   (вернул старое, не перезаписал: " + m.get("a") + ")");
        System.out.println("putIfAbsent(\"b\", 2)   = " + m.putIfAbsent("b", 2) + "  (вставил, вернул null)");

        int[] calls = {0};
        m.computeIfAbsent("c", k -> {
            calls[0]++;
            return 3;
        });
        m.computeIfAbsent("c", k -> {
            calls[0]++;
            return 4;
        });
        System.out.println("computeIfAbsent: функция вызвана " + calls[0] + " раз, c=" + m.get("c") + " (лениво, только если ключа нет)");
    }
}
