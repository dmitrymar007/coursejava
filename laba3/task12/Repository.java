package task12;

import java.util.List;
import java.util.Optional;

/**
 * Хранилище сущностей T с идентификатором ID.
 * Контракт: null не принимается нигде; сущность неизменяема, а идентификатор не меняется после save.
 */
public interface Repository<ID, T> {

    /**
     * Сохраняет сущность. Семантика upsert: если id уже есть - запись заменяется, иначе добавляется.
     *
     * @return сущность, которая раньше хранилась под этим id (пусто, если id был новым)
     */
    Optional<T> save(T entity);

    Optional<T> findById(ID id);

    /** Снимок всех сущностей в порядке первого сохранения. Изменение результата хранилище не затрагивает. */
    List<T> findAll();

    /** @return true, если сущность была и удалена; false, если такого id нет (не ошибка) */
    boolean deleteById(ID id);
}
