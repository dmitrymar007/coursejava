Задание 12. Generic repository

Repository<ID, T> имеет методы save, findById, findAll и deleteById. InMemoryRepository получает функцию, которая достаёт id из сущности, поэтому сущности не нужен общий интерфейс. Внутри LinkedHashMap: поиск по id за O(1) и предсказуемый порядок в findAll.

Повторный save: заменяет старую запись (upsert) и возвращает прежнюю сущность в Optional. Для нового id возвращается пустой Optional, так видно, была вставка или замена.

null не принимается ни в одном методе (NullPointerException). Отсутствие результата выражено через Optional у findById и через boolean у deleteById.

Внутреннюю коллекцию наружу не отдаём: findAll возвращает List.copyOf, то есть неизменяемую копию. Запись в неё бросает UnsupportedOperationException и на хранилище не влияет.

deleteById несуществующего id возвращает false, это не ошибка.
