package task14;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

/** Тестовый Reader: считает вызовы close и может отказать при закрытии. */
class TestReader extends Reader {
    private final StringReader delegate;
    private final boolean failOnClose;
    private int closeCalls;

    TestReader(String content, boolean failOnClose) {
        this.delegate = new StringReader(content);
        this.failOnClose = failOnClose;
    }

    int closeCalls() {
        return closeCalls;
    }

    @Override
    public int read(char[] buffer, int offset, int length) throws IOException {
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
