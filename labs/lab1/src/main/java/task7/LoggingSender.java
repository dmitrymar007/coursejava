package task7;

import task4.NotificationSender;

public final class LoggingSender implements NotificationSender {
    private final String label;
    private final NotificationSender delegate;

    public LoggingSender(String label, NotificationSender delegate) {
        this.label = label;
        this.delegate = delegate;
    }

    @Override
    public String send(String recipient, String message) {
        System.out.println("[" + label + "] send to " + recipient);
        try {
            String result = delegate.send(recipient, message);
            System.out.println("[" + label + "] ok: " + result);
            return result;
        } catch (RuntimeException e) {
            System.out.println("[" + label + "] failed: " + e.getMessage());
            throw e;
        }
    }
}
