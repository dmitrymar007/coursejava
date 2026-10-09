package task11;

public final class BrokenEquality {
    private BrokenEquality() {
    }

    public static class Point {
        final int x;
        final int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Point p)) return false;
            return x == p.x && y == p.y;
        }

        @Override
        public int hashCode() {
            return 31 * x + y;
        }
    }

    public static class ColoredPoint extends Point {
        final String color;

        public ColoredPoint(int x, int y, String color) {
            super(x, y);
            this.color = color;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof ColoredPoint cp)) return false;
            return super.equals(cp) && color.equals(cp.color);
        }

        @Override
        public int hashCode() {
            return 31 * super.hashCode() + color.hashCode();
        }
    }
}
