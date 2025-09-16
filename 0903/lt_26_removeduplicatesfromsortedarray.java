
/**
 * LeetCode 26. Remove Duplicates from Sorted Array
 *
 * 解題思路：
 * 1. 使用雙指針技術（快慢指針）
 * 2. 慢指針記錄不重複元素的位置
 * 3. 快指針遍歷整個陣列尋找新元素
 * 4. 當找到新元素時，將其放到慢指針位置
 * 5. 返回不重複元素的總數
 *
 * 時間複雜度：O(n)，需要遍歷陣列一次
 * 空間複雜度：O(1)，只使用常數額外空間
 */
class Solution {

    public int removeDuplicates(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int slow = 0;  // 慢指針：記錄不重複元素位置

        // 快指針遍歷陣列
        for (int fast = 1; fast < nums.length; fast++) {
            // 發現新的不重複元素
            if (nums[fast] != nums[slow]) {
                slow++;
                nums[slow] = nums[fast];
            }
        }

        return slow + 1;  // 返回不重複元素的數量
    }
}
