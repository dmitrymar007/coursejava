package lab4.task6;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class Demo {
    public static void main(String[] args) {
        var prices = List.of(new BigDecimal("19.90"), new BigDecimal("29.95"), new BigDecimal("10.15"));
        for (var mode : new RoundingMode[]{RoundingMode.DOWN, RoundingMode.HALF_UP}) {
            var parts = DiscountAllocator.allocate(new BigDecimal("5.00"), prices, 2, mode);
            System.out.println(mode + ": " + parts + ", sum="
                    + parts.stream().reduce(BigDecimal.ZERO, BigDecimal::add));
        }
    }
}
