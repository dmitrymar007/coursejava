Задание 5. Try-with-resources

Res реализует AutoCloseable и выводит open при создании, use при работе и close при закрытии. try-with-resources вызывает close и после успеха, и при исключении в теле блока.

Успех: open -> use -> close. Ошибка в use: ресурс всё равно закрывается, а наружу выходит IllegalStateException.

Если падают и use, и close, основным остаётся исключение из use. Ошибка close не теряется: она доступна в массиве getSuppressed(). Если падает только close, его IllegalArgumentException становится основным исключением.

cause и suppressed означают разное: cause объясняет происхождение ошибки при оборачивании, suppressed хранит дополнительный сбой, возникший при закрытии ресурса.
