package lab4.task2;

public class ReportBuilder {
    public static String withPlus(int rows) {
        if (rows < 0) throw new IllegalArgumentException("rows < 0");
        String report = "";
        for (int i = 1; i <= rows; i++) report += "Course " + i + "\n";
        return report;
    }

    public static String withBuilder(int rows) {
        if (rows < 0) throw new IllegalArgumentException("rows < 0");
        StringBuilder report = new StringBuilder();
        for (int i = 1; i <= rows; i++) report.append("Course ").append(i).append('\n');
        return report.toString();
    }

    public static void main(String[] args) {
        long start = System.nanoTime();
        String plus = withPlus(10_000);
        long middle = System.nanoTime();
        String builder = withBuilder(10_000);
        long end = System.nanoTime();
        System.out.printf("equal=%s, +=%.3f ms, builder=%.3f ms%n",
                plus.equals(builder), (middle - start) / 1e6, (end - middle) / 1e6);
    }
}
