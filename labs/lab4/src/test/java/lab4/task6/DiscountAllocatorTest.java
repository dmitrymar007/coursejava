package lab4.task6;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DiscountAllocatorTest {
    private static final List<BigDecimal> EQUAL = List.of(BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE);

    @Test
    void distributesBothPositiveAndNegativeRemainderInOrder() {
        assertEquals(List.of(new BigDecimal("0.34"), new BigDecimal("0.33"), new BigDecimal("0.33")),
                DiscountAllocator.allocate(new BigDecimal("1.00"), EQUAL, 2, RoundingMode.DOWN));
        assertEquals(List.of(new BigDecimal("0.00"), new BigDecimal("0.01"), new BigDecimal("0.01")),
                DiscountAllocator.allocate(new BigDecimal("0.02"), EQUAL, 2, RoundingMode.HALF_UP));
    }

    @Test
    void weightedDiscountPreservesSumForEveryRoundingMode() {
        var prices = List.of(new BigDecimal("19.90"), new BigDecimal("29.95"), new BigDecimal("10.15"));
        for (RoundingMode mode : RoundingMode.values()) {
            if (mode == RoundingMode.UNNECESSARY) continue;
            var parts = DiscountAllocator.allocate(new BigDecimal("5.00"), prices, 2, mode);
            assertEquals(new BigDecimal("5.00"), parts.stream().reduce(BigDecimal.ZERO, BigDecimal::add));
            assertTrue(parts.stream().allMatch(part -> part.signum() >= 0));
        }
    }

    @Test
    void validatesWeightsAndExactness() {
        assertThrows(IllegalArgumentException.class, () -> DiscountAllocator.allocate(
                BigDecimal.ONE, List.of(BigDecimal.ZERO), 2, RoundingMode.DOWN));
        assertThrows(IllegalArgumentException.class, () -> DiscountAllocator.allocate(
                BigDecimal.ONE, List.of(BigDecimal.ONE.negate()), 2, RoundingMode.DOWN));
        assertThrows(ArithmeticException.class, () -> DiscountAllocator.allocate(
                BigDecimal.ONE, EQUAL, 2, RoundingMode.UNNECESSARY));
        assertEquals(List.of(new BigDecimal("0.00")), DiscountAllocator.allocate(
                BigDecimal.ZERO, List.of(BigDecimal.ZERO), 2, RoundingMode.DOWN));
    }
}
