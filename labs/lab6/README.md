# Лабораторная работа 6

Семь заданий: `src/main/java/lab6/task1` … `task7`. В каждой папке — реализация с `main` и короткий `ANSWER.md`. Тесты: `src/test/java/lab6/taskN`; общие неизменяемые `Course` и `Level` исключают повторение модели. Используется Java 21, зависимости наследуются от общего Maven-проекта.

Из корня репозитория:

```bash
mvn -pl labs/lab6 clean test
mvn -pl labs/lab6 test -Dtest=lab6.task7.CourseHubReportTest
```

Запуск примеров после `mvn -pl labs/lab6 compile`:

```bash
java -cp labs/lab6/target/classes lab6.task1.CourseOrder
java -cp labs/lab6/target/classes lab6.task2.Topics
java -cp labs/lab6/target/classes lab6.task3.DurationReduction
java -cp labs/lab6/target/classes lab6.task4.CourseSummaries
java -cp labs/lab6/target/classes lab6.task5.LongestCourse
java -cp labs/lab6/target/classes lab6.task6.DurationStatistics
java -cp labs/lab6/target/classes lab6.task7.CourseHubReport
java -Xmx16m -cp labs/lab6/target/classes lab6.task7.CourseHubReport 2000000
```

`Course.duration` — неотрицательное число минут, порядок уровней: BEGINNER, INTERMEDIATE, ADVANCED. Список модулей защищён от изменений. Строка модуля считается одной темой.

В задании 7 завершение подразумевает участие, повторы пары студент–курс не увеличивают знаменатель completion rate. Top-3 ранжируется по completion rate, затем числу завершивших и коду. Распределение по level считает уникальные пары студент–курс. Некорректные события исключаются из метрик и полностью сохраняются в списке аномалий. Подробные правила и границы памяти приведены в `task7/ANSWER.md`.

Тест большого потока запускает отдельную JVM с heap 16 МиБ: 2 000 000 событий, 1000 студентов, 1000 записавшихся и 500 завершивших курс JAVA. Вход генерируется лениво; память не зависит от числа повторных валидных событий.
