package cn.cici.leetcode;

import lombok.Data;

import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @description: 类介绍：
 * @createDate: 2019/8/7 12:01
 * @author: Heyfan Xie
 */
public class Sort {


    public static void main(String[] args) {

//        int[] arr = {7, 9, 1, 12, 2, 15, 3, 4, 5, 6};
//        insert(arr);
//
//        for (int i : arr) {
//            System.out.println(i);
//        }
//        long begin = System.currentTimeMillis();
//        System.out.println("Before:");
//        buildingHeap(arr, arr.length);
//        System.out.println("After Building: ");
//        sort(arr);
//        System.out.println("After Sorting: ");
//        long end = System.currentTimeMillis();
//        System.out.println("time = " + (end - begin));

        Node node = new Node(0);

        Node node1 = new Node(1);
        Node node2 = new Node(2);
        node.left = node1;
        node.right = node2;
        Node node3 = new Node(3);
        Node node4 = new Node(4);
        Node node5 = new Node(5);
        node1.left = node4;
        node1.right = node3;
        node2.right = node5;
        List<Integer> result = new LinkedList<>();
        levelTrav(result, node);

        System.out.println(result);
    }


    public static void insert(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            for (int j = i - 1; j >= 0; j--) {
                if (arr[j + 1] < arr[j]) {
                    int temp = arr[j + 1];
                    arr[j + 1] = arr[j];
                    arr[j] = temp;
                } else {
                    break;
                }
            }
        }
    }


    public static void fast(int[] arr) {

    }


    private static void sort(int[] arr) {
        int length = arr.length;

        // 从最后一个元素开始对序列进行调整
        for (int i = length - 1; i > 0; --i) {
            int temp = arr[i];
            arr[i] = arr[0];
            arr[0] = temp;

            heapAdjust(arr, 0, i);
        }
    }

    /**
     * 创建一个大堆顶树
     * <p>
     * 已知arr[s...m]除了arr[s]外均满足堆的定义
     * 调整arr[s]，使其成为大顶堆，即将对第s个结点为根的子树筛选。
     *
     * @param arr    待调整的堆数组
     * @param s      待调整的元素的位置
     * @param length 数组的长度
     */
    private static void heapAdjust(int[] arr, int s, int length) {

        // 待调整的元素
        int temp = arr[s];

        // 左孩子的下标位置  child + 1 是右孩子的位置
        int child = 2 * s + 1;

        while (child < length) {

            // 如果右孩子存在，且左孩子小于右孩子
            // 则将下标定位到右孩子，从而找出最大的值
            if (child + 1 < length && arr[child] < arr[child + 1]) {
                ++child;
            }

            // 如果需要调整的结点值小于孩子的值
            if (arr[s] < arr[child]) {

                // 将待筛选的节点定位到孩子结点上，然后交换值
                arr[s] = arr[child];
                s = child;
                child = 2 * s + 1;
            } else {

                // 如果已经是比孩子大 就没必要往下调整了
                break;
            }

            // 这个时候就完成了 2个值得对调
            arr[s] = temp;
        }
    }

    /**
     * 创建一个大堆顶树
     * <p>
     * 初始堆进行调整
     * 将H[0..length-1]建成堆
     * 调整完之后第一个元素是序列的最小的元素
     */
    private static void buildingHeap(int[] arr, int length) {

        // 最后一个有孩子的节点的位置 i = (length - 1) / 2
        // 对每个节点进行筛选 创建原始堆
        for (int i = (length - 1) / 2; i >= 0; --i) {
            heapAdjust(arr, i, length);
        }
    }


    public static void levelTrav(List<Integer> result, Node root) {

        if (root == null) {
            return;
        }
        Queue<Node> q = new ArrayDeque<>();
        q.add(root);
        Node cur;
        while (!q.isEmpty()) {
            cur = q.peek();
            result.add(cur.value);
            if (cur.left != null) {
                q.add(cur.left);
            }
            if (cur.right != null) {
                q.add(cur.right);
            }
            q.poll();
        }
    }

    @Data
    public static class Node {

        public Node(int value) {
            this.value = value;
        }

        private int value;

        private Node left;

        private Node right;
    }
}
