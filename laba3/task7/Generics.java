package task7;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.BinaryOperator;
import java.util.function.Function;

public final class Generics {
    private Generics() {
    }

    /** Первый элемент; пустой список - NoSuchElementException (а не null и не IndexOutOfBounds). */
    public static <T> T firstOrThrow(List<T> list) {
        Objects.requireNonNull(list, "list");
        if (list.isEmpty()) {
            throw new NoSuchElementException("список пуст");
        }
        return list.get(0);
    }

    /** Меняет местами два элемента на месте. Индексы проверяет сам List (IndexOutOfBoundsException). */
    public static <T> void swap(List<T> list, int i, int j) {
        T tmp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, tmp);
    }

    /** Индекс по ключу; при конфликте ключей - IllegalStateException (без молчаливой перезаписи). */
    public static <T, K> Map<K, T> indexBy(List<T> items, Function<? super T, ? extends K> keyFn) {
        return indexBy(items, keyFn, (first, second) -> {
            throw new IllegalStateException("конфликт ключей: " + first + " и " + second);
        });
    }

    /** Индекс по ключу с явной политикой слияния: merge(старое, новое) решает, что хранить. */
    public static <T, K> Map<K, T> indexBy(List<T> items, Function<? super T, ? extends K> keyFn,
                                           BinaryOperator<T> merge) {
        Objects.requireNonNull(keyFn, "keyFn");
        Objects.requireNonNull(merge, "merge");
        Map<K, T> result = new HashMap<>();
        for (T item : items) {
            K key = keyFn.apply(item);
            if (key == null) {
                throw new IllegalArgumentException("ключ для " + item + " равен null");
            }
            result.merge(key, item, merge);
        }
        return result;
    }
}
