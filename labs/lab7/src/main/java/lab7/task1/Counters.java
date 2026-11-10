package lab7.task1;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import lab7.Pools;

public class Counters {
    public static class UnsafeCounter {
        private int value;

        public void increment() {
            value++;
        }

        // Барьер заставляет оба потока прочитать старое значение до записи.
        public void increment(CyclicBarrier barrier) throws Exception {
            int old = value;
            int next = old + 1;
            barrier.await(2, TimeUnit.SECONDS);
            value = next;
        }

        public int value() { return value; }
    }

    public static class MethodCounter {
        private int value;
        public synchronized void increment() { value++; }
        public synchronized int value() { return value; }
    }

    public static class BlockCounter {
        private final Object lock = new Object();
        private int value;

        public void increment() {
            synchronized (lock) { value++; }
        }

        public int value() {
            synchronized (lock) { return value; }
        }
    }

    public static int lostIncrement() throws Exception {
        var counter = new UnsafeCounter();
        var barrier = new CyclicBarrier(2);
        var pool = Pools.named(2, "counter");
        try {
            var first = pool.submit(() -> { counter.increment(barrier); return null; });
            var second = pool.submit(() -> { counter.increment(barrier); return null; });
            first.get(3, TimeUnit.SECONDS);
            second.get(3, TimeUnit.SECONDS);
            return counter.value();
        } finally {
            Pools.shutdown(pool);
        }
    }

    public static void main(String[] args) throws Exception {
        System.out.println("Ожидалось 2, получено " + lostIncrement());
        var method = new MethodCounter();
        var block = new BlockCounter();
        var pool = Pools.named(4, "safe-counter");
        try {
            var futures = new java.util.ArrayList<java.util.concurrent.Future<?>>();
            for (int i = 0; i < 4; i++) {
                futures.add(pool.submit(() -> {
                    for (int j = 0; j < 10000; j++) {
                        method.increment();
                        block.increment();
                    }
                }));
            }
            for (var future : futures) future.get(3, TimeUnit.SECONDS);
            System.out.println("Метод: " + method.value() + ", блок: " + block.value());
        } finally {
            Pools.shutdown(pool);
        }
    }
}
