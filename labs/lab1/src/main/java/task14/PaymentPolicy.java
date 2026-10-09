package task14;

public interface PaymentPolicy {
    int priceFor(Course course);

    String describe();
}
