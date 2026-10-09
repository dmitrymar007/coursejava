package task2;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Demo {
    /** Класс БЕЗ equals/hashCode: равенство по ссылке. */
    static final class RawCode {
        final String value;

        RawCode(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }
    }

    public static void main(String[] args) {
        List<CourseCode> codes = List.of(
                new CourseCode("java101"), new CourseCode("SQL"),
                new CourseCode(" JAVA101 "), new CourseCode("go"), new CourseCode("sql"));
        System.out.println("Исходно:          " + codes);
        System.out.println("LinkedHashSet:    " + new LinkedHashSet<>(codes));
        System.out.println("HashSet:          " + new HashSet<>(codes) + "  (порядок не гарантирован)");
        System.out.println("TreeSet:          " + new TreeSet<>(List.of("java101", "sql", "go")) + "  (сортировка, не порядок появления)");

        List<RawCode> raw = List.of(new RawCode("A"), new RawCode("A"));
        Set<RawCode> rawSet = new LinkedHashSet<>(raw);
        System.out.println("Без equals/hashCode два \"A\" остаются: " + rawSet.size() + " элемента");
    }
}
