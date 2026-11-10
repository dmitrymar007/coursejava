package lab7.task2;

import java.util.concurrent.TimeUnit;
import lab7.Pools;

public class StopFlags {
    public static class PlainWorker implements Runnable {
        private boolean stopped;
        public void requestStop() { stopped = true; }
        public void run() {
            while (!stopped && !Thread.currentThread().isInterrupted()) {
                Thread.onSpinWait();
            }
        }
    }

    public static class VolatileWorker implements Runnable {
        private volatile boolean stopped;
        public void requestStop() { stopped = true; }
        public void run() {
            while (!stopped && !Thread.currentThread().isInterrupted()) {
                Thread.onSpinWait();
            }
        }
    }

    private static void observe(String name, Runnable worker, Runnable stop) throws Exception {
        var pool = Pools.named(1, "worker");
        try {
            var started = new java.util.concurrent.CountDownLatch(1);
            var future = pool.submit(() -> {
                started.countDown();
                worker.run();
            });
            if (!started.await(2, TimeUnit.SECONDS)) throw new IllegalStateException("Нет старта");
            stop.run();
            try {
                future.get(1, TimeUnit.SECONDS);
                System.out.println(name + ": worker остановился");
            } catch (java.util.concurrent.TimeoutException e) {
                System.out.println(name + ": остановка не замечена за секунду; прерываем worker");
                future.cancel(true);
            }
        } finally {
            Pools.shutdown(pool);
        }
    }

    public static void main(String[] args) throws Exception {
        var plain = new PlainWorker();
        observe("Обычный флаг", plain, plain::requestStop);
        var safe = new VolatileWorker();
        observe("volatile-флаг", safe, safe::requestStop);
    }
}
