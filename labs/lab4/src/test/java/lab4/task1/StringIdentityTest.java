package lab4.task1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StringIdentityTest {
    @Test
    void identityAndValue() {
        String literal = "JAVA-101", prefix = new String("JAVA");
        String copy = new String(literal), runtime = prefix + "-101";
        assertSame(literal, "JAVA-101");
        assertSame(literal, "JAVA" + "-101");
        assertNotSame(literal, copy);
        assertNotSame(literal, runtime);
        assertEquals(literal, copy);
        assertEquals(literal, runtime);
        assertSame(literal, runtime.intern());
    }
}
