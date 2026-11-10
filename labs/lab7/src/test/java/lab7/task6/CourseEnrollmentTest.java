package lab7.task6;

import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import lab7.Pools;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import static org.junit.jupiter.api.Assertions.*;

@Timeout(15)
class CourseEnrollmentTest {
    @Test
    void concurrentEnrollmentsRespectCapacityAndUniqueness() throws Exception {
        for (var course : new CourseEnrollment.Course[] {
                new CourseEnrollment.LockedCourse(10), new CourseEnrollment.AtomicCourse(10)}) {
            var pool = Pools.named(8, "enroll-test");
            var start = new CountDownLatch(1);
            try {
                var futures = new ArrayList<Future<Boolean>>();
                for (int i = 0; i < 200; i++) {
                    String student = "student-" + (i % 30);
                    futures.add(pool.submit(() -> { start.await(); return course.enroll(student); }));
                }
                start.countDown();
                int successes = 0;
                for (var future : futures) if (future.get(3, TimeUnit.SECONDS)) successes++;
                assertEquals(10, successes);
                assertEquals(10, course.students().size());
                var before = course.students();
                assertFalse(course.enroll("new-student"));
                assertEquals(before, course.students());
            } finally {
                start.countDown();
                Pools.shutdown(pool);
            }
        }
    }

    @Test
    void rejectionPreservesStateAndSnapshotCannotBeChanged() {
        for (var course : new CourseEnrollment.Course[] {
                new CourseEnrollment.LockedCourse(2), new CourseEnrollment.AtomicCourse(2)}) {
            assertTrue(course.enroll("alice"));
            var before = course.students();
            assertFalse(course.enroll("alice"));
            assertEquals(before, course.students());
            assertThrows(NullPointerException.class, () -> course.enroll(null));
            assertEquals(before, course.students());
            assertTrue(course.enroll("bob"));
            assertEquals(Set.of("alice"), before);
            assertThrows(UnsupportedOperationException.class, () -> before.add("other"));
        }
    }

    @Test
    void handlesZeroAndRejectsNegativeCapacity() {
        assertFalse(new CourseEnrollment.LockedCourse(0).enroll("alice"));
        assertFalse(new CourseEnrollment.AtomicCourse(0).enroll("alice"));
        assertThrows(IllegalArgumentException.class, () -> new CourseEnrollment.LockedCourse(-1));
        assertThrows(IllegalArgumentException.class, () -> new CourseEnrollment.AtomicCourse(-1));
    }
}
