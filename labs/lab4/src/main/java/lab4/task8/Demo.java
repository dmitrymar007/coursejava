package lab4.task8;

import java.time.LocalDate;

public class Demo {
    public static void main(String[] args) {
        var start = LocalDate.of(2024, 1, 31);
        var calendar = StudyCalendar.monthly(start, 3);
        System.out.println("Module starts: " + calendar.moduleStarts());
        System.out.println("Last study day: " + calendar.lastStudyDay());
        System.out.println("Includes leap day: " + calendar.includesLeapDay());
        System.out.println("Original start: " + start);
    }
}
