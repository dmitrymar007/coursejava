package lab4.task15;

import lab4.task12.InvalidLocalTimeException;
import lab4.task14.Money;
import java.math.*;
import java.time.*;
import java.util.Currency;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CourseHubTest {
    private static final ZoneId BERLIN = ZoneId.of("Europe/Berlin");
    private static final Instant DEADLINE = Instant.parse("2024-02-28T10:00:00Z");
    private static final RoundingMode ROUNDING = RoundingMode.HALF_UP;

    private static Money rub(String amount) {
        return new Money(new BigDecimal(amount), Currency.getInstance("RUB"));
    }

    private static WebinarSeriesPlan plan(LocalDate date, LocalTime time, Period interval, int count) {
        return new WebinarSeriesPlan(date, time, BERLIN, interval, count, Duration.ofMinutes(90));
    }

    private static CourseOffering offering() {
        return new CourseOffering(plan(LocalDate.of(2024, 2, 29), LocalTime.NOON, Period.ofWeeks(1), 6), rub("100.01"));
    }

    private static PricingService pricing(Instant now) {
        return new PricingService(new EarlyBirdPolicy(DEADLINE, new BigDecimal("10")),
                ROUNDING, Clock.fixed(now, ZoneOffset.UTC));
    }

    @Test
    void earlyBirdBoundaryIsAnInstantAndPurchaseKeepsActualPayment() {
        var offering = offering();
        var service = pricing(DEADLINE.minusNanos(1));
        assertEquals(rub("90.01"), service.priceAt(offering, DEADLINE.minusNanos(1)));
        assertEquals(rub("100.01"), service.priceAt(offering, DEADLINE));
        assertEquals(rub("100.01"), service.priceAt(offering, DEADLINE.plusNanos(1)));
        var purchase = service.purchase(offering);
        assertEquals(rub("90.01"), purchase.paid());
        assertEquals(DEADLINE.minusNanos(1), purchase.purchasedAt());
    }

    @Test
    void leapDayWeeklyScheduleKeepsLocalHourAcrossDstForDifferentUsers() {
        var plan = offering().plan();
        var starts = plan.starts();
        assertEquals(LocalDate.of(2024, 2, 29), starts.getFirst().toLocalDate());
        assertTrue(starts.stream().allMatch(start -> start.getHour() == 12));
        assertEquals(Duration.ofHours(167), Duration.between(starts.get(4), starts.get(5)));
        var userStarts = plan.forUser(ZoneId.of("Asia/Novosibirsk"));
        assertEquals(18, userStarts.getFirst().getHour());
        assertEquals(17, userStarts.getLast().getHour());
        for (int i = 0; i < starts.size(); i++) {
            assertEquals(starts.get(i).toInstant(), userStarts.get(i).toInstant());
        }
        assertEquals(Duration.ofMinutes(90), Duration.between(starts.getLast(), plan.endsAt(5)));
    }

    @Test
    void nonexistentLessonIsRejectedAndRepeatedLessonUsesFirstOccurrence() {
        var gap = plan(LocalDate.of(2024, 3, 31), LocalTime.of(2, 30), Period.ofDays(1), 1);
        assertThrows(InvalidLocalTimeException.class, gap::starts);
        var overlap = plan(LocalDate.of(2024, 10, 27), LocalTime.of(2, 30), Period.ofDays(1), 1);
        assertEquals(ZoneOffset.ofHours(2), overlap.starts().getFirst().getOffset());
        assertEquals(Instant.parse("2024-10-27T02:00:00Z"), overlap.endsAt(0).toInstant());
    }

    @Test
    void refundUsesPaidAmountAndCountsOnlyNotStartedLessons() {
        var purchase = pricing(DEADLINE.minusSeconds(1)).purchase(offering());
        var starts = purchase.offering().plan().starts();
        Instant first = starts.getFirst().toInstant();
        assertEquals(rub("90.01"), RefundService.refund(purchase, first.minusNanos(1), ROUNDING));
        assertEquals(rub("75.01"), RefundService.refund(purchase, first, ROUNDING));
        assertEquals(rub("15.00"), RefundService.refund(purchase, starts.get(4).toInstant(), ROUNDING));
        assertEquals(rub("0"), RefundService.refund(purchase, starts.getLast().toInstant(), ROUNDING));
        assertThrows(IllegalArgumentException.class,
                () -> RefundService.refund(purchase, purchase.purchasedAt().minusNanos(1), ROUNDING));
    }

    @Test
    void refundRoundingIsExplicitAndCurrencyIsPreserved() {
        var plan = plan(LocalDate.of(2024, 2, 29), LocalTime.NOON, Period.ofDays(1), 3);
        var usd = new Money(new BigDecimal("1.00"), Currency.getInstance("USD"));
        var offering = new CourseOffering(plan, usd);
        var purchase = new Purchase(offering, usd, DEADLINE);
        var now = plan.starts().getFirst().toInstant();
        assertEquals(new Money(new BigDecimal("0.67"), usd.currency()), RefundService.refund(purchase, now, ROUNDING));
        assertEquals(new Money(new BigDecimal("0.66"), usd.currency()), RefundService.refund(purchase, now, RoundingMode.DOWN));
        assertThrows(IllegalArgumentException.class, () -> new Purchase(offering, rub("1"), DEADLINE));
    }

    @Test
    void invalidBusinessInputsAreRejected() {
        assertThrows(IllegalArgumentException.class, () -> new EarlyBirdPolicy(DEADLINE, new BigDecimal("101")));
        assertThrows(IllegalArgumentException.class, () -> new CourseOffering(offering().plan(), rub("-1")));
        assertThrows(IllegalArgumentException.class, () -> plan(LocalDate.of(2024, 2, 29), LocalTime.NOON, Period.ZERO, 3));
        assertThrows(IllegalArgumentException.class, () -> plan(LocalDate.of(2024, 2, 29), LocalTime.NOON, Period.ofDays(1), 0));
    }
}
