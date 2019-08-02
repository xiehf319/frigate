package cn.cici.leetcode;

/**
 * @description: 类介绍：
 * @createDate: 2019/8/1 14:37
 * @author: Heyfan Xie
 */
public class MergeSortTest {

    public static void main(String[] args) {

        MergeSortTest mergeSortTest = new MergeSortTest();

        int[] num1 = new int[] { 1, 2, 3, 0, 0, 0};
        int[] num2 = new int[] { 2, 5, 6};
        mergeSortTest.merge(num1, 3, num2, 3);

        System.out.println(num1);
    }



    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int index1 = m - 1, index2 = n - 1;
        int indexMerge = m + n - 1;
        while (index1 >= 0 || index2 >= 0) {
            if (index1 < 0) {
                nums1[indexMerge--] = nums2[index2--];
            } else if (index2 < 0) {
                nums1[indexMerge--] = nums1[index1--];
            } else if (nums1[index1] > nums2[index2]) {
                nums1[indexMerge--] = nums1[index1--];
            } else {
                nums1[indexMerge--] = nums2[index2--];
            }
        }
    }
}
