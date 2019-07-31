package cn.cici.leetcode;

/**
 * @description: 类介绍：
 * @createDate: 2019/7/31 14:38
 * @author: Heyfan Xie
 */
public class TwoNumberAddition {

    public static void main(String[] args) {

        TwoNumberAddition twoNumberAddition = new TwoNumberAddition();

        ListNode l1 = new ListNode(3);
        l1.next = new ListNode(7);

        ListNode l2 = new ListNode(9);
        l2.next = new ListNode(2);

        ListNode x = twoNumberAddition.addTwoNumbers(l1, l2);
        System.out.println(x);
    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {

        // 最终结果
        ListNode result = null;
        // 当前节点
        ListNode current = null;
        int flag = 0;
        while (l1 != null || l2 != null) {
            int val;
            if (l1 == null) {
                val = (l2.val + flag) % 10;
                flag = l2.val + flag > 9 ? 1 : 0;
                l2 = l2.next;
            } else if (l2 == null) {
                val = (l1.val + flag) % 10;
                flag = l1.val + flag > 9 ? 1 : 0;
                l1 = l1.next;
            } else {
                val = (l1.val + l2.val + flag) % 10;
                flag = l1.val + l2.val + flag > 9 ? 1 : 0;
                l1 = l1.next;
                l2 = l2.next;
            }
            if (result == null) {
                result = new ListNode(val);
                current = result;
            } else {
                current.next = new ListNode(val);
                current = current.next;
            }
        }
        if (flag == 1) {
            current.next = new ListNode(1);
        }
        return result;
    }
}


class ListNode {
    int val;

    ListNode next;

    ListNode(int x) {
        val = x;
    }
}
