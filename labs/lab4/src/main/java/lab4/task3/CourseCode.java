package lab4.task3;

import java.util.regex.Pattern;

public record CourseCode(String prefix, String number) {
    private static final Pattern CODE = Pattern.compile("(?<prefix>[A-Z]+)-(?<number>[0-9]{3})");

    public CourseCode {
        if (prefix == null || number == null || !CODE.matcher(prefix + "-" + number).matches()) {
            throw new IllegalArgumentException("Invalid course code");
        }
    }

    public static CourseCode parse(String value) {
        if (value == null) throw new IllegalArgumentException("Course code is null");
        var matcher = CODE.matcher(value);
        if (!matcher.matches()) throw new IllegalArgumentException("Invalid course code: " + value);
        return new CourseCode(matcher.group("prefix"), matcher.group("number"));
    }
}
