
import java.util.*;

public class TreePathProblems {

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
     * 1. 找出從根節點到所有葉節點的路徑
     */
    public static List<List<Integer>> allRootToLeafPaths(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        List<Integer> currentPath = new ArrayList<>();
        findAllPaths(root, currentPath, result);
        return result;
    }

    private static void findAllPaths(TreeNode node, List<Integer> currentPath,
            List<List<Integer>> result) {
        if (node == null) {
            return;
        }

        // 將當前節點加入路徑
        currentPath.add(node.val);

        // 如果是葉節點，保存路徑
        if (node.left == null && node.right == null) {
            result.add(new ArrayList<>(currentPath));
        } else {
            // 繼續搜尋左右子樹
            findAllPaths(node.left, currentPath, result);
            findAllPaths(node.right, currentPath, result);
        }

        // 回溯：移除當前節點
        currentPath.remove(currentPath.size() - 1);
    }

    /**
     * 2. 判斷是否存在根到葉的路徑，其和等於目標值
     */
    public static boolean hasPathSum(TreeNode root, int targetSum) {
        if (root == null) {
            return false;
        }

        // 如果是葉節點，檢查路徑和是否等於目標值
        if (root.left == null && root.right == null) {
            return root.val == targetSum;
        }

        // 遞迴檢查左右子樹
        int remainingSum = targetSum - root.val;
        return hasPathSum(root.left, remainingSum) || hasPathSum(root.right, remainingSum);
    }

    /**
     * 3. 找出所有根到葉的路徑，其和等於目標值
     */
    public static List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        List<Integer> currentPath = new ArrayList<>();
        findPathSum(root, targetSum, currentPath, result);
        return result;
    }

    private static void findPathSum(TreeNode node, int remainingSum,
            List<Integer> currentPath, List<List<Integer>> result) {
        if (node == null) {
            return;
        }

        // 將當前節點加入路徑
        currentPath.add(node.val);

        // 如果是葉節點且剩餘和等於當前節點值
        if (node.left == null && node.right == null && remainingSum == node.val) {
            result.add(new ArrayList<>(currentPath));
        } else {
            // 繼續搜尋左右子樹
            int newRemaining = remainingSum - node.val;
            findPathSum(node.left, newRemaining, currentPath, result);
            findPathSum(node.right, newRemaining, currentPath, result);
        }

        // 回溯
        currentPath.remove(currentPath.size() - 1);
    }

    /**
     * 4. 計算路徑總數，路徑和等於目標值（路徑可以不從根節點開始，也可以不在葉節點結束）
     */
    public static int pathSumCount(TreeNode root, int targetSum) {
        if (root == null) {
            return 0;
        }

        // 從當前節點開始的路徑數 + 左子樹中的路徑數 + 右子樹中的路徑數
        return pathSumFromNode(root, targetSum)
                + pathSumCount(root.left, targetSum)
                + pathSumCount(root.right, targetSum);
    }

    private static int pathSumFromNode(TreeNode node, int targetSum) {
        if (node == null) {
            return 0;
        }

        int count = 0;
        if (node.val == targetSum) {
            count = 1;
        }

        int remaining = targetSum - node.val;
        count += pathSumFromNode(node.left, remaining);
        count += pathSumFromNode(node.right, remaining);

        return count;
    }

    /**
     * 5. 找出樹中的最大路徑和（路徑可以從任意節點開始和結束）
     */
    public static int maxPathSum(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int[] maxSum = {Integer.MIN_VALUE};
        maxPathSumHelper(root, maxSum);
        return maxSum[0];
    }

    private static int maxPathSumHelper(TreeNode node, int[] maxSum) {
        if (node == null) {
            return 0;
        }

        // 計算左右子樹能提供的最大路徑和（如果是負數就不要）
        int leftGain = Math.max(maxPathSumHelper(node.left, maxSum), 0);
        int rightGain = Math.max(maxPathSumHelper(node.right, maxSum), 0);

        // 經過當前節點的最大路徑和
        int currentMaxPath = node.val + leftGain + rightGain;

        // 更新全局最大值
        maxSum[0] = Math.max(maxSum[0], currentMaxPath);

        // 返回包含當前節點的單側最大路徑和
        return node.val + Math.max(leftGain, rightGain);
    }

    /**
     * 6. 找出兩個節點之間的路徑
     */
    public static List<Integer> findPath(TreeNode root, int start, int end) {
        List<Integer> pathToStart = findPathToNode(root, start);
        List<Integer> pathToEnd = findPathToNode(root, end);

        if (pathToStart == null || pathToEnd == null) {
            return new ArrayList<>(); // 其中一個節點不存在
        }

        // 找到最低公共祖先
        int lca = findLCA(pathToStart, pathToEnd);

        // 構建從start到end的路徑
        List<Integer> result = new ArrayList<>();

        // 從start到LCA（需要反轉）
        for (int i = pathToStart.size() - 1; i >= lca; i--) {
            result.add(pathToStart.get(i));
        }

        // 從LCA到end（跳過LCA本身，因為已經加過了）
        for (int i = lca + 1; i < pathToEnd.size(); i++) {
            result.add(pathToEnd.get(i));
        }

        return result;
    }

    private static List<Integer> findPathToNode(TreeNode root, int target) {
        if (root == null) {
            return null;
        }

        if (root.val == target) {
            List<Integer> path = new ArrayList<>();
            path.add(root.val);
            return path;
        }

        // 搜尋左子樹
        List<Integer> leftPath = findPathToNode(root.left, target);
        if (leftPath != null) {
            leftPath.add(0, root.val);
            return leftPath;
        }

        // 搜尋右子樹
        List<Integer> rightPath = findPathToNode(root.right, target);
        if (rightPath != null) {
            rightPath.add(0, root.val);
            return rightPath;
        }

        return null;
    }

    private static int findLCA(List<Integer> path1, List<Integer> path2) {
        int i = 0;
        while (i < path1.size() && i < path2.size()
                && path1.get(i).equals(path2.get(i))) {
            i++;
        }
        return i - 1; // 返回最後一個相同節點的索引
    }

    /**
     * 7. 計算二元樹的直徑（任意兩個節點之間的最長路徑）
     */
    public static int diameterOfBinaryTree(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int[] diameter = {0};
        calculateDepth(root, diameter);
        return diameter[0];
    }

    private static int calculateDepth(TreeNode node, int[] diameter) {
        if (node == null) {
            return 0;
        }

        int leftDepth = calculateDepth(node.left, diameter);
        int rightDepth = calculateDepth(node.right, diameter);

        // 經過當前節點的最長路徑
        int currentDiameter = leftDepth + rightDepth;
        diameter[0] = Math.max(diameter[0], currentDiameter);

        return Math.max(leftDepth, rightDepth) + 1;
    }

    /**
     * 8. 找出所有距離根節點為k的節點
     */
    public static List<Integer> findNodesAtDistanceK(TreeNode root, int k) {
        List<Integer> result = new ArrayList<>();
        findNodesAtDistance(root, k, result);
        return result;
    }

    private static void findNodesAtDistance(TreeNode node, int k, List<Integer> result) {
        if (node == null) {
            return;
        }

        if (k == 0) {
            result.add(node.val);
            return;
        }

        findNodesAtDistance(node.left, k - 1, result);
        findNodesAtDistance(node.right, k - 1, result);
    }

    /**
     * 9. 找出距離目標節點為k的所有節點
     */
    public static List<Integer> distanceK(TreeNode root, TreeNode target, int k) {
        List<Integer> result = new ArrayList<>();
        findDistanceK(root, target, k, result);
        return result;
    }

    private static int findDistanceK(TreeNode node, TreeNode target, int k, List<Integer> result) {
        if (node == null) {
            return -1;
        }

        if (node == target) {
            // 找到目標節點，搜尋距離為k的節點
            collectNodesAtDistance(node, k, result);
            return 0;
        }

        // 在左子樹中搜尋目標節點
        int leftDistance = findDistanceK(node.left, target, k, result);
        if (leftDistance != -1) {
            if (leftDistance + 1 == k) {
                result.add(node.val);
            } else {
                collectNodesAtDistance(node.right, k - leftDistance - 2, result);
            }
            return leftDistance + 1;
        }

        // 在右子樹中搜尋目標節點
        int rightDistance = findDistanceK(node.right, target, k, result);
        if (rightDistance != -1) {
            if (rightDistance + 1 == k) {
                result.add(node.val);
            } else {
                collectNodesAtDistance(node.left, k - rightDistance - 2, result);
            }
            return rightDistance + 1;
        }

        return -1;
    }

    private static void collectNodesAtDistance(TreeNode node, int distance, List<Integer> result) {
        if (node == null || distance < 0) {
            return;
        }

        if (distance == 0) {
            result.add(node.val);
            return;
        }

        collectNodesAtDistance(node.left, distance - 1, result);
        collectNodesAtDistance(node.right, distance - 1, result);
    }

    /**
     * 10. 計算樹中所有路徑的長度總和
     */
    public static int sumOfAllPathLengths(TreeNode root) {
        if (root == null) {
            return 0;
        }

        return sumPathLengthsFromNode(root, 0);
    }

    private static int sumPathLengthsFromNode(TreeNode node, int currentDepth) {
        if (node == null) {
            return 0;
        }

        int sum = currentDepth; // 從根到當前節點的路徑長度

        sum += sumPathLengthsFromNode(node.left, currentDepth + 1);
        sum += sumPathLengthsFromNode(node.right, currentDepth + 1);

        return sum;
    }

    /**
     * 輔助方法：建立測試樹
     */
    public static TreeNode createSampleTree() {
        /*
         * 建立測試樹：
         *       5
         *      / \
         *     4   8
         *    /   / \
         *   11  13  4
         *  / \      \
         * 7   2      1
         */
        TreeNode root = new TreeNode(5);
        root.left = new TreeNode(4);
        root.right = new TreeNode(8);
        root.left.left = new TreeNode(11);
        root.right.left = new TreeNode(13);
        root.right.right = new TreeNode(4);
        root.left.left.left = new TreeNode(7);
        root.left.left.right = new TreeNode(2);
        root.right.right.right = new TreeNode(1);

        return root;
    }

    /**
     * 建立路徑測試樹
     */
    public static TreeNode createPathTestTree() {
        /*
         * 建立路徑測試樹：
         *       1
         *      / \
         *     2   3
         *    / \
         *   4   5
         */
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);

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
        System.out.println("=== 樹的路徑問題 ===");

        // 建立測試樹
        TreeNode tree = createSampleTree();
        printTree(tree, "測試樹結構：");

        // 1. 所有根到葉的路徑
        System.out.println("\n1. 所有根到葉的路徑：");
        List<List<Integer>> allPaths = allRootToLeafPaths(tree);
        for (int i = 0; i < allPaths.size(); i++) {
            System.out.printf("路徑%d：%s\n", i + 1, allPaths.get(i));
        }

        // 2. 路徑和測試
        System.out.println("\n2. 路徑和測試：");
        int[] targetSums = {22, 26, 18, 13};

        for (int target : targetSums) {
            boolean hasPath = hasPathSum(tree, target);
            List<List<Integer>> paths = pathSum(tree, target);

            System.out.printf("目標和 %d：", target);
            System.out.printf("存在路徑=%s，", hasPath);
            System.out.printf("路徑數量=%d", paths.size());

            if (!paths.isEmpty()) {
                System.out.printf("，路徑=%s", paths);
            }
            System.out.println();
        }

        // 3. 任意路徑和計數
        System.out.println("\n3. 任意路徑和計數：");
        int[] countTargets = {8, 4, 5};

        for (int target : countTargets) {
            int count = pathSumCount(tree, target);
            System.out.printf("和為 %d 的路徑總數：%d\n", target, count);
        }

        // 4. 最大路徑和
        System.out.println("\n4. 最大路徑和：");
        int maxPath = maxPathSum(tree);
        System.out.printf("樹中的最大路徑和：%d\n", maxPath);

        // 5. 樹的直徑
        System.out.println("\n5. 樹的直徑：");
        int diameter = diameterOfBinaryTree(tree);
        System.out.printf("樹的直徑（最長路徑的節點數-1）：%d\n", diameter);

        // 6. 建立路徑測試樹進行更多測試
        System.out.println("\n" + "=".repeat(50));
        TreeNode pathTree = createPathTestTree();
        printTree(pathTree, "路徑測試樹：");

        // 7. 兩點間路徑
        System.out.println("\n6. 兩點間路徑：");
        int[][] nodePairs = {{4, 5}, {4, 3}, {2, 3}, {1, 5}};

        for (int[] pair : nodePairs) {
            int start = pair[0], end = pair[1];
            List<Integer> path = findPath(pathTree, start, end);
            System.out.printf("從節點%d到節點%d的路徑：%s\n", start, end, path);
        }

        // 8. 距離根節點為k的節點
        System.out.println("\n7. 距離根節點為k的節點：");
        for (int k = 0; k <= 3; k++) {
            List<Integer> nodesAtK = findNodesAtDistanceK(pathTree, k);
            System.out.printf("距離根節點%d的節點：%s\n", k, nodesAtK);
        }

        // 9. 距離目標節點為k的節點
        System.out.println("\n8. 距離目標節點為k的節點：");
        TreeNode targetNode = pathTree.left; // 節點2
        for (int k = 0; k <= 2; k++) {
            List<Integer> nodesAtK = distanceK(pathTree, targetNode, k);
            System.out.printf("距離節點2為%d的節點：%s\n", k, nodesAtK);
        }

        // 10. 路徑長度統計
        System.out.println("\n9. 路徑長度統計：");
        int totalPathLength = sumOfAllPathLengths(pathTree);
        System.out.printf("所有路徑長度總和：%d\n", totalPathLength);

        // 11. 特殊情況測試
        System.out.println("\n10. 特殊情況測試：");

        // 空樹
        TreeNode emptyTree = null;
        System.out.printf("空樹的所有路徑：%s\n", allRootToLeafPaths(emptyTree));
        System.out.printf("空樹是否有和為0的路徑：%s\n", hasPathSum(emptyTree, 0));
        System.out.printf("空樹的最大路徑和：%d\n", maxPathSum(emptyTree));
        System.out.printf("空樹的直徑：%d\n", diameterOfBinaryTree(emptyTree));

        // 單節點樹
        TreeNode singleNode = new TreeNode(10);
        System.out.printf("單節點樹的所有路徑：%s\n", allRootToLeafPaths(singleNode));
        System.out.printf("單節點樹是否有和為10的路徑：%s\n", hasPathSum(singleNode, 10));
        System.out.printf("單節點樹的最大路徑和：%d\n", maxPathSum(singleNode));
        System.out.printf("單節點樹的直徑：%d\n", diameterOfBinaryTree(singleNode));

        // 負值節點測試
        System.out.println("\n11. 負值節點測試：");
        TreeNode negativeTree = new TreeNode(-3);
        negativeTree.left = new TreeNode(3);
        negativeTree.right = new TreeNode(-2);
        negativeTree.left.left = new TreeNode(1);
        negativeTree.left.right = new TreeNode(-1);

        printTree(negativeTree, "含負值的樹：");
        System.out.printf("負值樹的最大路徑和：%d\n", maxPathSum(negativeTree));
        System.out.printf("負值樹是否有和為0的路徑：%s\n", hasPathSum(negativeTree, 0));

        List<List<Integer>> negativePaths = pathSum(negativeTree, 0);
        System.out.printf("負值樹中和為0的路徑：%s\n", negativePaths);
    }
}
