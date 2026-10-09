package task13;

import java.util.HashMap;
import java.util.Map;

public class ChannelRegistry {
    private final Map<String, NotificationChannel> channels = new HashMap<>();

    public ChannelRegistry register(String type, NotificationChannel channel) {
        channels.put(type, channel);
        return this;
    }

    public NotificationChannel get(String type) {
        NotificationChannel channel = channels.get(type);
        if (channel == null) {
            throw new IllegalArgumentException("unknown notificationType: " + type);
        }
        return channel;
    }
}
