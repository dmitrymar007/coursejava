package task15;

import java.util.List;

/** Результат планирования: либо порядок изучения, либо цикл (первая и последняя вершины пути совпадают). */
public record Plan<V>(List<V> order, List<V> cycle) {
    public boolean hasCycle() {
        return !cycle.isEmpty();
    }
}
