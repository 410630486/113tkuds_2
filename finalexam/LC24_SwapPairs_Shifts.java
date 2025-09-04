
import java.util.*;

/**
 * LeetCode 24. Swap Nodes in Pairs - 班表兩兩交換
 *
 * 題目說明： 班表資料以鏈結串列儲存，每節點是一個時段。主管要快速檢視「成對輪班互換」的情況模擬， 只需將整條串列中相鄰兩節點成對交換位置（1↔2, 3↔4
 * …），長度為奇數時最後一個保留。
 *
 * 解題思路： 1. prev 指向 pair 前 2. 交換 (a,b) → prev.next=b, a.next=b.next, b.next=a 3.
 * Dummy + while (a!=null && a.next!=null)
 *
 * 時間複雜度：O(n) 空間複雜度：O(1)
 */
class ListNode24 {

    int val;
    ListNode24 next;

    ListNode24() {
    }

    ListNode24(int val) {
        this.val = val;
    }

    ListNode24(int val, ListNode24 next) {
        this.val = val;
        this.next = next;
    }
}

public class LC24_SwapPairs_Shifts {

    public static ListNode24 swapPairs(ListNode24 head) {
        ListNode24 dummy = new ListNode24(0);
        dummy.next = head;
        ListNode24 prev = dummy;

        while (prev.next != null && prev.next.next != null) {
            ListNode24 a = prev.next;
            ListNode24 b = prev.next.next;

            // 執行交換
            prev.next = b;
            a.next = b.next;
            b.next = a;

            // 移動 prev 到下一對的前面
            prev = a;
        }

        return dummy.next;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String line = scanner.nextLine().trim();
        if (line.isEmpty()) {
            System.out.println();
            scanner.close();
            return;
        }

        String[] nums = line.split(" ");

        ListNode24 dummy = new ListNode24(0);
        ListNode24 curr = dummy;

        for (String numStr : nums) {
            curr.next = new ListNode24(Integer.parseInt(numStr));
            curr = curr.next;
        }

        ListNode24 result = swapPairs(dummy.next);

        // 輸出結果
        List<Integer> output = new ArrayList<>();
        while (result != null) {
            output.add(result.val);
            result = result.next;
        }

        for (int i = 0; i < output.size(); i++) {
            if (i > 0) {
                System.out.print(" ");
            }
            System.out.print(output.get(i));
        }
        if (!output.isEmpty()) {
            System.out.println();
        }

        scanner.close();
    }
}
