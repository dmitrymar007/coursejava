package task5;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

public class Demo {
    private static List<String> sample() {
        return new ArrayList<>(List.of("alice", "x-bob", "x-carol", "dave"));
    }

    public static void main(String[] args) {
        List<String> broken = sample();
        try {
            Enrollments.removeCancelledBroken(broken);
        } catch (ConcurrentModificationException e) {
            System.out.println("enhanced for + remove: " + e.getClass().getSimpleName() + ", список: " + broken);
        }

        List<String> byIterator = sample();
        Enrollments.removeCancelledIterator(byIterator);
        System.out.println("Iterator.remove: " + byIterator);

        List<String> byRemoveIf = sample();
        Enrollments.removeCancelledRemoveIf(byRemoveIf);
        System.out.println("removeIf:        " + byRemoveIf);

        // Ловушка: удаление предпоследнего элемента НЕ бросает исключение, но пропускает элемент.
        List<String> trap = new ArrayList<>(List.of("a", "x-b", "x-c"));
        Enrollments.removeCancelledBroken(trap);
        System.out.println("Предпоследний элемент: исключения нет, но результат неверный: " + trap);
    }
}
