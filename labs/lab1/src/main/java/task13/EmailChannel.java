package task13;

public class EmailChannel implements NotificationChannel {
    private final String from;

    public EmailChannel(String from) {
        this.from = from;
    }

    @Override
    public String send(String recipient, String message) {
        return "[email from=" + from + "] to=" + recipient + " body=" + message;
    }
}
