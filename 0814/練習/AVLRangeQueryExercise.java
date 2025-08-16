
import java.util.*;

/**
 * AVL 樹範圍查詢練習 練習目標：實作範圍查詢功能，找出指定範圍內的所有元素
 */
class AVLRangeNode {

    int data;
    int height;
    AVLRangeNode left, right;

    public AVLRangeNode(int data) {
        this.data = data;
        this.height = 1;
        this.left = this.right = null;
    }

    public int getBalance() {
        int leftHeight = (left != null) ? left.height : 0;
        int rightHeight = (right != null) ? right.height : 0;
        return leftHeight - rightHeight;
    }

    public void updateHeight() {
        int leftHeight = (left != null) ? left.height : 0;
        int rightHeight = (right != null) ? right.height : 0;
        this.height = Math.max(leftHeight, rightHeight) + 1;
    }
}

public class AVLRangeQueryExercise {

    private AVLRangeNode root;

    // 插入操作
    public void insert(int data) {
        root = insertNode(root, data);
    }

    private AVLRangeNode insertNode(AVLRangeNode node, int data) {
        if (node == null) {
            return new AVLRangeNode(data);
        }

        if (data < node.data) {
            node.left = insertNode(node.left, data);
        } else if (data > node.data) {
            node.right = insertNode(node.right, data);
        } else {
            return node;
        }

        node.updateHeight();
        return balanceNode(node);
    }

    // TODO: 實作範圍查詢功能
    // 提示：利用 BST 性質進行剪枝優化
    public List<Integer> rangeQuery(int min, int max) {
        List<Integer> result = new ArrayList<>();
        // TODO: 實作範圍查詢邏輯
        rangeQueryHelper(root, min, max, result);
        return result;
    }

    private void rangeQueryHelper(AVLRangeNode node, int min, int max, List<Integer> result) {
        // TODO: 實作範圍查詢的遞迴邏輯
        if (node == null) {
            return;
        }

        // 利用 BST 性質進行剪枝
        // 如果當前節點值大於 min，可能在左子樹中有符合條件的節點
        if (node.data > min) {
            rangeQueryHelper(node.left, min, max, result);
        }

        // 如果當前節點在範圍內，加入結果
        if (node.data >= min && node.data <= max) {
            result.add(node.data);
        }

        // 如果當前節點值小於 max，可能在右子樹中有符合條件的節點
        if (node.data < max) {
            rangeQueryHelper(node.right, min, max, result);
        }
    }

    // TODO: 實作計算範圍內元素數量（不需儲存具體值）
    public int countInRange(int min, int max) {
        // 提示：類似範圍查詢，但只計算數量
        return countInRangeHelper(root, min, max);
    }

    private int countInRangeHelper(AVLRangeNode node, int min, int max) {
        // TODO: 實作範圍計數邏輯
        if (node == null) {
            return 0;
        }

        int count = 0;

        // 檢查左子樹
        if (node.data > min) {
            count += countInRangeHelper(node.left, min, max);
        }

        // 檢查當前節點
        if (node.data >= min && node.data <= max) {
            count++;
        }

        // 檢查右子樹
        if (node.data < max) {
            count += countInRangeHelper(node.right, min, max);
        }

        return count;
    }

    // TODO: 實作找到範圍內的最小值
    public Integer findMinInRange(int min, int max) {
        // 提示：找到 >= min 的最小值，且 <= max
        return findMinInRangeHelper(root, min, max);
    }

    private Integer findMinInRangeHelper(AVLRangeNode node, int min, int max) {
        // TODO: 實作範圍內最小值查找
        if (node == null) {
            return null;
        }

        // 如果當前節點小於 min，答案必然在右子樹
        if (node.data < min) {
            return findMinInRangeHelper(node.right, min, max);
        }

        // 如果當前節點大於 max，答案可能在左子樹
        if (node.data > max) {
            return findMinInRangeHelper(node.left, min, max);
        }

        // 當前節點在範圍內，檢查左子樹是否有更小的值
        Integer leftMin = findMinInRangeHelper(node.left, min, max);
        return (leftMin != null) ? leftMin : node.data;
    }

    // TODO: 實作找到範圍內的最大值
    public Integer findMaxInRange(int min, int max) {
        // 提示：找到 <= max 的最大值，且 >= min
        return findMaxInRangeHelper(root, min, max);
    }

    private Integer findMaxInRangeHelper(AVLRangeNode node, int min, int max) {
        // TODO: 實作範圍內最大值查找
        if (node == null) {
            return null;
        }

        // 如果當前節點大於 max，答案必然在左子樹
        if (node.data > max) {
            return findMaxInRangeHelper(node.left, min, max);
        }

        // 如果當前節點小於 min，答案可能在右子樹
        if (node.data < min) {
            return findMaxInRangeHelper(node.right, min, max);
        }

        // 當前節點在範圍內，檢查右子樹是否有更大的值
        Integer rightMax = findMaxInRangeHelper(node.right, min, max);
        return (rightMax != null) ? rightMax : node.data;
    }

    // TODO: 實作範圍內前 K 小的元素
    public List<Integer> rangeTopK(int min, int max, int k) {
        // 提示：可以先範圍查詢，再取前 k 個；或優化為提前終止
        List<Integer> result = new ArrayList<>();
        rangeTopKHelper(root, min, max, k, result);
        return result;
    }

    private void rangeTopKHelper(AVLRangeNode node, int min, int max, int k, List<Integer> result) {
        // TODO: 實作範圍內前 K 小元素查找
        if (node == null || result.size() >= k) {
            return;
        }

        // 中序遍歷保證按升序訪問
        // 先訪問左子樹
        if (node.data > min) {
            rangeTopKHelper(node.left, min, max, k, result);
        }

        // 訪問當前節點
        if (result.size() < k && node.data >= min && node.data <= max) {
            result.add(node.data);
        }

        // 再訪問右子樹
        if (result.size() < k && node.data < max) {
            rangeTopKHelper(node.right, min, max, k, result);
        }
    }

    // 平衡操作
    private AVLRangeNode balanceNode(AVLRangeNode node) {
        int balance = node.getBalance();

        if (balance > 1 && node.left.getBalance() >= 0) {
            return rightRotate(node);
        }

        if (balance > 1 && node.left.getBalance() < 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        if (balance < -1 && node.right.getBalance() <= 0) {
            return leftRotate(node);
        }

        if (balance < -1 && node.right.getBalance() > 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    private AVLRangeNode rightRotate(AVLRangeNode y) {
        AVLRangeNode x = y.left;
        AVLRangeNode T2 = x.right;

        x.right = y;
        y.left = T2;

        y.updateHeight();
        x.updateHeight();

        return x;
    }

    private AVLRangeNode leftRotate(AVLRangeNode x) {
        AVLRangeNode y = x.right;
        AVLRangeNode T2 = y.left;

        y.left = x;
        x.right = T2;

        x.updateHeight();
        y.updateHeight();

        return y;
    }

    // 中序遍歷
    public void inorderTraversal() {
        System.out.print("中序遍歷: ");
        inorder(root);
        System.out.println();
    }

    private void inorder(AVLRangeNode node) {
        if (node != null) {
            inorder(node.left);
            System.out.print(node.data + " ");
            inorder(node.right);
        }
    }

    // 樹形顯示
    public void printTree() {
        System.out.println("樹結構:");
        if (root != null) {
            printCompactVerticalTree();
        } else {
            System.out.println("空樹");
        }
    }

    private void printCompactVerticalTree() {
        int height = getHeight(root);
        int width = (int) Math.pow(2, height) + 5;
        String[][] grid = new String[height * 2][width];

        for (int i = 0; i < height * 2; i++) {
            for (int j = 0; j < width; j++) {
                grid[i][j] = " ";
            }
        }

        fillGrid(grid, root, 0, width / 2 - 1);

        for (int i = 0; i < height * 2; i++) {
            StringBuilder line = new StringBuilder();
            for (int j = 0; j < width; j++) {
                line.append(grid[i][j]);
            }
            String result = line.toString().replaceAll("\\s+$", "");
            if (!result.isEmpty()) {
                System.out.println(result);
            }
        }
    }

    private void fillGrid(String[][] grid, AVLRangeNode node, int row, int col) {
        if (node == null || row >= grid.length) {
            return;
        }

        String nodeStr = String.valueOf(node.data);
        for (int i = 0; i < nodeStr.length() && col + i < grid[0].length; i++) {
            grid[row][col + i] = String.valueOf(nodeStr.charAt(i));
        }

        int spacing = Math.max(1, (int) Math.pow(2, getHeight(root) - row - 2));

        if (node.left != null) {
            grid[row + 1][col - 1] = "/";
            fillGrid(grid, node.left, row + 2, col - spacing);
        }

        if (node.right != null) {
            grid[row + 1][col + 1] = "\\";
            fillGrid(grid, node.right, row + 2, col + spacing);
        }
    }

    private int getHeight(AVLRangeNode node) {
        return (node != null) ? node.height : 0;
    }

    public static void main(String[] args) {
        System.out.println("=== AVL 樹範圍查詢練習 ===");

        AVLRangeQueryExercise tree = new AVLRangeQueryExercise();

        // 建立測試樹
        System.out.println("\n--- 建立測試樹 ---");
        int[] values = {50, 30, 70, 20, 40, 60, 80, 10, 25, 35, 45, 55, 65, 75, 90};
        for (int value : values) {
            tree.insert(value);
        }

        tree.printTree();
        tree.inorderTraversal();

        // 測試範圍查詢
        System.out.println("\n--- 範圍查詢測試 ---");

        // 測試不同範圍
        int[][] ranges = {{25, 55}, {40, 70}, {10, 30}, {60, 100}, {5, 15}};

        for (int[] range : ranges) {
            int min = range[0], max = range[1];

            System.out.println("範圍 [" + min + ", " + max + "]:");

            // 範圍查詢
            List<Integer> rangeResult = tree.rangeQuery(min, max);
            System.out.println("  範圍內元素: " + rangeResult);

            // 計算數量
            int count = tree.countInRange(min, max);
            System.out.println("  元素數量: " + count);

            // 範圍內最小值
            Integer minInRange = tree.findMinInRange(min, max);
            System.out.println("  範圍內最小值: " + minInRange);

            // 範圍內最大值
            Integer maxInRange = tree.findMaxInRange(min, max);
            System.out.println("  範圍內最大值: " + maxInRange);

            // 範圍內前 3 小的元素
            List<Integer> top3 = tree.rangeTopK(min, max, 3);
            System.out.println("  範圍內前 3 小: " + top3);

            System.out.println();
        }

        // 性能測試
        System.out.println("--- 性能測試 ---");

        // 建立大樹
        AVLRangeQueryExercise bigTree = new AVLRangeQueryExercise();
        System.out.println("建立包含 1000 個元素的樹...");

        Random random = new Random(42); // 固定種子確保可重現
        for (int i = 0; i < 1000; i++) {
            bigTree.insert(random.nextInt(10000));
        }

        // 測試查詢性能
        long startTime = System.nanoTime();
        List<Integer> largeRangeResult = bigTree.rangeQuery(2000, 8000);
        long endTime = System.nanoTime();

        System.out.println("大範圍查詢 [2000, 8000] 結果數量: " + largeRangeResult.size());
        System.out.println("查詢耗時: " + (endTime - startTime) / 1000000.0 + " ms");

        System.out.println("\n練習提示：");
        System.out.println("1. 理解如何利用 BST 性質進行剪枝");
        System.out.println("2. 分析範圍查詢的時間複雜度: O(log n + k)");
        System.out.println("3. 嘗試優化範圍查詢的實作");
        System.out.println("4. 思考如何支援二維範圍查詢");
    }
}
