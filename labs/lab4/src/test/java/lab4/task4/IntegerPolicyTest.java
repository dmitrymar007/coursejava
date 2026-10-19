package lab4.task4;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class IntegerPolicyTest {
    @Test
    void comparesValuesWithoutCacheAssumptions() {
        for (int value : new int[]{127, 128, 1000, -1000}) {
            assertTrue(IntegerPolicy.sameValue(value, value));
        }
        assertFalse(IntegerPolicy.sameValue(1000, 1001));
    }

    @Test
    void nullViolatesExplicitContract() {
        assertThrows(NullPointerException.class, () -> IntegerPolicy.unsafeUnbox(null));
        assertEquals("left", assertThrows(NullPointerException.class,
                () -> IntegerPolicy.sameValue(null, 1)).getMessage());
        assertEquals("right", assertThrows(NullPointerException.class,
                () -> IntegerPolicy.sameValue(1, null)).getMessage());
    }
}
