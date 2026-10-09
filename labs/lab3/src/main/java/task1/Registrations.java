package task1;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

final class Registrations {
    private Registrations() {
    }

    /** Значения, встретившиеся в списке больше одного раза, в порядке обнаружения повтора. */
    static <T> Set<T> findDuplicates(List<T> registrations) {
        Set<T> seen = new LinkedHashSet<>();
        Set<T> duplicates = new LinkedHashSet<>();
        for (T item : registrations) {
            if (!seen.add(item)) {
                duplicates.add(item);
            }
        }
        return duplicates;
    }

    /** Вставка в позицию index со сдвигом хвоста; порядок остальных элементов сохраняется. */
    static <T> void insertAt(List<T> registrations, int index, T item) {
        registrations.add(index, item);
    }

    /** Удаляет только первое вхождение: остальные регистрации не трогаем. */
    static <T> boolean removeFirst(List<T> registrations, T item) {
        return registrations.remove(item);
    }
}
