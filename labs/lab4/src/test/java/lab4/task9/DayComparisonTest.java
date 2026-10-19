package lab4.task9;

import java.time.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DayComparisonTest {
    @Test
    void calendarDayIsTwentyThreeHoursAcrossSpringDst() {
        var start = ZonedDateTime.of(2024, 3, 30, 12, 0, 0, 0, ZoneId.of("Europe/Berlin"));
        var result = DayComparison.from(start);
        assertEquals(12, result.calendarDay().getHour());
        assertEquals(13, result.twentyFourHours().getHour());
        assertEquals(Duration.ofHours(23), Duration.between(start, result.calendarDay()));
        assertEquals(Duration.ofHours(24), Duration.between(start, result.twentyFourHours()));
        assertEquals(LocalDateTime.of(2024, 3, 31, 12, 0), start.toLocalDateTime().plus(Period.ofDays(1)));
    }
}
