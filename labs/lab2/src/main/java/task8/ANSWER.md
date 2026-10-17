Задание 8. Первый тест по AAA

CourseTest проверяет успешный вызов completeHours по схеме Arrange, Act, Assert. Сначала создаётся курс на 32 часа, затем засчитываются 6 часов, после чего assertEquals проверяет completedHours == 6.

Имя completeHours_withinRemaining_increasesCompletedHours описывает условие и результат. Единственная проверка фиксирует один эффект команды: увеличение числа пройденных часов. JUnit Jupiter запускает метод с @Test через Maven; демонстрационный main для этого не нужен.
