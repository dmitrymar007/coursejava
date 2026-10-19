package lab4.task4;

import java.util.Objects;

public class IntegerPolicy {
    // null запрещён контрактом; после проверки сравниваем примитивы.
    public static boolean sameValue(Integer left, Integer right) {
        return Objects.requireNonNull(left, "left").intValue()
                == Objects.requireNonNull(right, "right").intValue();
    }

    public static int unsafeUnbox(Integer value) {
        return value;
    }

    public static void main(String[] args) {
        for (int value : new int[]{127, 128, 1000}) {
            Integer a = value, b = value;
            System.out.printf("%d: == %s, value %s%n", value, a == b, sameValue(a, b));
        }
        try {
            unsafeUnbox(null);
        } catch (NullPointerException e) {
            System.out.println("Unboxing null: " + e.getClass().getSimpleName());
        }
    }
}
