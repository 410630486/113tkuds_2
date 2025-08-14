
public class RBInsertExample {

    public static void main(String[] args) {
        RedBlackTree rbTree = new RedBlackTree();

        System.out.println("=== 紅黑樹插入演示 ===");

        int[] values = {7, 3, 11, 1, 5, 9, 13, 6, 8, 12};

        for (int value : values) {
            System.out.println("插入: " + value);
            rbTree.insert(value);
            System.out.print("當前樹狀態: ");
            rbTree.printTree();
            System.out.println("是否為有效紅黑樹: " + rbTree.isValidRBTree());
            System.out.println();
        }

        System.out.println("=== 搜尋測試 ===");
        System.out.println("搜尋 6: " + rbTree.search(6));
        System.out.println("搜尋 15: " + rbTree.search(15));
    }
}
