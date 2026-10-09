package task13;

public class SmsChannel implements NotificationChannel {
    private final int maxLength;

    public SmsChannel(int maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public String send(String recipient, String message) {
        String text = message.length() > maxLength ? message.substring(0, maxLength) : message;
        return "[sms] to=" + recipient + " text=" + text;
    }
}
