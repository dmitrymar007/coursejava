Задание 9. PECS на практике

Метод копирования: copy(List<? extends T> src, List<? super T> dst). PECS: Producer Extends, Consumer Super. Источник только отдаёт элементы, поэтому extends. Приёмник только принимает, поэтому super. Так List<OnlineCourse> можно скопировать и в List<Course>, и в List<Object>.

Почему нельзя добавить объект в List<? extends Course>: такая переменная может ссылаться на список любого подтипа Course, и компилятор не знает какого. Если бы add разрешили, в список OnlineCourse попал бы обычный Course. Разрешён только null. Читать можно как Course.

Что можно читать из List<? super Course>: это список Course или любого его супертипа, поэтому гарантированно только Object. Зато записывать можно Course и его подтипы.
