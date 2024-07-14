package moe.qie2035.hz.service.callback;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum DecompressProgress {
    READ_TREE("读取哈夫曼树"),
    DECOMPRESS("解压");

    public final String desc;
}
