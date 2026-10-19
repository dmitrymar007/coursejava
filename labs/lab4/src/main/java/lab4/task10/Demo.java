package lab4.task10;

public class Demo {
    public static void main(String[] args) {
        for (String value : new String[]{"29.02.2024", "29.02.2023", "31.04.2024", "01.01.2024x"}) {
            try {
                System.out.println(value + " -> " + DateParser.parse("startDate", value));
            } catch (InvalidDateFieldException e) {
                System.out.println(e.getMessage() + "; cause=" + e.getCause().getClass().getSimpleName());
            }
        }
    }
}
