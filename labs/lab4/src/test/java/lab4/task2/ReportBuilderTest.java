package lab4.task2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ReportBuilderTest {
    @Test
    void tenThousandLinesAreEqual() {
        String report = ReportBuilder.withBuilder(10_000);
        assertEquals(report, ReportBuilder.withPlus(10_000));
        assertEquals(10_000, report.lines().count());
        assertTrue(report.startsWith("Course 1\n"));
        assertTrue(report.endsWith("Course 10000\n"));
        assertEquals("", ReportBuilder.withBuilder(0));
    }
}
