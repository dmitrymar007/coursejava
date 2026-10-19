package lab4.task13;

public class Demo {
    public static void main(String[] args) {
        var first = new CourseTitle("  Java\t BASICS  ");
        var second = new CourseTitle("java\u00a0basics");
        for (var title : new CourseTitle[]{first, second}) {
            System.out.printf("Display: '%s', search key: '%s'%n", title.displayValue(), title.searchKey());
        }
        System.out.println("Different display values: " + !first.displayValue().equals(second.displayValue()));
        System.out.println("Search key conflict: " + first.searchKey().equals(second.searchKey()));
    }
}
