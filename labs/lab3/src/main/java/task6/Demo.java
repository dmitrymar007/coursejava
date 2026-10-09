package task6;

public class Demo {
    static Result<Course> findCourse(String id) {
        return "J1".equals(id) ? Result.success(new Course("J1", "Java")) : Result.failure("нет курса " + id);
    }

    static Result<Integer> parseHours(String raw) {
        try {
            return Result.success(Integer.parseInt(raw));
        } catch (NumberFormatException e) {
            return Result.failure("не число: " + raw);
        }
    }

    public static void main(String[] args) {
        // Две конкретизации: Result<Course> и Result<Integer>
        Result<Course> course = findCourse("J1");
        Result<Course> missing = findCourse("zzz");
        Result<Integer> hours = parseHours("40");
        Result<Integer> bad = parseHours("abc");
        System.out.println(course + " | " + missing);
        System.out.println(hours + " | " + bad);

        // Инварианты
        try {
            missing.getValue();
        } catch (IllegalStateException e) {
            System.out.println("getValue у ошибки: " + e.getMessage());
        }
        try {
            Result.success(null);
        } catch (NullPointerException e) {
            System.out.println("success(null) запрещён: " + e.getMessage());
        }

        // Защита на этапе компиляции - эти строки не скомпилируются:
        // Result<Integer> mixed = findCourse("J1");   // Result<Course> нельзя присвоить Result<Integer>
        // Integer h = course.getValue();              // Course нельзя присвоить Integer
        Course c = course.getValue(); // без приведения типов: компилятор знает, что внутри Course
        System.out.println("Прочитано без cast: " + c.title());
    }
}
