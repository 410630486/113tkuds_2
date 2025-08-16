
/**
 * AVL 樹基本操作練習
 * 練習目標：實作簡化版 AVL 樹的基本功能
 */
class AVLBasicNode {

    int data;
    int height;
    AVLBasicNode left, right;

    public AVLBasicNode(int data) {
        this.data = data;
        this.height = 1;
        this.left = this.right = null;
    }

    // 計算平衡因子
    public int getBalance() {
        int leftHeight = (left != null) ? left.height : 0;
        int rightHeight = (right != null) ? right.height : 0;
        return leftHeight - rightHeight;
    }

    // 更新節點高度
    public void updateHeight() {
        int leftHeight = (left != null) ? left.height : 0;
        int rightHeight = (right != null) ? right.height : 0;
        this.height = Math.max(leftHeight, rightHeight) + 1;
    }
}

public class AVLBasicExercise {

    private AVLBasicNode root;

    // TODO: 實作插入節點功能
    public void insert(int data) {
        // 提示：先實作標準 BST 插入，然後加入平衡檢查
        // 1. 標準 BST 插入
        // 2. 更新高度
        // 3. 檢查平衡並進行旋轉（進階版本需要）

        // 範例實作（基本版本，不含自動平衡）
        root = insertNode(root, data);
    }

    private AVLBasicNode insertNode(AVLBasicNode node, int data) {
        // 基本情況：空節點
        if (node == null) {
            return new AVLBasicNode(data);
        }

        // 標準 BST 插入
        if (data < node.data) {
            node.left = insertNode(node.left, data);
        } else if (data > node.data) {
            node.right = insertNode(node.right, data);
        } else {
            return node; // 重複值不插入
        }

        // 更新高度
        node.updateHeight();

        // TODO: 在這裡加入平衡檢查和旋轉邏輯
        // 練習：實作左旋、右旋等平衡操作
        return node;
    }

    // TODO: 實作搜尋節點功能
    public boolean search(int data) {
        // 提示：使用標準 BST 搜尋邏輯
        return searchNode(root, data);
    }

    private boolean searchNode(AVLBasicNode node, int data) {
        // TODO: 實作搜尋邏輯
        if (node == null) {
            return false;
        }

        if (data == node.data) {
            return true;
        } else if (data < node.data) {
            return searchNode(node.left, data);
        } else {
            return searchNode(node.right, data);
        }
    }

    // TODO: 計算樹的高度
    public int getTreeHeight() {
        // 提示：使用遞迴計算
        return getHeight(root);
    }

    private int getHeight(AVLBasicNode node) {
        // TODO: 實作高度計算
        return (node != null) ? node.height : 0;
    }

    // TODO: 檢查是否為有效的 AVL 樹
    public boolean isValidAVL() {
        // 提示：檢查每個節點的平衡因子是否在 [-1, 1] 範圍內
        return checkAVLProperty(root);
    }

    private boolean checkAVLProperty(AVLBasicNode node) {
        // TODO: 實作 AVL 性質檢查
        if (node == null) {
            return true;
        }

        // 檢查當前節點的平衡因子
        int balance = node.getBalance();
        if (balance < -1 || balance > 1) {
            return false;
        }

        // 遞迴檢查左右子樹
        return checkAVLProperty(node.left) && checkAVLProperty(node.right);
    }

    // 樹形顯示（使用統一格式）
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

    private void fillGrid(String[][] grid, AVLBasicNode node, int row, int col) {
        if (node == null || row >= grid.length) {
            return;
        }

        // 放置節點
        String nodeStr = String.valueOf(node.data);
        for (int i = 0; i < nodeStr.length() && col + i < grid[0].length; i++) {
            grid[row][col + i] = String.valueOf(nodeStr.charAt(i));
        }

        int spacing = Math.max(1, (int) Math.pow(2, getHeight(root) - row - 2));

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

    // 測試主函數
    public static void main(String[] args) {
        System.out.println("=== AVL 樹基本操作練習 ===");

        AVLBasicExercise tree = new AVLBasicExercise();

        // 測試插入操作
        System.out.println("\n--- 插入測試 ---");
        int[] values = {10, 5, 15, 3, 7, 12, 20};
        for (int value : values) {
            System.out.println("插入: " + value);
            tree.insert(value);
            tree.printTree();
            System.out.println("樹高度: " + tree.getTreeHeight());
            System.out.println("是否為有效 AVL 樹: " + tree.isValidAVL());
            System.out.println();
        }

        // 測試搜尋操作
        System.out.println("--- 搜尋測試 ---");
        int[] searchValues = {7, 8, 15, 25};
        for (int value : searchValues) {
            boolean found = tree.search(value);
            System.out.println("搜尋 " + value + ": " + found);
        }

        // 最終狀態
        System.out.println("\n--- 最終樹狀態 ---");
        tree.printTree();
        System.out.println("最終樹高度: " + tree.getTreeHeight());
        System.out.println("是否為有效 AVL 樹: " + tree.isValidAVL());

        System.out.println("\n練習提示：");
        System.out.println("1. 嘗試加入旋轉操作來維持 AVL 性質");
        System.out.println("2. 觀察插入順序對樹結構的影響");
        System.out.println("3. 測試更多邊界情況");
    }
}
