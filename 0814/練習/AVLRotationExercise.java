
/**
 * AVL 樹旋轉操作練習
 * 練習目標：實作四種旋轉操作並測試各種不平衡情況
 */

class AVLRotationNode {

    int data;
    int height;
    AVLRotationNode left, right;

    public AVLRotationNode(int data) {
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

public class AVLRotationExercise {

    private AVLRotationNode root;

    // TODO: 實作右旋操作
    // 提示：右旋用於處理 Left-Left 不平衡情況
    private AVLRotationNode rightRotate(AVLRotationNode y) {
        // 練習：完成右旋實作
        /*
         * 右旋示意圖：
         *     y                x
         *    / \              / \
         *   x   T3    =>     T1  y
         *  / \                  / \
         * T1  T2               T2 T3
         */

        // TODO: 實作右旋邏輯
        AVLRotationNode x = y.left;
        AVLRotationNode T2 = x.right;

        // 執行旋轉
        x.right = y;
        y.left = T2;

        // 更新高度
        y.updateHeight();
        x.updateHeight();

        return x; // 新的根節點
    }

    // TODO: 實作左旋操作
    // 提示：左旋用於處理 Right-Right 不平衡情況
    private AVLRotationNode leftRotate(AVLRotationNode x) {
        // 練習：完成左旋實作
        /*
         * 左旋示意圖：
         *   x                    y
         *  / \                  / \
         * T1  y        =>      x   T3
         *    / \              / \
         *   T2  T3           T1  T2
         */

        // TODO: 實作左旋邏輯
        AVLRotationNode y = x.right;
        AVLRotationNode T2 = y.left;

        // 執行旋轉
        y.left = x;
        x.right = T2;

        // 更新高度
        x.updateHeight();
        y.updateHeight();

        return y; // 新的根節點
    }

    // TODO: 實作左右旋操作
    // 提示：用於處理 Left-Right 不平衡情況
    private AVLRotationNode leftRightRotate(AVLRotationNode node) {
        // 練習：完成左右旋實作
        // 步驟：先對左子節點左旋，再對當前節點右旋

        // TODO: 實作左右旋邏輯
        node.left = leftRotate(node.left);
        return rightRotate(node);
    }

    // TODO: 實作右左旋操作
    // 提示：用於處理 Right-Left 不平衡情況
    private AVLRotationNode rightLeftRotate(AVLRotationNode node) {
        // 練習：完成右左旋實作
        // 步驟：先對右子節點右旋，再對當前節點左旋

        // TODO: 實作右左旋邏輯
        node.right = rightRotate(node.right);
        return leftRotate(node);
    }

    // 完整的插入操作（包含旋轉平衡）
    public void insert(int data) {
        root = insertNode(root, data);
    }

    private AVLRotationNode insertNode(AVLRotationNode node, int data) {
        // 1. 標準 BST 插入
        if (node == null) {
            return new AVLRotationNode(data);
        }

        if (data < node.data) {
            node.left = insertNode(node.left, data);
        } else if (data > node.data) {
            node.right = insertNode(node.right, data);
        } else {
            return node; // 重複值不插入
        }

        // 2. 更新高度
        node.updateHeight();

        // 3. 檢查平衡因子
        int balance = node.getBalance();

        // 4. 處理不平衡情況
        // Left Left 情況
        if (balance > 1 && data < node.left.data) {
            return rightRotate(node);
        }

        // Right Right 情況
        if (balance < -1 && data > node.right.data) {
            return leftRotate(node);
        }

        // Left Right 情況
        if (balance > 1 && data > node.left.data) {
            return leftRightRotate(node);
        }

        // Right Left 情況
        if (balance < -1 && data < node.right.data) {
            return rightLeftRotate(node);
        }

        return node;
    }

    // 手動創建不平衡樹進行旋轉測試
    public void testRotations() {
        System.out.println("=== 旋轉操作測試 ===");

        // 測試右旋 (Left-Left 情況)
        System.out.println("\n--- 右旋測試 (Left-Left) ---");
        AVLRotationNode llRoot = new AVLRotationNode(30);
        llRoot.left = new AVLRotationNode(20);
        llRoot.left.left = new AVLRotationNode(10);
        updateHeights(llRoot);

        System.out.println("旋轉前 (不平衡):");
        printTree(llRoot);

        llRoot = rightRotate(llRoot);
        System.out.println("右旋後:");
        printTree(llRoot);

        // 測試左旋 (Right-Right 情況)
        System.out.println("\n--- 左旋測試 (Right-Right) ---");
        AVLRotationNode rrRoot = new AVLRotationNode(10);
        rrRoot.right = new AVLRotationNode(20);
        rrRoot.right.right = new AVLRotationNode(30);
        updateHeights(rrRoot);

        System.out.println("旋轉前 (不平衡):");
        printTree(rrRoot);

        rrRoot = leftRotate(rrRoot);
        System.out.println("左旋後:");
        printTree(rrRoot);

        // 測試左右旋 (Left-Right 情況)
        System.out.println("\n--- 左右旋測試 (Left-Right) ---");
        AVLRotationNode lrRoot = new AVLRotationNode(30);
        lrRoot.left = new AVLRotationNode(10);
        lrRoot.left.right = new AVLRotationNode(20);
        updateHeights(lrRoot);

        System.out.println("旋轉前 (不平衡):");
        printTree(lrRoot);

        lrRoot = leftRightRotate(lrRoot);
        System.out.println("左右旋後:");
        printTree(lrRoot);

        // 測試右左旋 (Right-Left 情況)
        System.out.println("\n--- 右左旋測試 (Right-Left) ---");
        AVLRotationNode rlRoot = new AVLRotationNode(10);
        rlRoot.right = new AVLRotationNode(30);
        rlRoot.right.left = new AVLRotationNode(20);
        updateHeights(rlRoot);

        System.out.println("旋轉前 (不平衡):");
        printTree(rlRoot);

        rlRoot = rightLeftRotate(rlRoot);
        System.out.println("右左旋後:");
        printTree(rlRoot);
    }

    // 更新樹的高度（遞迴）
    private void updateHeights(AVLRotationNode node) {
        if (node != null) {
            updateHeights(node.left);
            updateHeights(node.right);
            node.updateHeight();
        }
    }

    // 樹形顯示
    public void printTree() {
        printTree(root);
    }

    private void printTree(AVLRotationNode node) {
        System.out.println("樹結構:");
        if (node != null) {
            printCompactVerticalTree(node);
        } else {
            System.out.println("空樹");
        }
    }

    private void printCompactVerticalTree(AVLRotationNode root) {
        if (root == null) {
            return;
        }

        int height = getHeight(root);
        int width = (int) Math.pow(2, height) + 5;
        String[][] grid = new String[height * 2][width];

        // 初始化
        for (int i = 0; i < height * 2; i++) {
            for (int j = 0; j < width; j++) {
                grid[i][j] = " ";
            }
        }

        // 填充樹
        fillGrid(grid, root, 0, width / 2 - 1);

        // 打印
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

    private void fillGrid(String[][] grid, AVLRotationNode node, int row, int col) {
        if (node == null || row >= grid.length) {
            return;
        }

        // 放置節點
        String nodeStr = String.valueOf(node.data);
        for (int i = 0; i < nodeStr.length() && col + i < grid[0].length; i++) {
            grid[row][col + i] = String.valueOf(nodeStr.charAt(i));
        }

        int spacing = Math.max(1, (int) Math.pow(2, getHeight(node) - row - 2));

        // 放置連接線和子節點
        if (node.left != null) {
            grid[row + 1][col - 1] = "/";
            fillGrid(grid, node.left, row + 2, col - spacing);
        }

        if (node.right != null) {
            grid[row + 1][col + 1] = "\\";
            fillGrid(grid, node.right, row + 2, col + spacing);
        }
    }

    private int getHeight(AVLRotationNode node) {
        return (node != null) ? node.height : 0;
    }

    public static void main(String[] args) {
        AVLRotationExercise exercise = new AVLRotationExercise();

        // 測試各種旋轉操作
        exercise.testRotations();

        // 測試完整的插入操作（自動平衡）
        System.out.println("\n=== 自動平衡插入測試 ===");

        // 插入會導致各種不平衡情況的序列
        int[] values = {10, 20, 30, 40, 50, 25};
        for (int value : values) {
            System.out.println("\n插入: " + value);
            exercise.insert(value);
            exercise.printTree();
        }

        System.out.println("\n練習提示：");
        System.out.println("1. 觀察每種旋轉操作如何恢復平衡");
        System.out.println("2. 理解何時使用哪種旋轉");
        System.out.println("3. 注意旋轉後高度的更新順序");
        System.out.println("4. 嘗試畫圖理解旋轉過程");
    }
}
