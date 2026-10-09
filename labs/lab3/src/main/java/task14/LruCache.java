package task14;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * LRU-кэш фиксированной ёмкости. LinkedHashMap с accessOrder=true помнит порядок использования:
 * первым в обходе идёт самый давно использованный элемент.
 * null-политика: null-ключи и null-значения запрещены, поэтому get() == null означает "ключа нет".
 */
public class LruCache<K, V> {
    private final Map<K, V> map;

    public LruCache(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("ёмкость должна быть > 0");
        }
        this.map = new LinkedHashMap<>(16, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
                boolean evict = size() > capacity;
                if (evict) {
                    System.out.println("  вытеснен: " + eldest.getKey());
                }
                return evict;
            }
        };
    }

    /** Обращение делает ключ самым свежим. */
    public V get(K key) {
        return map.get(Objects.requireNonNull(key, "key"));
    }

    public void put(K key, V value) {
        map.put(Objects.requireNonNull(key, "key"), Objects.requireNonNull(value, "value"));
    }

    /** Ключи от самого старого к самому свежему. */
    @Override
    public String toString() {
        return map.keySet().toString();
    }
}
