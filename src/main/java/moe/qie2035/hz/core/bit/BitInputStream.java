package moe.qie2035.hz.core.bit;

import lombok.SneakyThrows;

import java.io.Closeable;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

import static moe.qie2035.hz.core.Constants.MAX_BIT_COUNT;

public class BitInputStream implements Closeable {
    private final InputStream in;

    private byte currentByte;
    private byte currentBitCount = MAX_BIT_COUNT;

    public BitInputStream(final InputStream in) {
        this.in = in;
    }

    @SneakyThrows
    public boolean read() throws EOFException {
        if (currentBitCount >= MAX_BIT_COUNT) {
            final int read = in.read();
            if (read < 0) throw new EOFException();
            currentByte = (byte) read;
            currentBitCount = 0;
        }
        boolean bit = (currentByte & (1 << (7 - currentBitCount))) != 0;
        currentBitCount++;
        return bit;
    }

    @Override
    public void close() throws IOException {
        in.close();
    }
}
