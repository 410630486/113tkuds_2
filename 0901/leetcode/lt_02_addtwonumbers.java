class ListNode {
    int val;
    ListNode next;
    ListNode() {}
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    
    // LeetCode 平台需要的反序列化方法
    public static ListNode deserialize(String data) {
        if (data == null || data.equals("[]")) {
            return null;
        }
        
        data = data.substring(1, data.length() - 1);
        String[] values = data.split(",");
        
        ListNode dummy = new ListNode(0);
        ListNode current = dummy;
        
        for (String value : values) {
            current.next = new ListNode(Integer.parseInt(value.trim()));
            current = current.next;
        }
        
        return dummy.next;
    }
}

class Solution {
    /**
     * Add Two Numbers 解題思路：
     * 1. 同時遍歷兩個鏈結串列，從最低位數字開始相加
     * 2. 處理進位問題 (carry)，當兩數相加 >= 10 時需要進位
     * 3. 創建新的鏈結串列來存放結果
     * 4. 處理兩個串列長度不同的情況
     * 5. 最後檢查是否還有進位需要處理
     * 
     * 時間複雜度：O(max(m,n)) - m, n 分別為兩個鏈結串列的長度
     * 空間複雜度：O(max(m,n)) - 結果鏈結串列的長度
     */
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        // 創建虛擬頭節點，簡化鏈結串列操作
        ListNode dummyHead = new ListNode(0);
        // current 指標用來構建結果鏈結串列
        ListNode current = dummyHead;
        // 進位變數，記錄上一位的進位值
        int carry = 0;
        
        // 當 l1 或 l2 還有節點，或者還有進位時，繼續處理
        while (l1 != null || l2 != null || carry != 0) {
            // 取得當前位的數值，如果節點為 null 則視為 0
            int x = (l1 != null) ? l1.val : 0;
            int y = (l2 != null) ? l2.val : 0;
            
            // 計算當前位的總和：兩數相加再加上進位
            int sum = x + y + carry;
            
            // 計算新的進位值（總和除以10的商）
            carry = sum / 10;
            
            // 當前位的結果（總和除以10的餘數）
            int digit = sum % 10;
            
            // 創建新節點存放當前位結果
            current.next = new ListNode(digit);
            // 移動 current 指標到新創建的節點
            current = current.next;
            
            // 移動 l1 和 l2 指標到下一個節點（如果存在）
            if (l1 != null) l1 = l1.next;
            if (l2 != null) l2 = l2.next;
        }
        
        // 返回結果鏈結串列（跳過虛擬頭節點）
        return dummyHead.next;
    }
}
