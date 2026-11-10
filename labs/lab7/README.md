# Лабораторная работа 7

Основы конкурентности: шесть заданий в `src/main/java/lab7/task1` … `task6`.
В каждой папке — реализация с `main` и краткий `ANSWER.md`.
Тесты: `src/test/java/lab7/taskN`. Общий `Pools` задаёт имена потоков и завершает executor.
Используется Java 21; настройки Maven и JUnit наследуются от корневого проекта.

Из корня репозитория:

```bash
mvn -pl labs/lab7 clean test
mvn -pl labs/lab7 test -Dtest=lab7.task5.DeterministicRaceTest
```

Запуск примеров после `mvn -pl labs/lab7 compile`:

```bash
java -cp labs/lab7/target/classes lab7.task1.Counters
java -cp labs/lab7/target/classes lab7.task2.StopFlags
java -cp labs/lab7/target/classes lab7.task3.SessionRegistry
java -cp labs/lab7/target/classes lab7.task4.RequestPipeline
java -cp labs/lab7/target/classes lab7.task5.DeterministicRace
java -cp labs/lab7/target/classes lab7.task6.CourseEnrollment
```

Гонка счётчика воспроизводится барьером; `Thread.sleep` для синхронизации не используется.
Обычный флаг остановки может сработать в конкретном запуске, но не гарантирует видимость.
Порядок результатов producer–consumer зависит от планировщика.
Для записи на курс сравниваются общая блокировка и CAS неизменяемого набора студентов.
