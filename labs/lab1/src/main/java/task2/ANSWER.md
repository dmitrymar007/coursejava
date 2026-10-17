Задание 2. Dynamic dispatch на бумаге

Предсказания:

- a.send(s) - EmailSender.send(String)
- a.send(o) - NotificationSender.send(Object)
- a.send((Object) s) - NotificationSender.send(Object)
- a.send(null) - EmailSender.send(String)
- b.send(s) - NotificationSender.send(String)
- b.send(o) - ConsoleSender.send(Object)
- c.send(s) - EmailSender.send(String)
- c.send(o) - NotificationSender.send(Object)
- a.channel() - email
- b.channel() - console
- c.channel() - email
- a.kind() - NotificationSender.kind()
- c.kind() - EmailSender.kind()

Перегрузка выбирается при компиляции по типам ссылки и аргументов. Поэтому для s выбирается send(String), для o и (Object) s — send(Object), а для null — более конкретный send(String).

После выбора сигнатуры переопределённый экземплярный метод выбирается по фактическому классу объекта. Поэтому b.send(s) вызывает унаследованный NotificationSender.send(String): ConsoleSender переопределяет только send(Object).

Статический kind не переопределяется, а скрывается: выбор зависит от типа ссылки. Метод sendWithSubject отсутствует у NotificationSender, поэтому для его вызова через a требуется приведение к EmailSender.
