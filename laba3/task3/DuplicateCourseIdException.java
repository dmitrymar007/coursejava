package task3;

public class DuplicateCourseIdException extends RuntimeException {
    private final CourseId id;

    public DuplicateCourseIdException(CourseId id) {
        super("курс с id " + id.value() + " уже есть в индексе");
        this.id = id;
    }

    public CourseId id() {
        return id;
    }
}
