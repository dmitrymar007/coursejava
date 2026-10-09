package task14;

public class Main {
    public static void main(String[] args) {
        Course course = new Course(10, "Java Fundamentals", 32);

        CourseFormat[] formats = {
                new OnlineFormat(),
                new ClassroomFormat("A-101"),
                new HybridFormat("A-101")
        };
        PaymentPolicy[] payments = {
                new FreePayment(),
                new FixedPayment(500),
                new SubscriptionPayment(100, 10)
        };

        for (CourseFormat format : formats) {
            for (PaymentPolicy payment : payments) {
                System.out.println(new CourseOffering(course, format, payment).summary());
            }
        }

        CourseFormat selfPaced = new SelfPacedFormat();
        PaymentPolicy installments = new InstallmentPayment(900, 3);
        System.out.println(new CourseOffering(course, selfPaced, new FreePayment()).summary());

        System.out.println(new CourseOffering(course, new HybridFormat("B-2"), installments).summary());

        System.out.println(new CourseOffering(course, selfPaced, installments).summary());

    }
}
