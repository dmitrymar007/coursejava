Задание 7. Generic-метод

firstOrThrow(List<T>) возвращает первый элемент, а для пустого списка бросает NoSuchElementException.

swap(List<T>, i, j) меняет два элемента местами через временную переменную. Неверный индекс даёт IndexOutOfBoundsException.

indexBy(List<T>, Function<T,K>) строит Map<K,T>. При конфликте ключей по умолчанию бросает IllegalStateException, чтобы ничего не терялось молча. Есть вариант с третьим параметром BinaryOperator: он явно решает, какой из двух элементов оставить (первый или последний).
