package task10;

public interface TextRenderer {
    default String render() {
        return "text";
    }
}
