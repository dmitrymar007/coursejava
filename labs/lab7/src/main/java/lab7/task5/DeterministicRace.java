package lab7.task5;

import lab7.task1.Counters;

public class DeterministicRace {
    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 5; i++) {
            System.out.println("Ожидалось 2, получено " + Counters.lostIncrement());
        }
    }
}
