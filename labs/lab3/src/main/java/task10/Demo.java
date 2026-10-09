package task10;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Demo {
    public static void main(String[] args) {
        // 1. Поле вне равенства: изменение безопасно
        Course java = new Course(1, "Java");
        Set<Course> set = new HashSet<>(Set.of(java));
        Map<Course, String> map = new HashMap<>(Map.of(java, "группа A"));
        java.rename("Java Advanced");
        System.out.println("Хороший ключ после rename: set.contains=" + set.contains(java)
                + ", map.get=" + map.get(java));

        // 2. Поле в hashCode: объект теряется
        BadCourse bad = new BadCourse(2, "SQL");
        Set<BadCourse> badSet = new HashSet<>(Set.of(bad));
        Map<BadCourse, String> badMap = new HashMap<>(Map.of(bad, "группа B"));
        bad.rename("SQL Advanced");
        System.out.println("Плохой ключ после rename:  set.contains=" + badSet.contains(bad)
                + ", map.get=" + badMap.get(bad)
                + ", remove=" + badSet.remove(bad)
                + ", size=" + badSet.size() + ", при обходе виден: " + badSet);

        // Дубликат: «тот же» объект добавляется повторно, потому что лежит в старой корзине
        badSet.add(bad);
        System.out.println("После повторного add того же объекта size=" + badSet.size() + " (дубликат!)");
    }
}
