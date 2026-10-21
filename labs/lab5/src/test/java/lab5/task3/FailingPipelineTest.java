package lab5.task3;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FailingPipelineTest {
    @Test
    void closesBothInReverseOrderAndPreservesPrimaryException() {
        var events = new ArrayList<String>();
        var source = new StringReader("Каталог");
        var target = new StringWriter();
        var reader = new FilterReader(source) {
            @Override public void close() throws IOException {
                events.add("reader");
                super.close();
                throw new IOException("reader close");
            }
        };
        var writer = new FilterWriter(target) {
            @Override public void close() throws IOException {
                events.add("writer");
                super.close();
                throw new IOException("writer close");
            }
        };
        var error = assertThrows(IOException.class, () -> FailingPipeline.copyAndFail(reader, writer));
        assertEquals("Сбой в середине обработки", error.getMessage());
        assertEquals("К", target.toString());
        assertEquals(List.of("writer", "reader"), events);
        assertEquals(List.of("writer close", "reader close"),
                java.util.Arrays.stream(error.getSuppressed()).map(Throwable::getMessage).toList());
        assertThrows(IOException.class, source::read);
    }
}
