package task12;

public class Demo {
    record Course(String id, String title) {
    }

    public static void main(String[] args) {
        Repository<String, Course> repo = new InMemoryRepository<>(Course::id);
        System.out.println("save новый:   " + repo.save(new Course("J1", "Java")));
        System.out.println("save повтор:  " + repo.save(new Course("J1", "Java 2")) + "  (вернул заменённую запись)");
        repo.save(new Course("S1", "SQL"));
        System.out.println("findById:     " + repo.findById("J1"));
        System.out.println("findAll:      " + repo.findAll());
        try {
            repo.findAll().add(new Course("X", "X"));
        } catch (UnsupportedOperationException e) {
            System.out.println("findAll().add -> UnsupportedOperationException (внутренняя коллекция защищена)");
        }
        System.out.println("delete J1:    " + repo.deleteById("J1") + ", повторно: " + repo.deleteById("J1"));
    }
}
