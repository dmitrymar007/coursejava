package task14;

public class SubscriptionPayment implements PaymentPolicy {
    private final int monthlyFee;
    private final int hoursPerMonth;

    public SubscriptionPayment(int monthlyFee, int hoursPerMonth) {
        this.monthlyFee = monthlyFee;
        this.hoursPerMonth = hoursPerMonth;
    }

    @Override
    public int priceFor(Course course) {
        int months = (course.durationHours() + hoursPerMonth - 1) / hoursPerMonth;
        return months * monthlyFee;
    }

    @Override
    public String describe() {
        return "subscription " + monthlyFee + "/month";
    }
}
