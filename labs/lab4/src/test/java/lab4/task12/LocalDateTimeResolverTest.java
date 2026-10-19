package lab4.task12;

import java.time.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LocalDateTimeResolverTest {
    private static final ZoneId BERLIN = ZoneId.of("Europe/Berlin");

    @Test
    void gapIsRejectedInsteadOfSilentlyShifted() {
        var gap = LocalDateTime.of(2024, 3, 31, 2, 30);
        assertEquals(LocalTime.of(3, 30), gap.atZone(BERLIN).toLocalTime());
        assertThrows(InvalidLocalTimeException.class, () -> LocalDateTimeResolver.resolve(gap, BERLIN));
    }

    @Test
    void overlapUsesEarlierInstantAndHourRepeatsLocalTime() {
        var overlap = LocalDateTimeResolver.resolve(LocalDateTime.of(2024, 10, 27, 2, 30), BERLIN);
        assertEquals(ZoneOffset.ofHours(2), overlap.getOffset());
        assertEquals(overlap.toLocalDateTime(), overlap.plusHours(1).toLocalDateTime());
        assertEquals(ZoneOffset.ofHours(1), overlap.plusHours(1).getOffset());
        assertEquals(Duration.ofHours(1), Duration.between(overlap, overlap.plusHours(1)));
    }

    @Test
    void hourUsesTimelineAndDayUsesCalendar() {
        var before = LocalDateTime.of(2024, 3, 30, 12, 0).atZone(BERLIN);
        assertEquals(Duration.ofHours(1), Duration.between(before, before.plusHours(1)));
        assertEquals(Duration.ofHours(23), Duration.between(before, before.plusDays(1)));
        assertEquals(12, before.plusDays(1).getHour());
    }
}
