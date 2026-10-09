package task15;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Прикладной слой. Ловит только конкретные типы (ValidationException, NumberFormatException, IOException)
 * и переводит их в CourseHubException с кодом и cause. Ошибки программиста не ловятся.
 */
public class CourseHubService {
    private final Map<Long, Course> courses = new HashMap<>();

    public void register(Course course) {
        courses.put(course.id(), course);
    }

    /** Формат строки: id;title;durationHours;capacity. Курсы регистрируются только если принята вся порция. */
    public int importCourses(Reader reader) throws CourseHubException {
        Objects.requireNonNull(reader, "reader");
        List<Course> parsed = new ArrayList<>();
        try (BufferedReader in = new BufferedReader(reader)) {
            String line;
            int lineNumber = 0;
            while ((line = in.readLine()) != null) {
                lineNumber++;
                if (!line.isBlank()) {
                    parsed.add(parse(line, lineNumber));
                }
            }
        } catch (IOException e) {
            throw new CourseHubException(ErrorCode.IMPORT_IO_ERROR, "failed to read import source", 0, e);
        }
        parsed.forEach(this::register);
        return parsed.size();
    }

    public EnrollmentResult enroll(long courseId, Student student) {
        Objects.requireNonNull(student, "student");
        Course course = courses.get(courseId);
        return course == null ? EnrollmentResult.COURSE_NOT_FOUND : course.enroll(student);
    }

    private static Course parse(String line, int lineNumber) throws CourseHubException {
        try {
            String[] parts = line.split(";", -1);
            if (parts.length != 4) {
                throw new ValidationException("expected 4 fields, got " + parts.length);
            }
            return new Course(Long.parseLong(parts[0].trim()), parts[1],
                    Integer.parseInt(parts[2].trim()), Integer.parseInt(parts[3].trim()),
                    true, Set.of());
        } catch (NumberFormatException | ValidationException e) {
            throw new CourseHubException(ErrorCode.INVALID_RECORD, "invalid record #" + lineNumber, lineNumber, e);
        }
    }
}
