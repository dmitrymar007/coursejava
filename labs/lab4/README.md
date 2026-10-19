# Лабораторная работа 4

Задания: `src/main/java/lab4/task1` … `task15`, в каждой папке — короткий `ANSWER.md`.
Тесты расположены в соответствующих пакетах `src/test/java/lab4/taskN`.
Префикс `lab4` исключает пересечение с пакетами других лабораторных.

Из корня репозитория:

```bash
mvn -pl labs/lab4 test
mvn -pl labs/lab4 test -Dtest=lab4.task15.CourseHubTest
```

У каждого задания есть `main`, который можно запустить из IDE. Для запуска из терминала сначала соберите классы:

```bash
mvn -pl labs/lab4 compile
java -cp labs/lab4/target/classes lab4.task1.StringIdentityDemo
java -cp labs/lab4/target/classes lab4.task2.ReportBuilder
java -cp labs/lab4/target/classes lab4.task3.Demo
java -cp labs/lab4/target/classes lab4.task4.IntegerPolicy
java -cp labs/lab4/target/classes lab4.task5.DecimalDemo
java -cp labs/lab4/target/classes lab4.task6.Demo
java -cp labs/lab4/target/classes lab4.task7.Demo
java -cp labs/lab4/target/classes lab4.task8.Demo
java -cp labs/lab4/target/classes lab4.task9.Demo
java -cp labs/lab4/target/classes lab4.task10.Demo
java -cp labs/lab4/target/classes lab4.task11.WebinarSchedule
java -cp labs/lab4/target/classes lab4.task12.LocalDateTimeResolver
java -cp labs/lab4/target/classes lab4.task13.Demo
java -cp labs/lab4/target/classes lab4.task14.Demo
java -cp labs/lab4/target/classes lab4.task15.Demo
```
