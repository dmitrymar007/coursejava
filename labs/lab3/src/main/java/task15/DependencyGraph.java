package task15;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

/** Граф зависимостей: ребро course -> prerequisite значит "course требует prerequisite". */
public class DependencyGraph<V> {
    private static final int VISITING = 1;
    private static final int DONE = 2;

    // LinkedHashMap/LinkedHashSet: обход в порядке добавления, поэтому результат детерминирован
    private final Map<V, Set<V>> prerequisites = new LinkedHashMap<>();

    public void addVertex(V vertex) {
        prerequisites.putIfAbsent(vertex, new LinkedHashSet<>());
    }

    public void addDependency(V course, V prerequisite) {
        requireVertex(course);
        requireVertex(prerequisite);
        prerequisites.get(course).add(prerequisite);
    }

    /** Порядок изучения (prerequisites раньше курса) или найденный цикл. */
    public Plan<V> plan() {
        Map<V, Integer> state = new HashMap<>();
        List<V> order = new ArrayList<>();
        List<V> path = new ArrayList<>();
        for (V vertex : prerequisites.keySet()) {
            if (!state.containsKey(vertex)) {
                List<V> cycle = visit(vertex, state, order, path);
                if (cycle != null) {
                    return new Plan<>(List.of(), cycle);
                }
            }
        }
        return new Plan<>(order, List.of());
    }

    /** Обход в глубину. Возвращает цикл, если нашёл, иначе null. */
    private List<V> visit(V vertex, Map<V, Integer> state, List<V> order, List<V> path) {
        state.put(vertex, VISITING);
        path.add(vertex);
        for (V pre : prerequisites.get(vertex)) {
            Integer s = state.get(pre);
            if (s == null) {
                List<V> cycle = visit(pre, state, order, path);
                if (cycle != null) {
                    return cycle;
                }
            } else if (s == VISITING) {
                // pre уже на текущем пути: цикл от pre до конца пути и обратно к pre
                List<V> cycle = new ArrayList<>(path.subList(path.indexOf(pre), path.size()));
                cycle.add(pre);
                return cycle;
            }
        }
        path.remove(path.size() - 1);
        state.put(vertex, DONE);
        order.add(vertex); // все prerequisites уже добавлены
        return null;
    }

    private void requireVertex(V vertex) {
        if (!prerequisites.containsKey(vertex)) {
            throw new NoSuchElementException("вершина не найдена: " + vertex);
        }
    }
}
