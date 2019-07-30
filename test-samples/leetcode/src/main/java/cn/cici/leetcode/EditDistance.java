package cn.cici.leetcode;

/**
 * @description: 类介绍：
 *
 *  编辑距离算法
 *
 *
 *
 *
 * @createDate: 2019/7/30 10:16
 * @author: Heyfan Xie
 */
public class EditDistance {


    public static void main(String[] args) {

        System.out.println(minDistance("hello", "world"));
    }


    /**
     * 编辑距离算法
     *
     * @param word1
     * @param word2
     * @return
     */
    public static int minDistance(String word1, String word2) {

        int len1 = word1.length();
        int len2 = word2.length();

        // 如果有一个字符串为空串, 最小步数为不为空的长度
        if (len1 * len2 == 0) {
            return len1 + len2;
        }

        int[][] matrix = new int[len1 + 1][len2 + 1];

        for (int i = 0; i < len1 + 1; i++) {
            matrix[i][0] = i;
        }
        for (int j = 0; j < len2 + 1; j++) {
            matrix[0][j] = j;
        }

        for (int i = 1; i < len1 + 1; i++) {
            for (int j = 1; j < len2 + 1; j++) {
                int temp = word1.charAt(i - 1) == word2.charAt(j - 1) ? 0 : 1;
                matrix[i][j] = Math.min(Math.min(matrix[i - 1][j] + 1, matrix[i][j - 1] + 1), matrix[i - 1][j - 1] + temp);
            }
        }
        return matrix[len1][len2];
    }
}
