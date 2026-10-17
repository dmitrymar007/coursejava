Задание 4. try-catch-finally

returnInTry: сначала выводится try, затем finally, после этого метод возвращает 1. Значение return вычисляется до finally, но выход происходит после него.

throwInTry: try бросает исключение, catch его обрабатывает и готовит return 2, затем выполняется finally и возвращается 2.

throwInCatch: после try и catch выполняется finally, затем наружу выходит IllegalStateException из catch.

returnInFinally: try бросает RuntimeException, но return 3 из finally отменяет распространение исключения. Вызывающий код получает 3 и не узнаёт об ошибке. Этот метод — намеренный плохой пример: в рабочем коде return из finally использовать не следует. Исключение из самого finally тоже заменило бы прежний результат или исключение.
