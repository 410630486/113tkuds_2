
import java.util.*;

/**
 * LeetCode 34. Find First and Last Position of Element in Sorted Array -
 * 延誤等級首末定位
 *
 * 題目說明： 延誤等級（整數）被記錄在已排序的事件序列中，需快速取出某一等級值出現的 首個與最後一個索引（形成區間）以統計連續區塊。若該等級不存在輸出 -1
 * -1。
 *
 * 解題思路： 1. 兩次 lower_bound：右界 = lower_bound(target+1)-1 2. 或寫左右兩個專用二分 3.
 * 雙二分鎖定左右界
 *
 * 時間複雜度：O(log n) 空間複雜度：O(1)
 */
public class LC34_SearchRange_DelaySpan {

    public static int[] searchRange(int[] nums, int target) {
        int[] result = new int[]{-1, -1};

        if (nums == null || nums.length == 0) {
            return result;
        }

        // 找左邊界
        int left = findLeftBound(nums, target);
        if (left == -1) {
            return result;
        }

        // 找右邊界
        int right = findRightBound(nums, target);

        return new int[]{left, right};
    }

    private static int findLeftBound(int[] nums, int target) {
        int left = 0, right = nums.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (nums[mid] == target) {
                if (mid == 0 || nums[mid - 1] != target) {
                    return mid;
                } else {
                    right = mid - 1;
                }
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return -1;
    }

    private static int findRightBound(int[] nums, int target) {
        int left = 0, right = nums.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (nums[mid] == target) {
                if (mid == nums.length - 1 || nums[mid + 1] != target) {
                    return mid;
                } else {
                    left = mid + 1;
                }
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return -1;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();
        int target = scanner.nextInt();
        int[] nums = new int[n];

        for (int i = 0; i < n; i++) {
            nums[i] = scanner.nextInt();
        }

        int[] result = searchRange(nums, target);
        System.out.println(result[0] + " " + result[1]);

        scanner.close();
    }
}
