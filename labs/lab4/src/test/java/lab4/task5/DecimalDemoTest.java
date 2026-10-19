package lab4.task5;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DecimalDemoTest {
    @Test
    void decimalInputPreservesExpectedValue() {
        BigDecimal tenth = new BigDecimal("0.1");
        assertTrue(new BigDecimal(0.1).compareTo(tenth) > 0);
        assertEquals(tenth, BigDecimal.valueOf(0.1));
        assertEquals(new BigDecimal("0.3"), DecimalDemo.total(tenth, tenth, tenth));
        assertEquals(new BigDecimal("60.00"), DecimalDemo.total(new BigDecimal("19.90"),
                new BigDecimal("29.95"), new BigDecimal("10.15")));
    }
}
