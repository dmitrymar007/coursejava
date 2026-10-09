package task13;

public class Main {
    public static void main(String[] args) {

        LegacyNotificationService legacy = new LegacyNotificationService();
        System.out.println(legacy.send("email", "a@b.c", "hi"));
        System.out.println(legacy.send("sms", "+100", "a long message that will be cut"));

        ChannelRegistry registry = new ChannelRegistry()
                .register("email", new EmailChannel("no-reply"))
                .register("email-support", new EmailChannel("support@courses.io"))
                .register("sms", new SmsChannel(20))
                .register("push", new PushChannel());
        NotificationService service = new NotificationService(registry);

        System.out.println(service.send("email", Priority.NORMAL, "a@b.c", "hi"));
        System.out.println(service.send("sms", Priority.NORMAL, "+100", "a long message that will be cut"));
        System.out.println(service.send("push", Priority.URGENT, "device-1", "Deadline"));
        System.out.println(service.send("email-support", Priority.URGENT, "a@b.c", "Help"));

        try {
            service.send("fax", Priority.NORMAL, "1", "x");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
