package moe.qie2035.hz.core.huffman;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.PriorityQueue;

@EqualsAndHashCode
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
public class HuffmanTree {
    public final Node root;

    public HuffmanTree(final long[] weights) {
        final PriorityQueue<Node> priorityQueue = new PriorityQueue<>();

        // 将每个字节及其权重转换成叶子节点，并加入优先队列
        for (int data = 0; data < weights.length; data++) {
            final long weight = weights[data];
            if (weight <= 0) continue;
            priorityQueue.add(new Node.Leaf(weight, (byte) data));
        }

        // 不断合并最小权重的两个节点，直到队列中只剩下一个节点
        while (priorityQueue.size() > 1) {
            final Node left = priorityQueue.poll();
            final Node right = priorityQueue.poll();
            priorityQueue.add(left.combine(right));
        }

        // 剩下的最后一个节点就是哈夫曼树的根节点
        root = priorityQueue.poll();
    }

    @NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
    public static class Node implements Comparable<Node> {
        public final transient long weight;

        private Node(final long weight) {
            this.weight = weight;
        }

        @Override
        public int compareTo(final Node other) {
            return Long.compare(weight, other.weight);
        }

        public Node combine(final Node other) {
            return new Path(weight + other.weight, this, other);
        }

        @EqualsAndHashCode(callSuper = false)
        @NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
        public static final class Leaf extends Node {
            public final byte value;

            private Leaf(final long weight, final byte value) {
                super(weight);
                this.value = value;
            }
        }

        @EqualsAndHashCode(callSuper = false)
        @NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
        public static final class Path extends Node {
            public final Node left;
            public final Node right;

            private Path(final long weight, final Node left, final Node right) {
                super(weight);
                this.left = left;
                this.right = right;
            }
        }
    }
}
