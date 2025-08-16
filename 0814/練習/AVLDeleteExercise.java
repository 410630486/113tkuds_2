
/**
 * AVL 樹刪除操作練習
 * 練習目標：實作 AVL 樹的刪除操作，處理三種刪除情況
 */

class AVLDeleteNode {

    int data;
    int height;
    AVLDeleteNode left, right;

    public AVLDeleteNode(int data) {
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

public class AVLDeleteExercise {

    private AVLDeleteNode root;

    // 插入操作（包含旋轉平衡）
    public void insert(int data) {
        root = insertNode(root, data);
    }

    private AVLDeleteNode insertNode(AVLDeleteNode node, int data) {
        if (node == null) {
            return new AVLDeleteNode(data);
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

    // TODO: 實作刪除操作
    public void delete(int data) {
        // 提示：刪除後需要重新平衡樹
        root = deleteNode(root, data);
    }

    private AVLDeleteNode deleteNode(AVLDeleteNode node, int data) {
        // TODO: 實作刪除邏輯

        // 1. 標準 BST 刪除
        if (node == null) {
            return node;
        }

        if (data < node.data) {
            node.left = deleteNode(node.left, data);
        } else if (data > node.data) {
            node.right = deleteNode(node.right, data);
        } else {
            // 找到要刪除的節點

            // 情況 1：葉子節點或只有一個子節點
            if (node.left == null || node.right == null) {
                AVLDeleteNode temp = null;
                if (temp == node.left) {
                    temp = node.right;
                } else {
                    temp = node.left;
                }

                // 沒有子節點的情況
                if (temp == null) {
                    temp = node;
                    node = null;
                } else {
                    // 有一個子節點的情況
                    node = temp;
                }
            } else {
                // 情況 2：有兩個子節點
                // TODO: 找前驅或後繼節點替代

                // 找到中序後繼（右子樹的最小值）
                AVLDeleteNode temp = findMin(node.right);

                // 複製後繼節點的資料到當前節點
                node.data = temp.data;

                // 刪除後繼節點
                node.right = deleteNode(node.right, temp.data);
            }
        }

        // 如果樹只有一個節點，直接返回
        if (node == null) {
            return node;
        }

        // 2. 更新高度
        node.updateHeight();

        // 3. 重新平衡
        return balanceNode(node);
    }

    // TODO: 找到最小值節點（用於尋找後繼）
    private AVLDeleteNode findMin(AVLDeleteNode node) {
        // 提示：一直往左走到底
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    // TODO: 找到最大值節點（用於尋找前驅）
    private AVLDeleteNode findMax(AVLDeleteNode node) {
        // 提示：一直往右走到底
        while (node.right != null) {
            node = node.right;
        }
        return node;
    }

    // 平衡節點（檢查並執行旋轉）
    private AVLDeleteNode balanceNode(AVLDeleteNode node) {
        int balance = node.getBalance();

        // Left Left 情況
        if (balance > 1 && node.left.getBalance() >= 0) {
            return rightRotate(node);
        }

        // Left Right 情況
        if (balance > 1 && node.left.getBalance() < 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // Right Right 情況
        if (balance < -1 && node.right.getBalance() <= 0) {
            return leftRotate(node);
        }

        // Right Left 情況
        if (balance < -1 && node.right.getBalance() > 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    // 旋轉操作
    private AVLDeleteNode rightRotate(AVLDeleteNode y) {
        AVLDeleteNode x = y.left;
        AVLDeleteNode T2 = x.right;

        x.right = y;
        y.left = T2;

        y.updateHeight();
        x.updateHeight();

        return x;
    }

    private AVLDeleteNode leftRotate(AVLDeleteNode x) {
        AVLDeleteNode y = x.right;
        AVLDeleteNode T2 = y.left;

        y.left = x;
        x.right = T2;

        x.updateHeight();
        y.updateHeight();

        return y;
    }

    // 搜尋操作
    public boolean search(int data) {
        return searchNode(root, data);
    }

    private boolean searchNode(AVLDeleteNode node, int data) {
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

    // 中序遍歷
    public void inorderTraversal() {
        System.out.print("中序遍歷: ");
        inorder(root);
        System.out.println();
    }

    private void inorder(AVLDeleteNode node) {
        if (node != null) {
            inorder(node.left);
            System.out.print(node.data + " ");
            inorder(node.right);
        }
    }

    // 驗證 AVL 性質
    public boolean isValidAVL() {
        return checkAVLProperty(root);
    }

    private boolean checkAVLProperty(AVLDeleteNode node) {
        if (node == null) {
            return true;
        }

        int balance = node.getBalance();
        if (balance < -1 || balance > 1) {
            return false;
        }

        return checkAVLProperty(node.left) && checkAVLProperty(node.right);
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

    private void fillGrid(String[][] grid, AVLDeleteNode node, int row, int col) {
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

    private int getHeight(AVLDeleteNode node) {
        return (node != null) ? node.height : 0;
    }

    public static void main(String[] args) {
        System.out.println("=== AVL 樹刪除操作練習 ===");

        AVLDeleteExercise tree = new AVLDeleteExercise();

        // 建立初始樹
        System.out.println("\n--- 建立初始樹 ---");
        int[] values = {50, 30, 70, 20, 40, 60, 80, 10, 25, 35, 45};
        for (int value : values) {
            tree.insert(value);
        }

        System.out.println("初始樹:");
        tree.printTree();
        tree.inorderTraversal();
        System.out.println("是否為有效 AVL 樹: " + tree.isValidAVL());

        // 測試刪除葉子節點
        System.out.println("\n--- 刪除葉子節點測試 ---");
        System.out.println("刪除葉子節點 10:");
        tree.delete(10);
        tree.printTree();
        tree.inorderTraversal();
        System.out.println("是否為有效 AVL 樹: " + tree.isValidAVL());

        // 測試刪除只有一個子節點的節點
        System.out.println("\n--- 刪除只有一個子節點的節點測試 ---");
        System.out.println("刪除節點 25 (只有右子節點):");
        tree.delete(25);
        tree.printTree();
        tree.inorderTraversal();
        System.out.println("是否為有效 AVL 樹: " + tree.isValidAVL());

        // 測試刪除有兩個子節點的節點
        System.out.println("\n--- 刪除有兩個子節點的節點測試 ---");
        System.out.println("刪除節點 30 (有兩個子節點):");
        tree.delete(30);
        tree.printTree();
        tree.inorderTraversal();
        System.out.println("是否為有效 AVL 樹: " + tree.isValidAVL());

        // 測試刪除根節點
        System.out.println("\n--- 刪除根節點測試 ---");
        System.out.println("刪除根節點 50:");
        tree.delete(50);
        tree.printTree();
        tree.inorderTraversal();
        System.out.println("是否為有效 AVL 樹: " + tree.isValidAVL());

        // 測試連續刪除
        System.out.println("\n--- 連續刪除測試 ---");
        int[] deleteValues = {70, 40, 60};
        for (int value : deleteValues) {
            System.out.println("刪除: " + value);
            tree.delete(value);
            tree.printTree();
            tree.inorderTraversal();
            System.out.println("是否為有效 AVL 樹: " + tree.isValidAVL());
            System.out.println();
        }

        System.out.println("練習提示：");
        System.out.println("1. 觀察刪除操作如何維持 AVL 性質");
        System.out.println("2. 理解前驅和後繼節點的概念");
        System.out.println("3. 注意刪除後的平衡調整");
        System.out.println("4. 測試邊界情況，如刪除所有節點");
    }
}
