
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

    // LeetCode 環境需要的方法 - 從 JsonArray 創建鏈表（使用反射避免編譯錯誤）
    public static ListNode arrayToListNode(Object jsonArray) {
        if (jsonArray == null) {
            return null;
        }

        try {
            // 使用反射調用 JsonArray 方法
            Class<?> jsonArrayClass = jsonArray.getClass();
            java.lang.reflect.Method sizeMethod = jsonArrayClass.getMethod("size");
            java.lang.reflect.Method getMethod = jsonArrayClass.getMethod("get", int.class);

            int size = (Integer) sizeMethod.invoke(jsonArray);
            if (size == 0) {
                return null;
            }

            ListNode dummy = new ListNode(0);
            ListNode curr = dummy;

            for (int i = 0; i < size; i++) {
                Object element = getMethod.invoke(jsonArray, i);
                java.lang.reflect.Method asIntMethod = element.getClass().getMethod("asInt");
                int val = (Integer) asIntMethod.invoke(element);
                curr.next = new ListNode(val);
                curr = curr.next;
            }

            return dummy.next;
        } catch (Exception e) {
            return null;
        }
    }
}

/**
 * LeetCode 21. Merge Two Sorted Lists
 *
 * 解題思路： 1. 使用dummy節點簡化邊界處理 2. 比較兩個鏈表當前節點的值 3. 將較小值的節點接到結果鏈表 4. 移動對應鏈表的指針 5.
 * 處理剩餘未比較完的節點
 *
 * 時間複雜度：O(m + n)，其中 m 和 n 分別為兩個鏈表長度 空間複雜度：O(1)，只使用常數額外空間
 */
class Solution {

    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        // 建立dummy節點簡化邊界處理
        ListNode dummy = new ListNode(0);
        ListNode current = dummy;

        // 比較兩個鏈表的節點值，選擇較小的
        while (list1 != null && list2 != null) {
            if (list1.val <= list2.val) {
                current.next = list1;
                list1 = list1.next;
            } else {
                current.next = list2;
                list2 = list2.next;
            }
            current = current.next;
        }

        // 連接剩餘的節點（其中一個鏈表已經遍歷完）
        current.next = (list1 != null) ? list1 : list2;

        return dummy.next;
    }
}
