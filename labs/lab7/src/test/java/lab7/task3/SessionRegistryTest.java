package lab7.task3;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import lab7.Pools;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import static org.junit.jupiter.api.Assertions.*;

@Timeout(10)
class SessionRegistryTest {
    @Test
    void concurrentCreationReturnsSameSession() throws Exception {
        var registry = new SessionRegistry();
        var start = new CountDownLatch(1);
        var pool = Pools.named(8, "session-test");
        try {
            var futures = new ArrayList<Future<SessionRegistry.Session>>();
            for (int i = 0; i < 100; i++) {
                futures.add(pool.submit(() -> { start.await(); return registry.getOrCreate("alice"); }));
            }
            start.countDown();
            var session = futures.getFirst().get(3, TimeUnit.SECONDS);
            for (var future : futures) assertSame(session, future.get(3, TimeUnit.SECONDS));
            assertEquals(1, registry.snapshot().size());
            assertNotSame(session, registry.getOrCreate("bob"));
            assertTrue(registry.remove("alice", session));
            assertEquals(1, registry.snapshot().size());
        } finally {
            start.countDown();
            Pools.shutdown(pool);
        }
    }
}
