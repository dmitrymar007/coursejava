package lab4.task14;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;
import java.util.List;

public class Demo {
    public static void main(String[] args) {
        var rub = Currency.getInstance("RUB");
        var price = new Money(new BigDecimal("3.25"), rub);
        var discount = price.percentage(new BigDecimal("10"), RoundingMode.HALF_UP);
        System.out.println("Price: " + price + ", 10%: " + discount);
        System.out.println("After discount: " + price.subtract(discount));
        System.out.println("Add discount back: " + price.subtract(discount).add(discount));
        var one = new Money(BigDecimal.ONE, rub);
        System.out.println("1 equals 1.00: " + one.equals(new Money(new BigDecimal("1.00"), rub)));
        System.out.println("Allocation: " + one.allocation(
                List.of(BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE), RoundingMode.DOWN));
        try {
            one.add(new Money(BigDecimal.ONE, Currency.getInstance("USD")));
        } catch (IllegalArgumentException e) {
            System.out.println("Rejected: " + e.getMessage());
        }
    }
}
