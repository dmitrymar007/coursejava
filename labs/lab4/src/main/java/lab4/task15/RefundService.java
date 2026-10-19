package lab4.task15;

import lab4.task14.Money;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.Objects;

public class RefundService {
    // Занятие считается использованным в момент начала; цена берётся из покупки.
    public static Money refund(Purchase purchase, Instant now, RoundingMode rounding) {
        Objects.requireNonNull(rounding);
        if (now.isBefore(purchase.purchasedAt())) throw new IllegalArgumentException("Refund before purchase");
        var starts = purchase.offering().plan().starts();
        long remaining = starts.stream().filter(start -> start.toInstant().isAfter(now)).count();
        Money paid = purchase.paid();
        BigDecimal amount = paid.amount().multiply(BigDecimal.valueOf(remaining))
                .divide(BigDecimal.valueOf(starts.size()), paid.amount().scale(), rounding);
        return new Money(amount, paid.currency());
    }
}
