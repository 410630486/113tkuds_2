
/**
 * Definition for singly-linked list.
 */
class ListNode {

    int val;
    ListNode next;

    ListNode() {
    }

    ListNode(int val) {
        this.val = val;
    }

    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }

    // 序列化方法 - 將鏈表轉換為字串
    public String serialize() {
        StringBuilder sb = new StringBuilder();
        ListNode curr = this;
        while (curr != null) {
            sb.append(curr.val);
            if (curr.next != null) {
                sb.append("->");
            }
            curr = curr.next;
        }
        return sb.toString();
    }

    // 反序列化方法 - 將字串轉換為鏈表
    public static ListNode deserialize(String data) {
        if (data == null || data.isEmpty()) {
            return null;
        }

        // 處理 LeetCode 格式 [1,2,3]
        if (data.startsWith("[") && data.endsWith("]")) {
            data = data.substring(1, data.length() - 1);
            if (data.isEmpty()) {
                return null;
            }

            String[] values = data.split(",");
            ListNode dummy = new ListNode(0);
            ListNode curr = dummy;

            for (String val : values) {
                curr.next = new ListNode(Integer.parseInt(val.trim()));
                curr = curr.next;
            }

            return dummy.next;
        }

        // 處理箭頭格式 1->2->3
        String[] values = data.split("->");
        ListNode dummy = new ListNode(0);
        ListNode curr = dummy;

        for (String val : values) {
            curr.next = new ListNode(Integer.parseInt(val.trim()));
            curr = curr.next;
        }

        return dummy.next;
    }
}

/**
 * LeetCode 24. Swap Nodes in Pairs
 *
 * 解題思路： 1. 使用dummy節點簡化邊界處理 2. 每次交換兩個相鄰節點 3. 維護prev指針指向待交換節點的前一個節點 4.
 * 更新指針連接關係完成交換 5. 移動prev指針到下一組待交換節點的前面
 *
 * 時間複雜度：O(n)，需要遍歷鏈表一次 空間複雜度：O(1)，只使用常數額外空間
 */
class Solution {

    public ListNode swapPairs(ListNode head) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode prev = dummy;

        // 確保有兩個節點可以交換
        while (prev.next != null && prev.next.next != null) {
            // 需要交換的兩個節點
            ListNode first = prev.next;
            ListNode second = prev.next.next;

            // 執行交換：調整指針連接
            prev.next = second;
            first.next = second.next;
            second.next = first;

            // 移動prev指針到下一組
            prev = first;
        }

        return dummy.next;
    }
}
