
import java.util.*;

/**
 * LeetCode 26. Remove Duplicates from Sorted Array - 去重學生成績單
 *
 * 題目說明： 學生成績單（已依學號排序）因系統重複匯入，出現重複學號條目。 需就地（O(1) 額外空間）壓縮，使每個學號只出現一次，
 * 並回傳壓縮後長度與前段內容，用於後續產生報表。
 *
 * 解題思路： 1. write 指向可寫位置 2. 若當前值與前一保留不同則寫入 3. 注意 i 從 1 開始比較
 *
 * 時間複雜度：O(n) 空間複雜度：O(1)
 */
public class LC26_RemoveDuplicates_Scores {

    public static int removeDuplicates(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }

        int write = 0; // 慢指針，指向下一個要寫入的位置

        for (int i = 1; i < nums.length; i++) {
            if (nums[i] != nums[write]) {
                write++;
                nums[write] = nums[i];
            }
        }

        return write + 1; // 返回新長度
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();
        int[] nums = new int[n];

        for (int i = 0; i < n; i++) {
            nums[i] = scanner.nextInt();
        }

        int newLength = removeDuplicates(nums);

        // 輸出新長度
        System.out.println(newLength);

        // 輸出前段結果
        for (int i = 0; i < newLength; i++) {
            if (i > 0) {
                System.out.print(" ");
            }
            System.out.print(nums[i]);
        }
        if (newLength > 0) {
            System.out.println();
        }

        scanner.close();
    }
}
