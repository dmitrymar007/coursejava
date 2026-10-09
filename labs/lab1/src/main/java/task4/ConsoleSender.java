package task4;

public class ConsoleSender extends AbstractNotificationSender {
    @Override
    protected String doSend(String recipient, String message) {
        return "[console] " + recipient + ": " + message;
    }
}
