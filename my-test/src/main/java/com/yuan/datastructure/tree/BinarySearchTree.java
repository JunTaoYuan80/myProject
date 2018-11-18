package com.yuan.datastructure.tree;

import com.yuan.datastructure.Node;

/**
 * @author yuanjuntao
 * @date 2018/8/26 10:08
 */
public class BinarySearchTree {


    /**
     * 递归查找
     *
     * @param root
     * @param b
     * @return
     */
    public Node findRecursion(Node root, int b) {
        if (root == null) {
            return null;
        }

        if (root.getValue() == b) {
            return root;
        } else if (root.getValue() > b) {
            return findRecursion(root.getLeft(), b);
        } else {
            return findRecursion(root.getRight(), b);
        }
    }

    /**
     * 非递归查找
     *
     * @param root
     * @param b
     * @return
     */
    public Node findNotRecursion(Node root, int b) {
        Node t = root;
        while (t != null) {
            if (t.getValue() == b) {
                return t;
            } else if (t.getValue() > b) {
                t = t.getLeft();
            } else {
                t = t.getRight();
            }
        }

        return null;
    }

    /**
     * 递归插入
     *
     * @param node
     * @param b
     */
    public Node insertRecursion(Node node, int b) {
        if (node == null) {
            return new Node(b);
        }
        if (node.getValue() == b) {
            return node;
        } else if (node.getValue() > b) {
            node.setLeft(insertRecursion(node.getLeft(), b));
        } else {
            node.setRight(insertRecursion(node.getRight(), b));
        }
        return node;
    }

    /**
     * 非递归插入
     *
     * @param node
     * @param b
     */
    public void insertNotRecursion(Node node, int b) {
        if (node == null) {
            node = new Node(b);
            return;
        }
        Node temp = node;
        while (true) {
            if (temp.getValue() == b) {
                return;
            } else if (temp.getValue() > b) {
                if (temp.getLeft() == null) {
                    temp.setLeft(new Node(b));
                    return;
                }
                temp = temp.getLeft();
            } else {
                if (temp.getRight() == null) {
                    temp.setRight(new Node(b));
                    return;
                }
                temp = temp.getRight();
            }
        }
    }

    /**
     * 删除节点
     *
     * @param root
     * @param b
     * @return
     */
    public Node deleteNode(Node root, int b) {
        if (root == null) {
            return null;
        }
        if (root.getValue() > b) {
            return deleteNode(root.getLeft(), b);
        } else if (root.getValue() < b) {
            return deleteNode(root.getRight(), b);
        } else {
            if (root.getLeft() == null) {
                root = root.getRight();
                return root.getRight();
            } else if (root.getRight() == null) {
                root = root.getLeft();
                return root.getLeft();
            } else {
                Node t = root;
                root = minNode(root.getRight());
                System.out.println(root.getValue());
                root.setRight(delMin(t.getRight()));
                root.setLeft(t.getLeft());
                System.out.println(root.getValue());
            }
        }

        return root;
    }

    public void deleteNode2(Node root, int b) {
        if (root == null) {
            return;
        }
        if (root.getValue() > b) {
            deleteNode2(root.getLeft(), b);
        } else if (root.getValue() < b) {
            deleteNode2(root.getRight(), b);
        } else {
            if (root.getLeft() == null) {
                Node t = root;
                root = root.getRight();
                root.setRight(t.getRight());
                root.setLeft(t.getLeft());
            } else if (root.getRight() == null) {
                root = root.getLeft();
            } else {
                Node t = root;
                root = minNode(root.getRight());
                System.out.println(root.getValue());
                root.setRight(delMin(t.getRight()));
                root.setLeft(t.getLeft());
                System.out.println(root.getValue());
            }
        }
    }

    public Node delMin(Node root) {
        if (root.getLeft() == null) {
            return root.getRight();
        }
        root.setLeft(delMin(root.getLeft()));
        return root;
    }

    private Node minNode(Node node) {
        if (node.getLeft() == null) {
            return node;
        }
        return minNode(node.getLeft());
    }

    public void minddleQuery(Node node) {
        if (node == null) {
            return;
        }
        minddleQuery(node.getLeft());
        System.out.print(node.getValue() + ", ");
        minddleQuery(node.getRight());
    }

    public void preQuery(Node node) {
        if (node == null) {
            return;
        }
        System.out.print(node.getValue() + ", ");
        minddleQuery(node.getLeft());
        minddleQuery(node.getRight());
    }


    public static void main(String[] args) {
        BinarySearchTree bst = new BinarySearchTree();
        Node node = new Node(10);
        /*node = bst.insertRecursion(node, 5);
        node = bst.insertRecursion(node, 8);
        node = bst.insertRecursion(node, 12);
        node = bst.insertRecursion(node, 15);
        node = bst.insertRecursion(node, 3);
        node = bst.insertRecursion(node, 20);*/
        bst.insertNotRecursion(node, 5);
        bst.insertNotRecursion(node, 8);
        bst.insertNotRecursion(node, 12);
        bst.insertNotRecursion(node, 15);
        bst.insertNotRecursion(node, 3);
        bst.insertNotRecursion(node, 20);
        bst.minddleQuery(node);
        System.out.println("");
        bst.deleteNode2(node, 15);
        bst.minddleQuery(node);


    }
}
