
public class RBNode {

    int data;
    RBNode left, right, parent;
    boolean isRed; // true = 紅色, false = 黑色

    public RBNode(int data) {
        this.data = data;
        this.isRed = true; // 新節點預設為紅色
    }

    public RBNode(int data, boolean isRed) {
        this.data = data;
        this.isRed = isRed;
    }

    // 取得兄弟節點
    public RBNode getSibling() {
        if (parent == null) {
            return null;
        }
        if (this == parent.left) {
            return parent.right;
        }
        return parent.left;
    }

    // 取得叔叔節點
    public RBNode getUncle() {
        if (parent == null || parent.parent == null) {
            return null;
        }
        return parent.getSibling();
    }

    // 取得祖父節點
    public RBNode getGrandparent() {
        if (parent == null) {
            return null;
        }
        return parent.parent;
    }
}
