package lab4.task7;

import java.util.List;

public record SampleStats(List<Double> values, double min, double max, double mean) {
    public SampleStats {
        values = List.copyOf(values);
    }

    public double lowerBound() { return Math.floor(min); }
    public double upperBound() { return Math.ceil(max); }
    public double roundedMean() { return Math.round(mean * 100.0) / 100.0; }
}
