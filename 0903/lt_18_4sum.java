
import java.util.*;

class Solution {

    /**
     * 4Sum 解題思路： 1. 先將陣列排序，使雙指針法成為可能 2. 固定前兩個數字，用雙指針在剩餘元素中搜索 3.
     * 對每個四元組，檢查和是否等於target 4. 跳過重複元素避免重複解 5. 將所有符合條件的四元組加入結果
     *
     * 時間複雜度：O(n³) - 排序O(n log n) + 三層迴圈O(n³) 空間複雜度：O(1) - 不考慮結果儲存空間
     */
    public List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> result = new ArrayList<>();

        // 處理邊界情況
        if (nums == null || nums.length < 4) {
            return result;
        }

        // 排序陣列
        Arrays.sort(nums);
        int n = nums.length;

        // 外層迴圈：固定第一個數字
        for (int i = 0; i < n - 3; i++) {
            // 跳過重複的第一個數字
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }

            // 中層迴圈：固定第二個數字
            for (int j = i + 1; j < n - 2; j++) {
                // 跳過重複的第二個數字
                if (j > i + 1 && nums[j] == nums[j - 1]) {
                    continue;
                }

                // 雙指針搜索第三、四個數字
                int left = j + 1;
                int right = n - 1;

                while (left < right) {
                    // 使用long避免溢出
                    long sum = (long) nums[i] + nums[j] + nums[left] + nums[right];

                    if (sum == target) {
                        // 找到有效的四元組
                        result.add(Arrays.asList(nums[i], nums[j], nums[left], nums[right]));

                        // 跳過重複的左指針元素
                        while (left < right && nums[left] == nums[left + 1]) {
                            left++;
                        }
                        // 跳過重複的右指針元素
                        while (left < right && nums[right] == nums[right - 1]) {
                            right--;
                        }

                        left++;
                        right--;
                    } else if (sum < target) {
                        left++;
                    } else {
                        right--;
                    }
                }
            }
        }

        return result;
    }
}
