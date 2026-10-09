package task11;

public final class StrictEquality {
    private StrictEquality() {
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
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point p = (Point) o;
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
            return super.equals(o) && color.equals(((ColoredPoint) o).color);
        }

        @Override
        public int hashCode() {
            return 31 * super.hashCode() + color.hashCode();
        }
    }
}
