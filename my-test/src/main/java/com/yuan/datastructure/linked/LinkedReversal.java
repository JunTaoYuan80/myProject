package com.yuan.datastructure.linked;

import com.alibaba.fastjson.JSON;
import com.yuan.datastructure.Node;

import java.util.LinkedList;

/**
 * 链表反转
 * <p>
 * https://www.cnblogs.com/csbdong/p/5674990.html
 * https://blog.csdn.net/acquaintanceship/article/details/73011169
 *
 * @author yuanjuntao
 * @date 2018/11/18 9:58
 */
public class LinkedReversal {


    public static void main(String[] args) {
        LinkedList linkedList = new LinkedList();
        linkedList.add(1);
        linkedList.add(2);
        linkedList.add(3);
        linkedList.add(4);
        linkedList.add(5);
        linkedList.add(6);
        reversal(linkedList);

        Node n1 = new Node(1);
        Node n2 = new Node(2);
        Node n3 = new Node(3);
        Node n4 = new Node(4);
        Node n5 = new Node(5);
        Node n6 = new Node(6);
        n1.setRight(n2);
        n2.setRight(n3);
        n3.setRight(n4);
        n4.setRight(n5);
        n5.setRight(n6);
        System.out.println(JSON.toJSON(n1));
        System.out.println(JSON.toJSON(reversalCycle(n1)));

    }

    /**
     * LinkedList链表反转，空间复杂度O(n)，时间复杂度O(n)
     *
     * @param linkedList
     */
    public static void reversal(LinkedList linkedList) {
        if (linkedList == null || linkedList.size() == 0) {
            return;
        }
        System.out.println("before reversal linkedList:" + JSON.toJSON(linkedList));
        LinkedList newList = new LinkedList();
        linkedList.stream().forEach(e -> newList.add(0, e));
        System.out.println("after reversal linkedList:" + JSON.toJSON(newList));
    }


    /**
     * 循环遍历 反转
     *
     * @param node
     * @return
     */
    public static Node reversalCycle(Node node) {
        if (node == null || node.getRight() == null) {
            return node;
        }
        Node pre = null;
        Node cur = node;
        Node head = null;
        while (cur != null) {
            Node next = cur.getRight();
            if (next == null) {
                head = cur;
            }
            cur.setRight(pre);
            pre = cur;
            cur = next;
        }
        return head;
    }
}
