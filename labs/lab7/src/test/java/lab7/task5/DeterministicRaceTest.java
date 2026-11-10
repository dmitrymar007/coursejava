package lab7.task5;

import lab7.task1.Counters;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import static org.junit.jupiter.api.Assertions.*;

@Timeout(15)
class DeterministicRaceTest {
    @Test
    void repeatedlyLosesIncrementAfterBothReads() throws Exception {
        for (int i = 0; i < 30; i++) {
            assertEquals(1, Counters.lostIncrement(), "Две записи одного значения вместо двух инкрементов");
        }
    }
}
