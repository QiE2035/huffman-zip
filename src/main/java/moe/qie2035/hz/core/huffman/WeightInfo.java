package moe.qie2035.hz.core.huffman;

import lombok.SneakyThrows;

import java.io.InputStream;

import static moe.qie2035.hz.core.Constants.BYTE_COUNT;

public class WeightInfo {
    public final long[] weights = new long[BYTE_COUNT];

    @SneakyThrows
    public WeightInfo(final InputStream in) {
        int data;
        while ((data = in.read()) >= 0) {
            weights[data]++;
        }
    }
}
