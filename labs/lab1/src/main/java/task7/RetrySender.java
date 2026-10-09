package task7;

import task4.NotificationSender;

public final class RetrySender implements NotificationSender {
    private final NotificationSender delegate;
    private final int maxAttempts;

    public RetrySender(NotificationSender delegate, int maxAttempts) {
        if (maxAttempts < 1) {
            throw new IllegalArgumentException("maxAttempts must be >= 1");
        }
        this.delegate = delegate;
        this.maxAttempts = maxAttempts;
    }

    @Override
    public String send(String recipient, String message) {
        RuntimeException last = null;
        for (int attempt = 1; attempt <= maxAttempts; attempt++) {
            try {
                return delegate.send(recipient, message);
            } catch (RuntimeException e) {
                last = e;
            }
        }
        throw last;
    }
}
