
import java.util.*;

public class TreeMirrorAndSymmetry {

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

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    /**
     * 檢查二元樹是否對稱
     */
    public static boolean isSymmetric(TreeNode root) {
        if (root == null) {
            return true;
        }

        return isSymmetricHelper(root.left, root.right);
    }

    /**
     * 輔助方法：檢查兩個子樹是否對稱
     */
    private static boolean isSymmetricHelper(TreeNode left, TreeNode right) {
        // 兩個節點都為空，對稱
        if (left == null && right == null) {
            return true;
        }

        // 其中一個為空，不對稱
        if (left == null || right == null) {
            return false;
        }

        // 值不相等，不對稱
        if (left.val != right.val) {
            return false;
        }

        // 遞迴檢查：左子樹的左子樹與右子樹的右子樹對稱，
        // 左子樹的右子樹與右子樹的左子樹對稱
        return isSymmetricHelper(left.left, right.right)
                && isSymmetricHelper(left.right, right.left);
    }

    /**
     * 建立樹的鏡像（修改原樹）
     */
    public static TreeNode mirrorTree(TreeNode root) {
        if (root == null) {
            return null;
        }

        // 交換左右子樹
        TreeNode temp = root.left;
        root.left = root.right;
        root.right = temp;

        // 遞迴處理左右子樹
        mirrorTree(root.left);
        mirrorTree(root.right);

        return root;
    }

    /**
     * 建立樹的鏡像副本（不修改原樹）
     */
    public static TreeNode createMirrorCopy(TreeNode root) {
        if (root == null) {
            return null;
        }

        // 建立新節點
        TreeNode newNode = new TreeNode(root.val);

        // 遞迴建立鏡像：左子樹變右子樹，右子樹變左子樹
        newNode.left = createMirrorCopy(root.right);
        newNode.right = createMirrorCopy(root.left);

        return newNode;
    }

    /**
     * 檢查兩棵樹是否相同
     */
    public static boolean isSameTree(TreeNode p, TreeNode q) {
        // 兩個節點都為空
        if (p == null && q == null) {
            return true;
        }

        // 其中一個為空
        if (p == null || q == null) {
            return false;
        }

        // 值不相等
        if (p.val != q.val) {
            return false;
        }

        // 遞迴檢查左右子樹
        return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }

    /**
     * 檢查兩棵樹是否互為鏡像
     */
    public static boolean areMirrors(TreeNode tree1, TreeNode tree2) {
        // 兩個節點都為空
        if (tree1 == null && tree2 == null) {
            return true;
        }

        // 其中一個為空
        if (tree1 == null || tree2 == null) {
            return false;
        }

        // 值不相等
        if (tree1.val != tree2.val) {
            return false;
        }

        // 遞迴檢查：tree1的左子樹與tree2的右子樹是否互為鏡像，
        // tree1的右子樹與tree2的左子樹是否互為鏡像
        return areMirrors(tree1.left, tree2.right)
                && areMirrors(tree1.right, tree2.left);
    }

    /**
     * 檢查樹是否為回文樹（從左到右的層序遍歷是回文）
     */
    public static boolean isPalindromeTree(TreeNode root) {
        if (root == null) {
            return true;
        }

        // 獲取每一層的節點值
        List<List<Integer>> levels = getLevelValues(root);

        // 檢查每一層是否為回文
        for (List<Integer> level : levels) {
            if (!isPalindromeList(level)) {
                return false;
            }
        }

        return true;
    }

    /**
     * 獲取每一層的節點值（包括null節點用特殊值表示）
     */
    private static List<List<Integer>> getLevelValues(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            List<Integer> currentLevel = new ArrayList<>();
            boolean hasNonNull = false;

            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();

                if (node != null) {
                    currentLevel.add(node.val);
                    hasNonNull = true;
                    queue.offer(node.left);
                    queue.offer(node.right);
                } else {
                    currentLevel.add(null);
                    queue.offer(null);
                    queue.offer(null);
                }
            }

            result.add(currentLevel);

            // 如果這一層沒有非空節點，停止遍歷
            if (!hasNonNull) {
                break;
            }
        }

        return result;
    }

    /**
     * 檢查列表是否為回文
     */
    private static boolean isPalindromeList(List<Integer> list) {
        int left = 0, right = list.size() - 1;

        while (left < right) {
            Integer leftVal = list.get(left);
            Integer rightVal = list.get(right);

            // 處理null值的比較
            if (!Objects.equals(leftVal, rightVal)) {
                return false;
            }

            left++;
            right--;
        }

        return true;
    }

    /**
     * 層序遍歷（用於檢驗結果）
     */
    public static List<Integer> levelOrderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            result.add(node.val);

            if (node.left != null) {
                queue.offer(node.left);
            }
            if (node.right != null) {
                queue.offer(node.right);
            }
        }

        return result;
    }

    /**
     * 中序遍歷
     */
    public static List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        inorderHelper(root, result);
        return result;
    }

    private static void inorderHelper(TreeNode root, List<Integer> result) {
        if (root == null) {
            return;
        }

        inorderHelper(root.left, result);
        result.add(root.val);
        inorderHelper(root.right, result);
    }

    /**
     * 複製樹（深度複製）
     */
    public static TreeNode copyTree(TreeNode root) {
        if (root == null) {
            return null;
        }

        TreeNode newNode = new TreeNode(root.val);
        newNode.left = copyTree(root.left);
        newNode.right = copyTree(root.right);

        return newNode;
    }

    /**
     * 顯示樹的結構
     */
    public static void printTree(TreeNode root) {
        printTree(root, "");
    }

    public static void printTree(TreeNode root, String title) {
        if (!title.isEmpty()) {
            System.out.println(title);
        }

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
     * 建立對稱樹範例
     */
    public static TreeNode createSymmetricTree() {
        /*
         * 建立對稱樹：
         *       1
         *      / \
         *     2   2
         *    / \ / \
         *   3  4 4  3
         */
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(2);
        root.left.left = new TreeNode(3);
        root.left.right = new TreeNode(4);
        root.right.left = new TreeNode(4);
        root.right.right = new TreeNode(3);

        return root;
    }

    /**
     * 建立非對稱樹範例
     */
    public static TreeNode createAsymmetricTree() {
        /*
         * 建立非對稱樹：
         *       1
         *      / \
         *     2   2
         *      \ / 
         *       3 3
         */
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(2);
        root.left.right = new TreeNode(3);
        root.right.left = new TreeNode(3);

        return root;
    }

    /**
     * 建立一般樹範例
     */
    public static TreeNode createGeneralTree() {
        /*
         * 建立一般樹：
         *       1
         *      / \
         *     2   3
         *    / \   \
         *   4   5   6
         */
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        root.right.right = new TreeNode(6);

        return root;
    }

    public static void main(String[] args) {
        System.out.println("=== 樹的鏡像與對稱性檢查 ===");

        // 1. 對稱樹測試
        System.out.println("1. 對稱樹測試：");
        TreeNode symmetricTree = createSymmetricTree();
        printTree(symmetricTree, "對稱樹結構：");

        System.out.printf("是否對稱：%s\n", isSymmetric(symmetricTree));
        System.out.printf("層序遍歷：%s\n", levelOrderTraversal(symmetricTree));
        System.out.printf("中序遍歷：%s\n", inorderTraversal(symmetricTree));

        // 2. 非對稱樹測試
        System.out.println("\n" + "=".repeat(50));
        System.out.println("2. 非對稱樹測試：");
        TreeNode asymmetricTree = createAsymmetricTree();
        printTree(asymmetricTree, "非對稱樹結構：");

        System.out.printf("是否對稱：%s\n", isSymmetric(asymmetricTree));
        System.out.printf("層序遍歷：%s\n", levelOrderTraversal(asymmetricTree));
        System.out.printf("中序遍歷：%s\n", inorderTraversal(asymmetricTree));

        // 3. 鏡像操作測試
        System.out.println("\n3. 鏡像操作測試：");
        TreeNode originalTree = createGeneralTree();
        printTree(originalTree, "原始樹：");

        // 建立鏡像副本
        TreeNode mirrorCopy = createMirrorCopy(originalTree);
        printTree(mirrorCopy, "鏡像副本：");

        System.out.printf("原樹層序遍歷：%s\n", levelOrderTraversal(originalTree));
        System.out.printf("鏡像層序遍歷：%s\n", levelOrderTraversal(mirrorCopy));

        System.out.printf("原樹中序遍歷：%s\n", inorderTraversal(originalTree));
        System.out.printf("鏡像中序遍歷：%s\n", inorderTraversal(mirrorCopy));

        // 4. 樹比較測試
        System.out.println("\n4. 樹比較測試：");
        TreeNode tree1 = createGeneralTree();
        TreeNode tree2 = copyTree(tree1);
        TreeNode tree3 = createMirrorCopy(tree1);

        System.out.printf("tree1 與 tree2 相同：%s\n", isSameTree(tree1, tree2));
        System.out.printf("tree1 與 tree3 相同：%s\n", isSameTree(tree1, tree3));
        System.out.printf("tree1 與 tree3 互為鏡像：%s\n", areMirrors(tree1, tree3));

        // 5. 修改原樹的鏡像操作
        System.out.println("\n5. 修改原樹的鏡像操作：");
        TreeNode treeToMirror = createGeneralTree();

        System.out.println("鏡像前：");
        printTree(treeToMirror);
        System.out.printf("層序遍歷：%s\n", levelOrderTraversal(treeToMirror));

        // 執行鏡像操作
        mirrorTree(treeToMirror);

        System.out.println("\n鏡像後：");
        printTree(treeToMirror);
        System.out.printf("層序遍歷：%s\n", levelOrderTraversal(treeToMirror));

        // 6. 各種特殊情況測試
        System.out.println("\n6. 特殊情況測試：");

        // 空樹
        TreeNode emptyTree = null;
        System.out.printf("空樹是否對稱：%s\n", isSymmetric(emptyTree));

        // 單節點樹
        TreeNode singleNode = new TreeNode(1);
        System.out.printf("單節點樹是否對稱：%s\n", isSymmetric(singleNode));
        printTree(singleNode, "單節點樹：");

        // 只有左子樹的樹
        TreeNode leftOnlyTree = new TreeNode(1);
        leftOnlyTree.left = new TreeNode(2);
        System.out.printf("只有左子樹的樹是否對稱：%s\n", isSymmetric(leftOnlyTree));
        printTree(leftOnlyTree, "只有左子樹的樹：");

        // 7. 回文樹測試
        System.out.println("\n7. 回文樹測試：");

        // 建立回文樹範例
        TreeNode palindromeTree = new TreeNode(1);
        palindromeTree.left = new TreeNode(2);
        palindromeTree.right = new TreeNode(2);
        palindromeTree.left.left = new TreeNode(3);
        palindromeTree.left.right = new TreeNode(3);
        palindromeTree.right.left = new TreeNode(3);
        palindromeTree.right.right = new TreeNode(3);

        printTree(palindromeTree, "回文樹範例：");
        System.out.printf("是否為回文樹：%s\n", isPalindromeTree(palindromeTree));

        System.out.println("\n8. 互為鏡像的樹對測試：");
        TreeNode tree_a = createSymmetricTree();
        TreeNode tree_b = createMirrorCopy(tree_a);

        printTree(tree_a, "樹A：");
        printTree(tree_b, "樹B（A的鏡像）：");

        System.out.printf("A 是否對稱：%s\n", isSymmetric(tree_a));
        System.out.printf("B 是否對稱：%s\n", isSymmetric(tree_b));
        System.out.printf("A 和 B 是否互為鏡像：%s\n", areMirrors(tree_a, tree_b));
        System.out.printf("A 和 B 是否相同：%s\n", isSameTree(tree_a, tree_b));
    }
}
