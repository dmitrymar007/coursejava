package task10;

public interface HtmlRenderer {
    default String render() {
        return "<html>";
    }
}
