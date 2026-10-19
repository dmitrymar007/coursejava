package lab4.task15;

import lab4.task14.Money;

import java.time.Instant;
import java.util.Objects;

public record Purchase(CourseOffering offering, Money paid, Instant purchasedAt) {
    public Purchase {
        Objects.requireNonNull(offering);
        Objects.requireNonNull(purchasedAt);
        offering.price().requireCurrency(paid);
        if (paid.amount().signum() < 0) throw new IllegalArgumentException("Negative payment");
    }
}
