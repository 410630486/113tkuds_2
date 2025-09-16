
import java.util.*;

/**
 * LeetCode 23. Merge k Sorted Lists - 多院區即時叫號合併
 *
 * 題目說明： 多院區即時叫號系統同時接收 k 個科別/院區的已排序候診名單， 需要快速整合成一條升序序列（依叫號優先權或時間）。
 * 請合併所有串列，效率需優於單純兩兩合併的 O(kN)。
 *
 * 解題思路： 1. 使用最小堆（PriorityQueue）維持所有當前頭節點 2. 每次取最小值推進那條串列 3. 初始將非空頭放堆
 *
 * 時間複雜度：O(N log k) 空間複雜度：O(k)
 */
class ListNode23 {

    int val;
    ListNode23 next;

    ListNode23() {
    }

    ListNode23(int val) {
        this.val = val;
    }

    ListNode23(int val, ListNode23 next) {
        this.val = val;
        this.next = next;
    }
}

public class LC23_MergeKLists_Hospitals {

    public static ListNode23 mergeKLists(ListNode23[] lists) {
        if (lists == null || lists.length == 0) {
            return null;
        }

        // 建立最小堆，依節點值排序
        PriorityQueue<ListNode23> minHeap = new PriorityQueue<>((a, b) -> a.val - b.val);

        // 將每個非空鏈表的頭節點加入優先佇列
        for (ListNode23 head : lists) {
            if (head != null) {
                minHeap.offer(head);
            }
        }

        // 使用 dummy 節點簡化結果鏈表組裝
        ListNode23 dummy = new ListNode23(0);
        ListNode23 current = dummy;

        // 遍歷直到 heap 清空
        while (!minHeap.isEmpty()) {
            ListNode23 node = minHeap.poll();
            current.next = node;
            current = current.next;

            // 如果該節點有下一個節點，加入優先佇列
            if (node.next != null) {
                minHeap.offer(node.next);
            }
        }

        return dummy.next;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int k = scanner.nextInt();
        scanner.nextLine(); // 消耗換行符

        ListNode23[] lists = new ListNode23[k];

        for (int i = 0; i < k; i++) {
            String line = scanner.nextLine();
            String[] nums = line.split(" ");

            ListNode23 dummy = new ListNode23(0);
            ListNode23 curr = dummy;

            for (String numStr : nums) {
                int num = Integer.parseInt(numStr);
                if (num == -1) {
                    break; // -1 結尾

                                }curr.next = new ListNode23(num);
                curr = curr.next;
            }
            lists[i] = dummy.next;
        }

        ListNode23 result = mergeKLists(lists);

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
