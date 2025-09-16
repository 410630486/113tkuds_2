/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
import java.util.PriorityQueue;
import java.util.Comparator;

class Solution {
    public ListNode mergeKLists(ListNode[] lists) {
        // 建立 min-heap，依節點值排序
        PriorityQueue<ListNode> minHeap = new PriorityQueue<>(Comparator.comparingInt(a -> a.val));

        // 將每個非空鏈表的頭節點加入優先佇列
        for (ListNode head : lists) {
            if (head != null) {
                minHeap.offer(head);
            }
        }

        // 使用 dummy 節點簡化結果鏈表組裝
        ListNode dummy = new ListNode(0);
        ListNode current = dummy;

        // 遍歷直到 heap 清空
        while (!minHeap.isEmpty()) {
            ListNode node = minHeap.poll();
            current.next = node;
            current = current.next;

            if (node.next != null) {
                minHeap.offer(node.next);
            }
        }

        return dummy.next;
    }
}
