package task6;

// Низкоуровневый парсер: NumberFormatException не перехватывает
public class CourseParser {
    // Запись имеет вид "название;часы"
    public static int parseHours(String record) {
        String[] parts = record.split(";");
        return Integer.parseInt(parts[1].trim());
    }
}
