# Лабораторная работа 5

Задания: `src/main/java/lab5/task1` … `task9`, в каждой папке — короткий `ANSEWR.md`.
Тесты: `src/test/java/lab5/taskN`. Общие `Course` и `ImportError` убирают повторение кода.
Текстовый каталог: одна запись `id;название` на строку, UTF-8; id положительный, название непустое, без `;` и переносов строк.

Из корня репозитория:

```bash
mvn -pl labs/lab5 test
mvn -pl labs/lab5 test -Dtest=lab5.task9.VersionedCatalogTest
```

У каждого задания есть `main` для запуска из IDE или терминала:

```bash
mvn -pl labs/lab5 compile
java -cp labs/lab5/target/classes lab5.task1.TextCatalog
java -cp labs/lab5/target/classes lab5.task2.BufferedText
java -cp labs/lab5/target/classes lab5.task3.FailingPipeline
java -cp labs/lab5/target/classes lab5.task4.LazyLines
java -cp labs/lab5/target/classes lab5.task5.BinaryCatalog
java -cp labs/lab5/target/classes lab5.task6.AtomicCatalog
java -cp labs/lab5/target/classes lab5.task7.AtomicImporter
java -cp labs/lab5/target/classes lab5.task8.StreamingImporter
java -cp labs/lab5/target/classes lab5.task9.VersionedCatalog
```

Примеры создают и удаляют временные файлы. Задание 8 также принимает путь к большому файлу первым аргументом и выводит счётчики без накопления курсов.
Тест большого импорта обрабатывает 700 000 строк в отдельной JVM с `-Xmx16m`; размер входного файла превышает этот лимит.
