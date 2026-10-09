package task4;

public class Main {
    public static void main(String[] args) {
        NotificationSender email = new EmailSender();
        NotificationSender console = new ConsoleSender();

        System.out.println(email.send("dima@example.com", "hello"));
        System.out.println(console.send("  dima  ", "hello"));

        for (NotificationSender sender : new NotificationSender[] { email, console }) {
            try {
                sender.send("   ", "x");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        try {
            email.send("dima", "x");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

    }
}
