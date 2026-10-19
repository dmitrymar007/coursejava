package lab4.task12;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class LocalDateTimeResolver {
    // Gap отклоняем; при overlap выбираем первое вхождение (ранний Instant).
    public static ZonedDateTime resolve(LocalDateTime local, ZoneId zone) {
        var offsets = zone.getRules().getValidOffsets(local);
        if (offsets.isEmpty()) throw new InvalidLocalTimeException(local, zone);
        return ZonedDateTime.ofStrict(local, offsets.getFirst(), zone);
    }

    public static void main(String[] args) {
        ZoneId zone = ZoneId.of("Europe/Berlin");
        LocalDateTime gap = LocalDateTime.of(2024, 3, 31, 2, 30);
        System.out.println("Default gap shift: " + gap.atZone(zone));
        var before = LocalDateTime.of(2024, 3, 30, 12, 0).atZone(zone);
        System.out.println("+hour: " + before.plusHours(1));
        System.out.println("+day: " + before.plusDays(1));
        var overlap = resolve(LocalDateTime.of(2024, 10, 27, 2, 30), zone);
        System.out.println("Overlap: " + overlap + " -> +hour: " + overlap.plusHours(1));
    }
}
