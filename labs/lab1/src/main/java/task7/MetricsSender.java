package task7;

import task4.NotificationSender;

public final class MetricsSender implements NotificationSender {
    private final NotificationSender delegate;
    private int calls;
    private int successes;
    private int failures;

    public MetricsSender(NotificationSender delegate) {
        this.delegate = delegate;
    }

    @Override
    public String send(String recipient, String message) {
        calls++;
        try {
            String result = delegate.send(recipient, message);
            successes++;
            return result;
        } catch (RuntimeException e) {
            failures++;
            throw e;
        }
    }

    public String summary() {
        return "calls=" + calls + " successes=" + successes + " failures=" + failures;
    }
}
