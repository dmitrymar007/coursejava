package task11;

public final class CompositionEquality {
    private CompositionEquality() {
    }

    public record Point(int x, int y) {
    }

    public record ColoredPoint(Point point, String color) {
        public ColoredPoint(int x, int y, String color) {
            this(new Point(x, y), color);
        }
    }
}
