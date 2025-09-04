
/**
 * LeetCode 27. Remove Element
 *
 * 解題思路：
 * 1. 使用雙指針技術（快慢指針）
 * 2. 慢指針記錄結果陣列的位置
 * 3. 快指針遍歷陣列尋找非目標值元素
 * 4. 當快指針指向非目標值時，複製到慢指針位置
 * 5. 返回保留元素的總數
 *
 * 時間複雜度：O(n)，需要遍歷陣列一次
 * 空間複雜度：O(1)，只使用常數額外空間
 */
class Solution {

    public int removeElement(int[] nums, int val) {
        int slow = 0;  // 慢指針：記錄結果位置

        // 快指針遍歷陣列
        for (int fast = 0; fast < nums.length; fast++) {
            // 保留非目標值的元素
            if (nums[fast] != val) {
                nums[slow] = nums[fast];
                slow++;
            }
        }

        return slow;  // 返回保留元素的數量
    }
}
