package com.yuan.datastructure;

import lombok.Data;

/**
 * @author yuanjuntao
 * @date 2018/8/26 10:04
 */
@Data
public class Node {
    public Node(int value) {
        this.value = value;
        this.left = null;
        this.right = null;
    }

    private Integer value;
    private Node left;
    private Node right;
}
