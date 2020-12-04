package com.yuan.datastructure;

/**
 * 如何去除有序数组的重复元素，并返回不重复元素的个数
 *
 * <p>
 * 快慢双指针方式
 * </p>
 *
 * @Author yuanjt
 * @Date 2020-12-04 09:56
 **/
public class DuplicateInArray {

    public static void main(String[] args) {
        int[] nums = {1, 2, 2, 4, 4, 5, 5, 5, 6, 8,8,9};
        System.out.println(removeDuplicates(nums));

        ListNode n12 = new ListNode(9, null);
        ListNode n11 = new ListNode(8, n12);
        ListNode n10 = new ListNode(8, n11);
        ListNode n9 = new ListNode(6, n10);
        ListNode n8 = new ListNode(5, n9);
        ListNode n7 = new ListNode(5, n8);
        ListNode n6 = new ListNode(5, n7);
        ListNode n5 = new ListNode(4, n6);
        ListNode n4 = new ListNode(4, n5);
        ListNode n3 = new ListNode(2, n4);
        ListNode n2 = new ListNode(2, n3);
        ListNode n1 = new ListNode(1, n2);
        System.out.println(removeDumplicates2(n1));
    }

    /**
     * 重复数组
     *
     * @param nums
     * @return
     */
    static int removeDuplicates(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        if (nums.length == 1) {
            return 1;
        }
        int len = nums.length;
        int slow = 0;
        int fast = 1;

        while (fast < len) {
            if (nums[slow] != nums[fast]) {
                slow++;
                nums[slow] = nums[fast];
            }
            fast++;
        }

        return slow + 1;
    }

    /**
     * 重复 单向链表
     *
     * @param node
     * @return
     */
    static int removeDumplicates2(ListNode node) {
        if (node == null) {
            return 0;
        }
        if (node.next == null) {
            return 1;
        }

        int count = 1;
        ListNode head = node;
        ListNode fast = node.next;
        while (fast != null) {
            if (head.val != fast.val) {
                head.next = fast.next;
                head = fast;
                count++;
            }
            fast = fast.next;
        }

        head.next = null;

        return count;
    }


    static class ListNode {
        int val;
        ListNode next;

        public ListNode(int val, ListNode node) {
            this.val = val;
            this.next = node;
        }
    }
}
