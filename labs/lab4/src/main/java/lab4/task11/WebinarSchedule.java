package lab4.task11;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Objects;

public record WebinarSchedule(Instant startsAt) {
    public WebinarSchedule {
        Objects.requireNonNull(startsAt);
    }

    public ZonedDateTime forUser(ZoneId zone) {
        return startsAt.atZone(zone);
    }

    public static void main(String[] args) {
        var schedule = new WebinarSchedule(Instant.parse("2024-03-31T08:00:00Z"));
        for (String zone : new String[]{"Europe/Berlin", "Asia/Novosibirsk", "America/New_York"}) {
            System.out.println(schedule.forUser(ZoneId.of(zone)));
        }
    }
}
