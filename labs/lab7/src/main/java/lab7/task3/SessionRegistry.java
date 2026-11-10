package lab7.task3;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class SessionRegistry {
    public record Session(String userId) {}
    private final ConcurrentHashMap<String, Session> sessions = new ConcurrentHashMap<>();

    public Session getOrCreate(String userId) {
        Objects.requireNonNull(userId);
        return sessions.computeIfAbsent(userId, Session::new);
    }

    public boolean remove(String userId, Session expected) {
        return sessions.remove(userId, expected);
    }

    public Map<String, Session> snapshot() { return Map.copyOf(sessions); }

    public static void main(String[] args) {
        var registry = new SessionRegistry();
        System.out.println(registry.getOrCreate("student-1") == registry.getOrCreate("student-1"));
        System.out.println(registry.snapshot());
    }
}
