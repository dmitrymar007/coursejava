package lab7.task6;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

public class CourseEnrollment {
    public interface Course {
        boolean enroll(String student);
        Set<String> students();
    }

    public static class LockedCourse implements Course {
        private final int capacity;
        private final Set<String> students = new HashSet<>();

        public LockedCourse(int capacity) {
            if (capacity < 0) throw new IllegalArgumentException("Отрицательная вместимость");
            this.capacity = capacity;
        }

        public synchronized boolean enroll(String student) {
            Objects.requireNonNull(student);
            if (students.contains(student) || students.size() >= capacity) return false;
            return students.add(student);
        }

        public synchronized Set<String> students() { return Set.copyOf(students); }
    }

    public static class AtomicCourse implements Course {
        private final int capacity;
        private final AtomicReference<Set<String>> students = new AtomicReference<>(Set.of());

        public AtomicCourse(int capacity) {
            if (capacity < 0) throw new IllegalArgumentException("Отрицательная вместимость");
            this.capacity = capacity;
        }

        public boolean enroll(String student) {
            Objects.requireNonNull(student);
            while (true) {
                var current = students.get();
                if (current.contains(student) || current.size() >= capacity) return false;
                var next = new HashSet<>(current);
                next.add(student);
                if (students.compareAndSet(current, Set.copyOf(next))) return true;
            }
        }

        public Set<String> students() { return students.get(); }
    }

    public static void main(String[] args) {
        for (Course course : new Course[] {new LockedCourse(1), new AtomicCourse(1)}) {
            System.out.println(course.enroll("student-1"));
            System.out.println(course.enroll("student-1"));
            System.out.println(course.enroll("student-2"));
            System.out.println(course.students());
        }
    }
}
