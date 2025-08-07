
import java.util.*;

public class BSTRangeQuerySystem {

    /**
     * 二元搜尋樹節點
     */
    static class BSTNode {

        int val;
        BSTNode left;
        BSTNode right;

        BSTNode(int val) {
            this.val = val;
        }
    }

    /**
     * 二元搜尋樹類別
     */
    static class BST {

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

            if (val < node.val) {
                node.left = insertHelper(node.left, val);
            } else if (val > node.val) {
                node.right = insertHelper(node.right, val);
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
         * 範圍查詢：找出在[low, high]範圍內的所有節點
         */
        public List<Integer> rangeQuery(int low, int high) {
            List<Integer> result = new ArrayList<>();
            rangeQueryHelper(root, low, high, result);
            return result;
        }

        private void rangeQueryHelper(BSTNode node, int low, int high, List<Integer> result) {
            if (node == null) {
                return;
            }

            // 如果當前節點在範圍內，加入結果
            if (node.val >= low && node.val <= high) {
                result.add(node.val);
            }

            // 根據BST性質決定遞迴方向
            if (node.val > low) {
                rangeQueryHelper(node.left, low, high, result);
            }
            if (node.val < high) {
                rangeQueryHelper(node.right, low, high, result);
            }
        }

        /**
         * 計算範圍內節點的數量
         */
        public int countInRange(int low, int high) {
            return countInRangeHelper(root, low, high);
        }

        private int countInRangeHelper(BSTNode node, int low, int high) {
            if (node == null) {
                return 0;
            }

            int count = 0;

            // 如果當前節點在範圍內，計數加1
            if (node.val >= low && node.val <= high) {
                count = 1;
            }

            // 根據BST性質決定遞迴方向
            if (node.val > low) {
                count += countInRangeHelper(node.left, low, high);
            }
            if (node.val < high) {
                count += countInRangeHelper(node.right, low, high);
            }

            return count;
        }

        /**
         * 計算範圍內節點值的總和
         */
        public int rangeSumQuery(int low, int high) {
            return rangeSumHelper(root, low, high);
        }

        private int rangeSumHelper(BSTNode node, int low, int high) {
            if (node == null) {
                return 0;
            }

            int sum = 0;

            // 如果當前節點在範圍內，加入總和
            if (node.val >= low && node.val <= high) {
                sum += node.val;
            }

            // 根據BST性質決定遞迴方向
            if (node.val > low) {
                sum += rangeSumHelper(node.left, low, high);
            }
            if (node.val < high) {
                sum += rangeSumHelper(node.right, low, high);
            }

            return sum;
        }

        /**
         * 找出最接近目標值的節點
         */
        public int closestValue(int target) {
            if (root == null) {
                throw new RuntimeException("樹為空");
            }

            return closestValueHelper(root, target, root.val);
        }

        private int closestValueHelper(BSTNode node, int target, int closest) {
            if (node == null) {
                return closest;
            }

            // 更新最接近的值
            if (Math.abs(node.val - target) < Math.abs(closest - target)) {
                closest = node.val;
            }

            // 根據BST性質決定搜尋方向
            if (target < node.val) {
                return closestValueHelper(node.left, target, closest);
            } else {
                return closestValueHelper(node.right, target, closest);
            }
        }

        /**
         * 找出範圍內的最小值
         */
        public Integer rangeMin(int low, int high) {
            return rangeMinHelper(root, low, high);
        }

        private Integer rangeMinHelper(BSTNode node, int low, int high) {
            if (node == null) {
                return null;
            }

            // 如果當前節點值小於low，只需要搜尋右子樹
            if (node.val < low) {
                return rangeMinHelper(node.right, low, high);
            }

            // 如果當前節點值大於high，只需要搜尋左子樹
            if (node.val > high) {
                return rangeMinHelper(node.left, low, high);
            }

            // 當前節點在範圍內，它可能是最小值，但還需要檢查左子樹
            Integer leftMin = rangeMinHelper(node.left, low, high);
            if (leftMin != null) {
                return leftMin;
            } else {
                return node.val;
            }
        }

        /**
         * 找出範圍內的最大值
         */
        public Integer rangeMax(int low, int high) {
            return rangeMaxHelper(root, low, high);
        }

        private Integer rangeMaxHelper(BSTNode node, int low, int high) {
            if (node == null) {
                return null;
            }

            // 如果當前節點值小於low，只需要搜尋右子樹
            if (node.val < low) {
                return rangeMaxHelper(node.right, low, high);
            }

            // 如果當前節點值大於high，只需要搜尋左子樹
            if (node.val > high) {
                return rangeMaxHelper(node.left, low, high);
            }

            // 當前節點在範圍內，它可能是最大值，但還需要檢查右子樹
            Integer rightMax = rangeMaxHelper(node.right, low, high);
            if (rightMax != null) {
                return rightMax;
            } else {
                return node.val;
            }
        }

        /**
         * 找出小於目標值的最大節點（前驅）
         */
        public Integer predecessor(int target) {
            return predecessorHelper(root, target);
        }

        private Integer predecessorHelper(BSTNode node, int target) {
            if (node == null) {
                return null;
            }

            if (node.val >= target) {
                return predecessorHelper(node.left, target);
            } else {
                Integer rightResult = predecessorHelper(node.right, target);
                return (rightResult != null) ? rightResult : node.val;
            }
        }

        /**
         * 找出大於目標值的最小節點（後繼）
         */
        public Integer successor(int target) {
            return successorHelper(root, target);
        }

        private Integer successorHelper(BSTNode node, int target) {
            if (node == null) {
                return null;
            }

            if (node.val <= target) {
                return successorHelper(node.right, target);
            } else {
                Integer leftResult = successorHelper(node.left, target);
                return (leftResult != null) ? leftResult : node.val;
            }
        }

        /**
         * 計算範圍內節點值的平均值
         */
        public double rangeAverage(int low, int high) {
            int sum = rangeSumQuery(low, high);
            int count = countInRange(low, high);

            if (count == 0) {
                return 0.0;
            }

            return (double) sum / count;
        }

        /**
         * 找出第k小的元素（k從1開始）
         */
        public Integer kthSmallest(int k) {
            List<Integer> inorder = new ArrayList<>();
            inorderTraversal(root, inorder);

            if (k <= 0 || k > inorder.size()) {
                return null;
            }

            return inorder.get(k - 1);
        }

        /**
         * 找出第k大的元素（k從1開始）
         */
        public Integer kthLargest(int k) {
            List<Integer> inorder = new ArrayList<>();
            inorderTraversal(root, inorder);

            if (k <= 0 || k > inorder.size()) {
                return null;
            }

            return inorder.get(inorder.size() - k);
        }

        /**
         * 中序遍歷
         */
        private void inorderTraversal(BSTNode node, List<Integer> result) {
            if (node == null) {
                return;
            }

            inorderTraversal(node.left, result);
            result.add(node.val);
            inorderTraversal(node.right, result);
        }

        /**
         * 取得所有節點值（有序）
         */
        public List<Integer> getAllValues() {
            List<Integer> result = new ArrayList<>();
            inorderTraversal(root, result);
            return result;
        }

        /**
         * 層序遍歷顯示樹結構
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

            System.out.println(prefix + (isLast ? "└── " : "├── ") + node.val);

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
         * 統計資訊
         */
        public void printStatistics() {
            List<Integer> values = getAllValues();

            if (values.isEmpty()) {
                System.out.println("樹為空，無統計資訊");
                return;
            }

            System.out.printf("節點總數：%d\n", values.size());
            System.out.printf("最小值：%d\n", values.get(0));
            System.out.printf("最大值：%d\n", values.get(values.size() - 1));

            int sum = values.stream().mapToInt(Integer::intValue).sum();
            System.out.printf("總和：%d\n", sum);
            System.out.printf("平均值：%.2f\n", (double) sum / values.size());
        }
    }

    public static void main(String[] args) {
        System.out.println("=== 二元搜尋樹範圍查詢系統 ===");

        // 建立BST並插入數據
        BST bst = new BST();
        int[] values = {50, 30, 70, 20, 40, 60, 80, 10, 25, 35, 45};

        System.out.println("1. 插入數據：");
        for (int val : values) {
            bst.insert(val);
            System.out.printf("插入 %d ", val);
        }
        System.out.println();

        System.out.println("\n2. 建立的BST結構：");
        bst.printTree();

        System.out.println("\n3. 基本統計：");
        bst.printStatistics();

        System.out.println("\n4. 有序序列：");
        System.out.printf("中序遍歷：%s\n", bst.getAllValues());

        // 範圍查詢測試
        System.out.println("\n" + "=".repeat(50));
        System.out.println("5. 範圍查詢測試：");

        int[][] testRanges = {{25, 50}, {30, 60}, {15, 35}, {70, 90}};

        for (int[] range : testRanges) {
            int low = range[0], high = range[1];

            System.out.printf("\n範圍 [%d, %d]：\n", low, high);

            List<Integer> rangeValues = bst.rangeQuery(low, high);
            System.out.printf("  範圍內的值：%s\n", rangeValues);
            System.out.printf("  節點數量：%d\n", bst.countInRange(low, high));
            System.out.printf("  總和：%d\n", bst.rangeSumQuery(low, high));
            System.out.printf("  平均值：%.2f\n", bst.rangeAverage(low, high));

            Integer min = bst.rangeMin(low, high);
            Integer max = bst.rangeMax(low, high);
            System.out.printf("  範圍內最小值：%s\n", min != null ? min : "無");
            System.out.printf("  範圍內最大值：%s\n", max != null ? max : "無");
        }

        // 最接近值查詢
        System.out.println("\n6. 最接近值查詢：");
        int[] targets = {22, 37, 55, 75, 100};

        for (int target : targets) {
            int closest = bst.closestValue(target);
            System.out.printf("最接近 %d 的值：%d (差距: %d)\n",
                    target, closest, Math.abs(target - closest));
        }

        // 前驅和後繼查詢
        System.out.println("\n7. 前驅和後繼查詢：");
        int[] testValues = {25, 40, 55, 70};

        for (int val : testValues) {
            Integer pred = bst.predecessor(val);
            Integer succ = bst.successor(val);

            System.out.printf("值 %d - 前驅：%s, 後繼：%s\n",
                    val,
                    pred != null ? pred : "無",
                    succ != null ? succ : "無");
        }

        // 第k小/大元素查詢
        System.out.println("\n8. 第k小/大元素查詢：");
        int totalNodes = bst.getAllValues().size();

        for (int k = 1; k <= Math.min(5, totalNodes); k++) {
            Integer kthSmall = bst.kthSmallest(k);
            Integer kthLarge = bst.kthLargest(k);

            System.out.printf("第%d小：%s, 第%d大：%s\n",
                    k, kthSmall, k, kthLarge);
        }

        // 搜尋測試
        System.out.println("\n9. 搜尋測試：");
        int[] searchValues = {30, 45, 55, 90};

        for (int val : searchValues) {
            boolean found = bst.search(val);
            System.out.printf("搜尋 %d：%s\n", val, found ? "找到" : "未找到");
        }

        // 極端情況測試
        System.out.println("\n10. 極端情況測試：");

        // 範圍查詢不存在的範圍
        List<Integer> emptyRange = bst.rangeQuery(100, 200);
        System.out.printf("範圍 [100, 200] 查詢結果：%s\n", emptyRange);

        // 單點範圍查詢
        List<Integer> singlePoint = bst.rangeQuery(50, 50);
        System.out.printf("範圍 [50, 50] 查詢結果：%s\n", singlePoint);

        // 最接近邊界值的查詢
        System.out.printf("最接近 0 的值：%d\n", bst.closestValue(0));
        System.out.printf("最接近 1000 的值：%d\n", bst.closestValue(1000));
    }
}
