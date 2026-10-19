package lab4.task11;

import java.time.*;
import java.util.TimeZone;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class WebinarScheduleTest {
    @Test
    void threeExplicitZonesAreIndependentOfServerDefault() {
        var moment = Instant.parse("2024-03-31T08:00:00Z");
        var schedule = new WebinarSchedule(moment);
        var original = TimeZone.getDefault();
        try {
            for (String server : new String[]{"UTC", "Pacific/Honolulu"}) {
                TimeZone.setDefault(TimeZone.getTimeZone(server));
                assertEquals(10, schedule.forUser(ZoneId.of("Europe/Berlin")).getHour());
                assertEquals(15, schedule.forUser(ZoneId.of("Asia/Novosibirsk")).getHour());
                assertEquals(4, schedule.forUser(ZoneId.of("America/New_York")).getHour());
                assertEquals(moment, schedule.forUser(ZoneId.of("Europe/Berlin")).toInstant());
            }
        } finally {
            TimeZone.setDefault(original);
        }
    }
}
