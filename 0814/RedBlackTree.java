
public class RedBlackTree {

    private RBNode root;
    private final RBNode NIL; // 哨兵節點

    public RedBlackTree() {
        NIL = new RBNode(0, false); // 黑色 NIL 節點
        root = NIL;
    }

    // 左旋操作
    // 時間複雜度: O(1), 空間複雜度: O(1)
    private void leftRotate(RBNode x) {
        RBNode y = x.right;
        x.right = y.left;

        if (y.left != NIL) {
            y.left.parent = x;
        }

        y.parent = x.parent;

        if (x.parent == NIL) {
            root = y;
        } else if (x == x.parent.left) {
            x.parent.left = y;
        } else {
            x.parent.right = y;
        }

        y.left = x;
        x.parent = y;
    }

    // 右旋操作
    // 時間複雜度: O(1), 空間複雜度: O(1)
    private void rightRotate(RBNode y) {
        RBNode x = y.left;
        y.left = x.right;

        if (x.right != NIL) {
            x.right.parent = y;
        }

        x.parent = y.parent;

        if (y.parent == NIL) {
            root = x;
        } else if (y == y.parent.right) {
            y.parent.right = x;
        } else {
            y.parent.left = x;
        }

        x.right = y;
        y.parent = x;
    }

    // 插入節點
    // 時間複雜度: O(log n), 空間複雜度: O(1)
    public void insert(int data) {
        RBNode newNode = new RBNode(data);
        newNode.left = NIL;
        newNode.right = NIL;
        newNode.parent = NIL;

        RBNode parent = NIL;
        RBNode current = root;

        // 找到插入位置
        while (current != NIL) {
            parent = current;
            if (newNode.data < current.data) {
                current = current.left;
            } else {
                current = current.right;
            }
        }

        newNode.parent = parent;

        if (parent == NIL) {
            root = newNode; // 樹為空
        } else if (newNode.data < parent.data) {
            parent.left = newNode;
        } else {
            parent.right = newNode;
        }

        // 新節點預設為紅色
        newNode.isRed = true;

        // 修復紅黑性質
        insertFixup(newNode);
    }

    // 插入修復
    private void insertFixup(RBNode node) {
        while (node != root && node.parent != null && node.parent.isRed) {
            if (node.parent == node.parent.parent.left) {
                RBNode uncle = node.parent.parent.right;

                if (uncle.isRed) {
                    // 情況 1：叔叔是紅色
                    node.parent.isRed = false;
                    uncle.isRed = false;
                    node.parent.parent.isRed = true;
                    node = node.parent.parent;
                } else {
                    if (node == node.parent.right) {
                        // 情況 2：叔叔是黑色，當前節點是右子節點
                        node = node.parent;
                        leftRotate(node);
                    }
                    // 情況 3：叔叔是黑色，當前節點是左子節點
                    node.parent.isRed = false;
                    node.parent.parent.isRed = true;
                    rightRotate(node.parent.parent);
                }
            } else {
                // 對稱情況
                RBNode uncle = node.parent.parent.left;

                if (uncle.isRed) {
                    node.parent.isRed = false;
                    uncle.isRed = false;
                    node.parent.parent.isRed = true;
                    node = node.parent.parent;
                } else {
                    if (node == node.parent.left) {
                        node = node.parent;
                        rightRotate(node);
                    }
                    node.parent.isRed = false;
                    node.parent.parent.isRed = true;
                    leftRotate(node.parent.parent);
                }
            }
        }
        root.isRed = false; // 根節點必須是黑色
    }

    // 搜尋節點
    // 時間複雜度: O(log n), 空間複雜度: O(1)
    public boolean search(int data) {
        return searchNode(root, data) != NIL;
    }

    private RBNode searchNode(RBNode node, int data) {
        while (node != NIL && data != node.data) {
            if (data < node.data) {
                node = node.left;
            } else {
                node = node.right;
            }
        }
        return node;
    }

    // 找最小值節點
    private RBNode minimum(RBNode node) {
        while (node.left != NIL) {
            node = node.left;
        }
        return node;
    }

    // 節點替換
    private void transplant(RBNode u, RBNode v) {
        if (u.parent == NIL) {
            root = v;
        } else if (u == u.parent.left) {
            u.parent.left = v;
        } else {
            u.parent.right = v;
        }
        v.parent = u.parent;
    }

    // 刪除節點
    // 時間複雜度: O(log n), 空間複雜度: O(1)
    public void delete(int data) {
        RBNode nodeToDelete = searchNode(root, data);
        if (nodeToDelete != NIL) {
            deleteNode(nodeToDelete);
        }
    }

    private void deleteNode(RBNode node) {
        RBNode originalNode = node;
        boolean originalColor = originalNode.isRed;
        RBNode replacement;

        if (node.left == NIL) {
            replacement = node.right;
            transplant(node, node.right);
        } else if (node.right == NIL) {
            replacement = node.left;
            transplant(node, node.left);
        } else {
            originalNode = minimum(node.right);
            originalColor = originalNode.isRed;
            replacement = originalNode.right;

            if (originalNode.parent == node) {
                replacement.parent = originalNode;
            } else {
                transplant(originalNode, originalNode.right);
                originalNode.right = node.right;
                originalNode.right.parent = originalNode;
            }

            transplant(node, originalNode);
            originalNode.left = node.left;
            originalNode.left.parent = originalNode;
            originalNode.isRed = node.isRed;
        }

        if (!originalColor) {
            deleteFixup(replacement);
        }
    }

    // 刪除修復
    private void deleteFixup(RBNode node) {
        while (node != root && !node.isRed) {
            if (node == node.parent.left) {
                RBNode sibling = node.parent.right;

                if (sibling.isRed) {
                    // 情況 1：兄弟是紅色
                    sibling.isRed = false;
                    node.parent.isRed = true;
                    leftRotate(node.parent);
                    sibling = node.parent.right;
                }

                if (!sibling.left.isRed && !sibling.right.isRed) {
                    // 情況 2：兄弟和其子節點都是黑色
                    sibling.isRed = true;
                    node = node.parent;
                } else {
                    if (!sibling.right.isRed) {
                        // 情況 3：兄弟是黑色，左子節點是紅色，右子節點是黑色
                        sibling.left.isRed = false;
                        sibling.isRed = true;
                        rightRotate(sibling);
                        sibling = node.parent.right;
                    }

                    // 情況 4：兄弟是黑色，右子節點是紅色
                    sibling.isRed = node.parent.isRed;
                    node.parent.isRed = false;
                    sibling.right.isRed = false;
                    leftRotate(node.parent);
                    node = root;
                }
            } else {
                // 對稱情況
                RBNode sibling = node.parent.left;

                if (sibling.isRed) {
                    sibling.isRed = false;
                    node.parent.isRed = true;
                    rightRotate(node.parent);
                    sibling = node.parent.left;
                }

                if (!sibling.right.isRed && !sibling.left.isRed) {
                    sibling.isRed = true;
                    node = node.parent;
                } else {
                    if (!sibling.left.isRed) {
                        sibling.right.isRed = false;
                        sibling.isRed = true;
                        leftRotate(sibling);
                        sibling = node.parent.left;
                    }

                    sibling.isRed = node.parent.isRed;
                    node.parent.isRed = false;
                    sibling.left.isRed = false;
                    rightRotate(node.parent);
                    node = root;
                }
            }
        }
        node.isRed = false;
    }

    // 中序遍歷列印
    public void printTree() {
        printInOrder(root);
        System.out.println();
    }

    private void printInOrder(RBNode node) {
        if (node != NIL) {
            printInOrder(node.left);
            String color = node.isRed ? "R" : "B";
            System.out.print(node.data + "(" + color + ") ");
            printInOrder(node.right);
        }
    }

    // 驗證紅黑樹性質
    public boolean isValidRBTree() {
        if (root != NIL && root.isRed) {
            return false; // 性質 2：根節點必須是黑色

                }return checkBlackHeight(root) != -1;
    }

    private int checkBlackHeight(RBNode node) {
        if (node == NIL) {
            return 0;
        }

        // 檢查性質 4：紅色節點的子節點必須是黑色
        if (node.isRed) {
            if ((node.left != NIL && node.left.isRed)
                    || (node.right != NIL && node.right.isRed)) {
                return -1;
            }
        }

        int leftHeight = checkBlackHeight(node.left);
        int rightHeight = checkBlackHeight(node.right);

        if (leftHeight == -1 || rightHeight == -1 || leftHeight != rightHeight) {
            return -1;
        }

        return leftHeight + (node.isRed ? 0 : 1);
    }
}
