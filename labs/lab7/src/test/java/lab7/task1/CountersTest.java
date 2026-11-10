package lab7.task1;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import lab7.Pools;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import static org.junit.jupiter.api.Assertions.*;

@Timeout(10)
class CountersTest {
    @Test
    void bothLocksPreserveAllIncrements() throws Exception {
        var method = new Counters.MethodCounter();
        var block = new Counters.BlockCounter();
        var start = new CountDownLatch(1);
        var pool = Pools.named(4, "counter-test");
        try {
            var futures = new ArrayList<Future<?>>();
            for (int i = 0; i < 4; i++) {
                futures.add(pool.submit(() -> {
                    start.await();
                    for (int j = 0; j < 10000; j++) {
                        method.increment();
                        block.increment();
                    }
                    return null;
                }));
            }
            start.countDown();
            for (var future : futures) future.get(3, TimeUnit.SECONDS);
            assertEquals(40000, method.value());
            assertEquals(40000, block.value());
        } finally {
            start.countDown();
            Pools.shutdown(pool);
        }
    }
}
