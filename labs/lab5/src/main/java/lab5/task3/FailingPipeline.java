package lab5.task3;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

public class FailingPipeline {
    public static void copyAndFail(Reader reader, Writer writer) throws IOException {
        try (reader; writer) {
            int ch = reader.read();
            if (ch != -1) writer.write(ch);
            throw new IOException("Сбой в середине обработки");
        }
    }

    public static void main(String[] args) {
        var reader = new StringReader("Каталог") {
            @Override public void close() {
                System.out.println("close reader");
                super.close();
            }
        };
        var writer = new StringWriter() {
            @Override public void close() {
                System.out.println("close writer");
            }
        };
        try {
            copyAndFail(reader, writer);
        } catch (IOException e) {
            System.out.println("Ожидаемая ошибка: " + e.getMessage());
        }
    }
}
