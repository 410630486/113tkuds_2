/*
 * LeetCode 35: Search Insert Position
 * 難度: Easy
 * 
 * 解題思路:
 * 1. 使用二分搜索
 * 2. 如果找到target直接返回索引
 * 3. 如果沒找到，返回應該插入的位置
 * 
 * 時間複雜度: O(log n)
 * 空間複雜度: O(1)
 */

class Solution {

    public int searchInsert(int[] nums, int target) {
        int left = 0, right = nums.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return left; // 插入位置
    }
}

public class lt_35_searchinsertposition {

    public static void main(String[] args) {
        Solution solution = new Solution();

        int[] nums = {1, 3, 5, 6};

        System.out.println(solution.searchInsert(nums, 5)); // 預期: 2
        System.out.println(solution.searchInsert(nums, 2)); // 預期: 1
        System.out.println(solution.searchInsert(nums, 7)); // 預期: 4
        System.out.println(solution.searchInsert(nums, 0)); // 預期: 0
    }
}
