package com.yuan.datastructure;

/**
 * 如何判定回文链表
 * 1.反转链表，进行判定。时间复杂度O(n),空间复杂度O(n)
 * 2.快慢双指针，反转后半部分链表，进行比较
 *
 * @Author yuanjt
 * @Date 2020-12-31 09:52
 **/
public class PlalindromeLink {

    public static void main(String[] args) {
        Node n1 = new Node(1);
        Node n2 = new Node(2);
        Node n3 = new Node(3);
        Node n4 = new Node(4);
        Node n5 = new Node(4);
        Node n6 = new Node(3);
        Node n7 = new Node(2);
        Node n8 = new Node(1);
        n1.setRight(n2);
        n2.setRight(n3);
        n3.setRight(n4);
        n4.setRight(n5);
        n5.setRight(n6);
        n6.setRight(n7);
        n7.setRight(n8);

        System.out.println(isPlaindrome(n1));
    }

    /**
     * 反转链表 跟 原链表 比较
     *
     * @param node
     * @return
     */
    static boolean isPlaindrome(Node node) {
        if (node == null || node.getRight() == null) {
            return true;
        }

        Node n2 = node;
        Node head = new Node(n2.getValue());
        Node nextHead = head;
        //copy 一份新链表
        while (n2.getRight() != null) {
            Node next = new Node(n2.getRight().getValue());
            nextHead.setRight(next);
            nextHead = next;
            System.out.println(n2.getValue());
            n2 = n2.getRight();
        }
        //反转链表
        Node n = traverse(node);
        //原链表跟反转链表 比较
        while (head != null) {
            if (!head.getValue().equals(n.getValue())) {
                return false;
            }
            head = node.getRight();
            n = n.getRight();
        }
        return true;
    }


    /**
     * 反转链表
     *
     * @param node
     * @return
     */
    static Node traverse(Node node) {
        Node head = null;
        Node cur = node;
        Node nxt = node;
        while (nxt != null) {
            nxt = cur.getRight();
            cur.setRight(head);
            head = cur;
            cur = nxt;
        }

        return head;
    }

}
