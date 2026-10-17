Задание 6. Граница перевода исключения

CourseParser.parseHours разбирает часы через Integer.parseInt. Для «Git;abc» возникает NumberFormatException: парсер знает о неверном числе, но не знает номера записи в импорте.

CourseImporter ловит только NumberFormatException и преобразует её в checked CourseImportException. Номер записи считается как i + 1, исходное исключение сохраняется в cause. В Demo ошибка относится к записи 3, текст — «Import failed at record #3».

Перевод выполняется на уровне импорта, потому что здесь есть нужный контекст. catch (Exception) скрыл бы посторонние ошибки программы. Отсутствие второго поля текущий парсер отдельно не проверяет: такой сбой не превращается в CourseImportException.
