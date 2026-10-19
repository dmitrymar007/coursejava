package lab4.task3;

public class Demo {
    public static void main(String[] args) {
        for (String value : new String[]{"JAVA-101", "SQL-007", "java-101", "ЯВА-101",
                "JAVA-١٠١", " JAVA-101", "JAVA-101x"}) {
            try {
                System.out.println(value + " -> " + CourseCode.parse(value));
            } catch (IllegalArgumentException e) {
                System.out.println(value + " -> rejected");
            }
        }
    }
}
