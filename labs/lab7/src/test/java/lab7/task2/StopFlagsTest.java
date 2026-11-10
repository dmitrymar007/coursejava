package lab7.task2;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import lab7.Pools;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import static org.junit.jupiter.api.Assertions.*;

@Timeout(10)
class StopFlagsTest {
    @Test
    void volatileFlagStopsWorker() throws Exception {
        var worker = new StopFlags.VolatileWorker();
        var started = new CountDownLatch(1);
        var pool = Pools.named(1, "stop-test");
        try {
            var future = pool.submit(() -> {
                started.countDown();
                worker.run();
            });
            assertTrue(started.await(2, TimeUnit.SECONDS));
            worker.requestStop();
            future.get(2, TimeUnit.SECONDS);
            assertTrue(future.isDone());
        } finally {
            worker.requestStop();
            Pools.shutdown(pool);
        }
    }

    @Test
    void plainWorkerHasEmergencyInterruptExit() throws Exception {
        var worker = new StopFlags.PlainWorker();
        var started = new CountDownLatch(1);
        var pool = Pools.named(1, "plain-test");
        try {
            pool.submit(() -> { started.countDown(); worker.run(); });
            assertTrue(started.await(2, TimeUnit.SECONDS));
            pool.shutdownNow();
            assertTrue(pool.awaitTermination(2, TimeUnit.SECONDS));
        } finally {
            Pools.shutdown(pool);
        }
    }
}
