
import java.util.*;

public class BSTConversionAndBalance {

    /**
     * 二元樹節點
     */
    static class TreeNode {

        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int val) {
            this.val = val;
        }
    }

    /**
     * AVL樹節點（帶高度資訊）
     */
    static class AVLNode {

        int val;
        int height;
        AVLNode left;
        AVLNode right;

        AVLNode(int val) {
            this.val = val;
            this.height = 1;
        }
    }

    /**
     * 1. 將BST轉換為有序陣列
     */
    public static int[] bstToSortedArray(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        inorderTraversal(root, result);
        return result.stream().mapToInt(i -> i).toArray();
    }

    private static void inorderTraversal(TreeNode node, List<Integer> result) {
        if (node == null) {
            return;
        }

        inorderTraversal(node.left, result);
        result.add(node.val);
        inorderTraversal(node.right, result);
    }

    /**
     * 2. 從有序陣列建立平衡BST
     */
    public static TreeNode sortedArrayToBST(int[] nums) {
        if (nums == null || nums.length == 0) {
            return null;
        }

        return sortedArrayToBSTHelper(nums, 0, nums.length - 1);
    }

    private static TreeNode sortedArrayToBSTHelper(int[] nums, int left, int right) {
        if (left > right) {
            return null;
        }

        int mid = left + (right - left) / 2;
        TreeNode root = new TreeNode(nums[mid]);

        root.left = sortedArrayToBSTHelper(nums, left, mid - 1);
        root.right = sortedArrayToBSTHelper(nums, mid + 1, right);

        return root;
    }

    /**
     * 3. 將BST轉換為平衡BST
     */
    public static TreeNode balanceBST(TreeNode root) {
        if (root == null) {
            return null;
        }

        // 將BST轉換為有序陣列
        int[] sortedArray = bstToSortedArray(root);

        // 從有序陣列建立平衡BST
        return sortedArrayToBST(sortedArray);
    }

    /**
     * 4. 將BST轉換為有序雙向鏈結串列
     */
    static class DLLNode {

        int val;
        DLLNode prev;
        DLLNode next;

        DLLNode(int val) {
            this.val = val;
        }
    }

    public static DLLNode bstToDoublyLinkedList(TreeNode root) {
        if (root == null) {
            return null;
        }

        DLLNode[] result = new DLLNode[2]; // [head, tail]
        bstToDLLHelper(root, result);
        return result[0]; // 返回頭節點
    }

    private static void bstToDLLHelper(TreeNode node, DLLNode[] result) {
        if (node == null) {
            return;
        }

        DLLNode current = new DLLNode(node.val);

        // 處理左子樹
        DLLNode[] leftResult = new DLLNode[2];
        bstToDLLHelper(node.left, leftResult);

        // 處理右子樹
        DLLNode[] rightResult = new DLLNode[2];
        bstToDLLHelper(node.right, rightResult);

        // 連接左子樹
        if (leftResult[1] != null) {
            leftResult[1].next = current;
            current.prev = leftResult[1];
        }

        // 連接右子樹
        if (rightResult[0] != null) {
            current.next = rightResult[0];
            rightResult[0].prev = current;
        }

        // 設定頭尾節點
        result[0] = (leftResult[0] != null) ? leftResult[0] : current;
        result[1] = (rightResult[1] != null) ? rightResult[1] : current;
    }

    /**
     * 5. 將BST轉換為較大總和樹（每個節點變為原值+所有較大節點的總和）
     */
    public static TreeNode bstToGST(TreeNode root) {
        convertToGST(root, new int[]{0});
        return root;
    }

    private static void convertToGST(TreeNode node, int[] sum) {
        if (node == null) {
            return;
        }

        // 先處理右子樹（從大到小）
        convertToGST(node.right, sum);

        // 更新當前節點的值
        sum[0] += node.val;
        node.val = sum[0];

        // 處理左子樹
        convertToGST(node.left, sum);
    }

    /**
     * 6. 建立AVL樹（自平衡BST）
     */
    static class AVLTree {

        private AVLNode root;

        public void insert(int val) {
            root = insert(root, val);
        }

        private AVLNode insert(AVLNode node, int val) {
            // 1. 標準BST插入
            if (node == null) {
                return new AVLNode(val);
            }

            if (val < node.val) {
                node.left = insert(node.left, val);
            } else if (val > node.val) {
                node.right = insert(node.right, val);
            } else {
                return node; // 重複值，不插入
            }

            // 2. 更新高度
            node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));

            // 3. 計算平衡因子
            int balance = getBalance(node);

            // 4. 如果不平衡，進行旋轉
            // 左左情況
            if (balance > 1 && val < node.left.val) {
                return rightRotate(node);
            }

            // 右右情況
            if (balance < -1 && val > node.right.val) {
                return leftRotate(node);
            }

            // 左右情況
            if (balance > 1 && val > node.left.val) {
                node.left = leftRotate(node.left);
                return rightRotate(node);
            }

            // 右左情況
            if (balance < -1 && val < node.right.val) {
                node.right = rightRotate(node.right);
                return leftRotate(node);
            }

            return node;
        }

        private int getHeight(AVLNode node) {
            return (node == null) ? 0 : node.height;
        }

        private int getBalance(AVLNode node) {
            return (node == null) ? 0 : getHeight(node.left) - getHeight(node.right);
        }

        private AVLNode rightRotate(AVLNode y) {
            AVLNode x = y.left;
            AVLNode T2 = x.right;

            // 執行旋轉
            x.right = y;
            y.left = T2;

            // 更新高度
            y.height = Math.max(getHeight(y.left), getHeight(y.right)) + 1;
            x.height = Math.max(getHeight(x.left), getHeight(x.right)) + 1;

            return x;
        }

        private AVLNode leftRotate(AVLNode x) {
            AVLNode y = x.right;
            AVLNode T2 = y.left;

            // 執行旋轉
            y.left = x;
            x.right = T2;

            // 更新高度
            x.height = Math.max(getHeight(x.left), getHeight(x.right)) + 1;
            y.height = Math.max(getHeight(y.left), getHeight(y.right)) + 1;

            return y;
        }

        public TreeNode getRoot() {
            return convertAVLToTreeNode(root);
        }

        private TreeNode convertAVLToTreeNode(AVLNode avlNode) {
            if (avlNode == null) {
                return null;
            }

            TreeNode treeNode = new TreeNode(avlNode.val);
            treeNode.left = convertAVLToTreeNode(avlNode.left);
            treeNode.right = convertAVLToTreeNode(avlNode.right);

            return treeNode;
        }

        public void printAVL() {
            printAVLHelper(root, "", true);
        }

        private void printAVLHelper(AVLNode node, String prefix, boolean isLast) {
            if (node == null) {
                return;
            }

            System.out.println(prefix + (isLast ? "└── " : "├── ")
                    + node.val + " (h:" + node.height + ", b:" + getBalance(node) + ")");

            if (node.left != null || node.right != null) {
                if (node.right != null) {
                    printAVLHelper(node.right, prefix + (isLast ? "    " : "│   "), node.left == null);
                }
                if (node.left != null) {
                    printAVLHelper(node.left, prefix + (isLast ? "    " : "│   "), true);
                }
            }
        }
    }

    /**
     * 7. 檢查BST是否平衡
     */
    public static boolean isBalanced(TreeNode root) {
        return checkBalance(root) != -1;
    }

    private static int checkBalance(TreeNode node) {
        if (node == null) {
            return 0;
        }

        int leftHeight = checkBalance(node.left);
        if (leftHeight == -1) {
            return -1;
        }

        int rightHeight = checkBalance(node.right);
        if (rightHeight == -1) {
            return -1;
        }

        if (Math.abs(leftHeight - rightHeight) > 1) {
            return -1;
        }

        return Math.max(leftHeight, rightHeight) + 1;
    }

    /**
     * 8. 計算樹的平衡因子
     */
    public static void printBalanceFactors(TreeNode root) {
        Map<TreeNode, Integer> balanceFactors = new HashMap<>();
        calculateBalanceFactors(root, balanceFactors);

        System.out.println("各節點的平衡因子：");
        printBalanceFactorsHelper(root, balanceFactors);
    }

    private static int calculateBalanceFactors(TreeNode node, Map<TreeNode, Integer> factors) {
        if (node == null) {
            return 0;
        }

        int leftHeight = calculateBalanceFactors(node.left, factors);
        int rightHeight = calculateBalanceFactors(node.right, factors);

        int balanceFactor = leftHeight - rightHeight;
        factors.put(node, balanceFactor);

        return Math.max(leftHeight, rightHeight) + 1;
    }

    private static void printBalanceFactorsHelper(TreeNode node, Map<TreeNode, Integer> factors) {
        if (node == null) {
            return;
        }

        printBalanceFactorsHelper(node.left, factors);
        System.out.printf("節點 %d: 平衡因子 = %d\n", node.val, factors.get(node));
        printBalanceFactorsHelper(node.right, factors);
    }

    /**
     * 9. 將BST扁平化為有序鏈結串列
     */
    public static TreeNode flattenBSTToLinkedList(TreeNode root) {
        if (root == null) {
            return null;
        }

        TreeNode[] prev = new TreeNode[1];
        TreeNode dummy = new TreeNode(0);
        prev[0] = dummy;

        flattenHelper(root, prev);

        TreeNode head = dummy.right;
        if (head != null) {
            head.left = null;
        }

        return head;
    }

    private static void flattenHelper(TreeNode node, TreeNode[] prev) {
        if (node == null) {
            return;
        }

        flattenHelper(node.left, prev);

        // 將當前節點連接到鏈結串列
        prev[0].right = node;
        node.left = prev[0];
        prev[0] = node;

        flattenHelper(node.right, prev);
    }

    /**
     * 輔助方法：顯示樹的結構
     */
    public static void printTree(TreeNode root, String title) {
        System.out.println(title);
        if (root == null) {
            System.out.println("空樹");
            return;
        }

        printTreeHelper(root, "", true);
    }

    private static void printTreeHelper(TreeNode root, String prefix, boolean isLast) {
        if (root == null) {
            return;
        }

        System.out.println(prefix + (isLast ? "└── " : "├── ") + root.val);

        if (root.left != null || root.right != null) {
            if (root.right != null) {
                printTreeHelper(root.right, prefix + (isLast ? "    " : "│   "), root.left == null);
            }
            if (root.left != null) {
                printTreeHelper(root.left, prefix + (isLast ? "    " : "│   "), true);
            }
        }
    }

    /**
     * 顯示雙向鏈結串列
     */
    public static void printDLL(DLLNode head) {
        System.out.print("雙向鏈結串列：");
        DLLNode current = head;
        while (current != null) {
            System.out.print(current.val);
            if (current.next != null) {
                System.out.print(" <-> ");
            }
            current = current.next;
        }
        System.out.println();
    }

    /**
     * 顯示扁平化鏈結串列
     */
    public static void printFlattenedList(TreeNode head) {
        System.out.print("扁平化鏈結串列：");
        TreeNode current = head;
        while (current != null) {
            System.out.print(current.val);
            if (current.right != null) {
                System.out.print(" -> ");
            }
            current = current.right;
        }
        System.out.println();
    }

    /**
     * 建立不平衡的BST
     */
    public static TreeNode createUnbalancedBST() {
        /*
         * 建立不平衡的BST：
         *   1
         *    \
         *     2
         *      \
         *       3
         *        \
         *         4
         *          \
         *           5
         */
        TreeNode root = new TreeNode(1);
        root.right = new TreeNode(2);
        root.right.right = new TreeNode(3);
        root.right.right.right = new TreeNode(4);
        root.right.right.right.right = new TreeNode(5);

        return root;
    }

    /**
     * 建立中等不平衡的BST
     */
    public static TreeNode createMediumUnbalancedBST() {
        /*
         * 建立中等不平衡的BST：
         *       10
         *      /  \
         *     5   15
         *    /   /  \
         *   2   12  20
         *  /   /   /  \
         * 1   11  17  25
         */
        TreeNode root = new TreeNode(10);
        root.left = new TreeNode(5);
        root.right = new TreeNode(15);
        root.left.left = new TreeNode(2);
        root.right.left = new TreeNode(12);
        root.right.right = new TreeNode(20);
        root.left.left.left = new TreeNode(1);
        root.right.left.left = new TreeNode(11);
        root.right.right.left = new TreeNode(17);
        root.right.right.right = new TreeNode(25);

        return root;
    }

    public static void main(String[] args) {
        System.out.println("=== 二元搜尋樹轉換與平衡 ===");

        // 1. 建立不平衡的BST
        TreeNode unbalancedBST = createUnbalancedBST();
        printTree(unbalancedBST, "1. 不平衡的BST：");

        System.out.printf("是否平衡：%s\n", isBalanced(unbalancedBST));
        printBalanceFactors(unbalancedBST);

        // 2. 轉換為有序陣列
        System.out.println("\n2. BST轉換為有序陣列：");
        int[] sortedArray = bstToSortedArray(unbalancedBST);
        System.out.printf("有序陣列：%s\n", Arrays.toString(sortedArray));

        // 3. 平衡BST
        System.out.println("\n3. 平衡BST：");
        TreeNode balancedBST = balanceBST(unbalancedBST);
        printTree(balancedBST, "平衡後的BST：");

        System.out.printf("是否平衡：%s\n", isBalanced(balancedBST));
        printBalanceFactors(balancedBST);

        // 4. 測試中等不平衡的BST
        System.out.println("\n" + "=".repeat(50));
        TreeNode mediumBST = createMediumUnbalancedBST();
        printTree(mediumBST, "4. 中等不平衡的BST：");

        System.out.printf("是否平衡：%s\n", isBalanced(mediumBST));

        // 5. 轉換為雙向鏈結串列
        System.out.println("\n5. 轉換為雙向鏈結串列：");
        TreeNode copyForDLL = balanceBST(createMediumUnbalancedBST());
        DLLNode dllHead = bstToDoublyLinkedList(copyForDLL);
        printDLL(dllHead);

        // 6. 轉換為較大總和樹
        System.out.println("\n6. 轉換為較大總和樹：");
        TreeNode copyForGST = balanceBST(createMediumUnbalancedBST());
        System.out.printf("原始中序遍歷：%s\n", Arrays.toString(bstToSortedArray(copyForGST)));

        bstToGST(copyForGST);
        printTree(copyForGST, "較大總和樹：");

        // 7. 扁平化為鏈結串列
        System.out.println("\n7. 扁平化為鏈結串列：");
        TreeNode copyForFlatten = balanceBST(createMediumUnbalancedBST());
        TreeNode flattenedHead = flattenBSTToLinkedList(copyForFlatten);
        printFlattenedList(flattenedHead);

        // 8. AVL樹測試
        System.out.println("\n" + "=".repeat(50));
        System.out.println("8. AVL樹測試：");

        AVLTree avlTree = new AVLTree();
        int[] insertValues = {10, 20, 30, 40, 50, 25};

        System.out.println("依序插入：" + Arrays.toString(insertValues));

        for (int val : insertValues) {
            avlTree.insert(val);
            System.out.printf("\n插入 %d 後的AVL樹：\n", val);
            avlTree.printAVL();
        }

        TreeNode avlAsTreeNode = avlTree.getRoot();
        System.out.printf("\nAVL樹是否平衡：%s\n", isBalanced(avlAsTreeNode));

        // 9. 比較不同平衡方法
        System.out.println("\n9. 平衡方法比較：");

        TreeNode[] testTrees = {
            createUnbalancedBST(),
            createMediumUnbalancedBST()
        };

        String[] treeNames = {"極度不平衡樹", "中等不平衡樹"};

        for (int i = 0; i < testTrees.length; i++) {
            TreeNode originalTree = testTrees[i];
            String treeName = treeNames[i];

            System.out.printf("\n%s：\n", treeName);
            System.out.printf("  原始樹高度：%d\n", getTreeHeight(originalTree));
            System.out.printf("  原始樹是否平衡：%s\n", isBalanced(originalTree));

            TreeNode balanced = balanceBST(originalTree);
            System.out.printf("  平衡後高度：%d\n", getTreeHeight(balanced));
            System.out.printf("  平衡後是否平衡：%s\n", isBalanced(balanced));

            // 驗證中序遍歷結果相同
            int[] originalInorder = bstToSortedArray(originalTree);
            int[] balancedInorder = bstToSortedArray(balanced);
            boolean sameInorder = Arrays.equals(originalInorder, balancedInorder);
            System.out.printf("  中序遍歷結果相同：%s\n", sameInorder);
        }

        // 10. 特殊情況測試
        System.out.println("\n10. 特殊情況測試：");

        // 空樹
        TreeNode emptyTree = null;
        TreeNode balancedEmpty = balanceBST(emptyTree);
        System.out.printf("空樹平衡：%s\n", balancedEmpty == null);

        // 單節點樹
        TreeNode singleNode = new TreeNode(42);
        TreeNode balancedSingle = balanceBST(singleNode);
        System.out.printf("單節點樹平衡後值：%d\n", balancedSingle.val);
        System.out.printf("單節點樹是否平衡：%s\n", isBalanced(balancedSingle));

        // 已經平衡的樹
        TreeNode alreadyBalanced = sortedArrayToBST(new int[]{1, 2, 3, 4, 5, 6, 7});
        TreeNode rebalanced = balanceBST(alreadyBalanced);
        System.out.printf("已平衡樹重新平衡後是否平衡：%s\n", isBalanced(rebalanced));
    }

    private static int getTreeHeight(TreeNode node) {
        if (node == null) {
            return 0;
        }
        return 1 + Math.max(getTreeHeight(node.left), getTreeHeight(node.right));
    }
}
