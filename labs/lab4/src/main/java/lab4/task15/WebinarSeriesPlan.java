package lab4.task15;

import lab4.task12.LocalDateTimeResolver;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

public record WebinarSeriesPlan(LocalDate firstDate, LocalTime localTime, ZoneId organizerZone,
                                Period interval, int lessons, Duration lessonDuration) {
    public WebinarSeriesPlan {
        Objects.requireNonNull(firstDate);
        Objects.requireNonNull(localTime);
        Objects.requireNonNull(organizerZone);
        if (lessons <= 0 || interval.isZero() || interval.isNegative()
                || lessonDuration.isZero() || lessonDuration.isNegative()) {
            throw new IllegalArgumentException("Positive lessons, interval and duration required");
        }
    }

    public List<ZonedDateTime> starts() {
        // Привязка к первой дате исключает накопление сдвига при коротких месяцах.
        return IntStream.range(0, lessons).mapToObj(i -> LocalDateTimeResolver.resolve(
                firstDate.plus(interval.multipliedBy(i)).atTime(localTime), organizerZone)).toList();
    }

    public List<ZonedDateTime> forUser(ZoneId userZone) {
        return starts().stream().map(start -> start.withZoneSameInstant(userZone)).toList();
    }

    public ZonedDateTime endsAt(int lessonIndex) {
        return starts().get(lessonIndex).plus(lessonDuration);
    }
}
