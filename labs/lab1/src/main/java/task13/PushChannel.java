package task13;

public class PushChannel implements NotificationChannel {
    @Override
    public String send(String recipient, String message) {
        return "[push] device=" + recipient + " title=" + message;
    }
}
