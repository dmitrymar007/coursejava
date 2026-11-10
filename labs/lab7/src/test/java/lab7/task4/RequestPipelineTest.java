package lab7.task4;

import java.util.List;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import static org.junit.jupiter.api.Assertions.*;

@Timeout(15)
class RequestPipelineTest {
    @Test
    void everyRequestIsProcessedOnceWithTinyQueue() throws Exception {
        var first = IntStream.range(0, 100).boxed().toList();
        var second = IntStream.range(100, 200).boxed().toList();
        var actual = RequestPipeline.process(List.of(first, second), 3, 1);
        assertEquals(IntStream.range(0, 200).boxed().toList(), actual.stream().sorted().toList());
    }

    @Test
    void emptyInputAlsoStopsConsumers() throws Exception {
        assertEquals(List.of(), RequestPipeline.process(List.of(), 2, 1));
        assertEquals(List.of(), RequestPipeline.process(List.of(List.of()), 2, 1));
    }

    @Test
    void rejectsInvalidArgumentsBeforeStartingThreads() {
        assertThrows(IllegalArgumentException.class, () -> RequestPipeline.process(List.of(), 0, 1));
        assertThrows(IllegalArgumentException.class, () -> RequestPipeline.process(List.of(), 1, 0));
        assertThrows(IllegalArgumentException.class,
                () -> RequestPipeline.process(List.of(List.of(-1)), 1, 1));
    }

    @Test
    void interruptionReleasesExecutor() {
        Thread.currentThread().interrupt();
        try {
            assertThrows(InterruptedException.class,
                    () -> RequestPipeline.process(List.of(List.of(1, 2)), 1, 1));
            assertTrue(Thread.currentThread().isInterrupted());
        } finally {
            Thread.interrupted();
        }
    }
}
