package task5;

public class Res implements AutoCloseable {
    private final boolean failOnUse;
    private final boolean failOnClose;

    public Res(boolean failOnUse, boolean failOnClose) {
        this.failOnUse = failOnUse;
        this.failOnClose = failOnClose;
        System.out.println("  open");
    }

    public void use() {
        System.out.println("  use");
        if (failOnUse) {
            throw new IllegalStateException("ошибка в работе");
        }
    }

    @Override
    public void close() {
        System.out.println("  close");
        if (failOnClose) {
            throw new IllegalArgumentException("ошибка при закрытии");
        }
    }
}
