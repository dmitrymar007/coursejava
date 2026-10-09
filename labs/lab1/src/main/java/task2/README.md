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
