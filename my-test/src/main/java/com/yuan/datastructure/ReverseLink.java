package com.yuan.datastructure;

/**
 * 如何k个一组 反转链表
 *
 * @Author yuanjt
 * @Date 2020-12-15 10:35
 **/
public class ReverseLink {

    public static void main(String[] args) {
        Node n1 = new Node(1);
        Node n2 = new Node(2);
        Node n3 = new Node(3);
        Node n4 = new Node(4);
        Node n5 = new Node(5);
        Node n6 = new Node(6);
        Node n7 = new Node(7);
        Node n8 = new Node(8);
        n1.setRight(n2);
        n2.setRight(n3);
        n3.setRight(n4);
        n4.setRight(n5);
        n5.setRight(n6);
        n6.setRight(n7);
        n7.setRight(n8);

        //Node rev2 = reverse(n1);
        //Node rev1 = reverse(n1, n5);
        Node rev1 = reverseKGroup(n1, 3);
        while (rev1 != null){
            System.out.println(rev1.getValue());
            rev1 = rev1.getRight();
        }

    }


    /**
     * 单链表反转
     *
     * @param a
     */
    static Node reverse(Node a) {
        if (a == null) {
            return null;
        }
        Node head = null;
        Node cur = a;
        Node nxt = a;
        while (cur != null) {
            nxt = cur.getRight();
            //当前节点反转，顺序循环处理
            cur.setRight(head);
            head = cur;
            cur = nxt;
        }
        //返回头节点
        return head;
    }


    /**
     * 反转[a, b)链表的元素
     *
     * @param a
     * @param b
     * @return
     */
    static Node reverse(Node a, Node b) {
        if (a == null || b == null) {
            return null;
        }
        Node pre = null, cur = a, nxt = a;
        while (cur != null && cur != b) {
            nxt = cur.getRight();
            cur.setRight(pre);
            pre = cur;
            cur = nxt;
        }

        return pre;
    }

    /**
     * 反转 k个一组的链表元素
     *
     * @param head
     * @param k
     * @return
     */
    static Node reverseKGroup(Node head, int k) {
        if (head == null) {
            return null;
        }
        Node a = head;
        Node b = head;
        //不足k个 返回，base case
        for (int i = 0; i < k; i++) {
            if (b == null) {
                return head;
            }
            b = b.getRight();
        }

        //反转前k个元素
        Node newHead = reverse(a, b);
        //递归剩余的链表 并连接起来
        a.setRight(reverseKGroup(b, k));


        return newHead;
    }


}
