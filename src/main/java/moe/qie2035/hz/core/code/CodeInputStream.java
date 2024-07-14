package moe.qie2035.hz.core.code;

import moe.qie2035.hz.core.bit.BitInputStream;
import moe.qie2035.hz.core.huffman.HuffmanTree;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

public class CodeInputStream extends InputStream {
    private final BitInputStream in;
    private final HuffmanTree tree;

    public CodeInputStream(final InputStream in, final HuffmanTree tree) {
        this.in = new BitInputStream(in);
        this.tree = tree;
    }

    @Override
    public int read() {
        HuffmanTree.Node node = tree.root;
        while (node instanceof HuffmanTree.Node.Path path) {
            try {
                node = in.read() ? path.right : path.left;
            } catch (EOFException e) {
                return -1;
            }
        }

        if (node instanceof HuffmanTree.Node.Leaf leaf) {
            return Byte.toUnsignedInt(leaf.value);
        }

        return -1;
    }

    @Override
    public void close() throws IOException {
        super.close();
        in.close();
    }
}
