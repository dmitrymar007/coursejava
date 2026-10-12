## Структура

coursejava/
├── pom.xml                 родительский pom: версия Java, JUnit, список лаб
└── labs/
    └── labN/
        ├── pom.xml         модуль лабораторной
        ├── tasks.txt       условия
        └── src/
            ├── main/java/taskM/   решение задачи M (+ README.md / ANSWER.md)
            └── test/java/taskM/   тесты задачи M


## Сборка и запуск

```bash
mvn test                       # собрать всё и прогнать все тесты
mvn -pl labs/lab2 test         # только одна лабораторная
mvn -pl labs/lab2 test -Dtest='task15.*'   # только тесты одной задачи

mvn -q -pl labs/lab3 compile && java -cp labs/lab3/target/classes task1.Demo
```

открывать корневую папку `coursejava` 
