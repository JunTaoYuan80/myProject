package com.yuan.datastructure.linked;

import com.yuan.datastructure.Node;

/**
 * 循环链表
 * https://blog.csdn.net/tyler_download/article/details/53691695
 *
 * @author yuanjuntao
 * @date 2018/11/18 11:26
 */
public class CircleList {

    private static Node stepOne;
    private static Node stepTwo;
    private static int stepCount = 0;
    private static int visitCount = 0;
    static int lenOfFirstVisit = 0;
    static int lenOfSecondVisit = 0;

    public static void main(String[] args) {
        Node n1 = new Node(1);
        Node n2 = new Node(2);
        Node n3 = new Node(3);
        Node n4 = new Node(4);
        Node n5 = new Node(5);
        Node n6 = new Node(6);
        Node n7 = new Node(7);
        Node n8 = new Node(8);
        Node n9 = new Node(9);

        n1.setRight(n2);
        n2.setRight(n3);
        n3.setRight(n4);
        n4.setRight(n5);
        n5.setRight(n6);
        n6.setRight(n7);
        n7.setRight(n8);
        n8.setRight(n9);
        n9.setRight(n5);
        System.out.println(getCircleLength(n1));
    }

    public static int getCircleLength(Node head) {
        stepOne = head;
        stepTwo = head;
        lenOfFirstVisit = 0;
        lenOfSecondVisit = 0;

        do {
            if (goOneStep() == false || goTwoStep() == false) {
                break;
            }

            stepCount++;

            if (stepOne == stepTwo) {
                visitCount++;
                if (visitCount == 1) {
                    lenOfFirstVisit = stepCount;
                }

                if (visitCount == 2) {
                    lenOfSecondVisit = stepCount;
                }
            }
        } while (visitCount < 2);

        return lenOfSecondVisit - lenOfFirstVisit;
    }

    private static boolean goOneStep() {
        if (stepOne == null || stepOne.getRight() == null) {
            return false;
        }

        stepOne = stepOne.getRight();
        return true;
    }

    private static boolean goTwoStep() {
        if (stepTwo == null || stepTwo.getRight() == null || stepTwo.getRight().getRight() == null) {
            return false;
        }

        stepTwo = stepTwo.getRight().getRight();
        return true;
    }
}
