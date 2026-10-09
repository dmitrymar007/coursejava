EnrollmentResult это sealed интерфейс, а Accepted, Rejected, WaitListed и добавленный позже PendingPayment это его допустимые типы. Метод describe в Main разбирает результат через switch без default.

При добавлении PendingPayment компилятор потребовал изменений в двух местах.

Первое: в списке permits самого интерфейса. Без этого ошибка: class is not allowed to extend sealed class: EnrollmentResult (as it is not listed in its 'permits' clause).

Второе: в switch в Main.describe. Пока не добавлена ветка для PendingPayment, ошибка: the switch expression does not cover all possible input values.

Без default компилятор сам показывает все места, где новый вариант нужно обработать. Если бы default был, новый тип молча попал бы в него.
