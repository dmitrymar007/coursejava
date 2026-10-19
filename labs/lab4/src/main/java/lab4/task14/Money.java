package lab4.task14;

import lab4.task6.DiscountAllocator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;
import java.util.List;
import java.util.Objects;

public record Money(BigDecimal amount, Currency currency) {
    public Money {
        Objects.requireNonNull(currency, "currency");
        if (currency.getDefaultFractionDigits() < 0) throw new IllegalArgumentException("Unknown currency scale");
        amount = amount.setScale(currency.getDefaultFractionDigits(), RoundingMode.UNNECESSARY);
    }

    public static Money of(BigDecimal amount, Currency currency, RoundingMode rounding) {
        return new Money(amount.setScale(currency.getDefaultFractionDigits(),
                Objects.requireNonNull(rounding)), currency);
    }

    public Money add(Money other) {
        requireCurrency(other);
        return new Money(amount.add(other.amount), currency);
    }

    public Money subtract(Money other) {
        requireCurrency(other);
        return new Money(amount.subtract(other.amount), currency);
    }

    public Money percentage(BigDecimal percent, RoundingMode rounding) {
        return of(amount.multiply(percent).movePointLeft(2), currency, rounding);
    }

    public List<Money> allocation(List<BigDecimal> weights, RoundingMode rounding) {
        return DiscountAllocator.allocate(amount.abs(), weights, amount.scale(), rounding).stream()
                .map(part -> new Money(amount.signum() < 0 ? part.negate() : part, currency)).toList();
    }

    public void requireCurrency(Money other) {
        if (!currency.equals(other.currency)) throw new IllegalArgumentException("Currency mismatch");
    }
}
