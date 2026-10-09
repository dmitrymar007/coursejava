package task13;

public class NotificationService {
    private final ChannelRegistry registry;

    public NotificationService(ChannelRegistry registry) {
        this.registry = registry;
    }

    public String send(String notificationType, Priority priority, String recipient, String message) {
        return registry.get(notificationType).send(recipient, priority.prefix() + message);
    }
}
