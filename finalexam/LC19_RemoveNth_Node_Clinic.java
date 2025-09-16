
import java.util.*;

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
}

/**
 * LeetCode 19. Remove Nth Node From End of List
 */
class RemoveNthSolution {

    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;

        ListNode fast = dummy;
        ListNode slow = dummy;

        // fast 先走 n+1 步
        for (int i = 0; i <= n; i++) {
            fast = fast.next;
        }

        // 同時移動直到 fast 到達末尾
        while (fast != null) {
            fast = fast.next;
            slow = slow.next;
        }

        // 刪除節點
        slow.next = slow.next.next;

        return dummy.next;
    }
}

public class LC19_RemoveNth_Node_Clinic {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();

        // 創建鏈表
        ListNode dummy = new ListNode(0);
        ListNode curr = dummy;

        for (int i = 0; i < n; i++) {
            curr.next = new ListNode(scanner.nextInt());
            curr = curr.next;
        }

        int k = scanner.nextInt();

        RemoveNthSolution solution = new RemoveNthSolution();
        ListNode result = solution.removeNthFromEnd(dummy.next, k);

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
