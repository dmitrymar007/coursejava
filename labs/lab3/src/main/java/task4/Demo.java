package task4;

import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class Demo {
    public static void main(String[] args) {
        Waitlist<String> waitlist = new Waitlist<>();
        waitlist.join("alice");
        waitlist.join("bob");
        waitlist.join("carol");

        System.out.println("Следующий: " + waitlist.next().orElse("-"));
        System.out.println("Обслужен:  " + waitlist.serve().orElse("-"));
        System.out.println("Обслужен:  " + waitlist.serve().orElse("-"));
        System.out.println("Обслужен:  " + waitlist.serve().orElse("-"));
        System.out.println("Пустая очередь, serve(): " + waitlist.serve());
        try {
            waitlist.serveOrThrow();
        } catch (NoSuchElementException e) {
            System.out.println("Пустая очередь, serveOrThrow(): NoSuchElementException");
        }

        // Пары методов: различие видно на ограниченной очереди ёмкостью 1.
        Queue<String> bounded = new ArrayBlockingQueue<>(1);
        System.out.println("offer(a) = " + bounded.offer("a"));
        System.out.println("offer(b) = " + bounded.offer("b") + "   (очередь полна -> false)");
        try {
            bounded.add("b");
        } catch (IllegalStateException e) {
            System.out.println("add(b)   -> IllegalStateException: " + e.getMessage());
        }
        System.out.println("peek()   = " + bounded.peek() + ", element() = " + bounded.element());
        bounded.clear();
        System.out.println("peek()   = " + bounded.peek() + "   (пусто -> null)");
        System.out.println("poll()   = " + bounded.poll() + "   (пусто -> null)");
        try {
            bounded.element();
        } catch (NoSuchElementException e) {
            System.out.println("element()-> NoSuchElementException");
        }
    }
}
