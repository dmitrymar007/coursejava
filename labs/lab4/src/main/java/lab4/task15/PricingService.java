package lab4.task15;

import lab4.task14.Money;

import java.math.RoundingMode;
import java.time.Clock;
import java.time.Instant;
import java.util.Objects;

public record PricingService(EarlyBirdPolicy earlyBird, RoundingMode rounding, Clock clock) {
    public PricingService {
        Objects.requireNonNull(earlyBird);
        Objects.requireNonNull(rounding);
        Objects.requireNonNull(clock);
    }

    public Money priceAt(CourseOffering offering, Instant now) {
        Money price = offering.price();
        return earlyBird.appliesAt(now)
                ? price.subtract(price.percentage(earlyBird.discountPercent(), rounding)) : price;
    }

    public Purchase purchase(CourseOffering offering) {
        Instant now = clock.instant();
        return new Purchase(offering, priceAt(offering, now), now);
    }
}
