package lab4.task7;

public class Demo {
    public static void main(String[] args) {
        var sample = RandomSampleService.seeded(42).sample(5);
        System.out.println("Seed 42: " + sample);
        System.out.println("Repeated seed gives same result: "
                + sample.equals(RandomSampleService.seeded(42).sample(5)));
        System.out.printf("Bounds: [%.0f, %.0f], rounded mean: %.2f%n",
                sample.lowerBound(), sample.upperBound(), sample.roundedMean());
        System.out.println("Production: " + RandomSampleService.production().sample(5).values());
    }
}
