TextRenderer и HtmlRenderer оба содержат default-метод render(). Если класс реализует оба интерфейса и не переопределяет render, компилятор выдаёт ошибку:

types TextRenderer and HtmlRenderer are incompatible; class CoursePage inherits unrelated defaults for render() from types TextRenderer and HtmlRenderer

Конфликт решается явным переопределением render в CoursePage, где через TextRenderer.super.render() и HtmlRenderer.super.render() выбирается, что вызвать. Результат: строка от TextRenderer (text), плюс, строка от HtmlRenderer (тег html в угловых скобках). Вызов через ссылки любого из двух интерфейсов даёт тот же результат, потому что выполняется метод CoursePage.

Явный выбор нужен, потому что оба интерфейса равноправны и у компилятора нет причин предпочесть один. Если бы он выбирал сам, например по порядку в implements, то перестановка интерфейсов или новый default в библиотечном интерфейсе молча меняли бы поведение класса. Поэтому решение оставлено автору класса.
