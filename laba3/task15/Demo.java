package task15;

import java.util.NoSuchElementException;

public class Demo {
    static DependencyGraph<String> build() {
        DependencyGraph<String> g = new DependencyGraph<>();
        for (String c : new String[]{"Java", "OOP", "Collections", "Streams", "Spring"}) {
            g.addVertex(c);
        }
        g.addDependency("OOP", "Java");
        g.addDependency("Collections", "OOP");
        g.addDependency("Streams", "Collections");
        g.addDependency("Spring", "Streams");
        g.addDependency("Spring", "OOP");
        return g;
    }

    public static void main(String[] args) {
        DependencyGraph<String> g = build();
        System.out.println("Порядок:           " + g.plan().order());
        System.out.println("Тот же ввод снова: " + build().plan().order().equals(g.plan().order()) + " (детерминированность)");

        g.addDependency("Java", "Spring"); // Java -> Spring -> Streams -> Collections -> OOP -> Java
        Plan<String> plan = g.plan();
        System.out.println("Есть цикл: " + plan.hasCycle() + ", путь: " + String.join(" -> ", plan.cycle()));

        try {
            g.addDependency("Java", "Kotlin");
        } catch (NoSuchElementException e) {
            System.out.println("Отсутствующая вершина: " + e.getMessage());
        }
    }
}
