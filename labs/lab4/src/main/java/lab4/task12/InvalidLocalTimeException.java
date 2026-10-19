package lab4.task12;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class InvalidLocalTimeException extends IllegalArgumentException {
    public InvalidLocalTimeException(LocalDateTime local, ZoneId zone) {
        super("Nonexistent local time: " + local + " in " + zone);
    }
}
