
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
 * LeetCode 25. Reverse Nodes in k-Group
 *
 * 解題思路： 1. 每k個節點為一組進行反轉 2. 如果最後不足k個節點，保持原順序不變 3. 使用輔助方法檢查是否有足夠的k個節點 4.
 * 對每組k個節點進行局部反轉 5. 正確連接反轉後的各組節點
 *
 * 時間複雜度：O(n)，需要遍歷所有節點 空間複雜度：O(1)，只使用常數額外空間
 */
class Solution {

    public ListNode reverseKGroup(ListNode head, int k) {
        if (head == null || k == 1) {
            return head;
        }

        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode prev = dummy;
        ListNode curr = head;

        // 計算總長度
        int length = 0;
        while (curr != null) {
            length++;
            curr = curr.next;
        }

        curr = head;
        // 每k個節點為一組進行反轉
        for (int i = 0; i < length / k; i++) {
            ListNode tail = curr;
            // 反轉當前組的k個節點
            for (int j = 0; j < k; j++) {
                ListNode next = curr.next;
                curr.next = prev.next;
                prev.next = curr;
                curr = next;
            }
            // 連接到下一組
            tail.next = curr;
            prev = tail;
        }

        return dummy.next;
    }
}
