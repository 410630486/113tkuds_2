import java.util.Arrays;

class Solution {
    /**
     * 3Sum Closest 解題思路：
     * 1. 先將陣列排序，使雙指針法成為可能
     * 2. 固定第一個數字，用雙指針在剩餘元素中搜索
     * 3. 計算三數之和，比較與target的距離
     * 4. 根據和與target的關係移動指針：
     *    - 和 < target：左指針右移（增大和）
     *    - 和 > target：右指針左移（減小和）
     * 5. 持續更新最接近target的和
     * 
     * 時間複雜度：O(n²) - 排序O(n log n) + 雙重迴圈O(n²)
     * 空間複雜度：O(1) - 只使用常數額外空間
     */
    public int threeSumClosest(int[] nums, int target) {
        // 排序陣列，為雙指針法做準備
        Arrays.sort(nums);
        
        int n = nums.length;
        // 初始化最接近的和為前三個元素的和
        int closestSum = nums[0] + nums[1] + nums[2];
        
        // 遍歷陣列，固定第一個數字
        for (int i = 0; i < n - 2; i++) {
            // 跳過重複的元素
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            
            // 設置雙指針
            int left = i + 1;
            int right = n - 1;
            
            while (left < right) {
                // 計算當前三數之和
                int currentSum = nums[i] + nums[left] + nums[right];
                
                // 如果找到完全匹配，直接返回
                if (currentSum == target) {
                    return currentSum;
                }
                
                // 如果當前和更接近目標值，更新記錄
                if (Math.abs(currentSum - target) < Math.abs(closestSum - target)) {
                    closestSum = currentSum;
                }
                
                // 根據和與目標值的關係移動指針
                if (currentSum < target) {
                    left++;
                } else {
                    right--;
                }
            }
        }
        
        return closestSum;
    }
}
