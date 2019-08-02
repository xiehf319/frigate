package cn.cici.leetcode;

/**
 * @description: 类介绍：
 *
 * @createDate: 2019/8/2 13:15
 * @author: Heyfan Xie
 */
public class Test1 {


    public static void main(String[] args) {

        // 加密
        System.out.println("Cv 79 agctu qh cig, vjg yknn tgkipu; cv 69, vjg ykv; cpf cv 59, vjg lwfiogpv.");
        System.out.println(encrypt("At 20 years of age, the will reigns; at 30, the wit; and at 40, the judgment."));

        // 解密
        System.out.println("At 20 years of age, the will reigns; at 30, the wit; and at 40, the judgment.");
        System.out.println(decrypt("Cv 79 agctu qh cig, vjg yknn tgkipu; cv 69, vjg ykv; cpf cv 59, vjg lwfiogpv."));

        // 随机均匀抽样
        int[] arr = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11,1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11,1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
        for (int i : random(arr, 11)) {
            System.out.print(i + ",");
        }
        System.out.println();
        for (int i : random(arr, 5)) {
            System.out.print(i + ",");
        }
        System.out.println();
        for (int i : random(arr, 1)) {
            System.out.print(i + ",");
        }

        // 错误
//        for (int i : random(arr, 0)) {
//            System.out.print(i + ",");
//        }
//        for (int i : random(arr, arr.length + 1)) {
//            System.out.print(i + ",");
//        }
    }

    /**
     * 自定义解密算法
     *
     * @param raw 原始字符串
     * @return 加密后字符串
     */
    public static String decrypt(String raw) {

        // 为空不需要处理
        if (raw == null || raw.length() == 0) {
            return raw;
        }
        // 返回结果
        StringBuilder sb = new StringBuilder(raw.length());
        for (int i = 0; i < raw.length(); i++) {
            char c = raw.charAt(i);
            if (c <= 57 && c >= 48) {
                int target = '9' - c;
                // acsii '0' = 48
                sb.append((char) (target + 48));
            } else if (c >= 'A' && c <= 'z') {
                if (c != 65 && c != 66 && c != 97 && c != 98) {
                    sb.append((char) (c - 2));
                } else {
                    sb.append((char) (c + 24));
                }
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 自定义加密算法
     *
     * @param raw
     * @return
     */
    public static String encrypt(String raw) {

        // 为空不需要处理
        if (raw == null || raw.length() == 0) {
            return raw;
        }
        // 返回结果
        StringBuilder sb = new StringBuilder(raw.length());
        for (int i = 0; i < raw.length(); i++) {
            char c = raw.charAt(i);
            if (c <= 57 && c >= 48) {
                int target = '9' - c;
                // acsii '0' = 48
                sb.append((char) (target + 48));
            } else if (c >= 'A' && c <= 'z') {
                // Y = 89 Z = 90 A = 65 B = 66
                // y = 121 z = 122 a = 97 b = 98
                if (c != 89 && c != 90 && c != 121 && c != 122) {
                    sb.append((char) (c + 2));
                } else {
                    sb.append((char) (c - 24));
                }

            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }


    /**
     * 随机均匀抽样方法
     * 1、尽量均匀抽样
     * 2、抽样数量小于总数
     *
     * @param arr 待抽样的数组
     * @param n   抽样数量
     * @return 返回抽样样本数组
     */
    public static int[] random(int[] arr, int n) {
        int total = arr.length;
        if (total < n || n <= 0) {
            throw new RuntimeException("抽样数量过大");
        }

        // 保证抽一个是在中间位置 (如果需要的话)
        if (n == 1) {
            return new int[]{arr[total / 2]};
        }

        // 构造抽样结果数组
        int[] result = new int[n];
        int mid = total / 2;
        int midResult = n / 2;

        binaryPick(arr, 0, mid, result, 0, midResult);
        binaryPick(arr, mid + 1, total - 1, result, midResult + 1, n - 1);

        return result;
    }

    public static void binaryPick(int[] arr, int start, int end, int[] result, int resultStart, int resultEnd) {
        if (resultStart > resultEnd) {
            return;
        }
        if (resultStart == resultEnd) {
            result[resultStart] = arr[(start + end) / 2];
        } else {
            int mid = (start + end) / 2;
            int midResult = (resultStart + resultEnd) / 2;
            binaryPick(arr, start, mid, result, resultStart, midResult);
            binaryPick(arr, mid + 1, end, result, midResult + 1, resultEnd);
        }
    }
}
