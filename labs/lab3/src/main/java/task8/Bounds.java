package task8;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public final class Bounds {
    private Bounds() {
    }

    /**
     * Максимум по естественному порядку.
     * <p>
     * Bound {@code T extends Comparable<? super T>}, а не {@code Comparable<T>}: если подкласс
     * (Dog extends Animal) наследует {@code Comparable<Animal>}, то Dog сравним с Animal, то есть
     * с суперклассом T. Благодаря этому для {@code List<Dog>} выводится {@code T = Dog} и результат
     * имеет тип Dog. Узкий bound {@code Comparable<T>} для Dog не подходит, см. {@link #maxNarrow}.
     * <p>
     * {@code Collection<? extends T>} - коллекция только читается (producer).
     */
    public static <T extends Comparable<? super T>> T max(Collection<? extends T> items) {
        Objects.requireNonNull(items, "items");
        Iterator<? extends T> it = items.iterator();
        if (!it.hasNext()) {
            throw new NoSuchElementException("коллекция пуста");
        }
        T best = Objects.requireNonNull(it.next(), "null-элемент");
        while (it.hasNext()) {
            T candidate = Objects.requireNonNull(it.next(), "null-элемент");
            if (candidate.compareTo(best) > 0) {
                best = candidate;
            }
        }
        return best;
    }

    /**
     * Слишком узкий вариант - для сравнения, см. Demo и ANSWER.md. Из-за {@code ? extends T}
     * для {@code List<Dog>} он всё же компилируется, но выводит {@code T = Animal}, поэтому
     * возвращает Animal, а не Dog. С параметром {@code List<T>} такой вызов был бы ошибкой компиляции.
     */
    public static <T extends Comparable<T>> T maxNarrow(Collection<? extends T> items) {
        return max(items);
    }
}
