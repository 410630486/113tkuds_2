
import java.util.*;

/**
 * 持久化 AVL 樹練習 練習目標：實作支援版本控制的 AVL 樹，每次修改產生新版本
 */
class PersistentAVLNode {

    final int data;              // 不可變數據
    final int height;            // 不可變高度
    final PersistentAVLNode left, right; // 不可變子節點引用

    public PersistentAVLNode(int data, PersistentAVLNode left, PersistentAVLNode right) {
        this.data = data;
        this.left = left;
        this.right = right;
        this.height = 1 + Math.max(getHeight(left), getHeight(right));
    }

    public PersistentAVLNode(int data) {
        this(data, null, null);
    }

    public int getBalance() {
        return getHeight(left) - getHeight(right);
    }

    public static int getHeight(PersistentAVLNode node) {
        return (node != null) ? node.height : 0;
    }
}

class PersistentAVLVersion {

    final int versionId;
    final PersistentAVLNode root;
    final String operation; // 記錄操作類型
    final int operationData; // 記錄操作數據

    public PersistentAVLVersion(int versionId, PersistentAVLNode root, String operation, int operationData) {
        this.versionId = versionId;
        this.root = root;
        this.operation = operation;
        this.operationData = operationData;
    }
}

public class PersistentAVLExercise {

    private List<PersistentAVLVersion> versions;
    private int currentVersionId;

    public PersistentAVLExercise() {
        this.versions = new ArrayList<>();
        this.currentVersionId = 0;
        // 版本 0：空樹
        versions.add(new PersistentAVLVersion(0, null, "init", 0));
    }

    // TODO: 插入操作產生新版本
    public int insert(int data) {
        // 提示：使用路徑複製，只複製從根到插入點路徑上的節點
        PersistentAVLNode currentRoot = getCurrentRoot();
        PersistentAVLNode newRoot = insertNode(currentRoot, data);

        currentVersionId++;
        versions.add(new PersistentAVLVersion(currentVersionId, newRoot, "insert", data));

        return currentVersionId;
    }

    private PersistentAVLNode insertNode(PersistentAVLNode node, int data) {
        // TODO: 實作持久化插入
        // 基本情況：空節點
        if (node == null) {
            return new PersistentAVLNode(data);
        }

        // 如果數據已存在，不插入
        if (data == node.data) {
            return node; // 返回原節點，不創建新版本
        }

        // 創建新節點（路徑複製）
        PersistentAVLNode newLeft = node.left;
        PersistentAVLNode newRight = node.right;

        if (data < node.data) {
            newLeft = insertNode(node.left, data);
        } else {
            newRight = insertNode(node.right, data);
        }

        // 創建新節點
        PersistentAVLNode newNode = new PersistentAVLNode(node.data, newLeft, newRight);

        // 平衡調整
        return balanceNode(newNode);
    }

    // TODO: 刪除操作產生新版本
    public int delete(int data) {
        // 提示：類似插入，使用路徑複製
        PersistentAVLNode currentRoot = getCurrentRoot();
        PersistentAVLNode newRoot = deleteNode(currentRoot, data);

        currentVersionId++;
        versions.add(new PersistentAVLVersion(currentVersionId, newRoot, "delete", data));

        return currentVersionId;
    }

    private PersistentAVLNode deleteNode(PersistentAVLNode node, int data) {
        // TODO: 實作持久化刪除
        if (node == null) {
            return null;
        }

        if (data < node.data) {
            PersistentAVLNode newLeft = deleteNode(node.left, data);
            return balanceNode(new PersistentAVLNode(node.data, newLeft, node.right));
        } else if (data > node.data) {
            PersistentAVLNode newRight = deleteNode(node.right, data);
            return balanceNode(new PersistentAVLNode(node.data, node.left, newRight));
        } else {
            // 找到要刪除的節點
            if (node.left == null || node.right == null) {
                // 沒有子節點或只有一個子節點
                return (node.left != null) ? node.left : node.right;
            } else {
                // 有兩個子節點，找後繼節點
                PersistentAVLNode successor = findMin(node.right);
                PersistentAVLNode newRight = deleteNode(node.right, successor.data);
                return balanceNode(new PersistentAVLNode(successor.data, node.left, newRight));
            }
        }
    }

    // TODO: 查詢歷史版本
    public boolean searchInVersion(int versionId, int data) {
        // 提示：獲取指定版本的根節點，然後搜尋
        if (versionId < 0 || versionId >= versions.size()) {
            return false;
        }

        PersistentAVLNode root = versions.get(versionId).root;
        return searchNode(root, data);
    }

    private boolean searchNode(PersistentAVLNode node, int data) {
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

    // TODO: 獲取版本間的差異
    public List<Integer> getVersionDiff(int version1, int version2) {
        // 提示：比較兩個版本，找出不同的元素
        Set<Integer> set1 = getAllElements(version1);
        Set<Integer> set2 = getAllElements(version2);

        List<Integer> diff = new ArrayList<>();

        // 在 version2 中但不在 version1 中的元素
        for (Integer element : set2) {
            if (!set1.contains(element)) {
                diff.add(element);
            }
        }

        // 在 version1 中但不在 version2 中的元素（用負數表示）
        for (Integer element : set1) {
            if (!set2.contains(element)) {
                diff.add(-element);
            }
        }

        return diff;
    }

    private Set<Integer> getAllElements(int versionId) {
        Set<Integer> elements = new HashSet<>();
        if (versionId >= 0 && versionId < versions.size()) {
            collectElements(versions.get(versionId).root, elements);
        }
        return elements;
    }

    private void collectElements(PersistentAVLNode node, Set<Integer> elements) {
        if (node != null) {
            elements.add(node.data);
            collectElements(node.left, elements);
            collectElements(node.right, elements);
        }
    }

    // TODO: 版本回滾
    public void rollbackToVersion(int versionId) {
        // 提示：創建基於指定版本的新版本
        if (versionId >= 0 && versionId < versions.size()) {
            PersistentAVLNode targetRoot = versions.get(versionId).root;
            currentVersionId++;
            versions.add(new PersistentAVLVersion(currentVersionId, targetRoot, "rollback", versionId));
        }
    }

    // 平衡操作（創建新節點）
    private PersistentAVLNode balanceNode(PersistentAVLNode node) {
        if (node == null) {
            return null;
        }

        int balance = node.getBalance();

        // Left Left 情況
        if (balance > 1 && node.left.getBalance() >= 0) {
            return rightRotate(node);
        }

        // Left Right 情況
        if (balance > 1 && node.left.getBalance() < 0) {
            PersistentAVLNode newLeft = leftRotate(node.left);
            return rightRotate(new PersistentAVLNode(node.data, newLeft, node.right));
        }

        // Right Right 情況
        if (balance < -1 && node.right.getBalance() <= 0) {
            return leftRotate(node);
        }

        // Right Left 情況
        if (balance < -1 && node.right.getBalance() > 0) {
            PersistentAVLNode newRight = rightRotate(node.right);
            return leftRotate(new PersistentAVLNode(node.data, node.left, newRight));
        }

        return node;
    }

    private PersistentAVLNode rightRotate(PersistentAVLNode y) {
        PersistentAVLNode x = y.left;
        PersistentAVLNode T2 = x.right;

        // 創建新節點
        PersistentAVLNode newY = new PersistentAVLNode(y.data, T2, y.right);
        PersistentAVLNode newX = new PersistentAVLNode(x.data, x.left, newY);

        return newX;
    }

    private PersistentAVLNode leftRotate(PersistentAVLNode x) {
        PersistentAVLNode y = x.right;
        PersistentAVLNode T2 = y.left;

        // 創建新節點
        PersistentAVLNode newX = new PersistentAVLNode(x.data, x.left, T2);
        PersistentAVLNode newY = new PersistentAVLNode(y.data, newX, y.right);

        return newY;
    }

    private PersistentAVLNode findMin(PersistentAVLNode node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    // 輔助方法
    private PersistentAVLNode getCurrentRoot() {
        return versions.get(currentVersionId).root;
    }

    public int getCurrentVersion() {
        return currentVersionId;
    }

    public int getVersionCount() {
        return versions.size();
    }

    // 顯示版本歷史
    public void printVersionHistory() {
        System.out.println("=== 版本歷史 ===");
        for (PersistentAVLVersion version : versions) {
            System.out.println("版本 " + version.versionId + ": " + version.operation
                    + " " + version.operationData + " (節點數: " + countNodes(version.root) + ")");
        }
    }

    private int countNodes(PersistentAVLNode node) {
        if (node == null) {
            return 0;
        }
        return 1 + countNodes(node.left) + countNodes(node.right);
    }

    // 顯示指定版本的樹
    public void printVersion(int versionId) {
        if (versionId >= 0 && versionId < versions.size()) {
            System.out.println("版本 " + versionId + " 的樹結構:");
            PersistentAVLNode root = versions.get(versionId).root;
            if (root != null) {
                printCompactVerticalTree(root);
            } else {
                System.out.println("空樹");
            }
        }
    }

    private void printCompactVerticalTree(PersistentAVLNode root) {
        if (root == null) {
            return;
        }

        int height = PersistentAVLNode.getHeight(root);
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

    private void fillGrid(String[][] grid, PersistentAVLNode node, int row, int col) {
        if (node == null || row >= grid.length) {
            return;
        }

        String nodeStr = String.valueOf(node.data);
        for (int i = 0; i < nodeStr.length() && col + i < grid[0].length; i++) {
            grid[row][col + i] = String.valueOf(nodeStr.charAt(i));
        }

        int spacing = Math.max(1, (int) Math.pow(2, PersistentAVLNode.getHeight(node) - row - 2));

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
        System.out.println("=== 持久化 AVL 樹練習 ===");

        PersistentAVLExercise persistentTree = new PersistentAVLExercise();

        // 測試插入操作
        System.out.println("\n--- 插入操作測試 ---");
        int[] values = {10, 5, 15, 3, 7, 12, 20};
        for (int value : values) {
            int version = persistentTree.insert(value);
            System.out.println("插入 " + value + " -> 版本 " + version);
        }

        persistentTree.printVersionHistory();

        // 顯示不同版本的樹
        System.out.println("\n--- 不同版本的樹結構 ---");
        persistentTree.printVersion(0); // 空樹
        persistentTree.printVersion(3); // 插入 10, 5, 15 後
        persistentTree.printVersion(7); // 最終版本

        // 測試版本查詢
        System.out.println("\n--- 版本查詢測試 ---");
        System.out.println("版本 3 中是否包含 7: " + persistentTree.searchInVersion(3, 7));
        System.out.println("版本 7 中是否包含 7: " + persistentTree.searchInVersion(7, 7));
        System.out.println("版本 5 中是否包含 12: " + persistentTree.searchInVersion(5, 12));

        // 測試刪除操作
        System.out.println("\n--- 刪除操作測試 ---");
        int deleteVersion1 = persistentTree.delete(5);
        System.out.println("刪除 5 -> 版本 " + deleteVersion1);

        int deleteVersion2 = persistentTree.delete(15);
        System.out.println("刪除 15 -> 版本 " + deleteVersion2);

        persistentTree.printVersion(deleteVersion2);

        // 測試版本差異
        System.out.println("\n--- 版本差異測試 ---");
        List<Integer> diff = persistentTree.getVersionDiff(7, deleteVersion2);
        System.out.println("版本 7 和版本 " + deleteVersion2 + " 的差異: " + diff);

        // 測試版本回滾
        System.out.println("\n--- 版本回滾測試 ---");
        persistentTree.rollbackToVersion(5);
        System.out.println("回滾到版本 5");
        persistentTree.printVersion(persistentTree.getCurrentVersion());

        persistentTree.printVersionHistory();

        // 測試空間共享
        System.out.println("\n--- 空間共享分析 ---");
        System.out.println("總版本數: " + persistentTree.getVersionCount());
        System.out.println("注意：持久化數據結構通過共享不變節點來節省空間");
        System.out.println("新版本只需複製修改路徑上的節點，其他節點可以共享");

        System.out.println("\n練習提示：");
        System.out.println("1. 理解路徑複製的概念和實作");
        System.out.println("2. 分析空間複雜度：每次操作 O(log n) 額外空間");
        System.out.println("3. 思考如何優化空間使用");
        System.out.println("4. 考慮併發環境下的持久化數據結構");
    }
}
