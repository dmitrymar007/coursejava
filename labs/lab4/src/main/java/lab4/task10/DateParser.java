package lab4.task10;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Locale;

public class DateParser {
    private static final DateTimeFormatter FORMAT = DateTimeFormatter
            .ofPattern("dd.MM.uuuu", Locale.ROOT).withResolverStyle(ResolverStyle.STRICT);

    public static LocalDate parse(String field, String value) {
        if (value == null) throw new InvalidDateFieldException(field, null, null);
        try {
            return LocalDate.parse(value, FORMAT);
        } catch (DateTimeParseException e) {
            throw new InvalidDateFieldException(field, value, e);
        }
    }
}
