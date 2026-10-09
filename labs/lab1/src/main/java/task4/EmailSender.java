package task4;

public class EmailSender extends AbstractNotificationSender {
    @Override
    protected String doSend(String recipient, String message) {

        if (!recipient.contains("@")) {
            throw new IllegalArgumentException("email recipient must contain '@': " + recipient);
        }
        return "[email] to=" + recipient + " body=" + message;
    }
}
