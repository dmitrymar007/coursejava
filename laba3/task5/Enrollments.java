package task5;

import java.util.Iterator;
import java.util.List;

final class Enrollments {
    private Enrollments() {
    }

    /** ОШИБКА: удаление из list внутри enhanced for ломает внутренний итератор. */
    static void removeCancelledBroken(List<String> names) {
        for (String name : names) {
            if (name.startsWith("x-")) {
                names.remove(name);
            }
        }
    }

    /** Исправление 1: удаляем через сам итератор - он знает об изменении. */
    static void removeCancelledIterator(List<String> names) {
        Iterator<String> it = names.iterator();
        while (it.hasNext()) {
            if (it.next().startsWith("x-")) {
                it.remove();
            }
        }
    }

    /** Исправление 2: removeIf - короче и для ArrayList делает один проход вместо O(n) на каждое удаление. */
    static void removeCancelledRemoveIf(List<String> names) {
        names.removeIf(name -> name.startsWith("x-"));
    }
}
