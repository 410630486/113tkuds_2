/*
 * Time Complexity: O(n)
 * 說明：每個節點訪問一次檢查BST性質和計算高度，總時間為O(n)
 * 空間複雜度O(h)，h為樹的高度（遞迴調用棧）
 */

import java.util.*;

class TreeNode9 {

    int val;
    TreeNode9 left;
    TreeNode9 right;

    TreeNode9(int val) {
        this.val = val;
    }
}

public class M09_AVLValidate {

    private static TreeNode9 buildTree(int[] values) {
        if (values.length == 0 || values[0] == -1) {
            return null;
        }

        TreeNode9 root = new TreeNode9(values[0]);
        Queue<TreeNode9> queue = new LinkedList<>();
        queue.offer(root);

        int i = 1;
        while (!queue.isEmpty() && i < values.length) {
            TreeNode9 current = queue.poll();

            // 左子節點
            if (i < values.length) {
                if (values[i] != -1) {
                    current.left = new TreeNode9(values[i]);
                    queue.offer(current.left);
                }
                i++;
            }

            // 右子節點
            if (i < values.length) {
                if (values[i] != -1) {
                    current.right = new TreeNode9(values[i]);
                    queue.offer(current.right);
                }
                i++;
            }
        }

        return root;
    }

    private static boolean isValidBST(TreeNode9 root, long min, long max) {
        if (root == null) {
            return true;
        }

        if (root.val <= min || root.val >= max) {
            return false;
        }

        return isValidBST(root.left, min, root.val)
                && isValidBST(root.right, root.val, max);
    }

    private static int checkAVL(TreeNode9 root) {
        if (root == null) {
            return 0;
        }

        int leftHeight = checkAVL(root.left);
        if (leftHeight == -1) {
            return -1; // 左子樹不是有效AVL
        }

        int rightHeight = checkAVL(root.right);
        if (rightHeight == -1) {
            return -1; // 右子樹不是有效AVL
        }

        int balance = Math.abs(leftHeight - rightHeight);
        if (balance > 1) {
            return -1; // 當前節點不滿足AVL性質
        }

        return Math.max(leftHeight, rightHeight) + 1;
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int n = scanner.nextInt();
            int[] values = new int[n];

            for (int i = 0; i < n; i++) {
                values[i] = scanner.nextInt();
            }

            TreeNode9 root = buildTree(values);

            // 檢查BST性質
            boolean isBST = isValidBST(root, Long.MIN_VALUE, Long.MAX_VALUE);
            if (!isBST) {
                System.out.println("Invalid BST");
                return;
            }

            // 檢查AVL性質
            boolean isAVL = checkAVL(root) != -1;
            if (!isAVL) {
                System.out.println("Invalid AVL");
                return;
            }

            System.out.println("Valid");
        }
    }
}
