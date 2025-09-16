
public class AVLNode {

    int data;
    AVLNode left, right;
    int height;

    public AVLNode(int data) {
        this.data = data;
        this.height = 1;
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

    @Override
    public String toString() {
        return "AVLNode{"
                + "data=" + data
                + ", height=" + height
                + '}';
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
        System.out.println("=== AVL 節點測試 ===");

        // 創建根節點
        AVLNode root = new AVLNode(10);
        System.out.println("創建根節點: " + root);
        System.out.println("平衡因子: " + root.getBalance());

        // 添加左子節點
        root.left = new AVLNode(5);
        root.updateHeight();
        System.out.println("\n添加左子節點後:");
        System.out.println("根節點: " + root);
        System.out.println("平衡因子: " + root.getBalance());

        // 添加右子節點
        root.right = new AVLNode(15);
        root.updateHeight();
        System.out.println("\n添加右子節點後:");
        System.out.println("根節點: " + root);
        System.out.println("平衡因子: " + root.getBalance());

        // 添加左子節點的左子節點
        root.left.left = new AVLNode(3);
        root.left.updateHeight();
        root.updateHeight();
        System.out.println("\n添加左子節點的左子節點後:");
        System.out.println("根節點: " + root);
        System.out.println("平衡因子: " + root.getBalance());

        // 添加右子節點的右子節點
        root.right.right = new AVLNode(20);
        root.right.updateHeight();
        root.updateHeight();
        System.out.println("\n添加右子節點的右子節點後:");
        System.out.println("根節點: " + root);
        System.out.println("平衡因子: " + root.getBalance());

        // 顯示樹結構
        System.out.println("\n=== 當前樹結構 (斜線風格) ===");
        printTree(root, "", true, "");

        System.out.println("\n=== 當前樹結構 (傳統風格) ===");
        printTreeTraditional(root, "", true);

        // 測試不平衡情況
        System.out.println("\n=== 測試不平衡情況 ===");
        AVLNode unbalanced = new AVLNode(1);
        unbalanced.right = new AVLNode(2);
        unbalanced.right.right = new AVLNode(3);
        unbalanced.right.right.right = new AVLNode(4);

        // 更新高度
        unbalanced.right.right.right.updateHeight();
        unbalanced.right.right.updateHeight();
        unbalanced.right.updateHeight();
        unbalanced.updateHeight();

        System.out.println("不平衡樹的根節點平衡因子: " + unbalanced.getBalance());
        System.out.println("不平衡樹結構 (斜線風格):");
        printTree(unbalanced, "", true, "");
    }
}
