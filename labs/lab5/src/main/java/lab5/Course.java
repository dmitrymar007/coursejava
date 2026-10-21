package lab5;

public record Course(int id, String title) {
    public Course {
        if (id <= 0 || title == null || title.isBlank() || title.indexOf(';') >= 0
                || title.indexOf('\r') >= 0 || title.indexOf('\n') >= 0)
            throw new IllegalArgumentException("Ожидаются положительный id и название без ; и переносов строк");
    }

    public static Course parse(String line) {
        String[] fields = line.split(";", -1);
        if (fields.length != 2) throw new IllegalArgumentException("Ожидается id;название");
        return new Course(Integer.parseInt(fields[0]), fields[1]);
    }

    public String toLine() {
        return id + ";" + title;
    }
}
