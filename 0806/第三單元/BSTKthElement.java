
import java.util.*;

public class BSTKthElement {

    /**
     * 二元搜尋樹節點
     */
    static class BSTNode {

        int val;
        int count;  // 以此節點為根的子樹中的節點數量
        BSTNode left;
        BSTNode right;

        BSTNode(int val) {
            this.val = val;
            this.count = 1;
        }
    }

    /**
     * 增強型二元搜尋樹，支援高效的第k小/大元素查詢
     */
    static class EnhancedBST {

        private BSTNode root;

        /**
         * 插入節點
         */
        public void insert(int val) {
            root = insertHelper(root, val);
        }

        private BSTNode insertHelper(BSTNode node, int val) {
            if (node == null) {
                return new BSTNode(val);
            }

            // 更新節點計數
            node.count++;

            if (val < node.val) {
                node.left = insertHelper(node.left, val);
            } else if (val > node.val) {
                node.right = insertHelper(node.right, val);
            }

            return node;
        }

        /**
         * 刪除節點
         */
        public boolean delete(int val) {
            if (!search(val)) {
                return false;
            }
            root = deleteHelper(root, val);
            return true;
        }

        private BSTNode deleteHelper(BSTNode node, int val) {
            if (node == null) {
                return null;
            }

            if (val < node.val) {
                node.left = deleteHelper(node.left, val);
                if (node.left != null || node.right != null || node.val != val) {
                    node.count--;
                }
            } else if (val > node.val) {
                node.right = deleteHelper(node.right, val);
                if (node.left != null || node.right != null || node.val != val) {
                    node.count--;
                }
            } else {
                // 找到要刪除的節點
                if (node.left == null) {
                    return node.right;
                } else if (node.right == null) {
                    return node.left;
                } else {
                    // 有兩個子節點，找到右子樹的最小值替換
                    BSTNode minNode = findMin(node.right);
                    node.val = minNode.val;
                    node.right = deleteHelper(node.right, minNode.val);
                    node.count--;
                }
            }

            return node;
        }

        /**
         * 搜尋節點
         */
        public boolean search(int val) {
            return searchHelper(root, val);
        }

        private boolean searchHelper(BSTNode node, int val) {
            if (node == null) {
                return false;
            }

            if (val == node.val) {
                return true;
            } else if (val < node.val) {
                return searchHelper(node.left, val);
            } else {
                return searchHelper(node.right, val);
            }
        }

        /**
         * 找到第k小的元素（k從1開始） 時間複雜度：O(log n)
         */
        public Integer kthSmallest(int k) {
            if (k <= 0 || root == null || k > root.count) {
                return null;
            }

            return kthSmallestHelper(root, k);
        }

        private Integer kthSmallestHelper(BSTNode node, int k) {
            if (node == null) {
                return null;
            }

            int leftCount = (node.left != null) ? node.left.count : 0;

            if (k == leftCount + 1) {
                // 當前節點就是第k小的節點
                return node.val;
            } else if (k <= leftCount) {
                // 第k小的節點在左子樹中
                return kthSmallestHelper(node.left, k);
            } else {
                // 第k小的節點在右子樹中
                return kthSmallestHelper(node.right, k - leftCount - 1);
            }
        }

        /**
         * 找到第k大的元素（k從1開始） 時間複雜度：O(log n)
         */
        public Integer kthLargest(int k) {
            if (k <= 0 || root == null || k > root.count) {
                return null;
            }

            return kthLargestHelper(root, k);
        }

        private Integer kthLargestHelper(BSTNode node, int k) {
            if (node == null) {
                return null;
            }

            int rightCount = (node.right != null) ? node.right.count : 0;

            if (k == rightCount + 1) {
                // 當前節點就是第k大的節點
                return node.val;
            } else if (k <= rightCount) {
                // 第k大的節點在右子樹中
                return kthLargestHelper(node.right, k);
            } else {
                // 第k大的節點在左子樹中
                return kthLargestHelper(node.left, k - rightCount - 1);
            }
        }

        /**
         * 找到某個值的排名（從1開始，即它是第幾小的元素）
         */
        public Integer getRank(int val) {
            return getRankHelper(root, val);
        }

        private Integer getRankHelper(BSTNode node, int val) {
            if (node == null) {
                return null;
            }

            if (val == node.val) {
                int leftCount = (node.left != null) ? node.left.count : 0;
                return leftCount + 1;
            } else if (val < node.val) {
                return getRankHelper(node.left, val);
            } else {
                Integer rightRank = getRankHelper(node.right, val);
                if (rightRank == null) {
                    return null;
                }
                int leftCount = (node.left != null) ? node.left.count : 0;
                return leftCount + 1 + rightRank;
            }
        }

        /**
         * 查詢範圍內有多少個元素小於等於給定值
         */
        public int countLessOrEqual(int val) {
            return countLessOrEqualHelper(root, val);
        }

        private int countLessOrEqualHelper(BSTNode node, int val) {
            if (node == null) {
                return 0;
            }

            if (node.val <= val) {
                // 當前節點及其左子樹都小於等於val
                int leftCount = (node.left != null) ? node.left.count : 0;
                return leftCount + 1 + countLessOrEqualHelper(node.right, val);
            } else {
                // 只有左子樹可能有小於等於val的節點
                return countLessOrEqualHelper(node.left, val);
            }
        }

        /**
         * 查詢範圍內有多少個元素大於等於給定值
         */
        public int countGreaterOrEqual(int val) {
            return countGreaterOrEqualHelper(root, val);
        }

        private int countGreaterOrEqualHelper(BSTNode node, int val) {
            if (node == null) {
                return 0;
            }

            if (node.val >= val) {
                // 當前節點及其右子樹都大於等於val
                int rightCount = (node.right != null) ? node.right.count : 0;
                return rightCount + 1 + countGreaterOrEqualHelper(node.left, val);
            } else {
                // 只有右子樹可能有大於等於val的節點
                return countGreaterOrEqualHelper(node.right, val);
            }
        }

        /**
         * 找到範圍[low, high]內第k小的元素
         */
        public Integer kthSmallestInRange(int low, int high, int k) {
            List<Integer> rangeElements = new ArrayList<>();
            collectRangeElements(root, low, high, rangeElements);

            if (k <= 0 || k > rangeElements.size()) {
                return null;
            }

            return rangeElements.get(k - 1);
        }

        private void collectRangeElements(BSTNode node, int low, int high, List<Integer> result) {
            if (node == null) {
                return;
            }

            if (node.val >= low && node.val <= high) {
                collectRangeElements(node.left, low, high, result);
                result.add(node.val);
                collectRangeElements(node.right, low, high, result);
            } else if (node.val < low) {
                collectRangeElements(node.right, low, high, result);
            } else {
                collectRangeElements(node.left, low, high, result);
            }
        }

        /**
         * 取得所有元素（中序遍歷）
         */
        public List<Integer> getAllElements() {
            List<Integer> result = new ArrayList<>();
            inorderTraversal(root, result);
            return result;
        }

        private void inorderTraversal(BSTNode node, List<Integer> result) {
            if (node == null) {
                return;
            }

            inorderTraversal(node.left, result);
            result.add(node.val);
            inorderTraversal(node.right, result);
        }

        /**
         * 找到最小值節點
         */
        private BSTNode findMin(BSTNode node) {
            while (node.left != null) {
                node = node.left;
            }
            return node;
        }

        /**
         * 取得樹的總節點數
         */
        public int size() {
            return (root != null) ? root.count : 0;
        }

        /**
         * 檢查樹是否為空
         */
        public boolean isEmpty() {
            return root == null;
        }

        /**
         * 顯示樹的結構
         */
        public void printTree() {
            if (root == null) {
                System.out.println("空樹");
                return;
            }

            printTreeHelper(root, "", true);
        }

        private void printTreeHelper(BSTNode node, String prefix, boolean isLast) {
            if (node == null) {
                return;
            }

            System.out.println(prefix + (isLast ? "└── " : "├── ")
                    + node.val + " (count: " + node.count + ")");

            if (node.left != null || node.right != null) {
                if (node.right != null) {
                    printTreeHelper(node.right, prefix + (isLast ? "    " : "│   "), node.left == null);
                }
                if (node.left != null) {
                    printTreeHelper(node.left, prefix + (isLast ? "    " : "│   "), true);
                }
            }
        }

        /**
         * 顯示統計資訊
         */
        public void printStatistics() {
            if (isEmpty()) {
                System.out.println("樹為空，無統計資訊");
                return;
            }

            List<Integer> elements = getAllElements();
            System.out.printf("節點總數：%d\n", size());
            System.out.printf("最小值：%d\n", elements.get(0));
            System.out.printf("最大值：%d\n", elements.get(elements.size() - 1));
            System.out.printf("中位數：%s\n", getMedian());
        }

        /**
         * 取得中位數
         */
        public String getMedian() {
            if (isEmpty()) {
                return "無";
            }

            int n = size();
            if (n % 2 == 1) {
                // 奇數個元素，返回中間的元素
                return kthSmallest((n + 1) / 2).toString();
            } else {
                // 偶數個元素，返回中間兩個元素的平均值
                Integer mid1 = kthSmallest(n / 2);
                Integer mid2 = kthSmallest(n / 2 + 1);
                return String.format("%.1f", (mid1 + mid2) / 2.0);
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("=== 二元搜尋樹第k小/大元素查詢 ===");

        // 建立BST並插入數據
        EnhancedBST bst = new EnhancedBST();
        int[] values = {50, 30, 70, 20, 40, 60, 80, 10, 25, 35, 45, 55, 65, 75, 90};

        System.out.println("1. 插入數據：");
        for (int val : values) {
            bst.insert(val);
            System.out.printf("%d ", val);
        }
        System.out.println();

        System.out.println("\n2. 建立的BST結構：");
        bst.printTree();

        System.out.println("\n3. 基本統計：");
        bst.printStatistics();

        System.out.println("\n4. 有序序列：");
        List<Integer> elements = bst.getAllElements();
        System.out.printf("所有元素：%s\n", elements);

        // 第k小元素查詢
        System.out.println("\n" + "=".repeat(50));
        System.out.println("5. 第k小元素查詢：");

        for (int k = 1; k <= Math.min(10, bst.size()); k++) {
            Integer kthSmall = bst.kthSmallest(k);
            System.out.printf("第%2d小：%2d\n", k, kthSmall);
        }

        // 第k大元素查詢
        System.out.println("\n6. 第k大元素查詢：");

        for (int k = 1; k <= Math.min(10, bst.size()); k++) {
            Integer kthLarge = bst.kthLargest(k);
            System.out.printf("第%2d大：%2d\n", k, kthLarge);
        }

        // 排名查詢
        System.out.println("\n7. 排名查詢：");
        int[] queryValues = {25, 40, 55, 70, 100};

        for (int val : queryValues) {
            Integer rank = bst.getRank(val);
            if (rank != null) {
                System.out.printf("值 %d 的排名：第 %d 小\n", val, rank);
            } else {
                System.out.printf("值 %d 不存在於樹中\n", val);
            }
        }

        // 範圍計數查詢
        System.out.println("\n8. 範圍計數查詢：");
        int[] thresholds = {30, 50, 70};

        for (int threshold : thresholds) {
            int lessOrEqual = bst.countLessOrEqual(threshold);
            int greaterOrEqual = bst.countGreaterOrEqual(threshold);

            System.out.printf("≤ %d 的元素數量：%d\n", threshold, lessOrEqual);
            System.out.printf("≥ %d 的元素數量：%d\n", threshold, greaterOrEqual);
            System.out.println();
        }

        // 範圍內第k小查詢
        System.out.println("9. 範圍內第k小查詢：");
        int[][] ranges = {{30, 70}, {40, 80}, {20, 50}};

        for (int[] range : ranges) {
            int low = range[0], high = range[1];
            System.out.printf("\n範圍 [%d, %d] 內的元素：\n", low, high);

            for (int k = 1; k <= 5; k++) {
                Integer kthInRange = bst.kthSmallestInRange(low, high, k);
                if (kthInRange != null) {
                    System.out.printf("  第%d小：%d\n", k, kthInRange);
                } else {
                    System.out.printf("  第%d小：不存在\n", k);
                    break;
                }
            }
        }

        // 動態操作測試
        System.out.println("\n" + "=".repeat(50));
        System.out.println("10. 動態操作測試：");

        System.out.println("刪除前的第5小和第5大元素：");
        System.out.printf("第5小：%d\n", bst.kthSmallest(5));
        System.out.printf("第5大：%d\n", bst.kthLargest(5));

        // 刪除一些元素
        int[] toDelete = {30, 70};
        System.out.printf("\n刪除元素：%s\n", Arrays.toString(toDelete));

        for (int val : toDelete) {
            if (bst.delete(val)) {
                System.out.printf("成功刪除 %d\n", val);
            } else {
                System.out.printf("刪除 %d 失敗（不存在）\n", val);
            }
        }

        System.out.println("\n刪除後的樹結構：");
        bst.printTree();

        System.out.println("\n刪除後的統計：");
        bst.printStatistics();

        System.out.println("\n刪除後的第5小和第5大元素：");
        System.out.printf("第5小：%d\n", bst.kthSmallest(5));
        System.out.printf("第5大：%d\n", bst.kthLargest(5));

        // 特殊情況測試
        System.out.println("\n11. 特殊情況測試：");

        // 查詢超出範圍的k值
        System.out.printf("第100小：%s\n", bst.kthSmallest(100));
        System.out.printf("第0小：%s\n", bst.kthSmallest(0));

        // 中位數測試
        System.out.printf("中位數：%s\n", bst.getMedian());

        // 邊界值測試
        System.out.printf("第1小（最小值）：%d\n", bst.kthSmallest(1));
        System.out.printf("第1大（最大值）：%d\n", bst.kthLargest(1));

        int lastIndex = bst.size();
        System.out.printf("第%d小（最大值）：%d\n", lastIndex, bst.kthSmallest(lastIndex));
        System.out.printf("第%d大（最小值）：%d\n", lastIndex, bst.kthLargest(lastIndex));
    }
}
