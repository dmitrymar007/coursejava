package task5;

public class Demo {
    static void run(String title, boolean failOnUse, boolean failOnClose) {
        System.out.println(title);
        try (Res r = new Res(failOnUse, failOnClose)) {
            r.use();
        } catch (RuntimeException e) {
            System.out.println("  поймали: " + e.getMessage());
            for (Throwable s : e.getSuppressed()) {
                System.out.println("  suppressed: " + s.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        run("Успех:", false, false);
        run("Ошибка в работе:", true, false);
        run("Ошибка и в работе, и в close:", true, true);
        run("Ошибка только в close:", false, true);
    }
}
