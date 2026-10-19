package lab4.task6;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DiscountAllocator {
    // Сумма в минимальных денежных единицах; веса — любые неотрицательные числа.
    public static List<BigDecimal> allocate(BigDecimal amount, List<BigDecimal> weights,
                                             int scale, RoundingMode rounding) {
        Objects.requireNonNull(rounding, "rounding");
        if (scale < 0 || amount.signum() < 0 || weights.isEmpty()) {
            throw new IllegalArgumentException("Invalid amount, scale or empty weights");
        }
        amount = amount.setScale(scale, RoundingMode.UNNECESSARY);
        BigDecimal sum = BigDecimal.ZERO;
        for (BigDecimal weight : weights) {
            if (weight.signum() < 0) throw new IllegalArgumentException("Negative weight");
            sum = sum.add(weight);
        }
        if (sum.signum() == 0 && amount.signum() != 0) {
            throw new IllegalArgumentException("Positive amount requires positive weights");
        }
        List<BigDecimal> parts = new ArrayList<>();
        for (BigDecimal weight : weights) {
            parts.add(sum.signum() == 0 ? amount : amount.multiply(weight).divide(sum, scale, rounding));
        }
        BigDecimal allocated = parts.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        int remainder = amount.subtract(allocated).movePointRight(scale).intValueExact();
        BigDecimal unit = BigDecimal.ONE.movePointLeft(scale);
        // Устойчивый порядок: корректируем по одной копейке с начала списка.
        for (int i = 0; remainder != 0; i = (i + 1) % parts.size()) {
            int direction = Integer.signum(remainder);
            if (weights.get(i).signum() == 0 || (direction < 0 && parts.get(i).signum() == 0)) continue;
            parts.set(i, parts.get(i).add(unit.multiply(BigDecimal.valueOf(direction))));
            remainder -= direction;
        }
        return List.copyOf(parts);
    }
}
