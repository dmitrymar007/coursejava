package task1;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;

public class Demo {
    private static final int N = 20_000;
    private static final int WARMUP_RUNS = 3;
    private static final int MEASURED_RUNS = 7;

    public static void main(String[] args) {
        List<String> registrations = new ArrayList<>(List.of("alice", "bob", "alice", "carol", "bob"));
        System.out.println("Регистрации: " + registrations);

        Registrations.insertAt(registrations, 2, "dave");
        System.out.println("После вставки dave на позицию 2: " + registrations);

        Registrations.removeFirst(registrations, "carol");
        System.out.println("После удаления carol: " + registrations);

        System.out.println("Дубликаты: " + Registrations.findDuplicates(registrations));

        // Грубое измерение: прогрев + минимум из нескольких прогонов. Это НЕ JMH-микробенчмарк,
        // цифры показывают порядок величины, а не точные значения.
        compare("ArrayList", ArrayList::new);
        compare("LinkedList", LinkedList::new);
    }

    private static void compare(String name, Supplier<List<Integer>> factory) {
        List<Integer> list = factory.get();
        for (int i = 0; i < N; i++) {
            list.add(i);
        }

        long byIndex = Long.MAX_VALUE;
        long byIterator = Long.MAX_VALUE;
        long sink = 0;
        for (int run = 0; run < WARMUP_RUNS + MEASURED_RUNS; run++) {
            long t0 = System.nanoTime();
            for (int i = 0; i < N; i++) {
                sink += list.get(i);
            }
            long t1 = System.nanoTime();
            for (int v : list) {
                sink += v;
            }
            long t2 = System.nanoTime();
            if (run >= WARMUP_RUNS) {
                byIndex = Math.min(byIndex, t1 - t0);
                byIterator = Math.min(byIterator, t2 - t1);
            }
        }
        System.out.printf("%s (n=%d): get(i) %.2f мс, обход итератором %.2f мс [sink=%d]%n",
                name, N, byIndex / 1e6, byIterator / 1e6, sink);
    }
}
