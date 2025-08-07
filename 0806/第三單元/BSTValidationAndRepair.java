
import java.util.*;

public class BSTValidationAndRepair {

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
     * 1. 驗證是否為有效的二元搜尋樹
     */
    public static boolean isValidBST(TreeNode root) {
        return isValidBSTHelper(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    private static boolean isValidBSTHelper(TreeNode node, long minVal, long maxVal) {
        if (node == null) {
            return true;
        }

        // 檢查當前節點值是否在有效範圍內
        if (node.val <= minVal || node.val >= maxVal) {
            return false;
        }

        // 遞迴檢查左右子樹
        return isValidBSTHelper(node.left, minVal, node.val)
                && isValidBSTHelper(node.right, node.val, maxVal);
    }

    /**
     * 2. 使用中序遍歷驗證BST（另一種方法）
     */
    public static boolean isValidBSTInorder(TreeNode root) {
        List<Integer> inorderValues = new ArrayList<>();
        inorderTraversal(root, inorderValues);

        // 檢查中序遍歷結果是否嚴格遞增
        for (int i = 1; i < inorderValues.size(); i++) {
            if (inorderValues.get(i) <= inorderValues.get(i - 1)) {
                return false;
            }
        }

        return true;
    }

    private static void inorderTraversal(TreeNode node, List<Integer> result) {
        if (node == null) {
            return;
        }

        inorderTraversal(node.left, result);
        result.add(node.val);
        inorderTraversal(node.right, result);
    }

    /**
     * 3. 找出BST中違反規則的節點
     */
    public static List<TreeNode> findInvalidNodes(TreeNode root) {
        List<TreeNode> invalidNodes = new ArrayList<>();
        findInvalidNodesHelper(root, Long.MIN_VALUE, Long.MAX_VALUE, invalidNodes);
        return invalidNodes;
    }

    private static void findInvalidNodesHelper(TreeNode node, long minVal, long maxVal,
            List<TreeNode> invalidNodes) {
        if (node == null) {
            return;
        }

        // 檢查當前節點是否違反BST規則
        if (node.val <= minVal || node.val >= maxVal) {
            invalidNodes.add(node);
        }

        // 繼續檢查子樹
        findInvalidNodesHelper(node.left, minVal, Math.min(maxVal, node.val), invalidNodes);
        findInvalidNodesHelper(node.right, Math.max(minVal, node.val), maxVal, invalidNodes);
    }

    /**
     * 4. 修復BST：找出交換的兩個節點並修復
     */
    static class BSTRepair {

        private TreeNode firstViolation = null;
        private TreeNode secondViolation = null;
        private TreeNode prevNode = null;

        public void recoverBST(TreeNode root) {
            // 找到違反順序的兩個節點
            findViolations(root);

            // 交換兩個節點的值
            if (firstViolation != null && secondViolation != null) {
                int temp = firstViolation.val;
                firstViolation.val = secondViolation.val;
                secondViolation.val = temp;
            }
        }

        private void findViolations(TreeNode node) {
            if (node == null) {
                return;
            }

            findViolations(node.left);

            // 檢查當前節點與前一個節點的關係
            if (prevNode != null && prevNode.val > node.val) {
                if (firstViolation == null) {
                    firstViolation = prevNode;
                }
                secondViolation = node;
            }

            prevNode = node;
            findViolations(node.right);
        }

        public TreeNode getFirstViolation() {
            return firstViolation;
        }

        public TreeNode getSecondViolation() {
            return secondViolation;
        }
    }

    /**
     * 5. 將任意二元樹轉換為BST（保持結構不變）
     */
    public static void convertToBST(TreeNode root) {
        // 收集所有節點值
        List<Integer> values = new ArrayList<>();
        collectValues(root, values);

        // 排序
        Collections.sort(values);

        // 使用中序遍歷重新分配值
        Iterator<Integer> iterator = values.iterator();
        assignSortedValues(root, iterator);
    }

    private static void collectValues(TreeNode node, List<Integer> values) {
        if (node == null) {
            return;
        }

        collectValues(node.left, values);
        values.add(node.val);
        collectValues(node.right, values);
    }

    private static void assignSortedValues(TreeNode node, Iterator<Integer> iterator) {
        if (node == null) {
            return;
        }

        assignSortedValues(node.left, iterator);
        node.val = iterator.next();
        assignSortedValues(node.right, iterator);
    }

    /**
     * 6. 從有序陣列建立平衡BST
     */
    public static TreeNode createBalancedBST(int[] sortedArray) {
        if (sortedArray == null || sortedArray.length == 0) {
            return null;
        }

        return createBalancedBSTHelper(sortedArray, 0, sortedArray.length - 1);
    }

    private static TreeNode createBalancedBSTHelper(int[] array, int start, int end) {
        if (start > end) {
            return null;
        }

        int mid = start + (end - start) / 2;
        TreeNode node = new TreeNode(array[mid]);

        node.left = createBalancedBSTHelper(array, start, mid - 1);
        node.right = createBalancedBSTHelper(array, mid + 1, end);

        return node;
    }

    /**
     * 7. 檢查BST的平衡性
     */
    public static boolean isBalanced(TreeNode root) {
        return checkBalance(root) != -1;
    }

    private static int checkBalance(TreeNode node) {
        if (node == null) {
            return 0;
        }

        int leftHeight = checkBalance(node.left);
        if (leftHeight == -1) {
            return -1; // 左子樹不平衡
        }

        int rightHeight = checkBalance(node.right);
        if (rightHeight == -1) {
            return -1; // 右子樹不平衡
        }

        // 檢查當前節點是否平衡
        if (Math.abs(leftHeight - rightHeight) > 1) {
            return -1; // 不平衡
        }

        return Math.max(leftHeight, rightHeight) + 1;
    }

    /**
     * 8. 計算樹的平衡因子
     */
    public static Map<TreeNode, Integer> calculateBalanceFactors(TreeNode root) {
        Map<TreeNode, Integer> balanceFactors = new HashMap<>();
        calculateHeight(root, balanceFactors);
        return balanceFactors;
    }

    private static int calculateHeight(TreeNode node, Map<TreeNode, Integer> balanceFactors) {
        if (node == null) {
            return 0;
        }

        int leftHeight = calculateHeight(node.left, balanceFactors);
        int rightHeight = calculateHeight(node.right, balanceFactors);

        // 計算平衡因子（左子樹高度 - 右子樹高度）
        int balanceFactor = leftHeight - rightHeight;
        balanceFactors.put(node, balanceFactor);

        return Math.max(leftHeight, rightHeight) + 1;
    }

    /**
     * 9. BST的完整性檢查
     */
    public static BSTValidationResult validateBSTComprehensive(TreeNode root) {
        BSTValidationResult result = new BSTValidationResult();

        // 基本BST驗證
        result.isValidBST = isValidBST(root);

        // 平衡性檢查
        result.isBalanced = isBalanced(root);

        // 找出違規節點
        result.invalidNodes = findInvalidNodes(root);

        // 計算統計資訊
        if (root != null) {
            result.nodeCount = countNodes(root);
            result.height = calculateTreeHeight(root);
            result.leafCount = countLeafNodes(root);

            List<Integer> inorderValues = new ArrayList<>();
            inorderTraversal(root, inorderValues);
            result.inorderSequence = inorderValues;

            if (!inorderValues.isEmpty()) {
                result.minValue = inorderValues.get(0);
                result.maxValue = inorderValues.get(inorderValues.size() - 1);
            }
        }

        return result;
    }

    /**
     * BST驗證結果類別
     */
    static class BSTValidationResult {

        boolean isValidBST;
        boolean isBalanced;
        List<TreeNode> invalidNodes;
        int nodeCount;
        int height;
        int leafCount;
        List<Integer> inorderSequence;
        Integer minValue;
        Integer maxValue;

        public BSTValidationResult() {
            this.invalidNodes = new ArrayList<>();
            this.inorderSequence = new ArrayList<>();
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("BST驗證結果：\n");
            sb.append("  是否為有效BST：").append(isValidBST).append("\n");
            sb.append("  是否平衡：").append(isBalanced).append("\n");
            sb.append("  節點總數：").append(nodeCount).append("\n");
            sb.append("  樹的高度：").append(height).append("\n");
            sb.append("  葉節點數：").append(leafCount).append("\n");
            sb.append("  違規節點數：").append(invalidNodes.size()).append("\n");
            sb.append("  最小值：").append(minValue).append("\n");
            sb.append("  最大值：").append(maxValue).append("\n");
            sb.append("  中序序列：").append(inorderSequence);
            return sb.toString();
        }
    }

    /**
     * 輔助方法：計算節點數量
     */
    private static int countNodes(TreeNode node) {
        if (node == null) {
            return 0;
        }
        return 1 + countNodes(node.left) + countNodes(node.right);
    }

    /**
     * 輔助方法：計算樹的高度
     */
    private static int calculateTreeHeight(TreeNode node) {
        if (node == null) {
            return 0;
        }
        return 1 + Math.max(calculateTreeHeight(node.left), calculateTreeHeight(node.right));
    }

    /**
     * 輔助方法：計算葉節點數量
     */
    private static int countLeafNodes(TreeNode node) {
        if (node == null) {
            return 0;
        }
        if (node.left == null && node.right == null) {
            return 1;
        }
        return countLeafNodes(node.left) + countLeafNodes(node.right);
    }

    /**
     * 輔助方法：複製樹
     */
    public static TreeNode copyTree(TreeNode root) {
        if (root == null) {
            return null;
        }

        TreeNode newNode = new TreeNode(root.val);
        newNode.left = copyTree(root.left);
        newNode.right = copyTree(root.right);

        return newNode;
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

    /**
     * 建立有效的BST
     */
    public static TreeNode createValidBST() {
        /*
         * 建立有效的BST：
         *       50
         *      /  \
         *     30   70
         *    / \   / \
         *   20 40 60 80
         */
        TreeNode root = new TreeNode(50);
        root.left = new TreeNode(30);
        root.right = new TreeNode(70);
        root.left.left = new TreeNode(20);
        root.left.right = new TreeNode(40);
        root.right.left = new TreeNode(60);
        root.right.right = new TreeNode(80);

        return root;
    }

    /**
     * 建立無效的BST（有兩個節點交換了）
     */
    public static TreeNode createInvalidBST() {
        /*
         * 建立無效的BST（30和70交換了位置）：
         *       50
         *      /  \
         *     70   30
         *    / \   / \
         *   20 40 60 80
         */
        TreeNode root = new TreeNode(50);
        root.left = new TreeNode(70);  // 應該是30
        root.right = new TreeNode(30); // 應該是70
        root.left.left = new TreeNode(20);
        root.left.right = new TreeNode(40);
        root.right.left = new TreeNode(60);
        root.right.right = new TreeNode(80);

        return root;
    }

    /**
     * 建立不平衡的BST
     */
    public static TreeNode createUnbalancedBST() {
        /*
         * 建立不平衡的BST：
         *   10
         *  /  \
         * 5   20
         *    /  \
         *   15  30
         *      /  \
         *     25  40
         *        /
         *       35
         */
        TreeNode root = new TreeNode(10);
        root.left = new TreeNode(5);
        root.right = new TreeNode(20);
        root.right.left = new TreeNode(15);
        root.right.right = new TreeNode(30);
        root.right.right.left = new TreeNode(25);
        root.right.right.right = new TreeNode(40);
        root.right.right.right.left = new TreeNode(35);

        return root;
    }

    public static void main(String[] args) {
        System.out.println("=== 二元搜尋樹驗證與修復 ===");

        // 1. 測試有效的BST
        System.out.println("1. 有效BST測試：");
        TreeNode validBST = createValidBST();
        printTree(validBST, "有效的BST：");

        BSTValidationResult validResult = validateBSTComprehensive(validBST);
        System.out.println(validResult);

        // 2. 測試無效的BST
        System.out.println("\n" + "=".repeat(50));
        System.out.println("2. 無效BST測試：");
        TreeNode invalidBST = createInvalidBST();
        printTree(invalidBST, "無效的BST（30和70位置錯誤）：");

        BSTValidationResult invalidResult = validateBSTComprehensive(invalidBST);
        System.out.println(invalidResult);

        // 3. 修復無效的BST
        System.out.println("\n3. BST修復測試：");
        TreeNode toBeFix = copyTree(invalidBST);

        BSTRepair repair = new BSTRepair();
        repair.recoverBST(toBeFix);

        System.out.println("修復前後對比：");
        System.out.printf("修復前中序：%s\n",
                validateBSTComprehensive(invalidBST).inorderSequence);
        System.out.printf("修復後中序：%s\n",
                validateBSTComprehensive(toBeFix).inorderSequence);

        printTree(toBeFix, "修復後的BST：");

        BSTValidationResult repairedResult = validateBSTComprehensive(toBeFix);
        System.out.printf("修復後是否有效：%s\n", repairedResult.isValidBST);

        // 4. 從有序陣列建立平衡BST
        System.out.println("\n4. 從有序陣列建立平衡BST：");
        int[] sortedArray = {10, 20, 30, 40, 50, 60, 70, 80, 90};
        TreeNode balancedBST = createBalancedBST(sortedArray);

        printTree(balancedBST, "從有序陣列建立的平衡BST：");

        BSTValidationResult balancedResult = validateBSTComprehensive(balancedBST);
        System.out.println(balancedResult);

        // 5. 將任意二元樹轉換為BST
        System.out.println("\n5. 任意二元樹轉換為BST：");

        // 建立一個任意的二元樹
        TreeNode arbitraryTree = new TreeNode(10);
        arbitraryTree.left = new TreeNode(30);
        arbitraryTree.right = new TreeNode(15);
        arbitraryTree.left.left = new TreeNode(20);
        arbitraryTree.left.right = new TreeNode(5);

        printTree(arbitraryTree, "轉換前的任意二元樹：");
        System.out.printf("轉換前中序：%s\n",
                validateBSTComprehensive(arbitraryTree).inorderSequence);
        System.out.printf("轉換前是否為BST：%s\n",
                validateBSTComprehensive(arbitraryTree).isValidBST);

        // 轉換為BST
        convertToBST(arbitraryTree);

        printTree(arbitraryTree, "轉換後的BST：");
        BSTValidationResult convertedResult = validateBSTComprehensive(arbitraryTree);
        System.out.printf("轉換後是否為BST：%s\n", convertedResult.isValidBST);
        System.out.printf("轉換後中序：%s\n", convertedResult.inorderSequence);

        // 6. 平衡性分析
        System.out.println("\n6. 平衡性分析：");
        TreeNode unbalancedBST = createUnbalancedBST();
        printTree(unbalancedBST, "不平衡的BST：");

        BSTValidationResult unbalancedResult = validateBSTComprehensive(unbalancedBST);
        System.out.println(unbalancedResult);

        // 計算平衡因子
        Map<TreeNode, Integer> balanceFactors = calculateBalanceFactors(unbalancedBST);
        System.out.println("\n各節點的平衡因子：");
        List<Integer> inorderSeq = unbalancedResult.inorderSequence;
        balanceFactors.forEach((node, factor) -> {
            System.out.printf("節點 %d：平衡因子 = %d\n", node.val, factor);
        });

        // 7. 驗證方法比較
        System.out.println("\n7. 驗證方法比較：");
        TreeNode[] testTrees = {validBST, invalidBST, balancedBST, unbalancedBST};
        String[] treeNames = {"有效BST", "無效BST", "平衡BST", "不平衡BST"};

        for (int i = 0; i < testTrees.length; i++) {
            TreeNode tree = testTrees[i];
            String name = treeNames[i];

            boolean isValid1 = isValidBST(tree);
            boolean isValid2 = isValidBSTInorder(tree);
            boolean balanced = isBalanced(tree);

            System.out.printf("%s - 範圍檢查：%s, 中序檢查：%s, 平衡性：%s\n",
                    name, isValid1, isValid2, balanced);
        }

        // 8. 特殊情況測試
        System.out.println("\n8. 特殊情況測試：");

        // 空樹
        TreeNode emptyTree = null;
        System.out.printf("空樹是否為有效BST：%s\n", isValidBST(emptyTree));
        System.out.printf("空樹是否平衡：%s\n", isBalanced(emptyTree));

        // 單節點樹
        TreeNode singleNode = new TreeNode(42);
        System.out.printf("單節點樹是否為有效BST：%s\n", isValidBST(singleNode));
        System.out.printf("單節點樹是否平衡：%s\n", isBalanced(singleNode));

        // 重複值測試
        TreeNode duplicateTree = new TreeNode(5);
        duplicateTree.left = new TreeNode(5);
        duplicateTree.right = new TreeNode(5);
        System.out.printf("有重複值的樹是否為有效BST：%s\n", isValidBST(duplicateTree));
    }
}
