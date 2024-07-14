package moe.qie2035.hz.service;

import com.esotericsoftware.kryo.Kryo;
import moe.qie2035.hz.core.FileInfo;
import moe.qie2035.hz.core.huffman.HuffmanTree;

public class KryoHolder {
    public static final Kryo kryo = new Kryo();

    static {
        kryo.register(FileInfo.class);
        kryo.register(HuffmanTree.class);
        kryo.register(HuffmanTree.Node.Path.class);
        kryo.register(HuffmanTree.Node.Leaf.class);
    }

    private KryoHolder() {
    }
}
