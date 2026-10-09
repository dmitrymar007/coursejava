package task8;

import java.util.List;

public class Demo {
    static class Animal implements Comparable<Animal> {
        final int weight;

        Animal(int weight) {
            this.weight = weight;
        }

        @Override
        public int compareTo(Animal o) {
            return Integer.compare(weight, o.weight);
        }

        @Override
        public String toString() {
            return getClass().getSimpleName() + "(" + weight + ")";
        }
    }

    static class Dog extends Animal {
        Dog(int weight) {
            super(weight);
        }
    }

    public static void main(String[] args) {
        System.out.println(Bounds.max(List.of(3, 9, 4)));
        System.out.println(Bounds.max(List.of("pear", "apple", "zebra")));
        System.out.println(Bounds.max(List.of(new Price(500), new Price(1500), new Price(70))));

        List<Dog> dogs = List.of(new Dog(10), new Dog(25));
        Dog heaviest = Bounds.max(dogs); // T = Dog: Dog сравним как Animal, bound Comparable<? super T> это допускает
        System.out.println("Dog (Comparable<Animal>): " + heaviest);

        Animal widened = Bounds.maxNarrow(dogs); // компилируется, но T = Animal: возвращается Animal, не Dog
        System.out.println("maxNarrow для тех же Dog вернул тип Animal: " + widened);
        // Dog lost = Bounds.maxNarrow(dogs);  // не компилируется: нужен Dog, а T выведен как Animal
    }
}
