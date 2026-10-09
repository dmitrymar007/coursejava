# coursejava

Лабораторные работы по курсу Java.

## Структура

```
coursejava/
├── pom.xml                 родительский pom: версия Java, JUnit, список лаб
└── labs/
    └── labN/
        ├── pom.xml         модуль лабораторной
        ├── tasks.txt       условия
        └── src/
            ├── main/java/taskM/   решение задачи M (+ README.md / ANSWER.md)
            └── test/java/taskM/   тесты задачи M
```

Одна лабораторная — один Maven-модуль, одна задача — один пакет `taskM`.

## Сборка и запуск

Требуется JDK 21+ и Maven.

```bash
mvn test                       # собрать всё и прогнать все тесты
mvn -pl labs/lab2 test         # только одна лабораторная
mvn -pl labs/lab2 test -Dtest='task15.*'   # только тесты одной задачи

mvn -q -pl labs/lab3 compile && java -cp labs/lab3/target/classes task1.Demo
```

В VS Code / IntelliJ IDEA открывайте корневую папку `coursejava` — модули подхватятся из `pom.xml`, `main` запускается кнопкой Run.

## Новая лабораторная

1. Скопировать `labs/lab1/pom.xml` в `labs/labN/pom.xml`, заменить `artifactId` на `labN`.
2. Добавить `<module>labs/labN</module>` в корневой `pom.xml`.
3. Код задач класть в `labs/labN/src/main/java/taskM/`, тесты — в `labs/labN/src/test/java/taskM/`.
