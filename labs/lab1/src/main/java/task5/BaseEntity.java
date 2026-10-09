package task5;

import java.util.Objects;

public abstract class BaseEntity implements Identifiable {
    private final long id;

    protected BaseEntity(long id) {
        this.id = id;
    }

    @Override
    public final long id() {
        return id;
    }

    @Override
    public final boolean equals(Object o) {
        return o != null && getClass() == o.getClass() && id == ((BaseEntity) o).id;
    }

    @Override
    public final int hashCode() {
        return Objects.hash(getClass(), id);
    }
}
