package lab4.task15;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

public record EarlyBirdPolicy(Instant deadline, BigDecimal discountPercent) {
    public EarlyBirdPolicy {
        Objects.requireNonNull(deadline);
        if (discountPercent.signum() < 0 || discountPercent.compareTo(new BigDecimal("100")) > 0) {
            throw new IllegalArgumentException("Discount must be in [0, 100]");
        }
    }

    public boolean appliesAt(Instant now) {
        return now.isBefore(deadline);
    }
}
