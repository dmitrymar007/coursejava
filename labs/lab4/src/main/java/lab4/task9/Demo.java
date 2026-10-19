package lab4.task9;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class Demo {
    public static void main(String[] args) {
        var start = LocalDateTime.of(2024, 3, 30, 12, 0).atZone(ZoneId.of("Europe/Berlin"));
        var comparison = DayComparison.from(start);
        System.out.println("Start: " + start);
        System.out.println("Period 1 day: " + comparison.calendarDay()
                + ", elapsed hours=" + Duration.between(start, comparison.calendarDay()).toHours());
        System.out.println("Duration 24 hours: " + comparison.twentyFourHours()
                + ", elapsed hours=" + Duration.between(start, comparison.twentyFourHours()).toHours());
    }
}
