package task5;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;

public class Main {

    static final class CourseEntity extends BaseEntity {
        private final String title;

        CourseEntity(long id, String title) {
            super(id);
            this.title = title;
        }

        String title() {
            return title;
        }
    }

    public static void main(String[] args) {

        CourseEntity a = new CourseEntity(1, "Java");
        CourseEntity b = new CourseEntity(1, "Java (renamed)");
        Identifiable identifiable = a;
        System.out.println(identifiable.id());
        System.out.println(a.equals(b));

        CoursePolicy shortCoursesOnly = course -> course.durationHours() <= 40;
        System.out.println(shortCoursesOnly.allows(new Course(10, "Java Fundamentals", 32)));
        System.out.println(shortCoursesOnly.allows(new Course(11, "Spring Deep Dive", 80)));

        Formatter<Course> upper = course -> course.title().toUpperCase();
        System.out.println(upper.format(new Course(10, "Java Fundamentals", 32)));

        Clock clock = Clock.fixed(Instant.parse("2026-01-01T10:00:00Z"), ZoneOffset.UTC);
        System.out.println(clock.instant());
    }
}
