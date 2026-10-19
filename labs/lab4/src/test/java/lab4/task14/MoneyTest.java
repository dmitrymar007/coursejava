package lab4.task14;

import java.math.*;
import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MoneyTest {
    private static final Currency RUB = Currency.getInstance("RUB");
    private static Money rub(String value) { return new Money(new BigDecimal(value), RUB); }

    @Test
    void canonicalScaleMakesValueEqualityAndArithmeticMeaningful() {
        assertEquals(rub("1"), rub("1.00"));
        assertEquals(rub("1").hashCode(), rub("1.00").hashCode());
        assertEquals(rub("3.30"), rub("1.10").add(rub("2.20")));
        assertEquals(rub("-1.10"), rub("1.10").subtract(rub("2.20")));
        assertEquals(rub("0.33"), rub("3.25").percentage(new BigDecimal("10"), RoundingMode.HALF_UP));
        assertEquals(rub("0.32"), rub("3.25").percentage(new BigDecimal("10"), RoundingMode.HALF_EVEN));
    }

    @Test
    void currenciesAndFractionDigitsAreEnforced() {
        var usd = new Money(BigDecimal.ONE, Currency.getInstance("USD"));
        assertNotEquals(rub("1"), usd);
        assertThrows(IllegalArgumentException.class, () -> rub("1").add(usd));
        assertThrows(IllegalArgumentException.class, () -> rub("1").subtract(usd));
        assertThrows(ArithmeticException.class, () -> rub("1.001"));
        assertEquals(rub("1.01"), Money.of(new BigDecimal("1.005"), RUB, RoundingMode.HALF_UP));
        assertEquals(new BigDecimal("2"), Money.of(new BigDecimal("1.5"),
                Currency.getInstance("JPY"), RoundingMode.HALF_UP).amount());
        assertEquals(new BigDecimal("1.234"), new Money(new BigDecimal("1.234"),
                Currency.getInstance("KWD")).amount());
    }

    @Test
    void allocationPreservesBothPositiveAndNegativeTotals() {
        var weights = List.of(BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE);
        assertEquals(List.of(rub("0.34"), rub("0.33"), rub("0.33")),
                rub("1").allocation(weights, RoundingMode.DOWN));
        var negative = rub("-1").allocation(weights, RoundingMode.DOWN);
        assertEquals(rub("-1"), negative.stream().reduce(rub("0"), Money::add));
    }
}
