package lab7;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Pools {
    public static ExecutorService named(int size, String prefix) {
        var number = new AtomicInteger();
        return Executors.newFixedThreadPool(size,
                task -> new Thread(task, prefix + "-" + number.incrementAndGet()));
    }

    public static void shutdown(ExecutorService pool) {
        pool.shutdown();
        try {
            if (!pool.awaitTermination(2, TimeUnit.SECONDS)) {
                pool.shutdownNow();
                if (!pool.awaitTermination(2, TimeUnit.SECONDS)) {
                    throw new IllegalStateException("Потоки не завершились");
                }
            }
        } catch (InterruptedException e) {
            pool.shutdownNow();
            try {
                pool.awaitTermination(2, TimeUnit.SECONDS);
            } catch (InterruptedException again) {
                // Повторное прерывание не отменяет запрос остановки пула.
            } finally {
                Thread.currentThread().interrupt();
            }
        }
    }
}
