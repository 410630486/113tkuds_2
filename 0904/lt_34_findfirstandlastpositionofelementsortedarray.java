/*
 * LeetCode 34: Find First and Last Position of Element in Sorted Array
 * 難度: Medium
 * 
 * 解題思路:
 * 1. 使用兩次二分搜索
 * 2. 第一次找左邊界
 * 3. 第二次找右邊界
 * 
 * 時間複雜度: O(log n)
 * 空間複雜度: O(1)
 */

class Solution {

    public int[] searchRange(int[] nums, int target) {
        int[] result = new int[]{-1, -1};

        if (nums == null || nums.length == 0) {
            return result;
        }

        // 找左邊界
        result[0] = findLeftBound(nums, target);
        if (result[0] == -1) {
            return result;
        }

        // 找右邊界
        result[1] = findRightBound(nums, target);

        return result;
    }

    private int findLeftBound(int[] nums, int target) {
        int left = 0, right = nums.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (nums[mid] == target) {
                right = mid - 1; // 繼續向左搜索
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return (left < nums.length && nums[left] == target) ? left : -1;
    }

    private int findRightBound(int[] nums, int target) {
        int left = 0, right = nums.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (nums[mid] == target) {
                left = mid + 1; // 繼續向右搜索
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return (right >= 0 && nums[right] == target) ? right : -1;
    }
}

public class lt_34_findfirstandlastpositionofelementsortedarray {

    public static void main(String[] args) {
        Solution solution = new Solution();

        int[] nums1 = {5, 7, 7, 8, 8, 10};
        int[] result1 = solution.searchRange(nums1, 8);
        System.out.println("結果1: [" + result1[0] + ", " + result1[1] + "]"); // 預期: [3, 4]

        int[] result2 = solution.searchRange(nums1, 6);
        System.out.println("結果2: [" + result2[0] + ", " + result2[1] + "]"); // 預期: [-1, -1]

        int[] nums3 = {};
        int[] result3 = solution.searchRange(nums3, 0);
        System.out.println("結果3: [" + result3[0] + ", " + result3[1] + "]"); // 預期: [-1, -1]
    }
}
