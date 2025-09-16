/*
 * LeetCode 33: Search in Rotated Sorted Array
 * 難度: Medium
 * 
 * 解題思路:
 * 1. 使用二分搜索
 * 2. 判斷哪一半是有序的
 * 3. 根據target的位置決定搜索哪一半
 * 
 * 時間複雜度: O(log n)
 * 空間複雜度: O(1)
 */

class Solution {

    public int search(int[] nums, int target) {
        int left = 0, right = nums.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (nums[mid] == target) {
                return mid;
            }

            // 左半部分有序
            if (nums[left] <= nums[mid]) {
                if (target >= nums[left] && target < nums[mid]) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            } // 右半部分有序
            else {
                if (target > nums[mid] && target <= nums[right]) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
        }

        return -1;
    }
}

public class lt_33_searchinrotatedsortedarray {

    public static void main(String[] args) {
        Solution solution = new Solution();

        int[] nums1 = {4, 5, 6, 7, 0, 1, 2};
        System.out.println(solution.search(nums1, 0));  // 預期: 4
        System.out.println(solution.search(nums1, 3));  // 預期: -1

        int[] nums2 = {1};
        System.out.println(solution.search(nums2, 0));  // 預期: -1
        System.out.println(solution.search(nums2, 1));  // 預期: 0
    }
}
