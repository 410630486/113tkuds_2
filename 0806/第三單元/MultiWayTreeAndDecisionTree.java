
import java.util.*;

public class MultiWayTreeAndDecisionTree {

    /**
     * 多路樹節點（N叉樹）
     */
    static class NaryTreeNode {

        int val;
        List<NaryTreeNode> children;

        NaryTreeNode(int val) {
            this.val = val;
            this.children = new ArrayList<>();
        }

        public void addChild(NaryTreeNode child) {
            children.add(child);
        }
    }

    /**
     * B樹節點（簡化版）
     */
    static class BTreeNode {

        List<Integer> keys;
        List<BTreeNode> children;
        boolean isLeaf;
        int degree; // 最小度數

        BTreeNode(int degree, boolean isLeaf) {
            this.degree = degree;
            this.isLeaf = isLeaf;
            this.keys = new ArrayList<>();
            this.children = new ArrayList<>();
        }
    }

    /**
     * 決策樹節點
     */
    static class DecisionNode {

        String feature;      // 特徵名稱
        Object threshold;    // 閾值
        String decision;     // 決策結果（葉節點）
        DecisionNode left;   // 左子樹（小於等於閾值）
        DecisionNode right;  // 右子樹（大於閾值）
        boolean isLeaf;      // 是否為葉節點

        // 建構子：內部節點
        DecisionNode(String feature, Object threshold) {
            this.feature = feature;
            this.threshold = threshold;
            this.isLeaf = false;
        }

        // 建構子：葉節點
        DecisionNode(String decision) {
            this.decision = decision;
            this.isLeaf = true;
        }
    }

    /**
     * 資料記錄（用於決策樹）
     */
    static class DataRecord {

        Map<String, Object> features;
        String label;

        DataRecord() {
            this.features = new HashMap<>();
        }

        public void addFeature(String name, Object value) {
            features.put(name, value);
        }

        public Object getFeature(String name) {
            return features.get(name);
        }
    }

    /**
     * 1. N叉樹操作
     */
    static class NaryTreeOperations {

        /**
         * 計算N叉樹的最大深度
         */
        public static int maxDepth(NaryTreeNode root) {
            if (root == null) {
                return 0;
            }

            int maxChildDepth = 0;
            for (NaryTreeNode child : root.children) {
                maxChildDepth = Math.max(maxChildDepth, maxDepth(child));
            }

            return 1 + maxChildDepth;
        }

        /**
         * N叉樹的前序遍歷
         */
        public static List<Integer> preorder(NaryTreeNode root) {
            List<Integer> result = new ArrayList<>();
            preorderHelper(root, result);
            return result;
        }

        private static void preorderHelper(NaryTreeNode node, List<Integer> result) {
            if (node == null) {
                return;
            }

            result.add(node.val);
            for (NaryTreeNode child : node.children) {
                preorderHelper(child, result);
            }
        }

        /**
         * N叉樹的後序遍歷
         */
        public static List<Integer> postorder(NaryTreeNode root) {
            List<Integer> result = new ArrayList<>();
            postorderHelper(root, result);
            return result;
        }

        private static void postorderHelper(NaryTreeNode node, List<Integer> result) {
            if (node == null) {
                return;
            }

            for (NaryTreeNode child : node.children) {
                postorderHelper(child, result);
            }
            result.add(node.val);
        }

        /**
         * N叉樹的層序遍歷
         */
        public static List<List<Integer>> levelOrder(NaryTreeNode root) {
            List<List<Integer>> result = new ArrayList<>();
            if (root == null) {
                return result;
            }

            Queue<NaryTreeNode> queue = new LinkedList<>();
            queue.offer(root);

            while (!queue.isEmpty()) {
                int levelSize = queue.size();
                List<Integer> currentLevel = new ArrayList<>();

                for (int i = 0; i < levelSize; i++) {
                    NaryTreeNode node = queue.poll();
                    currentLevel.add(node.val);

                    for (NaryTreeNode child : node.children) {
                        queue.offer(child);
                    }
                }

                result.add(currentLevel);
            }

            return result;
        }

        /**
         * 計算N叉樹的節點數
         */
        public static int countNodes(NaryTreeNode root) {
            if (root == null) {
                return 0;
            }

            int count = 1; // 當前節點
            for (NaryTreeNode child : root.children) {
                count += countNodes(child);
            }

            return count;
        }

        /**
         * 計算N叉樹的葉節點數
         */
        public static int countLeaves(NaryTreeNode root) {
            if (root == null) {
                return 0;
            }

            if (root.children.isEmpty()) {
                return 1; // 葉節點
            }

            int leafCount = 0;
            for (NaryTreeNode child : root.children) {
                leafCount += countLeaves(child);
            }

            return leafCount;
        }
    }

    /**
     * 2. 簡化版B樹操作
     */
    static class SimpleBTree {

        private BTreeNode root;
        private int degree;

        public SimpleBTree(int degree) {
            this.degree = degree;
            this.root = new BTreeNode(degree, true);
        }

        /**
         * 搜尋鍵值
         */
        public boolean search(int key) {
            return search(root, key);
        }

        private boolean search(BTreeNode node, int key) {
            int i = 0;
            while (i < node.keys.size() && key > node.keys.get(i)) {
                i++;
            }

            if (i < node.keys.size() && key == node.keys.get(i)) {
                return true; // 找到
            }

            if (node.isLeaf) {
                return false; // 葉節點且未找到
            }

            return search(node.children.get(i), key);
        }

        /**
         * 簡化的插入操作（不處理分裂）
         */
        public void insertSimple(int key) {
            if (root.keys.size() < 2 * degree - 1) {
                insertNonFull(root, key);
            }
        }

        private void insertNonFull(BTreeNode node, int key) {
            int i = node.keys.size() - 1;

            if (node.isLeaf) {
                // 插入到葉節點
                node.keys.add(0); // 先增加一個位置
                while (i >= 0 && key < node.keys.get(i)) {
                    node.keys.set(i + 1, node.keys.get(i));
                    i--;
                }
                node.keys.set(i + 1, key);
            }
        }

        /**
         * 中序遍歷B樹
         */
        public List<Integer> inorderTraversal() {
            List<Integer> result = new ArrayList<>();
            inorderTraversal(root, result);
            return result;
        }

        private void inorderTraversal(BTreeNode node, List<Integer> result) {
            int i;
            for (i = 0; i < node.keys.size(); i++) {
                if (!node.isLeaf) {
                    inorderTraversal(node.children.get(i), result);
                }
                result.add(node.keys.get(i));
            }

            if (!node.isLeaf) {
                inorderTraversal(node.children.get(i), result);
            }
        }
    }

    /**
     * 3. 決策樹實作
     */
    static class DecisionTree {

        private DecisionNode root;

        /**
         * 建立簡單的決策樹（硬編碼範例）
         */
        public void buildSampleTree() {
            // 建立一個簡單的決策樹：判斷是否批准貸款
            root = new DecisionNode("income", 50000);

            // 收入 <= 50000
            root.left = new DecisionNode("age", 25);
            root.left.left = new DecisionNode("拒絕"); // 年齡 <= 25
            root.left.right = new DecisionNode("credit_score", 600);
            root.left.right.left = new DecisionNode("拒絕"); // 信用分數 <= 600
            root.left.right.right = new DecisionNode("批准"); // 信用分數 > 600

            // 收入 > 50000
            root.right = new DecisionNode("credit_score", 500);
            root.right.left = new DecisionNode("拒絕"); // 信用分數 <= 500
            root.right.right = new DecisionNode("批准"); // 信用分數 > 500
        }

        /**
         * 預測決策
         */
        public String predict(DataRecord record) {
            return predict(root, record);
        }

        private String predict(DecisionNode node, DataRecord record) {
            if (node.isLeaf) {
                return node.decision;
            }

            Object featureValue = record.getFeature(node.feature);

            if (featureValue instanceof Number) {
                Number numValue = (Number) featureValue;
                Number threshold = (Number) node.threshold;

                if (numValue.doubleValue() <= threshold.doubleValue()) {
                    return predict(node.left, record);
                } else {
                    return predict(node.right, record);
                }
            }

            return "未知";
        }

        /**
         * 顯示決策樹結構
         */
        public void printTree() {
            printTree(root, "", true);
        }

        private void printTree(DecisionNode node, String prefix, boolean isLast) {
            if (node == null) {
                return;
            }

            String nodeInfo;
            if (node.isLeaf) {
                nodeInfo = "決策: " + node.decision;
            } else {
                nodeInfo = node.feature + " <= " + node.threshold + " ?";
            }

            System.out.println(prefix + (isLast ? "└── " : "├── ") + nodeInfo);

            if (!node.isLeaf) {
                if (node.right != null) {
                    printTree(node.right, prefix + (isLast ? "    " : "│   "), node.left == null);
                }
                if (node.left != null) {
                    printTree(node.left, prefix + (isLast ? "    " : "│   "), true);
                }
            }
        }

        /**
         * 計算決策樹的深度
         */
        public int getDepth() {
            return getDepth(root);
        }

        private int getDepth(DecisionNode node) {
            if (node == null || node.isLeaf) {
                return 0;
            }

            return 1 + Math.max(getDepth(node.left), getDepth(node.right));
        }

        /**
         * 計算葉節點數量
         */
        public int countLeaves() {
            return countLeaves(root);
        }

        private int countLeaves(DecisionNode node) {
            if (node == null) {
                return 0;
            }

            if (node.isLeaf) {
                return 1;
            }

            return countLeaves(node.left) + countLeaves(node.right);
        }

        /**
         * 取得所有決策路徑
         */
        public List<String> getAllPaths() {
            List<String> paths = new ArrayList<>();
            getAllPaths(root, "", paths);
            return paths;
        }

        private void getAllPaths(DecisionNode node, String currentPath, List<String> paths) {
            if (node == null) {
                return;
            }

            if (node.isLeaf) {
                paths.add(currentPath + " → " + node.decision);
                return;
            }

            String condition = node.feature + " <= " + node.threshold;
            getAllPaths(node.left, currentPath + (currentPath.isEmpty() ? "" : " AND ") + condition, paths);

            String negCondition = node.feature + " > " + node.threshold;
            getAllPaths(node.right, currentPath + (currentPath.isEmpty() ? "" : " AND ") + negCondition, paths);
        }
    }

    /**
     * 輔助方法：顯示N叉樹結構
     */
    public static void printNaryTree(NaryTreeNode root, String title) {
        System.out.println(title);
        if (root == null) {
            System.out.println("空樹");
            return;
        }

        printNaryTreeHelper(root, "", true);
    }

    private static void printNaryTreeHelper(NaryTreeNode node, String prefix, boolean isLast) {
        if (node == null) {
            return;
        }

        System.out.println(prefix + (isLast ? "└── " : "├── ") + node.val);

        for (int i = 0; i < node.children.size(); i++) {
            boolean isLastChild = (i == node.children.size() - 1);
            printNaryTreeHelper(node.children.get(i),
                    prefix + (isLast ? "    " : "│   "),
                    isLastChild);
        }
    }

    /**
     * 建立範例N叉樹
     */
    public static NaryTreeNode createSampleNaryTree() {
        /*
         * 建立N叉樹：
         *       1
         *     / | \
         *    2  3  4
         *   /|  |  |\
         *  5 6  7  8 9
         *      /|
         *     10 11
         */
        NaryTreeNode root = new NaryTreeNode(1);

        NaryTreeNode node2 = new NaryTreeNode(2);
        NaryTreeNode node3 = new NaryTreeNode(3);
        NaryTreeNode node4 = new NaryTreeNode(4);

        root.addChild(node2);
        root.addChild(node3);
        root.addChild(node4);

        node2.addChild(new NaryTreeNode(5));
        node2.addChild(new NaryTreeNode(6));

        NaryTreeNode node7 = new NaryTreeNode(7);
        node3.addChild(node7);

        node4.addChild(new NaryTreeNode(8));
        node4.addChild(new NaryTreeNode(9));

        node7.addChild(new NaryTreeNode(10));
        node7.addChild(new NaryTreeNode(11));

        return root;
    }

    public static void main(String[] args) {
        System.out.println("=== 多路樹與決策樹 ===");

        // 1. N叉樹測試
        System.out.println("1. N叉樹操作：");
        NaryTreeNode naryTree = createSampleNaryTree();
        printNaryTree(naryTree, "範例N叉樹：");

        System.out.printf("最大深度：%d\n", NaryTreeOperations.maxDepth(naryTree));
        System.out.printf("節點總數：%d\n", NaryTreeOperations.countNodes(naryTree));
        System.out.printf("葉節點數：%d\n", NaryTreeOperations.countLeaves(naryTree));

        System.out.printf("前序遍歷：%s\n", NaryTreeOperations.preorder(naryTree));
        System.out.printf("後序遍歷：%s\n", NaryTreeOperations.postorder(naryTree));

        System.out.println("層序遍歷：");
        List<List<Integer>> levelOrder = NaryTreeOperations.levelOrder(naryTree);
        for (int i = 0; i < levelOrder.size(); i++) {
            System.out.printf("  第%d層：%s\n", i + 1, levelOrder.get(i));
        }

        // 2. 簡化版B樹測試
        System.out.println("\n" + "=".repeat(50));
        System.out.println("2. 簡化版B樹操作：");

        SimpleBTree btree = new SimpleBTree(3); // 度數為3的B樹
        int[] keys = {10, 20, 5, 6, 12, 30, 7, 17};

        System.out.printf("插入鍵值：%s\n", Arrays.toString(keys));
        for (int key : keys) {
            btree.insertSimple(key);
        }

        System.out.printf("中序遍歷：%s\n", btree.inorderTraversal());

        // 搜尋測試
        int[] searchKeys = {6, 12, 15, 30};
        System.out.println("搜尋測試：");
        for (int key : searchKeys) {
            boolean found = btree.search(key);
            System.out.printf("  搜尋 %d：%s\n", key, found ? "找到" : "未找到");
        }

        // 3. 決策樹測試
        System.out.println("\n" + "=".repeat(50));
        System.out.println("3. 決策樹操作：");

        DecisionTree decisionTree = new DecisionTree();
        decisionTree.buildSampleTree();

        System.out.println("貸款審批決策樹：");
        decisionTree.printTree();

        System.out.printf("決策樹深度：%d\n", decisionTree.getDepth());
        System.out.printf("葉節點數：%d\n", decisionTree.countLeaves());

        System.out.println("\n所有決策路徑：");
        List<String> paths = decisionTree.getAllPaths();
        for (int i = 0; i < paths.size(); i++) {
            System.out.printf("  路徑%d：%s\n", i + 1, paths.get(i));
        }

        // 4. 決策樹預測測試
        System.out.println("\n4. 決策樹預測測試：");

        // 測試案例
        DataRecord[] testCases = {
            new DataRecord(),
            new DataRecord(),
            new DataRecord(),
            new DataRecord(),
            new DataRecord()
        };

        // 案例1：低收入，年輕，低信用分數
        testCases[0].addFeature("income", 40000);
        testCases[0].addFeature("age", 22);
        testCases[0].addFeature("credit_score", 550);

        // 案例2：低收入，年長，高信用分數
        testCases[1].addFeature("income", 45000);
        testCases[1].addFeature("age", 35);
        testCases[1].addFeature("credit_score", 700);

        // 案例3：高收入，低信用分數
        testCases[2].addFeature("income", 80000);
        testCases[2].addFeature("age", 30);
        testCases[2].addFeature("credit_score", 450);

        // 案例4：高收入，高信用分數
        testCases[3].addFeature("income", 75000);
        testCases[3].addFeature("age", 28);
        testCases[3].addFeature("credit_score", 720);

        // 案例5：邊界情況
        testCases[4].addFeature("income", 50000);
        testCases[4].addFeature("age", 25);
        testCases[4].addFeature("credit_score", 600);

        String[] caseDescriptions = {
            "低收入(40K), 年輕(22), 低信用(550)",
            "低收入(45K), 年長(35), 高信用(700)",
            "高收入(80K), 年長(30), 低信用(450)",
            "高收入(75K), 年長(28), 高信用(720)",
            "邊界情況(50K, 25, 600)"
        };

        for (int i = 0; i < testCases.length; i++) {
            String prediction = decisionTree.predict(testCases[i]);
            System.out.printf("案例%d：%s → %s\n", i + 1, caseDescriptions[i], prediction);
        }

        // 5. 樹的類型比較
        System.out.println("\n" + "=".repeat(50));
        System.out.println("5. 各種樹結構比較：");

        System.out.println("樹類型特點比較：");
        System.out.println("  二元樹：每個節點最多2個子節點，適合搜尋和排序");
        System.out.println("  N叉樹：每個節點可有多個子節點，適合層次結構表示");
        System.out.println("  B樹：平衡的多路搜尋樹，適合磁碟存儲系統");
        System.out.println("  決策樹：用於分類和決策，每個內部節點表示判斷條件");

        System.out.println("\n應用場景：");
        System.out.println("  二元搜尋樹：資料庫索引、表達式解析");
        System.out.println("  N叉樹：檔案系統、組織架構圖");
        System.out.println("  B樹：資料庫管理系統、檔案系統");
        System.out.println("  決策樹：機器學習、專家系統、醫學診斷");

        // 6. 性能分析
        System.out.println("\n6. 性能分析：");

        System.out.printf("N叉樹節點數：%d，深度：%d\n",
                NaryTreeOperations.countNodes(naryTree),
                NaryTreeOperations.maxDepth(naryTree));

        System.out.printf("決策樹葉節點數：%d，深度：%d\n",
                decisionTree.countLeaves(),
                decisionTree.getDepth());

        // 7. 特殊情況測試
        System.out.println("\n7. 特殊情況測試：");

        // 空N叉樹
        NaryTreeNode emptyNary = null;
        System.out.printf("空N叉樹深度：%d\n", NaryTreeOperations.maxDepth(emptyNary));
        System.out.printf("空N叉樹節點數：%d\n", NaryTreeOperations.countNodes(emptyNary));

        // 單節點N叉樹
        NaryTreeNode singleNary = new NaryTreeNode(42);
        System.out.printf("單節點N叉樹深度：%d\n", NaryTreeOperations.maxDepth(singleNary));
        System.out.printf("單節點N叉樹節點數：%d\n", NaryTreeOperations.countNodes(singleNary));
        System.out.printf("單節點N叉樹葉節點數：%d\n", NaryTreeOperations.countLeaves(singleNary));

        // 線性N叉樹（每個節點只有一個子節點）
        NaryTreeNode linearNary = new NaryTreeNode(1);
        NaryTreeNode current = linearNary;
        for (int i = 2; i <= 5; i++) {
            NaryTreeNode child = new NaryTreeNode(i);
            current.addChild(child);
            current = child;
        }

        System.out.printf("線性N叉樹深度：%d\n", NaryTreeOperations.maxDepth(linearNary));
        System.out.printf("線性N叉樹節點數：%d\n", NaryTreeOperations.countNodes(linearNary));
        System.out.printf("線性N叉樹葉節點數：%d\n", NaryTreeOperations.countLeaves(linearNary));
    }
}
