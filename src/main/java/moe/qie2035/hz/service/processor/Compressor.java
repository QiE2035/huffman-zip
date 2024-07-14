package moe.qie2035.hz.service.processor;

import com.esotericsoftware.kryo.io.Output;
import moe.qie2035.hz.core.FileInfo;
import moe.qie2035.hz.core.code.CodeMap;
import moe.qie2035.hz.core.code.CodeOutputStream;
import moe.qie2035.hz.core.huffman.HuffmanTree;
import moe.qie2035.hz.core.huffman.WeightInfo;
import moe.qie2035.hz.service.callback.CompressProgress;
import moe.qie2035.hz.service.callback.ICallback;

import java.io.*;

import static moe.qie2035.hz.service.KryoHolder.kryo;
import static moe.qie2035.hz.service.callback.CompressProgress.*;

public class Compressor extends Processor<CompressProgress> {
    private final String in;
    private final String out;

    public Compressor(final String in, final String out, final ICallback<CompressProgress> callback) {
        super(callback);
        this.in = in;
        this.out = out;
    }

    public Compressor(final String in, final String out) {
        this(in, out, null);
    }

    @Override
    public void run() {
        try {
            onProgress(BUILD_WEIGHT);
            final BufferedInputStream weightIn = new BufferedInputStream(new FileInputStream(in));
            final WeightInfo weightInfo = new WeightInfo(weightIn);
            weightIn.close();

            onProgress(BUILD_TREE);
            final HuffmanTree tree = new HuffmanTree(weightInfo.weights);

            onProgress(BUILD_CODE_MAP);
            final CodeMap codeMap = new CodeMap(tree);

            onProgress(WRITE_FILE_INFO);
            final File file = new File(in);
            final BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(this.out));
            final Output output = new Output(out);
            kryo.writeObject(output, new FileInfo(tree, file.length()));

            onProgress(COMPRESS);
            try (CodeOutputStream codeOut = new CodeOutputStream(output, codeMap);
                 BufferedInputStream compressIn = new BufferedInputStream(new FileInputStream(file))
            ) {
                codeOut.write(compressIn);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            onFinished();
        }
    }
}
