package task12;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

public class InMemoryRepository<ID, T> implements Repository<ID, T> {
    // LinkedHashMap: поиск по id O(1) и предсказуемый порядок findAll (порядок первого save)
    private final Map<ID, T> storage = new LinkedHashMap<>();
    private final Function<? super T, ? extends ID> idExtractor;

    public InMemoryRepository(Function<? super T, ? extends ID> idExtractor) {
        this.idExtractor = Objects.requireNonNull(idExtractor, "idExtractor");
    }

    @Override
    public Optional<T> save(T entity) {
        Objects.requireNonNull(entity, "entity");
        ID id = Objects.requireNonNull(idExtractor.apply(entity), "id сущности");
        // put на существующем ключе сохраняет исходную позицию в порядке обхода
        return Optional.ofNullable(storage.put(id, entity));
    }

    @Override
    public Optional<T> findById(ID id) {
        Objects.requireNonNull(id, "id");
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<T> findAll() {
        return List.copyOf(storage.values());
    }

    @Override
    public boolean deleteById(ID id) {
        Objects.requireNonNull(id, "id");
        return storage.remove(id) != null;
    }
}
