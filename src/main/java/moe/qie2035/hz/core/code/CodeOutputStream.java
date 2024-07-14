package moe.qie2035.hz.core.code;

import lombok.SneakyThrows;
import moe.qie2035.hz.core.bit.BitOutputStream;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class CodeOutputStream extends OutputStream {
    private final CodeMap map;
    private final BitOutputStream<?> out;

    public CodeOutputStream(final OutputStream out, final CodeMap map) {
        this.map = map;
        this.out = new BitOutputStream<>(out);
    }

    @Override
    public void write(final int b) {
        out.write(map.getCode(b), map.getLastByteLength(b));
    }

    @SneakyThrows
    public void write(final InputStream in) {
        int data;
        while ((data = in.read()) >= 0) {
            write(data);
        }
        out.flush();
    }

    @Override
    public void flush() throws IOException {
        super.flush();
        out.flush();
    }

    @Override
    public void close() throws IOException {
        super.close();
        out.close();
    }
}
