/*
 * LeetCode 31: Next Permutation
 * 難度: Medium
 * 
 * 解題思路:
 * 1. 從右往左找第一個升序對(i, i+1)，nums[i] < nums[i+1]
 * 2. 從右往左找第一個比nums[i]大的數nums[j]
 * 3. 交換nums[i]和nums[j]
 * 4. 將i+1後面的數組反轉
 * 
 * 時間複雜度: O(n)
 * 空間複雜度: O(1)
 */

class Solution {

    public void nextPermutation(int[] nums) {
        int i = nums.length - 2;

        // 找到第一個升序對
        while (i >= 0 && nums[i] >= nums[i + 1]) {
            i--;
        }

        if (i >= 0) {
            int j = nums.length - 1;
            // 找到第一個比nums[i]大的數
            while (nums[j] <= nums[i]) {
                j--;
            }
            swap(nums, i, j);
        }

        // 反轉i+1後面的數組
        reverse(nums, i + 1);
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    private void reverse(int[] nums, int start) {
        int end = nums.length - 1;
        while (start < end) {
            swap(nums, start, end);
            start++;
            end--;
        }
    }
}

public class lt_31_nextpermutation {

    public static void main(String[] args) {
        Solution solution = new Solution();

        int[] nums1 = {1, 2, 3};
        solution.nextPermutation(nums1);
        System.out.print("結果1: ");
        for (int num : nums1) {
            System.out.print(num + " ");
        }
        System.out.println(); // 預期: [1, 3, 2]

        int[] nums2 = {3, 2, 1};
        solution.nextPermutation(nums2);
        System.out.print("結果2: ");
        for (int num : nums2) {
            System.out.print(num + " ");
        }
        System.out.println(); // 預期: [1, 2, 3]

        int[] nums3 = {1, 1, 5};
        solution.nextPermutation(nums3);
        System.out.print("結果3: ");
        for (int num : nums3) {
            System.out.print(num + " ");
        }
        System.out.println(); // 預期: [1, 5, 1]
    }
}
