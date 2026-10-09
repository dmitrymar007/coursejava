package task10;

public class Main {
    public static void main(String[] args) {
        CoursePage page = new CoursePage();
        System.out.println(page.render());

        TextRenderer asText = page;
        HtmlRenderer asHtml = page;
        System.out.println(asText.render());
        System.out.println(asHtml.render());
    }
}
