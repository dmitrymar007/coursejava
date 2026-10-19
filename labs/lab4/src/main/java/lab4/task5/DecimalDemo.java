package lab4.task5;

import java.math.BigDecimal;

public class DecimalDemo {
    public static BigDecimal total(BigDecimal... prices) {
        BigDecimal sum = BigDecimal.ZERO;
        for (BigDecimal price : prices) sum = sum.add(price);
        return sum;
    }

    public static void main(String[] args) {
        System.out.println("double constructor: " + new BigDecimal(0.1));
        System.out.println("valueOf: " + BigDecimal.valueOf(0.1));
        System.out.println("string constructor: " + new BigDecimal("0.1"));
        System.out.println("Three courses: " + total(new BigDecimal("19.90"),
                new BigDecimal("29.95"), new BigDecimal("10.15")));
    }
}
