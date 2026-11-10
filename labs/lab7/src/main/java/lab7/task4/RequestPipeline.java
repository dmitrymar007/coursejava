package lab7.task4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import lab7.Pools;

public class RequestPipeline {
    private static final int STOP = -1;

    public static List<Integer> process(List<List<Integer>> batches, int consumers, int capacity)
            throws Exception {
        if (consumers <= 0 || capacity <= 0) throw new IllegalArgumentException("Нужны положительные размеры");
        for (var batch : batches) {
            for (var id : batch) {
                if (id == null || id < 0) throw new IllegalArgumentException("id должен быть неотрицательным");
            }
        }
        var queue = new ArrayBlockingQueue<Integer>(capacity);
        var processed = Collections.synchronizedList(new ArrayList<Integer>());
        // Сценарий блокирующий: каждый producer и consumer получает свой поток.
        var pool = Pools.named(Math.max(1, batches.size()) + consumers, "requests");
        try {
            var readers = new ArrayList<Future<?>>();
            for (int i = 0; i < consumers; i++) {
                readers.add(pool.submit(() -> {
                    while (true) {
                        int id = queue.take();
                        if (id == STOP) return null;
                        processed.add(id);
                    }
                }));
            }
            var writers = new ArrayList<Future<?>>();
            for (var batch : batches) {
                writers.add(pool.submit(() -> {
                    for (int id : batch) queue.put(id);
                    return null;
                }));
            }
            for (var writer : writers) writer.get(5, TimeUnit.SECONDS);
            for (int i = 0; i < consumers; i++) {
                if (!queue.offer(STOP, 5, TimeUnit.SECONDS)) {
                    throw new java.util.concurrent.TimeoutException("Очередь не принимает сигнал завершения");
                }
            }
            for (var reader : readers) reader.get(5, TimeUnit.SECONDS);
            return List.copyOf(processed);
        } catch (Exception e) {
            pool.shutdownNow();
            if (e instanceof InterruptedException) Thread.currentThread().interrupt();
            throw e;
        } finally {
            Pools.shutdown(pool);
        }
    }

    public static void main(String[] args) throws Exception {
        System.out.println(process(List.of(List.of(1, 2, 3), List.of(4, 5, 6)), 2, 2));
    }
}
