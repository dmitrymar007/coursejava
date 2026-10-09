package task13;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/** Обратный индекс: тег -> курсы с этим тегом. */
public class TagIndex {
    private final Map<Tag, Set<CourseId>> index = new HashMap<>();
    // Теги, под которыми курс записан в индексе: по ним remove не зависит от переданного объекта Course
    private final Map<CourseId, Set<Tag>> tagsByCourse = new HashMap<>();

    /** Добавляет курс. Если курс с таким id уже есть, он заменяется целиком (старые теги снимаются). */
    public void add(Course course) {
        Objects.requireNonNull(course, "course");
        remove(course.id());
        for (Tag tag : course.tags()) {
            index.computeIfAbsent(tag, t -> new HashSet<>()).add(course.id());
        }
        tagsByCourse.put(course.id(), course.tags());
    }

    /** @return true, если курс был в индексе; false, если такого id нет (не ошибка) */
    public boolean remove(CourseId id) {
        Objects.requireNonNull(id, "id");
        Set<Tag> tags = tagsByCourse.remove(id);
        if (tags == null) {
            return false;
        }
        for (Tag tag : tags) {
            Set<CourseId> ids = index.get(tag);
            ids.remove(id);
            if (ids.isEmpty()) {
                index.remove(tag); // пустые множества в индексе не оставляем
            }
        }
        return true;
    }

    /**
     * Курсы, у которых есть все перечисленные теги (пересечение множеств). Для пустого набора тегов
     * и для неизвестного тега результат пуст. Результат всегда неизменяемый.
     */
    public Set<CourseId> findByAll(Set<Tag> tags) {
        Objects.requireNonNull(tags, "tags");
        if (tags.isEmpty()) {
            return Set.of();
        }
        Set<CourseId> result = null;
        for (Tag tag : tags) {
            Set<CourseId> ids = index.get(tag);
            if (ids == null) {
                return Set.of();
            }
            if (result == null) {
                result = new HashSet<>(ids); // копия: retainAll не должен портить множества внутри индекса
            } else {
                result.retainAll(ids);
                if (result.isEmpty()) {
                    return Set.of();
                }
            }
        }
        return Collections.unmodifiableSet(result);
    }

    /** Теги, у которых есть хотя бы один курс. Неизменяемая копия, порядок обхода стабилен. */
    public Set<Tag> tags() {
        return Collections.unmodifiableSet(new LinkedHashSet<>(index.keySet()));
    }
}
