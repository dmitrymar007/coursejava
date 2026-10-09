package task7;

import java.util.Map;
import java.util.Optional;

public class Demo {
    private static final Map<String, Integer> HOURS = Map.of("JAVA", 40, "SQL", 30);

    // Отсутствие курса ожидаемо -> Optional
    static Optional<Integer> findCourse(String id) {
        if (id == null) {
            throw new IllegalArgumentException("id не должен быть null");
        }
        return Optional.ofNullable(HOURS.get(id));
    }

    // Неверные аргументы - ошибка вызывающего -> unchecked exception
    static int calculatePrice(int hours, int pricePerHour) {
        if (hours < 0 || pricePerHour < 0) {
            throw new IllegalArgumentException("hours и pricePerHour должны быть >= 0");
        }
        return hours * pricePerHour;
    }

    public static void main(String[] args) {
        System.out.println(findCourse("JAVA"));
        System.out.println(findCourse("PYTHON"));

        System.out.println(calculatePrice(40, 10));

        try {
            calculatePrice(-1, 10);
        } catch (IllegalArgumentException e) {
            System.out.println("ошибка: " + e.getMessage());
        }

        try {
            findCourse(null);
        } catch (IllegalArgumentException e) {
            System.out.println("ошибка: " + e.getMessage());
        }
    }
}
