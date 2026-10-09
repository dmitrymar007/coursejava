package task5;

@FunctionalInterface
public interface Formatter<T> {
    String format(T value);
}
