
import java.util.*;

class RBNode {

    char color; // 'R' or 'B'
    RBNode left;
    RBNode right;

    RBNode(int val, char color) {
        this.color = color;
    }
}

public class M10_RBPropertiesCheck {

    private static RBNode[] buildArray(int n, Scanner scanner) {
        RBNode[] nodes = new RBNode[n];

        for (int i = 0; i < n; i++) {
            int val = scanner.nextInt();
            char color = scanner.next().charAt(0);

            if (val == -1) {
                nodes[i] = null;
            } else {
                nodes[i] = new RBNode(val, color);
            }
        }

        for (int i = 0; i < n; i++) {
            if (nodes[i] != null) {
                int leftIndex = 2 * i + 1;
                int rightIndex = 2 * i + 2;

                if (leftIndex < n) {
                    nodes[i].left = nodes[leftIndex];
                }

                if (rightIndex < n) {
                    nodes[i].right = nodes[rightIndex];
                }
            }
        }

        return nodes;
    }

    private static String checkRBProperties(RBNode[] nodes, int n) {
        if (n == 0 || nodes[0] == null) {
            return "RB Valid";
        }

        if (nodes[0].color != 'B') {
            return "RootNotBlack";
        }

        for (int i = 0; i < n; i++) {
            if (nodes[i] != null && nodes[i].color == 'R') {
                int leftIndex = 2 * i + 1;
                int rightIndex = 2 * i + 2;

                if (leftIndex < n && nodes[leftIndex] != null && nodes[leftIndex].color == 'R') {
                    return "RedRedViolation at index " + leftIndex;
                }

                if (rightIndex < n && nodes[rightIndex] != null && nodes[rightIndex].color == 'R') {
                    return "RedRedViolation at index " + rightIndex;
                }
            }
        }

        int expectedBlackHeight = getBlackHeight(nodes[0]);
        if (expectedBlackHeight == -1 || !checkBlackHeight(nodes[0], expectedBlackHeight)) {
            return "BlackHeightMismatch";
        }

        return "RB Valid";
    }

    private static int getBlackHeight(RBNode node) {
        if (node == null) {
            return 1;
        }

        int leftHeight = getBlackHeight(node.left);
        int rightHeight = getBlackHeight(node.right);

        if (leftHeight == -1 || rightHeight == -1 || leftHeight != rightHeight) {
            return -1;
        }

        return leftHeight + (node.color == 'B' ? 1 : 0);
    }

    private static boolean checkBlackHeight(RBNode node, int expectedHeight) {
        if (node == null) {
            return expectedHeight == 1;
        }

        int leftHeight = getBlackHeight(node.left);
        int rightHeight = getBlackHeight(node.right);

        if (leftHeight == -1 || rightHeight == -1 || leftHeight != rightHeight) {
            return false;
        }

        int currentHeight = leftHeight + (node.color == 'B' ? 1 : 0);
        return currentHeight == expectedHeight;
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int n = scanner.nextInt();

            RBNode[] nodes = buildArray(n, scanner);
            String result = checkRBProperties(nodes, n);

            System.out.println(result);
        }
    }
}
