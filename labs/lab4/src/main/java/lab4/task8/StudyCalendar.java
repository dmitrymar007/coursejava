package lab4.task8;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.stream.IntStream;

public record StudyCalendar(List<LocalDate> moduleStarts, LocalDate lastStudyDay, boolean includesLeapDay) {
    public StudyCalendar {
        moduleStarts = List.copyOf(moduleStarts);
    }

    public static StudyCalendar monthly(LocalDate start, int modules) {
        if (modules <= 0) throw new IllegalArgumentException("modules must be positive");
        var starts = IntStream.range(0, modules).mapToObj(start::plusMonths).toList();
        LocalDate endExclusive = start.plusMonths(modules);
        boolean leapDay = start.datesUntil(endExclusive)
                .anyMatch(date -> date.getMonth() == Month.FEBRUARY && date.getDayOfMonth() == 29);
        return new StudyCalendar(starts, endExclusive.minusDays(1), leapDay);
    }
}
