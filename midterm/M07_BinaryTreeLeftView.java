
import java.util.*;

class TreeNode7 {

    int val;
    TreeNode7 left;
    TreeNode7 right;

    TreeNode7(int val) {
        this.val = val;
    }
}

public class M07_BinaryTreeLeftView {

    public static TreeNode7 buildTree(int[] values) {
        if (values.length == 0 || values[0] == -1) {
            return null;
        }

        TreeNode7 root = new TreeNode7(values[0]);
        Queue<TreeNode7> queue = new LinkedList<>();
        queue.offer(root);

        int i = 1;
        while (!queue.isEmpty() && i < values.length) {
            TreeNode7 current = queue.poll();

            if (i < values.length) {
                if (values[i] != -1) {
                    current.left = new TreeNode7(values[i]);
                    queue.offer(current.left);
                }
                i++;
            }

            if (i < values.length) {
                if (values[i] != -1) {
                    current.right = new TreeNode7(values[i]);
                    queue.offer(current.right);
                }
                i++;
            }
        }

        return root;
    }

    public static List<Integer> leftView(TreeNode7 root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        Queue<TreeNode7> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            int levelSize = queue.size();

            for (int i = 0; i < levelSize; i++) {
                TreeNode7 current = queue.poll();

                if (i == 0) {
                    result.add(current.val);
                }

                if (current.left != null) {
                    queue.offer(current.left);
                }
                if (current.right != null) {
                    queue.offer(current.right);
                }
            }
        }

        return result;
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int n = scanner.nextInt();
            int[] values = new int[n];

            for (int i = 0; i < n; i++) {
                values[i] = scanner.nextInt();
            }

            TreeNode7 root = buildTree(values);
            List<Integer> leftViewNodes = leftView(root);

            System.out.print("LeftView:");
            for (int val : leftViewNodes) {
                System.out.print(" " + val);
            }
            System.out.println();
        }
    }
}
