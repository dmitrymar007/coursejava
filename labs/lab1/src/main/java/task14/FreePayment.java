package task14;

public class FreePayment implements PaymentPolicy {
    @Override
    public int priceFor(Course course) {
        return 0;
    }

    @Override
    public String describe() {
        return "free";
    }
}
