class ListNode {
    int val;
    ListNode next;
    ListNode() {}
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    
    // 序列化方法：將鏈表轉換為字符串
    public String serialize() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        ListNode curr = this;
        while (curr != null) {
            sb.append(curr.val);
            if (curr.next != null) {
                sb.append(",");
            }
            curr = curr.next;
        }
        sb.append("]");
        return sb.toString();
    }
    
    // 反序列化方法：將字符串轉換為鏈表
    public static ListNode deserialize(String data) {
        if (data == null || data.equals("[]")) {
            return null;
        }
        
        // 移除括號並分割字符串
        data = data.substring(1, data.length() - 1);
        String[] values = data.split(",");
        
        ListNode dummy = new ListNode(0);
        ListNode curr = dummy;
        
        for (String val : values) {
            curr.next = new ListNode(Integer.parseInt(val.trim()));
            curr = curr.next;
        }
        
        return dummy.next;
    }
}

class Solution {
    /**
     * Remove Nth Node From End of List 解題思路：
     * 1. 使用雙指針技巧（快慢指針）
     * 2. 快指針先走n+1步，建立間距
     * 3. 快慢指針同時移動，直到快指針到達末尾
     * 4. 此時慢指針指向要刪除節點的前一個節點
     * 5. 使用dummy節點處理刪除頭節點的特殊情況
     * 
     * 時間複雜度：O(L) - L為鏈表長度，只需遍歷一次
     * 空間複雜度：O(1) - 只使用常數額外空間
     */
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        
        ListNode fast = dummy;
        ListNode slow = dummy;
        
        // 快指針先走n+1步
        for (int i = 0; i <= n; i++) {
            fast = fast.next;
        }
        
        // 快慢指針同時走
        while (fast != null) {
            fast = fast.next;
            slow = slow.next;
        }
        
        // 刪除節點
        slow.next = slow.next.next;
        
        return dummy.next;
    }
}
