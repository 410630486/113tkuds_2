
import java.util.*;

public class TreeReconstruction {

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
     * 1. 從前序和中序遍歷結果重建二元樹
     */
    public static TreeNode buildTreeFromPreorderInorder(int[] preorder, int[] inorder) {
        if (preorder == null || inorder == null || preorder.length != inorder.length) {
            return null;
        }

        // 建立中序遍歷的值到索引的映射，加速查找
        Map<Integer, Integer> inorderMap = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            inorderMap.put(inorder[i], i);
        }

        return buildFromPreorderInorderHelper(preorder, 0, preorder.length - 1,
                inorder, 0, inorder.length - 1, inorderMap);
    }

    private static TreeNode buildFromPreorderInorderHelper(int[] preorder, int preStart, int preEnd,
            int[] inorder, int inStart, int inEnd,
            Map<Integer, Integer> inorderMap) {
        if (preStart > preEnd || inStart > inEnd) {
            return null;
        }

        // 前序遍歷的第一個元素是根節點
        int rootVal = preorder[preStart];
        TreeNode root = new TreeNode(rootVal);

        // 在中序遍歷中找到根節點的位置
        int rootIndex = inorderMap.get(rootVal);

        // 計算左子樹的節點數量
        int leftTreeSize = rootIndex - inStart;

        // 遞迴建立左右子樹
        root.left = buildFromPreorderInorderHelper(preorder, preStart + 1, preStart + leftTreeSize,
                inorder, inStart, rootIndex - 1, inorderMap);
        root.right = buildFromPreorderInorderHelper(preorder, preStart + leftTreeSize + 1, preEnd,
                inorder, rootIndex + 1, inEnd, inorderMap);

        return root;
    }

    /**
     * 2. 從後序和中序遍歷結果重建二元樹
     */
    public static TreeNode buildTreeFromPostorderInorder(int[] postorder, int[] inorder) {
        if (postorder == null || inorder == null || postorder.length != inorder.length) {
            return null;
        }

        // 建立中序遍歷的值到索引的映射
        Map<Integer, Integer> inorderMap = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            inorderMap.put(inorder[i], i);
        }

        return buildFromPostorderInorderHelper(postorder, 0, postorder.length - 1,
                inorder, 0, inorder.length - 1, inorderMap);
    }

    private static TreeNode buildFromPostorderInorderHelper(int[] postorder, int postStart, int postEnd,
            int[] inorder, int inStart, int inEnd,
            Map<Integer, Integer> inorderMap) {
        if (postStart > postEnd || inStart > inEnd) {
            return null;
        }

        // 後序遍歷的最後一個元素是根節點
        int rootVal = postorder[postEnd];
        TreeNode root = new TreeNode(rootVal);

        // 在中序遍歷中找到根節點的位置
        int rootIndex = inorderMap.get(rootVal);

        // 計算左子樹的節點數量
        int leftTreeSize = rootIndex - inStart;

        // 遞迴建立左右子樹
        root.left = buildFromPostorderInorderHelper(postorder, postStart, postStart + leftTreeSize - 1,
                inorder, inStart, rootIndex - 1, inorderMap);
        root.right = buildFromPostorderInorderHelper(postorder, postStart + leftTreeSize, postEnd - 1,
                inorder, rootIndex + 1, inEnd, inorderMap);

        return root;
    }

    /**
     * 3. 從前序和後序遍歷結果重建二元樹（可能有多種解法）
     */
    public static TreeNode buildTreeFromPreorderPostorder(int[] preorder, int[] postorder) {
        if (preorder == null || postorder == null || preorder.length != postorder.length) {
            return null;
        }

        return buildFromPreorderPostorderHelper(preorder, 0, preorder.length - 1,
                postorder, 0, postorder.length - 1);
    }

    private static TreeNode buildFromPreorderPostorderHelper(int[] preorder, int preStart, int preEnd,
            int[] postorder, int postStart, int postEnd) {
        if (preStart > preEnd || postStart > postEnd) {
            return null;
        }

        // 前序遍歷的第一個元素是根節點
        int rootVal = preorder[preStart];
        TreeNode root = new TreeNode(rootVal);

        if (preStart == preEnd) {
            return root; // 只有一個節點
        }

        // 前序遍歷的第二個元素是左子樹的根節點
        int leftRootVal = preorder[preStart + 1];

        // 在後序遍歷中找到左子樹根節點的位置
        int leftRootIndex = -1;
        for (int i = postStart; i <= postEnd; i++) {
            if (postorder[i] == leftRootVal) {
                leftRootIndex = i;
                break;
            }
        }

        if (leftRootIndex == -1) {
            return root; // 沒有找到，只有右子樹
        }

        // 計算左子樹的節點數量
        int leftTreeSize = leftRootIndex - postStart + 1;

        // 遞迴建立左右子樹
        root.left = buildFromPreorderPostorderHelper(preorder, preStart + 1, preStart + leftTreeSize,
                postorder, postStart, leftRootIndex);
        root.right = buildFromPreorderPostorderHelper(preorder, preStart + leftTreeSize + 1, preEnd,
                postorder, leftRootIndex + 1, postEnd - 1);

        return root;
    }

    /**
     * 4. 從層序遍歷結果重建完全二元樹
     */
    public static TreeNode buildTreeFromLevelOrder(Integer[] levelOrder) {
        if (levelOrder == null || levelOrder.length == 0 || levelOrder[0] == null) {
            return null;
        }

        TreeNode root = new TreeNode(levelOrder[0]);
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        int index = 1;
        while (!queue.isEmpty() && index < levelOrder.length) {
            TreeNode current = queue.poll();

            // 建立左子節點
            if (index < levelOrder.length && levelOrder[index] != null) {
                current.left = new TreeNode(levelOrder[index]);
                queue.offer(current.left);
            }
            index++;

            // 建立右子節點
            if (index < levelOrder.length && levelOrder[index] != null) {
                current.right = new TreeNode(levelOrder[index]);
                queue.offer(current.right);
            }
            index++;
        }

        return root;
    }

    /**
     * 5. 從括號表示法重建二元樹 例如：4(2(3)(1))(6(5))
     */
    public static TreeNode buildTreeFromBrackets(String s) {
        if (s == null || s.length() == 0) {
            return null;
        }

        Stack<TreeNode> stack = new Stack<>();
        int i = 0;

        while (i < s.length()) {
            char c = s.charAt(i);

            if (c == ')') {
                // 結束當前子樹
                if (!stack.isEmpty()) {
                    stack.pop();
                }
            } else if (c == '(') {
                // 開始子樹，無需處理
            } else {
                // 數字
                int j = i;
                while (j < s.length() && (Character.isDigit(s.charAt(j)) || s.charAt(j) == '-')) {
                    j++;
                }

                int val = Integer.parseInt(s.substring(i, j));
                TreeNode node = new TreeNode(val);

                if (!stack.isEmpty()) {
                    TreeNode parent = stack.peek();
                    if (parent.left == null) {
                        parent.left = node;
                    } else {
                        parent.right = node;
                    }
                }

                stack.push(node);
                i = j - 1;
            }

            i++;
        }

        return stack.isEmpty() ? null : stack.get(0);
    }

    /**
     * 6. 序列化二元樹為字串
     */
    public static String serialize(TreeNode root) {
        if (root == null) {
            return "null";
        }

        StringBuilder sb = new StringBuilder();
        serializeHelper(root, sb);
        return sb.toString();
    }

    private static void serializeHelper(TreeNode node, StringBuilder sb) {
        if (node == null) {
            sb.append("null").append(",");
            return;
        }

        sb.append(node.val).append(",");
        serializeHelper(node.left, sb);
        serializeHelper(node.right, sb);
    }

    /**
     * 7. 從序列化字串反序列化二元樹
     */
    public static TreeNode deserialize(String data) {
        if (data == null || data.equals("null")) {
            return null;
        }

        String[] nodes = data.split(",");
        Queue<String> queue = new LinkedList<>(Arrays.asList(nodes));
        return deserializeHelper(queue);
    }

    private static TreeNode deserializeHelper(Queue<String> queue) {
        if (queue.isEmpty()) {
            return null;
        }

        String val = queue.poll();
        if (val.equals("null")) {
            return null;
        }

        TreeNode node = new TreeNode(Integer.parseInt(val));
        node.left = deserializeHelper(queue);
        node.right = deserializeHelper(queue);

        return node;
    }

    /**
     * 8. 驗證重建結果的正確性
     */
    public static boolean validateReconstruction(TreeNode original, TreeNode reconstructed) {
        return isSameTree(original, reconstructed);
    }

    private static boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null && q == null) {
            return true;
        }

        if (p == null || q == null) {
            return false;
        }

        if (p.val != q.val) {
            return false;
        }

        return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }

    /**
     * 輔助方法：遍歷樹
     */
    public static int[] preorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        preorderHelper(root, result);
        return result.stream().mapToInt(i -> i).toArray();
    }

    private static void preorderHelper(TreeNode node, List<Integer> result) {
        if (node == null) {
            return;
        }

        result.add(node.val);
        preorderHelper(node.left, result);
        preorderHelper(node.right, result);
    }

    public static int[] inorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        inorderHelper(root, result);
        return result.stream().mapToInt(i -> i).toArray();
    }

    private static void inorderHelper(TreeNode node, List<Integer> result) {
        if (node == null) {
            return;
        }

        inorderHelper(node.left, result);
        result.add(node.val);
        inorderHelper(node.right, result);
    }

    public static int[] postorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        postorderHelper(root, result);
        return result.stream().mapToInt(i -> i).toArray();
    }

    private static void postorderHelper(TreeNode node, List<Integer> result) {
        if (node == null) {
            return;
        }

        postorderHelper(node.left, result);
        postorderHelper(node.right, result);
        result.add(node.val);
    }

    public static Integer[] levelOrderTraversal(TreeNode root) {
        if (root == null) {
            return new Integer[0];
        }

        List<Integer> result = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            if (node != null) {
                result.add(node.val);
                queue.offer(node.left);
                queue.offer(node.right);
            } else {
                result.add(null);
            }
        }

        // 移除末尾的null值
        while (!result.isEmpty() && result.get(result.size() - 1) == null) {
            result.remove(result.size() - 1);
        }

        return result.toArray(new Integer[0]);
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
     * 建立測試樹
     */
    public static TreeNode createTestTree() {
        /*
         * 建立測試樹：
         *       3
         *      / \
         *     9   20
         *        /  \
         *       15   7
         */
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(9);
        root.right = new TreeNode(20);
        root.right.left = new TreeNode(15);
        root.right.right = new TreeNode(7);

        return root;
    }

    public static void main(String[] args) {
        System.out.println("=== 樹的重建 ===");

        // 建立原始測試樹
        TreeNode originalTree = createTestTree();
        printTree(originalTree, "原始測試樹：");

        // 獲取遍歷結果
        int[] preorder = preorderTraversal(originalTree);
        int[] inorder = inorderTraversal(originalTree);
        int[] postorder = postorderTraversal(originalTree);
        Integer[] levelorder = levelOrderTraversal(originalTree);

        System.out.printf("前序遍歷：%s\n", Arrays.toString(preorder));
        System.out.printf("中序遍歷：%s\n", Arrays.toString(inorder));
        System.out.printf("後序遍歷：%s\n", Arrays.toString(postorder));
        System.out.printf("層序遍歷：%s\n", Arrays.toString(levelorder));

        // 1. 從前序和中序重建
        System.out.println("\n" + "=".repeat(50));
        System.out.println("1. 從前序和中序重建：");
        TreeNode tree1 = buildTreeFromPreorderInorder(preorder, inorder);
        printTree(tree1, "重建結果：");

        boolean isValid1 = validateReconstruction(originalTree, tree1);
        System.out.printf("重建是否正確：%s\n", isValid1);

        // 2. 從後序和中序重建
        System.out.println("\n2. 從後序和中序重建：");
        TreeNode tree2 = buildTreeFromPostorderInorder(postorder, inorder);
        printTree(tree2, "重建結果：");

        boolean isValid2 = validateReconstruction(originalTree, tree2);
        System.out.printf("重建是否正確：%s\n", isValid2);

        // 3. 從前序和後序重建
        System.out.println("\n3. 從前序和後序重建：");
        TreeNode tree3 = buildTreeFromPreorderPostorder(preorder, postorder);
        printTree(tree3, "重建結果：");

        boolean isValid3 = validateReconstruction(originalTree, tree3);
        System.out.printf("重建是否正確：%s\n", isValid3);

        if (!isValid3) {
            System.out.println("注意：從前序和後序重建可能有多種解法");
        }

        // 4. 從層序遍歷重建
        System.out.println("\n4. 從層序遍歷重建：");
        TreeNode tree4 = buildTreeFromLevelOrder(levelorder);
        printTree(tree4, "重建結果：");

        boolean isValid4 = validateReconstruction(originalTree, tree4);
        System.out.printf("重建是否正確：%s\n", isValid4);

        // 5. 序列化和反序列化
        System.out.println("\n5. 序列化和反序列化：");
        String serialized = serialize(originalTree);
        System.out.printf("序列化結果：%s\n", serialized);

        TreeNode tree5 = deserialize(serialized);
        printTree(tree5, "反序列化結果：");

        boolean isValid5 = validateReconstruction(originalTree, tree5);
        System.out.printf("反序列化是否正確：%s\n", isValid5);

        // 6. 從括號表示法重建
        System.out.println("\n6. 從括號表示法重建：");
        String bracketNotation = "3(9)(20(15)(7))";
        System.out.printf("括號表示法：%s\n", bracketNotation);

        TreeNode tree6 = buildTreeFromBrackets(bracketNotation);
        printTree(tree6, "重建結果：");

        boolean isValid6 = validateReconstruction(originalTree, tree6);
        System.out.printf("重建是否正確：%s\n", isValid6);

        // 7. 更複雜的測試用例
        System.out.println("\n" + "=".repeat(50));
        System.out.println("7. 複雜測試用例：");

        // 建立更複雜的樹
        TreeNode complexTree = new TreeNode(1);
        complexTree.left = new TreeNode(2);
        complexTree.right = new TreeNode(3);
        complexTree.left.left = new TreeNode(4);
        complexTree.left.right = new TreeNode(5);
        complexTree.right.left = new TreeNode(6);
        complexTree.right.right = new TreeNode(7);

        printTree(complexTree, "複雜測試樹：");

        // 測試各種重建方法
        int[] complexPre = preorderTraversal(complexTree);
        int[] complexIn = inorderTraversal(complexTree);
        int[] complexPost = postorderTraversal(complexTree);
        Integer[] complexLevel = levelOrderTraversal(complexTree);

        System.out.printf("前序：%s\n", Arrays.toString(complexPre));
        System.out.printf("中序：%s\n", Arrays.toString(complexIn));
        System.out.printf("後序：%s\n", Arrays.toString(complexPost));
        System.out.printf("層序：%s\n", Arrays.toString(complexLevel));

        TreeNode rebuilt1 = buildTreeFromPreorderInorder(complexPre, complexIn);
        TreeNode rebuilt2 = buildTreeFromPostorderInorder(complexPost, complexIn);
        TreeNode rebuilt3 = buildTreeFromLevelOrder(complexLevel);

        System.out.printf("前序+中序重建正確：%s\n", validateReconstruction(complexTree, rebuilt1));
        System.out.printf("後序+中序重建正確：%s\n", validateReconstruction(complexTree, rebuilt2));
        System.out.printf("層序重建正確：%s\n", validateReconstruction(complexTree, rebuilt3));

        // 8. 特殊情況測試
        System.out.println("\n8. 特殊情況測試：");

        // 空樹
        TreeNode emptyTree = null;
        String emptySerial = serialize(emptyTree);
        TreeNode emptyRebuilt = deserialize(emptySerial);
        System.out.printf("空樹序列化：%s\n", emptySerial);
        System.out.printf("空樹重建正確：%s\n", validateReconstruction(emptyTree, emptyRebuilt));

        // 單節點樹
        TreeNode singleNode = new TreeNode(42);
        String singleSerial = serialize(singleNode);
        TreeNode singleRebuilt = deserialize(singleSerial);
        System.out.printf("單節點序列化：%s\n", singleSerial);
        System.out.printf("單節點重建正確：%s\n", validateReconstruction(singleNode, singleRebuilt));

        // 左偏樹
        TreeNode leftSkewed = new TreeNode(1);
        leftSkewed.left = new TreeNode(2);
        leftSkewed.left.left = new TreeNode(3);

        int[] leftPre = preorderTraversal(leftSkewed);
        int[] leftIn = inorderTraversal(leftSkewed);
        TreeNode leftRebuilt = buildTreeFromPreorderInorder(leftPre, leftIn);

        System.out.printf("左偏樹重建正確：%s\n", validateReconstruction(leftSkewed, leftRebuilt));

        // 右偏樹
        TreeNode rightSkewed = new TreeNode(1);
        rightSkewed.right = new TreeNode(2);
        rightSkewed.right.right = new TreeNode(3);

        int[] rightPre = preorderTraversal(rightSkewed);
        int[] rightIn = inorderTraversal(rightSkewed);
        TreeNode rightRebuilt = buildTreeFromPreorderInorder(rightPre, rightIn);

        System.out.printf("右偏樹重建正確：%s\n", validateReconstruction(rightSkewed, rightRebuilt));
    }
}
