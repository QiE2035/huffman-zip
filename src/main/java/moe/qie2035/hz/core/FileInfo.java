package moe.qie2035.hz.core;

import moe.qie2035.hz.core.huffman.HuffmanTree;

public record FileInfo(HuffmanTree tree, long length) {
}
