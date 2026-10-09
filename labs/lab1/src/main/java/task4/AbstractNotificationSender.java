package task4;

public abstract class AbstractNotificationSender implements NotificationSender {

    @Override
    public final String send(String recipient, String message) {
        requireRecipient(recipient);
        return doSend(recipient.trim(), message);
    }

    private static void requireRecipient(String recipient) {
        if (recipient == null || recipient.isBlank()) {
            throw new IllegalArgumentException("recipient is blank");
        }
    }

    protected abstract String doSend(String recipient, String message);
}
