package task10;

public class CoursePage implements TextRenderer, HtmlRenderer {
    @Override
    public String render() {

        return TextRenderer.super.render() + " + " + HtmlRenderer.super.render();

    }
}
