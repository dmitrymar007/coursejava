package lab4.task15;

import lab4.task14.Money;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.*;
import java.util.Currency;

public class Demo {
    public static void main(String[] args) {
        var plan = new WebinarSeriesPlan(LocalDate.of(2024, 2, 29), LocalTime.NOON,
                ZoneId.of("Europe/Berlin"), Period.ofWeeks(1), 6, Duration.ofMinutes(90));
        var offering = new CourseOffering(plan, new Money(new BigDecimal("100.01"), Currency.getInstance("RUB")));
        var deadline = Instant.parse("2024-02-28T10:00:00Z");
        var pricing = new PricingService(new EarlyBirdPolicy(deadline, new BigDecimal("10")),
                RoundingMode.HALF_UP, Clock.fixed(deadline.minusSeconds(1), ZoneOffset.UTC));
        System.out.println("Organizer schedule: " + plan.starts());
        System.out.println("User schedule: " + plan.forUser(ZoneId.of("Asia/Novosibirsk")));
        System.out.println("Hours across DST: " + Duration.between(plan.starts().get(4), plan.starts().get(5)).toHours());
        System.out.println("Before deadline: " + pricing.priceAt(offering, deadline.minusNanos(1)));
        System.out.println("At deadline: " + pricing.priceAt(offering, deadline));
        var purchase = pricing.purchase(offering);
        System.out.println("Paid: " + purchase.paid());
        for (int lesson : new int[]{0, 4, 5}) {
            System.out.println("Refund at start of lesson " + (lesson + 1) + ": "
                    + RefundService.refund(purchase, plan.starts().get(lesson).toInstant(), RoundingMode.HALF_UP));
        }
    }
}
