package lab4.task7;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RandomSampleServiceTest {
    @Test
    void seedReproducesSampleAndStats() {
        var sample = RandomSampleService.seeded(42).sample(100);
        assertEquals(sample, RandomSampleService.seeded(42).sample(100));
        assertTrue(sample.min() >= 0 && sample.max() < 100);
        assertEquals(sample.values().stream().mapToDouble(Double::doubleValue).min().orElseThrow(), sample.min());
        assertEquals(sample.values().stream().mapToDouble(Double::doubleValue).max().orElseThrow(), sample.max());
        assertTrue(sample.lowerBound() <= sample.min() && sample.upperBound() >= sample.max());
        assertEquals(sample.mean(), sample.roundedMean(), 0.00501);
    }

    @Test
    void productionSourceCanBeUsedWithoutFixedSeed() {
        assertEquals(3, RandomSampleService.production().sample(3).values().size());
        assertThrows(IllegalArgumentException.class, () -> RandomSampleService.seeded(1).sample(0));
    }
}
