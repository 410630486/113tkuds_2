
import java.util.*;

// 合併兩院區掛號清單 - LeetCode 21
class MergeTwoSolution {

    public ListNodeBasic mergeTwoLists(ListNodeBasic list1, ListNodeBasic list2) {
        ListNodeBasic dummy = new ListNodeBasic(0);
        ListNodeBasic curr = dummy;

        while (list1 != null && list2 != null) {
            if (list1.val <= list2.val) {
                curr.next = list1;
                list1 = list1.next;
            } else {
                curr.next = list2;
                list2 = list2.next;
            }
            curr = curr.next;
        }

        curr.next = (list1 != null) ? list1 : list2;
        return dummy.next;
    }
}

class ListNodeBasic {

    int val;
    ListNodeBasic next;

    ListNodeBasic() {
    }

    ListNodeBasic(int val) {
        this.val = val;
    }

    ListNodeBasic(int val, ListNodeBasic next) {
        this.val = val;
        this.next = next;
    }
}

public class LC21_MergeTwoLists_Clinics {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();
        int m = scanner.nextInt();

        ListNodeBasic dummy1 = new ListNodeBasic(0);
        ListNodeBasic curr1 = dummy1;
        for (int i = 0; i < n; i++) {
            curr1.next = new ListNodeBasic(scanner.nextInt());
            curr1 = curr1.next;
        }

        ListNodeBasic dummy2 = new ListNodeBasic(0);
        ListNodeBasic curr2 = dummy2;
        for (int i = 0; i < m; i++) {
            curr2.next = new ListNodeBasic(scanner.nextInt());
            curr2 = curr2.next;
        }

        MergeTwoSolution solution = new MergeTwoSolution();
        ListNodeBasic result = solution.mergeTwoLists(dummy1.next, dummy2.next);

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
