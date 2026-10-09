package task2;

public class Main {
    public static void main(String[] args) {
        NotificationSender a = new EmailSender();
        NotificationSender b = new ConsoleSender();
        EmailSender c = new EmailSender();
        String s = "hi";
        Object o = "hi";

        System.out.println(a.send(s));

        System.out.println(a.send(o));

        System.out.println(a.send((Object) s));

        System.out.println(a.send(null));

        System.out.println(b.send(s));
        System.out.println(b.send(o));

        System.out.println(c.send(s));
        System.out.println(c.send(o));

        System.out.println(a.channel());
        System.out.println(b.channel());
        System.out.println(c.channel());

        System.out.println(a.kind());
        System.out.println(c.kind());

        System.out.println(((EmailSender) a).sendWithSubject("s", "b"));
    }
}
