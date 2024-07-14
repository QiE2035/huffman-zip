package moe.qie2035.hz.service.processor;

import com.esotericsoftware.kryo.io.Input;
import lombok.SneakyThrows;
import moe.qie2035.hz.core.FileInfo;
import moe.qie2035.hz.core.code.CodeInputStream;
import moe.qie2035.hz.service.callback.DecompressProgress;
import moe.qie2035.hz.service.callback.ICallback;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import static moe.qie2035.hz.service.KryoHolder.kryo;
import static moe.qie2035.hz.service.callback.DecompressProgress.DECOMPRESS;
import static moe.qie2035.hz.service.callback.DecompressProgress.READ_TREE;

public class Decompressor extends Processor<DecompressProgress> {
    private final String in;
    private final String out;

    public Decompressor(final String in, final String out, final ICallback<DecompressProgress> callback) {
        super(callback);
        this.in = in;
        this.out = out;
    }

    public Decompressor(final String in, final String out) {
        this(in, out, null);
    }

    @Override
    @SneakyThrows
    public void run() {
        onProgress(READ_TREE);
        final BufferedInputStream in = new BufferedInputStream(new FileInputStream(this.in));
        final Input input = new Input(in);
        final FileInfo info = kryo.readObject(input, FileInfo.class);

        onProgress(DECOMPRESS);
        try (CodeInputStream codeIn = new CodeInputStream(input, info.tree());
             BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(this.out))
        ) {
            for (int read = 0; read < info.length(); read++) {
                out.write(codeIn.read());
            }
        }

        onFinished();
    }
}
