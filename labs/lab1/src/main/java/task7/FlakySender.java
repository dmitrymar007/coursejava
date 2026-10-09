package task7;

import task4.NotificationSender;

public final class FlakySender implements NotificationSender {
    private final NotificationSender delegate;
    private final int failFirst;
    private int attempts;

    public FlakySender(NotificationSender delegate, int failFirst) {
        this.delegate = delegate;
        this.failFirst = failFirst;
    }

    @Override
    public String send(String recipient, String message) {
        attempts++;
        if (attempts <= failFirst) {
            throw new IllegalStateException("transient failure #" + attempts);
        }
        return delegate.send(recipient, message);
    }
}
