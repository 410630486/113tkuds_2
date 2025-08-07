
import java.util.*;

public class LevelOrderTraversalVariations {

    /**
     * 二元樹節點
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
     * 1. 標準層序走訪
     */
    public static List<Integer> levelOrder(TreeNode root) {
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
     * 2. 分層的層序走訪（每一層分別列出）
     */
    public static List<List<Integer>> levelOrderByLevels(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            List<Integer> currentLevel = new ArrayList<>();

            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();
                currentLevel.add(node.val);

                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }

            result.add(currentLevel);
        }

        return result;
    }

    /**
     * 3. 由下而上的層序走訪
     */
    public static List<List<Integer>> levelOrderBottomUp(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            List<Integer> currentLevel = new ArrayList<>();

            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();
                currentLevel.add(node.val);

                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }

            result.add(0, currentLevel); // 插入到開頭
        }

        return result;
    }

    /**
     * 4. Z字形（鋸齒狀）層序走訪
     */
    public static List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        boolean leftToRight = true;

        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            List<Integer> currentLevel = new ArrayList<>();

            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();

                if (leftToRight) {
                    currentLevel.add(node.val);
                } else {
                    currentLevel.add(0, node.val); // 插入到開頭
                }

                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }

            result.add(currentLevel);
            leftToRight = !leftToRight; // 切換方向
        }

        return result;
    }

    /**
     * 5. 右視圖（每一層最右邊的節點）
     */
    public static List<Integer> rightSideView(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            TreeNode rightmostNode = null;

            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();
                rightmostNode = node;

                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }

            result.add(rightmostNode.val);
        }

        return result;
    }

    /**
     * 6. 左視圖（每一層最左邊的節點）
     */
    public static List<Integer> leftSideView(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            int levelSize = queue.size();

            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();

                // 第一個節點就是最左邊的節點
                if (i == 0) {
                    result.add(node.val);
                }

                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
        }

        return result;
    }

    /**
     * 7. 計算每一層的平均值
     */
    public static List<Double> averageOfLevels(TreeNode root) {
        List<Double> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            long sum = 0; // 使用long避免溢出

            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();
                sum += node.val;

                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }

            result.add((double) sum / levelSize);
        }

        return result;
    }

    /**
     * 8. 找出每一層的最大值
     */
    public static List<Integer> largestValues(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            int maxVal = Integer.MIN_VALUE;

            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();
                maxVal = Math.max(maxVal, node.val);

                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }

            result.add(maxVal);
        }

        return result;
    }

    /**
     * 9. 找出每一層的最小值
     */
    public static List<Integer> smallestValues(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            int minVal = Integer.MAX_VALUE;

            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();
                minVal = Math.min(minVal, node.val);

                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }

            result.add(minVal);
        }

        return result;
    }

    /**
     * 10. 垂直順序走訪（按列排序）
     */
    public static List<List<Integer>> verticalOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        Map<Integer, List<Integer>> columnMap = new TreeMap<>();
        Queue<TreeNode> nodeQueue = new LinkedList<>();
        Queue<Integer> columnQueue = new LinkedList<>();

        nodeQueue.offer(root);
        columnQueue.offer(0);

        while (!nodeQueue.isEmpty()) {
            TreeNode node = nodeQueue.poll();
            int column = columnQueue.poll();

            columnMap.computeIfAbsent(column, k -> new ArrayList<>()).add(node.val);

            if (node.left != null) {
                nodeQueue.offer(node.left);
                columnQueue.offer(column - 1);
            }
            if (node.right != null) {
                nodeQueue.offer(node.right);
                columnQueue.offer(column + 1);
            }
        }

        result.addAll(columnMap.values());
        return result;
    }

    /**
     * 11. 計算每一層的節點數量
     */
    public static List<Integer> levelSizes(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            result.add(levelSize);

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

        return result;
    }

    /**
     * 12. 計算樹的最大寬度（可能包含null節點）
     */
    public static int widthOfBinaryTree(TreeNode root) {
        if (root == null) {
            return 0;
        }

        Queue<TreeNode> nodeQueue = new LinkedList<>();
        Queue<Integer> indexQueue = new LinkedList<>();

        nodeQueue.offer(root);
        indexQueue.offer(0);
        int maxWidth = 1;

        while (!nodeQueue.isEmpty()) {
            int levelSize = nodeQueue.size();
            int leftMost = indexQueue.peek();
            int rightMost = leftMost;

            for (int i = 0; i < levelSize; i++) {
                TreeNode node = nodeQueue.poll();
                int currentIndex = indexQueue.poll();
                rightMost = currentIndex;

                if (node.left != null) {
                    nodeQueue.offer(node.left);
                    indexQueue.offer(2 * currentIndex);
                }
                if (node.right != null) {
                    nodeQueue.offer(node.right);
                    indexQueue.offer(2 * currentIndex + 1);
                }
            }

            maxWidth = Math.max(maxWidth, rightMost - leftMost + 1);
        }

        return maxWidth;
    }

    /**
     * 13. 奇偶層分別處理的層序走訪
     */
    public static Map<String, List<Integer>> oddEvenLevelOrder(TreeNode root) {
        Map<String, List<Integer>> result = new HashMap<>();
        List<Integer> oddLevels = new ArrayList<>();
        List<Integer> evenLevels = new ArrayList<>();

        if (root == null) {
            result.put("odd", oddLevels);
            result.put("even", evenLevels);
            return result;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        boolean isOddLevel = true; // 第一層（根節點）算作奇數層

        while (!queue.isEmpty()) {
            int levelSize = queue.size();

            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();

                if (isOddLevel) {
                    oddLevels.add(node.val);
                } else {
                    evenLevels.add(node.val);
                }

                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }

            isOddLevel = !isOddLevel;
        }

        result.put("odd", oddLevels);
        result.put("even", evenLevels);
        return result;
    }

    /**
     * 輔助方法：建立測試樹
     */
    public static TreeNode createSampleTree() {
        /*
         * 建立以下結構的樹：
         *         3
         *       /   \
         *      9     20
         *     / \   /  \
         *    1   2 15   7
         *   /   /     \
         *  4   5       6
         */
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(9);
        root.right = new TreeNode(20);
        root.left.left = new TreeNode(1);
        root.left.right = new TreeNode(2);
        root.right.left = new TreeNode(15);
        root.right.right = new TreeNode(7);
        root.left.left.left = new TreeNode(4);
        root.left.right.left = new TreeNode(5);
        root.right.right.right = new TreeNode(6);

        return root;
    }

    /**
     * 輔助方法：建立平衡樹
     */
    public static TreeNode createBalancedTree() {
        /*
         * 建立以下結構的平衡樹：
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

    /**
     * 輔助方法：顯示樹的結構
     */
    public static void printTree(TreeNode root, String title) {
        System.out.println(title);
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

    public static void main(String[] args) {
        System.out.println("=== 層序走訪的各種變化 ===");

        // 建立測試樹
        TreeNode tree = createSampleTree();
        printTree(tree, "測試樹結構：");

        // 1. 標準層序走訪
        System.out.println("\n1. 標準層序走訪：");
        System.out.printf("結果：%s\n", levelOrder(tree));

        // 2. 分層的層序走訪
        System.out.println("\n2. 分層的層序走訪：");
        List<List<Integer>> levelsByLevels = levelOrderByLevels(tree);
        for (int i = 0; i < levelsByLevels.size(); i++) {
            System.out.printf("第%d層：%s\n", i + 1, levelsByLevels.get(i));
        }

        // 3. 由下而上的層序走訪
        System.out.println("\n3. 由下而上的層序走訪：");
        List<List<Integer>> bottomUp = levelOrderBottomUp(tree);
        for (int i = 0; i < bottomUp.size(); i++) {
            System.out.printf("層%d：%s\n", bottomUp.size() - i, bottomUp.get(i));
        }

        // 4. Z字形層序走訪
        System.out.println("\n4. Z字形層序走訪：");
        List<List<Integer>> zigzag = zigzagLevelOrder(tree);
        for (int i = 0; i < zigzag.size(); i++) {
            String direction = (i % 2 == 0) ? "→" : "←";
            System.out.printf("第%d層 %s：%s\n", i + 1, direction, zigzag.get(i));
        }

        // 5. 左右視圖
        System.out.println("\n5. 左右視圖：");
        System.out.printf("右視圖：%s\n", rightSideView(tree));
        System.out.printf("左視圖：%s\n", leftSideView(tree));

        // 6. 統計分析
        System.out.println("\n6. 統計分析：");
        System.out.printf("每層節點數：%s\n", levelSizes(tree));
        System.out.printf("每層平均值：%s\n", averageOfLevels(tree));
        System.out.printf("每層最大值：%s\n", largestValues(tree));
        System.out.printf("每層最小值：%s\n", smallestValues(tree));

        // 7. 樹的寬度
        System.out.println("\n7. 樹的寬度：");
        System.out.printf("最大寬度：%d\n", widthOfBinaryTree(tree));

        // 8. 垂直順序走訪
        System.out.println("\n8. 垂直順序走訪：");
        List<List<Integer>> vertical = verticalOrder(tree);
        for (int i = 0; i < vertical.size(); i++) {
            System.out.printf("列%d：%s\n", i - vertical.size() / 2, vertical.get(i));
        }

        // 9. 奇偶層分別處理
        System.out.println("\n9. 奇偶層分別處理：");
        Map<String, List<Integer>> oddEven = oddEvenLevelOrder(tree);
        System.out.printf("奇數層：%s\n", oddEven.get("odd"));
        System.out.printf("偶數層：%s\n", oddEven.get("even"));

        // 測試平衡樹
        System.out.println("\n" + "=".repeat(50));
        TreeNode balancedTree = createBalancedTree();
        printTree(balancedTree, "平衡樹測試：");

        System.out.println("\n平衡樹的各種走訪：");
        System.out.printf("標準層序：%s\n", levelOrder(balancedTree));
        System.out.printf("Z字形：%s\n", zigzagLevelOrder(balancedTree));
        System.out.printf("右視圖：%s\n", rightSideView(balancedTree));
        System.out.printf("左視圖：%s\n", leftSideView(balancedTree));
        System.out.printf("最大寬度：%d\n", widthOfBinaryTree(balancedTree));

        // 空樹測試
        System.out.println("\n空樹測試：");
        TreeNode emptyTree = null;
        System.out.printf("空樹層序走訪：%s\n", levelOrder(emptyTree));
        System.out.printf("空樹右視圖：%s\n", rightSideView(emptyTree));
        System.out.printf("空樹最大寬度：%d\n", widthOfBinaryTree(emptyTree));

        // 單節點樹測試
        System.out.println("\n單節點樹測試：");
        TreeNode singleNode = new TreeNode(42);
        System.out.printf("單節點層序走訪：%s\n", levelOrder(singleNode));
        System.out.printf("單節點右視圖：%s\n", rightSideView(singleNode));
        System.out.printf("單節點Z字形：%s\n", zigzagLevelOrder(singleNode));
        System.out.printf("單節點最大寬度：%d\n", widthOfBinaryTree(singleNode));
    }
}
