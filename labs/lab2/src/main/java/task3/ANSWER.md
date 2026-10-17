Задание 3. Собственное исключение

EnrollmentRejectedException наследует Exception: это checked-исключение, которое вызывающий код должен обработать или объявить в throws. Поля studentId, courseId и reasonCode сохраняют контекст отказа и доступны через геттеры.

Конструктор передаёт причину в super(message, cause), поэтому исходный IllegalStateException не теряется и доступен через getCause.

Сообщение содержит только «Enrollment rejected: STORAGE_FAILURE». Стандартный toString добавляет имя класса, но не id и не сообщение причины. Полный stack trace включает cause и его внутренние данные, поэтому его нельзя считать безопасным публичным ответом. В Demo поля выводятся отдельно и явно.
