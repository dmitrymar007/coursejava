package task8;

/** Собственный value object: цена в копейках, сравнивается по сумме. */
public record Price(long kopecks) implements Comparable<Price> {
    public Price {
        if (kopecks < 0) {
            throw new IllegalArgumentException("цена не может быть отрицательной");
        }
    }

    @Override
    public int compareTo(Price other) {
        return Long.compare(kopecks, other.kopecks);
    }
}
