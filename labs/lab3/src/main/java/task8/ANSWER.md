Задание 8. Ограничение типа

Сигнатура: <T extends Comparable<? super T>> T max(Collection<? extends T> items).

Почему Comparable<? super T>, а не Comparable<T>: если Dog наследует Animal, а Animal реализует Comparable<Animal>, то Dog сравним только как Animal, то есть Dog не реализует Comparable<Dog>. С bound Comparable<? super T> для List<Dog> выводится T = Dog, и max возвращает Dog. Узкий bound Comparable<T> для Dog не подходит. Наш maxNarrow(Collection<? extends T>) с List<Dog> всё же компилируется, но только потому, что компилятор выводит T = Animal, и результат имеет тип Animal: присвоить его переменной типа Dog нельзя. Если бы параметр был List<T>, вызов с List<Dog> был бы ошибкой компиляции.

Collection<? extends T> значит, что коллекцию мы только читаем.

Для пустой коллекции бросается NoSuchElementException, для null-элемента NullPointerException.

Проверено в Demo на Integer, String, собственном классе Price и на Dog.
