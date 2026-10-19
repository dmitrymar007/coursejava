package lab4.task8;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StudyCalendarTest {
    @Test
    void leapDayAndImmutableStart() {
        LocalDate start = LocalDate.of(2024, 1, 31);
        var plan = StudyCalendar.monthly(start, 3);
        assertEquals(List.of(start, LocalDate.of(2024, 2, 29), LocalDate.of(2024, 3, 31)), plan.moduleStarts());
        assertEquals(LocalDate.of(2024, 4, 29), plan.lastStudyDay());
        assertTrue(plan.includesLeapDay());
        assertEquals(LocalDate.of(2024, 1, 31), start);
    }

    @Test
    void leapYearAloneDoesNotMeanLeapDayIsIncluded() {
        assertFalse(StudyCalendar.monthly(LocalDate.of(2024, 3, 1), 1).includesLeapDay());
        assertFalse(StudyCalendar.monthly(LocalDate.of(2023, 2, 1), 1).includesLeapDay());
        assertThrows(IllegalArgumentException.class, () -> StudyCalendar.monthly(LocalDate.of(2024, 1, 1), 0));
    }
}
