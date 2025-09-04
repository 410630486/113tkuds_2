
import java.util.*;

/**
 * LeetCode 27. Remove Element - 回收站清單移除指定元素
 *
 * 題目說明： 回收場分類清單陣列中某一指定代碼 val（代表已下架類別）要被全面移除。 就地覆寫留下非 val
 * 元素，回傳新有效長度與更新後前段（順序保持可讀性）。
 *
 * 解題思路： 1. write 只寫入不等於 val 的數 2. if (x!=val) nums[write++]=x
 *
 * 時間複雜度：O(n) 空間複雜度：O(1)
 */
public class LC27_RemoveElement_Recycle {

    public static int removeElement(int[] nums, int val) {
        int write = 0; // 寫入位置指針

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != val) {
                nums[write] = nums[i];
                write++;
            }
        }

        return write; // 返回新長度
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();
        int val = scanner.nextInt();
        int[] nums = new int[n];

        for (int i = 0; i < n; i++) {
            nums[i] = scanner.nextInt();
        }

        int newLength = removeElement(nums, val);

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
