
public class AVLTree {

    private AVLNode root;

    // 取得節點高度
    private int getHeight(AVLNode node) {
        return (node != null) ? node.height : 0;
    }

    // 插入節點
    // 時間複雜度: O(log n), 空間複雜度: O(log n)
    public void insert(int data) {
        root = insertNode(root, data);
    }

    private AVLNode insertNode(AVLNode node, int data) {
        // 1. 標準 BST 插入
        if (node == null) {
            return new AVLNode(data);
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
            return AVLRotations.rightRotate(node);
        }

        // Right Right 情況
        if (balance < -1 && data > node.right.data) {
            return AVLRotations.leftRotate(node);
        }

        // Left Right 情況
        if (balance > 1 && data > node.left.data) {
            node.left = AVLRotations.leftRotate(node.left);
            return AVLRotations.rightRotate(node);
        }

        // Right Left 情況
        if (balance < -1 && data < node.right.data) {
            node.right = AVLRotations.rightRotate(node.right);
            return AVLRotations.leftRotate(node);
        }

        return node;
    }

    // 搜尋節點
    // 時間複雜度: O(log n), 空間複雜度: O(log n)
    public boolean search(int data) {
        return searchNode(root, data);
    }

    private boolean searchNode(AVLNode node, int data) {
        if (node == null) {
            return false;
        }
        if (data == node.data) {
            return true;
        }
        if (data < node.data) {
            return searchNode(node.left, data);
        }
        return searchNode(node.right, data);
    }

    // 找最小值節點
    private AVLNode findMin(AVLNode node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    // 刪除節點
    // 時間複雜度: O(log n), 空間複雜度: O(log n)
    public void delete(int data) {
        root = deleteNode(root, data);
    }

    private AVLNode deleteNode(AVLNode node, int data) {
        // 1. 標準 BST 刪除
        if (node == null) {
            return null;
        }

        if (data < node.data) {
            node.left = deleteNode(node.left, data);
        } else if (data > node.data) {
            node.right = deleteNode(node.right, data);
        } else {
            // 找到要刪除的節點
            if (node.left == null || node.right == null) {
                AVLNode temp = (node.left != null) ? node.left : node.right;
                if (temp == null) {
                    temp = node;
                    node = null;
                } else {
                    // 複製內容而不是引用
                    node.data = temp.data;
                    node.left = temp.left;
                    node.right = temp.right;
                    node.height = temp.height;
                }
            } else {
                AVLNode temp = findMin(node.right);
                node.data = temp.data;
                node.right = deleteNode(node.right, temp.data);
            }
        }

        if (node == null) {
            return node;
        }

        // 2. 更新高度
        node.updateHeight();

        // 3. 檢查平衡因子並修復
        int balance = node.getBalance();

        // Left Left 情況
        if (balance > 1 && node.left.getBalance() >= 0) {
            return AVLRotations.rightRotate(node);
        }

        // Left Right 情況
        if (balance > 1 && node.left.getBalance() < 0) {
            node.left = AVLRotations.leftRotate(node.left);
            return AVLRotations.rightRotate(node);
        }

        // Right Right 情況
        if (balance < -1 && node.right.getBalance() <= 0) {
            return AVLRotations.leftRotate(node);
        }

        // Right Left 情況
        if (balance < -1 && node.right.getBalance() > 0) {
            node.right = AVLRotations.rightRotate(node.right);
            return AVLRotations.leftRotate(node);
        }

        return node;
    }

    // 驗證是否為有效的 AVL 樹
    public boolean isValidAVL() {
        return checkAVL(root) != -1;
    }

    private int checkAVL(AVLNode node) {
        if (node == null) {
            return 0;
        }

        int leftHeight = checkAVL(node.left);
        int rightHeight = checkAVL(node.right);

        if (leftHeight == -1 || rightHeight == -1) {
            return -1;
        }

        if (Math.abs(leftHeight - rightHeight) > 1) {
            return -1;
        }

        return Math.max(leftHeight, rightHeight) + 1;
    }

    // 列印樹狀結構
    public void printTree() {
        printInOrder(root);
        System.out.println();
    }

    private void printInOrder(AVLNode node) {
        if (node != null) {
            printInOrder(node.left);
            System.out.print(node.data + "(" + node.getBalance() + ") ");
            printInOrder(node.right);
        }
    }

    // 樹形顯示 (緊湊垂直樹狀圖)
    public void printTreeStructure() {
        System.out.println("樹結構:");
        if (root != null) {
            printCompactVerticalTree();
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

        // 填充樹 (根節點位置往左移1格)
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

    private void fillGrid(String[][] grid, AVLNode node, int row, int col) {
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

    public static void main(String[] args) {
        System.out.println("=== AVL 樹測試 ===");

        AVLTree avl = new AVLTree();

        // 插入測試
        System.out.println("\n--- 插入測試 ---");
        int[] values = {10, 20, 30, 40, 50, 25};

        for (int value : values) {
            System.out.println("插入: " + value);
            avl.insert(value);
            avl.printTreeStructure();

            // 調試：查看中序遍歷
            System.out.print("中序遍歷: ");
            avl.printTree();
            System.out.println("是否為有效 AVL 樹: " + avl.isValidAVL());
            System.out.println();
        }

        // 刪除 20 後的詳細分析
        System.out.println("--- 刪除 20 前的樹結構分析 ---");
        System.out.println("當前樹的根節點: " + avl.root.data);
        System.out.println("左子樹根節點: " + (avl.root.left != null ? avl.root.left.data : "null"));
        System.out.println("右子樹根節點: " + (avl.root.right != null ? avl.root.right.data : "null"));

        if (avl.root.left != null) {
            System.out.println("左子樹的左子節點: " + (avl.root.left.left != null ? avl.root.left.left.data : "null"));
            System.out.println("左子樹的右子節點: " + (avl.root.left.right != null ? avl.root.left.right.data : "null"));
        }

        // 刪除測試
        System.out.println("\n--- 刪除測試 ---");
        System.out.println("刪除 20");
        avl.delete(20);

        System.out.println("刪除後的樹結構分析:");
        System.out.println("當前樹的根節點: " + avl.root.data);
        System.out.println("左子樹根節點: " + (avl.root.left != null ? avl.root.left.data : "null"));
        System.out.println("右子樹根節點: " + (avl.root.right != null ? avl.root.right.data : "null"));

        if (avl.root.left != null) {
            System.out.println("左子樹的左子節點: " + (avl.root.left.left != null ? avl.root.left.left.data : "null"));
            System.out.println("左子樹的右子節點: " + (avl.root.left.right != null ? avl.root.left.right.data : "null"));
        }

        avl.printTreeStructure();
        System.out.println("是否為有效 AVL 樹: " + avl.isValidAVL());

        System.out.println("\n最終中序遍歷:");
        avl.printTree();
    }
}
