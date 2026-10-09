package task14;

public class InstallmentPayment implements PaymentPolicy {
    private final int total;
    private final int parts;

    public InstallmentPayment(int total, int parts) {
        this.total = total;
        this.parts = parts;
    }

    @Override
    public int priceFor(Course course) {
        return total;
    }

    @Override
    public String describe() {
        return total + " in " + parts + " installments";
    }
}
