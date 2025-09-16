
import java.util.*;

/**
 * LeetCode 33. Search in Rotated Sorted Array - 旋轉陣列搜尋
 *
 * 題目說明： 一份原本升序排列的設備 ID 清單在部署時被切成兩段再拼接（旋轉）， 需要在這個「被旋轉的升序陣列」中查找指定設備 target
 * 的位置，找不到回 -1。
 *
 * 解題思路： 1. 每次二分中點，判斷左或右區是否有序並選擇縮小哪側 2. if nums[l] <= nums[mid] 左半有序 3.
 * 改良二分依有序半部決策
 *
 * 時間複雜度：O(log n) 空間複雜度：O(1)
 */
public class LC33_SearchRotated_RentHot {

    public static int search(int[] nums, int target) {
        int left = 0, right = nums.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (nums[mid] == target) {
                return mid;
            }

            // 判斷哪一半是有序的
            if (nums[left] <= nums[mid]) {
                // 左半有序
                if (target >= nums[left] && target < nums[mid]) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            } else {
                // 右半有序
                if (target > nums[mid] && target <= nums[right]) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
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

        int result = search(nums, target);
        System.out.println(result);

        scanner.close();
    }
}
