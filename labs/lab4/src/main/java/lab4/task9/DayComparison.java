package lab4.task9;

import java.time.Duration;
import java.time.Period;
import java.time.ZonedDateTime;

public record DayComparison(ZonedDateTime calendarDay, ZonedDateTime twentyFourHours) {
    public static DayComparison from(ZonedDateTime start) {
        return new DayComparison(start.plus(Period.ofDays(1)), start.plus(Duration.ofHours(24)));
    }
}
