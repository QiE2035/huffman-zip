package moe.qie2035.hz.service.callback;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum CompressProgress {
    BUILD_WEIGHT("建立权重表"),
    BUILD_TREE("建立哈夫曼树"),
    BUILD_CODE_MAP("建立码表"),

    WRITE_FILE_INFO("写入文件信息"),
    COMPRESS("压缩");

    public final String desc;
}
