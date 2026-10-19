package lab4.task1;

public class StringIdentityDemo {
    public static void main(String[] args) {
        String literal = "JAVA-101";
        String prefix = args.length == 0 ? "JAVA" : args[0];
        compare("literal", literal, "JAVA-101");
        compare("new String", literal, new String("JAVA-101"));
        compare("compile-time", literal, "JAVA" + "-101");
        String runtime = prefix + "-101";
        compare("runtime", literal, runtime);
        compare("intern", literal, runtime.intern());
    }

    private static void compare(String label, String a, String b) {
        System.out.printf("%s: == %s, equals %s%n", label, a == b, a.equals(b));
    }
}
