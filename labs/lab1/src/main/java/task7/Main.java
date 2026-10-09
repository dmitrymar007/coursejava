package task7;

import task4.EmailSender;
import task4.NotificationSender;

public class Main {
    public static void main(String[] args) {

        NotificationSender inherited = new LoggingEmailSender();
        System.out.println(inherited.send("a@b.c", "hi"));

        MetricsSender metricsA = new MetricsSender(new RetrySender(new FlakySender(new EmailSender(), 2), 3));
        NotificationSender a = new LoggingSender("A", metricsA);
        a.send("a@b.c", "hi");

        System.out.println("A metrics: " + metricsA.summary());

        MetricsSender metricsB = new MetricsSender(new FlakySender(new EmailSender(), 2));
        NotificationSender b = new RetrySender(new LoggingSender("B", metricsB), 3);
        b.send("a@b.c", "hi");

        System.out.println("B metrics: " + metricsB.summary());
    }
}
