package task2;

public class EmailSender extends NotificationSender {

    @Override
    public String send(String message) {
        return "EmailSender.send(String)";
    }

    @Override
    public String channel() {
        return "email";
    }

    public static String kind() {
        return "EmailSender.kind()";
    }

    public String sendWithSubject(String subject, String body) {
        return "EmailSender.sendWithSubject(String, String)";
    }
}
