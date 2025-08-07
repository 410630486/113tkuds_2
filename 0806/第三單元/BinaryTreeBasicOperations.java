
import java.util.*;

public class BinaryTreeBasicOperations {

    /**
     * 二元樹節點類別
     */
    static class TreeNode {

        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    /**
     * 計算樹中所有節點值的總和
     */
    public static int calculateSum(TreeNode root) {
        if (root == null) {
            return 0;
        }

        return root.val + calculateSum(root.left) + calculateSum(root.right);
    }

    /**
     * 計算樹中節點的數量
     */
    public static int countNodes(TreeNode root) {
        if (root == null) {
            return 0;
        }

        return 1 + countNodes(root.left) + countNodes(root.right);
    }

    /**
     * 計算樹中所有節點值的平均值
     */
    public static double calculateAverage(TreeNode root) {
        if (root == null) {
            return 0.0;
        }

        int sum = calculateSum(root);
        int count = countNodes(root);

        return (double) sum / count;
    }

    /**
     * 找出樹中的最大值節點
     */
    public static TreeNode findMaxNode(TreeNode root) {
        if (root == null) {
            return null;
        }

        TreeNode maxNode = root;
        TreeNode leftMax = findMaxNode(root.left);
        TreeNode rightMax = findMaxNode(root.right);

        if (leftMax != null && leftMax.val > maxNode.val) {
            maxNode = leftMax;
        }

        if (rightMax != null && rightMax.val > maxNode.val) {
            maxNode = rightMax;
        }

        return maxNode;
    }

    /**
     * 找出樹中的最小值節點
     */
    public static TreeNode findMinNode(TreeNode root) {
        if (root == null) {
            return null;
        }

        TreeNode minNode = root;
        TreeNode leftMin = findMinNode(root.left);
        TreeNode rightMin = findMinNode(root.right);

        if (leftMin != null && leftMin.val < minNode.val) {
            minNode = leftMin;
        }

        if (rightMin != null && rightMin.val < minNode.val) {
            minNode = rightMin;
        }

        return minNode;
    }

    /**
     * 計算樹的寬度（每一層節點數的最大值）
     */
    public static int calculateWidth(TreeNode root) {
        if (root == null) {
            return 0;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        int maxWidth = 0;

        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            maxWidth = Math.max(maxWidth, levelSize);

            // 處理當前層的所有節點
            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();

                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
        }

        return maxWidth;
    }

    /**
     * 獲取每一層的節點數量
     */
    public static List<Integer> getLevelWidths(TreeNode root) {
        List<Integer> widths = new ArrayList<>();
        if (root == null) {
            return widths;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            widths.add(levelSize);

            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();

                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
        }

        return widths;
    }

    /**
     * 判斷一棵樹是否為完全二元樹
     */
    public static boolean isCompleteTree(TreeNode root) {
        if (root == null) {
            return true;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        boolean foundNull = false;

        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();

            if (node == null) {
                foundNull = true;
            } else {
                // 如果之前已經遇到null節點，但現在又遇到非null節點，則不是完全二元樹
                if (foundNull) {
                    return false;
                }

                queue.offer(node.left);
                queue.offer(node.right);
            }
        }

        return true;
    }

    /**
     * 計算樹的高度
     */
    public static int calculateHeight(TreeNode root) {
        if (root == null) {
            return 0;
        }

        return 1 + Math.max(calculateHeight(root.left), calculateHeight(root.right));
    }

    /**
     * 判斷是否為滿二元樹（每個節點要麼是葉節點，要麼有兩個子節點）
     */
    public static boolean isFullTree(TreeNode root) {
        if (root == null) {
            return true;
        }

        // 葉節點
        if (root.left == null && root.right == null) {
            return true;
        }

        // 有兩個子節點
        if (root.left != null && root.right != null) {
            return isFullTree(root.left) && isFullTree(root.right);
        }

        // 只有一個子節點，不是滿二元樹
        return false;
    }

    /**
     * 判斷是否為完美二元樹（所有葉節點都在同一層，且所有內部節點都有兩個子節點）
     */
    public static boolean isPerfectTree(TreeNode root) {
        if (root == null) {
            return true;
        }

        int height = calculateHeight(root);
        return isPerfectTreeHelper(root, height, 1);
    }

    private static boolean isPerfectTreeHelper(TreeNode root, int totalHeight, int currentLevel) {
        if (root == null) {
            return true;
        }

        // 葉節點必須在最後一層
        if (root.left == null && root.right == null) {
            return currentLevel == totalHeight;
        }

        // 內部節點必須有兩個子節點
        if (root.left == null || root.right == null) {
            return false;
        }

        return isPerfectTreeHelper(root.left, totalHeight, currentLevel + 1)
                && isPerfectTreeHelper(root.right, totalHeight, currentLevel + 1);
    }

    /**
     * 計算葉節點的數量
     */
    public static int countLeafNodes(TreeNode root) {
        if (root == null) {
            return 0;
        }

        if (root.left == null && root.right == null) {
            return 1;
        }

        return countLeafNodes(root.left) + countLeafNodes(root.right);
    }

    /**
     * 計算內部節點的數量
     */
    public static int countInternalNodes(TreeNode root) {
        if (root == null) {
            return 0;
        }

        if (root.left == null && root.right == null) {
            return 0; // 葉節點
        }

        return 1 + countInternalNodes(root.left) + countInternalNodes(root.right);
    }

    /**
     * 層序走訪
     */
    public static List<Integer> levelOrderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            result.add(node.val);

            if (node.left != null) {
                queue.offer(node.left);
            }
            if (node.right != null) {
                queue.offer(node.right);
            }
        }

        return result;
    }

    /**
     * 前序走訪
     */
    public static List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        preorderHelper(root, result);
        return result;
    }

    private static void preorderHelper(TreeNode root, List<Integer> result) {
        if (root == null) {
            return;
        }

        result.add(root.val);
        preorderHelper(root.left, result);
        preorderHelper(root.right, result);
    }

    /**
     * 中序走訪
     */
    public static List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        inorderHelper(root, result);
        return result;
    }

    private static void inorderHelper(TreeNode root, List<Integer> result) {
        if (root == null) {
            return;
        }

        inorderHelper(root.left, result);
        result.add(root.val);
        inorderHelper(root.right, result);
    }

    /**
     * 後序走訪
     */
    public static List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        postorderHelper(root, result);
        return result;
    }

    private static void postorderHelper(TreeNode root, List<Integer> result) {
        if (root == null) {
            return;
        }

        postorderHelper(root.left, result);
        postorderHelper(root.right, result);
        result.add(root.val);
    }

    /**
     * 顯示樹的結構（簡化版）
     */
    public static void printTree(TreeNode root) {
        if (root == null) {
            System.out.println("空樹");
            return;
        }

        printTreeHelper(root, "", true);
    }

    private static void printTreeHelper(TreeNode root, String prefix, boolean isLast) {
        if (root == null) {
            return;
        }

        System.out.println(prefix + (isLast ? "└── " : "├── ") + root.val);

        if (root.left != null || root.right != null) {
            if (root.right != null) {
                printTreeHelper(root.right, prefix + (isLast ? "    " : "│   "), root.left == null);
            }
            if (root.left != null) {
                printTreeHelper(root.left, prefix + (isLast ? "    " : "│   "), true);
            }
        }
    }

    /**
     * 建立測試用的二元樹
     */
    public static TreeNode createSampleTree() {
        /*
         * 建立以下結構的樹：
         *       1
         *      / \
         *     2   3
         *    / \   \
         *   4   5   6
         *  /       / \
         * 7       8   9
         */
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        root.right.right = new TreeNode(6);
        root.left.left.left = new TreeNode(7);
        root.right.right.left = new TreeNode(8);
        root.right.right.right = new TreeNode(9);

        return root;
    }

    /**
     * 建立完全二元樹
     */
    public static TreeNode createCompleteTree() {
        /*
         * 建立以下結構的完全二元樹：
         *       1
         *      / \
         *     2   3
         *    / \ / \
         *   4  5 6  7
         */
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        root.right.left = new TreeNode(6);
        root.right.right = new TreeNode(7);

        return root;
    }

    public static void main(String[] args) {
        System.out.println("=== 二元樹基本操作練習 ===");

        // 建立測試樹
        TreeNode tree = createSampleTree();

        System.out.println("1. 樹的結構：");
        printTree(tree);

        // 基本統計
        System.out.println("\n2. 基本統計：");
        System.out.printf("節點總數：%d\n", countNodes(tree));
        System.out.printf("葉節點數：%d\n", countLeafNodes(tree));
        System.out.printf("內部節點數：%d\n", countInternalNodes(tree));
        System.out.printf("樹的高度：%d\n", calculateHeight(tree));

        // 數值統計
        System.out.println("\n3. 數值統計：");
        System.out.printf("所有節點值總和：%d\n", calculateSum(tree));
        System.out.printf("平均值：%.2f\n", calculateAverage(tree));

        TreeNode maxNode = findMaxNode(tree);
        TreeNode minNode = findMinNode(tree);
        System.out.printf("最大值：%d\n", maxNode != null ? maxNode.val : 0);
        System.out.printf("最小值：%d\n", minNode != null ? minNode.val : 0);

        // 寬度分析
        System.out.println("\n4. 寬度分析：");
        System.out.printf("樹的最大寬度：%d\n", calculateWidth(tree));
        List<Integer> levelWidths = getLevelWidths(tree);
        System.out.printf("各層寬度：%s\n", levelWidths);

        // 樹型判斷
        System.out.println("\n5. 樹型判斷：");
        System.out.printf("是否為完全二元樹：%s\n", isCompleteTree(tree));
        System.out.printf("是否為滿二元樹：%s\n", isFullTree(tree));
        System.out.printf("是否為完美二元樹：%s\n", isPerfectTree(tree));

        // 走訪方式
        System.out.println("\n6. 樹的走訪：");
        System.out.printf("前序走訪：%s\n", preorderTraversal(tree));
        System.out.printf("中序走訪：%s\n", inorderTraversal(tree));
        System.out.printf("後序走訪：%s\n", postorderTraversal(tree));
        System.out.printf("層序走訪：%s\n", levelOrderTraversal(tree));

        // 測試完全二元樹
        System.out.println("\n" + "=".repeat(50));
        System.out.println("7. 完全二元樹測試：");
        TreeNode completeTree = createCompleteTree();
        printTree(completeTree);

        System.out.printf("是否為完全二元樹：%s\n", isCompleteTree(completeTree));
        System.out.printf("是否為滿二元樹：%s\n", isFullTree(completeTree));
        System.out.printf("是否為完美二元樹：%s\n", isPerfectTree(completeTree));
        System.out.printf("樹的最大寬度：%d\n", calculateWidth(completeTree));

        // 空樹測試
        System.out.println("\n8. 空樹測試：");
        TreeNode emptyTree = null;
        System.out.printf("空樹節點數：%d\n", countNodes(emptyTree));
        System.out.printf("空樹是否為完全二元樹：%s\n", isCompleteTree(emptyTree));
        System.out.printf("空樹高度：%d\n", calculateHeight(emptyTree));
    }
}
