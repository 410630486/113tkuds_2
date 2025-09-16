
import java.util.*;

/**
 * LeetCode 25. Reverse Nodes in k-Group - 班表 k 組反轉
 *
 * 題目說明： 進行班表調整時，有時要把排班以 k 人一組做區段反轉（例如輪替順序）， 不足 k 的尾端保留原樣。給鏈結串列頭與 k，請原地分組反轉。
 *
 * 解題思路： 1. 檢查足夠長度再原地反轉該區段 2. 可寫輔助函式 reverse(start,end) 3. 迭代收集 k 節點再反轉
 *
 * 時間複雜度：O(n) 空間複雜度：O(1)
 */
class ListNode25 {

    int val;
    ListNode25 next;

    ListNode25() {
    }

    ListNode25(int val) {
        this.val = val;
    }

    ListNode25(int val, ListNode25 next) {
        this.val = val;
        this.next = next;
    }
}

public class LC25_ReverseKGroup_Shifts {

    public static ListNode25 reverseKGroup(ListNode25 head, int k) {
        if (head == null || k == 1) {
            return head;
        }

        // 檢查長度是否足夠 k
        ListNode25 curr = head;
        for (int i = 0; i < k; i++) {
            if (curr == null) {
                return head;
            }
            curr = curr.next;
        }

        // 反轉前 k 個節點
        ListNode25 prev = null;
        ListNode25 current = head;

        for (int i = 0; i < k; i++) {
            ListNode25 next = current.next;
            current.next = prev;
            prev = current;
            current = next;
        }

        // 遞歸處理剩餘部分
        head.next = reverseKGroup(current, k);

        return prev;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int k = scanner.nextInt();
        scanner.nextLine(); // 消耗換行符

        String line = scanner.nextLine().trim();
        if (line.isEmpty()) {
            System.out.println();
            scanner.close();
            return;
        }

        String[] nums = line.split(" ");

        ListNode25 dummy = new ListNode25(0);
        ListNode25 curr = dummy;

        for (String numStr : nums) {
            curr.next = new ListNode25(Integer.parseInt(numStr));
            curr = curr.next;
        }

        ListNode25 result = reverseKGroup(dummy.next, k);

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
