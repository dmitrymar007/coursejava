Задание 4. Очередь ожидания

Waitlist сделан на Queue с реализацией ArrayDeque: первым выходит тот, кто встал раньше (FIFO), операции O(1). null не принимается.

Пары методов. Первый метод в паре бросает исключение, второй возвращает специальное значение:
add и offer: вставка. При нехватке места add бросает IllegalStateException, offer возвращает false.
remove и poll: извлечение. На пустой очереди remove бросает NoSuchElementException, poll возвращает null.
element и peek: просмотр без извлечения. На пустой очереди element бросает исключение, peek возвращает null.

Разницу add и offer видно только на ограниченной очереди (в Demo это ArrayBlockingQueue), у ArrayDeque размер не ограничен. Пустая очередь это обычная ситуация, поэтому в Waitlist используются poll и peek, а результат обёрнут в Optional.
