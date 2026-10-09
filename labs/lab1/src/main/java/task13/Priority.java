package task13;

public enum Priority {
    NORMAL(""),
    URGENT("[URGENT] ");

    private final String prefix;

    Priority(String prefix) {
        this.prefix = prefix;
    }

    public String prefix() {
        return prefix;
    }
}
