package task15;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

/** Тестовый Reader: считает close и может отказать при чтении и при закрытии. */
class TestReader extends Reader {
    private final StringReader delegate;
    private final boolean failOnRead;
    private final boolean failOnClose;
    private int closeCalls;

    private TestReader(String content, boolean failOnRead, boolean failOnClose) {
        this.delegate = new StringReader(content);
        this.failOnRead = failOnRead;
        this.failOnClose = failOnClose;
    }

    static TestReader healthy(String content) {
        return new TestReader(content, false, false);
    }

    static TestReader failingOnRead(String content) {
        return new TestReader(content, true, false);
    }

    static TestReader failingOnClose(String content) {
        return new TestReader(content, false, true);
    }

    int closeCalls() {
        return closeCalls;
    }

    @Override
    public int read(char[] buffer, int offset, int length) throws IOException {
        if (failOnRead) {
            throw new IOException("read failed");
        }
        return delegate.read(buffer, offset, length);
    }

    @Override
    public void close() throws IOException {
        closeCalls++;
        delegate.close();
        if (failOnClose) {
            throw new IOException("close failed");
        }
    }
}
