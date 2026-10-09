package task4;

import java.util.ArrayDeque;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.Queue;

/** FIFO-очередь ожидания. Первым выходит тот, кто встал раньше. */
public class Waitlist<T> {
    private final Queue<T> queue = new ArrayDeque<>();

    /** Встать в очередь. ArrayDeque не ограничен по размеру, поэтому offer всегда true; null запрещён. */
    public void join(T student) {
        Objects.requireNonNull(student, "student");
        queue.offer(student);
    }

    /** Кто следующий, не извлекая. На пустой очереди - пустой Optional (peek). */
    public Optional<T> next() {
        return Optional.ofNullable(queue.peek());
    }

    /** Извлечь следующего. На пустой очереди - пустой Optional (poll). */
    public Optional<T> serve() {
        return Optional.ofNullable(queue.poll());
    }

    /** Строгий вариант: на пустой очереди бросает NoSuchElementException (remove). */
    public T serveOrThrow() {
        return queue.remove();
    }

    /** Строгий вариант просмотра (element). */
    public T nextOrThrow() {
        if (queue.isEmpty()) {
            throw new NoSuchElementException("очередь ожидания пуста");
        }
        return queue.element();
    }

    public int size() {
        return queue.size();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}
