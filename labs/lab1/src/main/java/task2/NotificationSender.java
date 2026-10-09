package task2;

public class NotificationSender {
    public String send(String message) {
        return "NotificationSender.send(String)";
    }

    public String send(Object message) {
        return "NotificationSender.send(Object)";
    }

    public String channel() {
        return "generic";
    }

    public static String kind() {
        return "NotificationSender.kind()";
    }
}
