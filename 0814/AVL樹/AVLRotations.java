
public class AVLRotations {

    // 右旋操作
    // 時間複雜度: O(1), 空間複雜度: O(1)
    public static AVLNode rightRotate(AVLNode y) {
        AVLNode x = y.left;
        AVLNode T2 = x.right;

        // 執行旋轉
        x.right = y;
        y.left = T2;

        // 更新高度
        y.updateHeight();
        x.updateHeight();

        return x; // 新的根節點
    }

    // 左旋操作
    // 時間複雜度: O(1), 空間複雜度: O(1)
    public static AVLNode leftRotate(AVLNode x) {
        AVLNode y = x.right;
        AVLNode T2 = y.left;

        // 執行旋轉
        y.left = x;
        x.right = T2;

        // 更新高度
        x.updateHeight();
        y.updateHeight();

        return y; // 新的根節點
    }

    // 創建測試節點的輔助方法
    public static AVLNode createNode(int data, AVLNode left, AVLNode right) {
        AVLNode node = new AVLNode(data);
        node.left = left;
        node.right = right;
        node.updateHeight();
        return node;
    }

    // 緊湊垂直樹狀圖顯示
    public static void printTree(AVLNode node, String prefix, boolean isRoot, String side) {
        if (node != null) {
            printCompactVerticalTree(node);
        }
    }

    private static void printCompactVerticalTree(AVLNode root) {
        if (root == null) {
            return;
        }

        int height = getNodeHeight(root);
        int width = (int) Math.pow(2, height) + 5;
        String[][] grid = new String[height * 2][width];

        // 初始化
        for (int i = 0; i < height * 2; i++) {
            for (int j = 0; j < width; j++) {
                grid[i][j] = " ";
            }
        }

        // 填充樹
        fillGrid(grid, root, 0, width / 2 - 1, root);

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

    private static void fillGrid(String[][] grid, AVLNode node, int row, int col, AVLNode root) {
        if (node == null || row >= grid.length) {
            return;
        }

        // 放置節點
        String nodeStr = String.valueOf(node.data);
        for (int i = 0; i < nodeStr.length() && col + i < grid[0].length; i++) {
            grid[row][col + i] = String.valueOf(nodeStr.charAt(i));
        }

        int spacing = Math.max(1, (int) Math.pow(2, getNodeHeight(root) - row - 2));

        // 放置連接線和子節點
        if (node.left != null) {
            grid[row + 1][col - 1] = "/";
            fillGrid(grid, node.left, row + 2, col - spacing, root);
        }

        if (node.right != null) {
            grid[row + 1][col + 1] = "\\";
            fillGrid(grid, node.right, row + 2, col + spacing, root);
        }
    }

    private static int getNodeHeight(AVLNode node) {
        return (node != null) ? node.height : 0;
    }

    // 傳統直角樹形顯示方法 (保留)
    public static void printTreeTraditional(AVLNode node, String prefix, boolean isLast) {
        if (node != null) {
            System.out.println(prefix + (isLast ? "└── " : "├── ") + node.data
                    + " (高度:" + node.height + ", 平衡:" + node.getBalance() + ")");
            if (node.left != null || node.right != null) {
                if (node.left != null) {
                    printTreeTraditional(node.left, prefix + (isLast ? "    " : "│   "), node.right == null);
                }
                if (node.right != null) {
                    printTreeTraditional(node.right, prefix + (isLast ? "    " : "│   "), true);
                }
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("=== AVL 旋轉操作測試 ===");

        // 測試右旋操作
        System.out.println("\n--- 右旋測試 ---");
        System.out.println("原始樹 (需要右旋):");
        AVLNode root1 = createNode(30, null, null);
        root1.left = createNode(20, null, null);
        root1.left.left = createNode(10, null, null);
        root1.left.updateHeight();
        root1.updateHeight();

        printTree(root1, "", true, "");
        System.out.println("根節點平衡因子: " + root1.getBalance());

        // 執行右旋
        AVLNode newRoot1 = rightRotate(root1);
        System.out.println("\n右旋後:");
        printTree(newRoot1, "", true, "");
        System.out.println("新根節點平衡因子: " + newRoot1.getBalance());

        // 測試左旋操作
        System.out.println("\n--- 左旋測試 ---");
        System.out.println("原始樹 (需要左旋):");
        AVLNode root2 = createNode(10, null, null);
        root2.right = createNode(20, null, null);
        root2.right.right = createNode(30, null, null);
        root2.right.updateHeight();
        root2.updateHeight();

        printTree(root2, "", true, "");
        System.out.println("根節點平衡因子: " + root2.getBalance());

        // 執行左旋
        AVLNode newRoot2 = leftRotate(root2);
        System.out.println("\n左旋後:");
        printTree(newRoot2, "", true, "");
        System.out.println("新根節點平衡因子: " + newRoot2.getBalance());

        // 測試複雜情況的旋轉
        System.out.println("\n--- 複雜樹旋轉測試 ---");
        AVLNode complexRoot = createNode(50, null, null);
        complexRoot.left = createNode(30, null, null);
        complexRoot.right = createNode(70, null, null);
        complexRoot.left.left = createNode(20, null, null);
        complexRoot.left.right = createNode(40, null, null);
        complexRoot.left.left.left = createNode(10, null, null);

        // 更新所有高度
        complexRoot.left.left.left.updateHeight();
        complexRoot.left.left.updateHeight();
        complexRoot.left.right.updateHeight();
        complexRoot.left.updateHeight();
        complexRoot.right.updateHeight();
        complexRoot.updateHeight();

        System.out.println("複雜樹結構 (左子樹不平衡):");
        printTree(complexRoot, "", true, "");
        System.out.println("根節點平衡因子: " + complexRoot.getBalance());
        System.out.println("左子節點平衡因子: " + complexRoot.left.getBalance());

        // 對左子樹進行右旋
        complexRoot.left = rightRotate(complexRoot.left);
        complexRoot.updateHeight();

        System.out.println("\n對左子樹右旋後:");
        printTree(complexRoot, "", true, "");
        System.out.println("根節點平衡因子: " + complexRoot.getBalance());
    }
}
