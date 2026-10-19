package lab4.task7;

import java.util.Objects;
import java.util.random.RandomGenerator;
import java.util.random.RandomGeneratorFactory;

public class RandomSampleService {
    private final RandomGenerator random;

    public RandomSampleService(RandomGenerator random) {
        this.random = Objects.requireNonNull(random);
    }

    public static RandomSampleService production() {
        return new RandomSampleService(RandomGenerator.getDefault());
    }

    public static RandomSampleService seeded(long seed) {
        return new RandomSampleService(RandomGeneratorFactory.of("L64X128MixRandom").create(seed));
    }

    public SampleStats sample(int size) {
        if (size <= 0) throw new IllegalArgumentException("size must be positive");
        var values = random.doubles(size, 0, 100).boxed().toList();
        var stats = values.stream().mapToDouble(Double::doubleValue).summaryStatistics();
        return new SampleStats(values, stats.getMin(), stats.getMax(), stats.getAverage());
    }
}
