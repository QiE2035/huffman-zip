package moe.qie2035.hz.core.bit;

import lombok.Getter;
import lombok.SneakyThrows;

import java.io.*;

import static moe.qie2035.hz.core.Constants.MAX_BIT_COUNT;

public class BitOutputStream<T extends OutputStream> implements Closeable, Flushable {
    @Getter
    private final T out;
    private byte currentByte;

    @Getter
    private byte bitCount;

    public BitOutputStream(final T out) {
        this(out, (byte) 0, (byte) 0);
    }

    private BitOutputStream(final T out, final byte currentByte, final byte bitCount) {
        this.out = out;
        this.currentByte = currentByte;
        this.bitCount = bitCount;
    }

    @SneakyThrows
    public void write(final boolean bit) {
        currentByte = (byte) ((currentByte << 1) | (bit ? 1 : 0));
        bitCount++;
        if (bitCount >= MAX_BIT_COUNT) {
            out.write(currentByte);
            currentByte = 0;
            bitCount = 0;
        }
    }

    public void write(final byte[] data, byte lastByteLength) {
        final int index = data.length - 1;

        for (int byteIdx = 0; byteIdx < index; byteIdx++) {
            for (int bitIdx = MAX_BIT_COUNT - 1; bitIdx >= 0; bitIdx--) {
                write((data[byteIdx] & (1 << bitIdx)) != 0);
            }
        }

        final byte lastByte = data[index];
        if (index == 0 && lastByteLength == 0) lastByteLength = MAX_BIT_COUNT;
        for (int i = 1; i <= lastByteLength; i++) {
            write((lastByte & (1 << MAX_BIT_COUNT - i)) != 0);
        }
    }

    @Override
    public void close() throws IOException {
        flush();
        out.close();
    }

    @Override
    public void flush() throws IOException {
        if (bitCount != 0) {
            currentByte <<= MAX_BIT_COUNT - bitCount;
            out.write(currentByte);
        }
        out.flush();
    }

    @Override
    public BitOutputStream<ByteArrayOutputStream> clone() throws CloneNotSupportedException {
        try {
            if (this.out instanceof ByteArrayOutputStream byteArrayOutputStream) {
                ByteArrayOutputStream out = new ByteArrayOutputStream(1);
                out.write(byteArrayOutputStream.toByteArray());

                return new BitOutputStream<>(out, currentByte, bitCount);
            } else {
                throw new CloneNotSupportedException("Unsupported OutputStream type for cloning");
            }
        } catch (IOException e) {
            throw new CloneNotSupportedException("Error while cloning: " + e.getMessage());
        }
    }
}
