package task6;

import java.util.List;

public class Demo {
    public static void main(String[] args) {
        List<String> records = List.of("Java;40", "SQL;30", "Git;abc", "Linux;20");
        try {
            System.out.println(new CourseImporter().importHours(records));
        } catch (CourseImportException e) {
            System.out.println("сообщение: " + e.getMessage());
            System.out.println("запись №:  " + e.getRecordNumber());
            System.out.println("cause:     " + e.getCause());
        }
    }
}
