package task9;

import java.util.List;
import java.util.Objects;

public final class Copying {
    private Copying() {
    }

    /**
     * PECS: Producer Extends, Consumer Super.
     * src только отдаёт элементы -> {@code ? extends T}; dst только принимает -> {@code ? super T}.
     */
    public static <T> void copy(List<? extends T> src, List<? super T> dst) {
        Objects.requireNonNull(src, "src");
        Objects.requireNonNull(dst, "dst");
        for (T item : src) {
            dst.add(item);
        }
    }

    /** Чтение из {@code List<? super Course>} возможно только как Object. */
    public static Object firstOf(List<? super Course> consumer) {
        return consumer.isEmpty() ? null : consumer.get(0);
    }
}
