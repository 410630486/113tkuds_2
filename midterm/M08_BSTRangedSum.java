
import java.util.*;

class TreeNode8 {

    int val;
    TreeNode8 left;
    TreeNode8 right;

    TreeNode8(int val) {
        this.val = val;
    }
}

public class M08_BSTRangedSum {

    private static TreeNode8 buildTree(int[] values) {
        if (values.length == 0 || values[0] == -1) {
            return null;
        }

        TreeNode8 root = new TreeNode8(values[0]);
        Queue<TreeNode8> queue = new LinkedList<>();
        queue.offer(root);

        int i = 1;
        while (!queue.isEmpty() && i < values.length) {
            TreeNode8 current = queue.poll();

            if (i < values.length) {
                if (values[i] != -1) {
                    current.left = new TreeNode8(values[i]);
                    queue.offer(current.left);
                }
                i++;
            }

            if (i < values.length) {
                if (values[i] != -1) {
                    current.right = new TreeNode8(values[i]);
                    queue.offer(current.right);
                }
                i++;
            }
        }

        return root;
    }

    private static int rangeSum(TreeNode8 root, int L, int R) {
        if (root == null) {
            return 0;
        }

        if (root.val < L) {
            return rangeSum(root.right, L, R);
        }

        if (root.val > R) {
            return rangeSum(root.left, L, R);
        }

        return root.val + rangeSum(root.left, L, R) + rangeSum(root.right, L, R);
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int n = scanner.nextInt();
            int[] values = new int[n];

            for (int i = 0; i < n; i++) {
                values[i] = scanner.nextInt();
            }

            int L = scanner.nextInt();
            int R = scanner.nextInt();

            TreeNode8 root = buildTree(values);
            int sum = rangeSum(root, L, R);

            System.out.println("Sum: " + sum);
        }
    }
}
