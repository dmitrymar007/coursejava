package lab4.task15;

import lab4.task14.Money;

import java.util.Objects;

public record CourseOffering(WebinarSeriesPlan plan, Money price) {
    public CourseOffering {
        Objects.requireNonNull(plan);
        Objects.requireNonNull(price);
        if (price.amount().signum() < 0) throw new IllegalArgumentException("Negative price");
    }
}
