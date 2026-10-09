package task14;

public record CourseOffering(Course course, CourseFormat format, PaymentPolicy payment) {
    public String summary() {
        return course.title() + " | " + format.describe(course)
                + " | " + payment.describe() + " => " + payment.priceFor(course);
    }
}
