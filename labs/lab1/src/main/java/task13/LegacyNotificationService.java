package task13;

public class LegacyNotificationService {
    public String send(String notificationType, String recipient, String message) {
        switch (notificationType) {
            case "email":
                return "[email from=no-reply] to=" + recipient + " body=" + message;
            case "sms":
                String text = message.length() > 20 ? message.substring(0, 20) : message;
                return "[sms] to=" + recipient + " text=" + text;
            case "push":
                return "[push] device=" + recipient + " title=" + message;
            default:
                throw new IllegalArgumentException("unknown notificationType: " + notificationType);
        }
    }
}
