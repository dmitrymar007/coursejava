package task4;

public class Demo {
    // return в try: finally выполняется ДО фактического выхода из метода
    static int returnInTry() {
        try {
            System.out.println("  try");
            return 1;
        } finally {
            System.out.println("  finally");
        }
    }

    // throw в try: catch перехватывает, потом finally, потом метод завершается
    static int throwInTry() {
        try {
            System.out.println("  try");
            throw new RuntimeException("из try");
        } catch (RuntimeException e) {
            System.out.println("  catch: " + e.getMessage());
            return 2;
        } finally {
            System.out.println("  finally");
        }
    }

    // throw в catch: finally всё равно выполняется, затем исключение летит наружу
    static int throwInCatch() {
        try {
            System.out.println("  try");
            throw new RuntimeException("из try");
        } catch (RuntimeException e) {
            System.out.println("  catch");
            throw new IllegalStateException("из catch");
        } finally {
            System.out.println("  finally");
        }
    }

    // ПЛОХО: return в finally отменяет летящее исключение, оно пропадает бесследно
    @SuppressWarnings("finally")
    static int returnInFinally() {
        try {
            System.out.println("  try");
            throw new RuntimeException("важная ошибка");
        } finally {
            System.out.println("  finally");
            return 3;
        }
    }

    public static void main(String[] args) {
        System.out.println("return в try:");
        System.out.println("  результат = " + returnInTry());

        System.out.println("throw в try, return в catch:");
        System.out.println("  результат = " + throwInTry());

        System.out.println("throw в catch:");
        try {
            throwInCatch();
        } catch (IllegalStateException e) {
            System.out.println("  снаружи поймали: " + e.getMessage());
        }

        System.out.println("return в finally (плохо):");
        System.out.println("  результат = " + returnInFinally() + ", исключение потеряно");
    }
}
