package com.yuan.datastructure;

/**
 * @author yuanjuntao
 * @date 2018/8/26 10:04
 */
public class Node {
    public Node(int value) {
        this.value = value;
        this.left = null;
        this.right = null;
    }

    private Integer value;
    private Node left;
    private Node right;

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }
}
