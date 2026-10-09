package task9;

import java.util.ArrayList;
import java.util.List;

public class Demo {
    public static void main(String[] args) {
        List<OnlineCourse> online = List.of(new OnlineCourse("Java"), new OnlineCourse("SQL"));
        List<Course> catalog = new ArrayList<>();
        List<Object> anything = new ArrayList<>();

        Copying.copy(online, catalog);   // OnlineCourse -> List<Course>: producer extends, consumer super
        Copying.copy(online, anything);  // и в List<Object>
        System.out.println("catalog:  " + catalog);
        System.out.println("anything: " + anything);

        List<? extends Course> producer = online;
        Course c = producer.get(0);      // читать как Course можно
        // producer.add(new Course("X"));          // не компилируется
        // producer.add(new OnlineCourse("Y"));    // не компилируется даже для подтипа
        // producer.add(null);                     // единственное, что компилируется: null

        List<? super Course> consumer = catalog;
        consumer.add(new Course("Go"));          // писать Course и подтипы можно
        consumer.add(new OnlineCourse("Rust"));
        Object o = consumer.get(0);              // читать можно только как Object
        // Course bad = consumer.get(0);            // не компилируется
        System.out.println("прочитано из producer: " + c + ", из consumer как Object: " + o);
    }
}
