package task2;

public class ConsoleSender extends NotificationSender {

    @Override
    public String send(Object message) {
        return "ConsoleSender.send(Object)";
    }

    @Override
    public String channel() {
        return "console";
    }
}
