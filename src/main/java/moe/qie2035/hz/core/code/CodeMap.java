package moe.qie2035.hz.core.code;

import lombok.SneakyThrows;
import moe.qie2035.hz.core.bit.BitOutputStream;
import moe.qie2035.hz.core.huffman.HuffmanTree;

import java.io.ByteArrayOutputStream;

import static moe.qie2035.hz.core.Constants.BYTE_COUNT;

public class CodeMap {

    private final byte[] lastByteLengths = new byte[BYTE_COUNT];
    private final byte[][] codes = new byte[BYTE_COUNT][];

    public CodeMap(final HuffmanTree tree) {
        final BitOutputStream<ByteArrayOutputStream> out = new BitOutputStream<>(new ByteArrayOutputStream(1));
        buildCodeMap(tree.root, out);
    }

    @SneakyThrows
    private void buildCodeMap(final HuffmanTree.Node node, final BitOutputStream<ByteArrayOutputStream> out) {
        if (node instanceof HuffmanTree.Node.Leaf leaf) {
            final int data = Byte.toUnsignedInt(leaf.value);
            lastByteLengths[data] = out.getBitCount();
            out.flush();
            final byte[] bytes = out.getOut().toByteArray();
            codes[data] = bytes;
        } else if (node instanceof HuffmanTree.Node.Path path) {
            try (BitOutputStream<ByteArrayOutputStream> cloned = out.clone()) {
                cloned.write(false);
                buildCodeMap(path.left, cloned);
            }
            try (BitOutputStream<ByteArrayOutputStream> cloned = out.clone()) {
                cloned.write(true);
                buildCodeMap(path.right, cloned);
            }
        }
    }

    public byte getLastByteLength(final int data) {
        return lastByteLengths[data];
    }

    public byte[] getCode(final int data) {
        return codes[data];
    }
}
