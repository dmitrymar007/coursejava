package task13;

import java.util.Objects;

public record Tag(String name) {
    public Tag {
        Objects.requireNonNull(name, "name");
    }
}
