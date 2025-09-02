import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Solution {
    /**
     * 3Sum 解題思路：
     * 1. 先對陣列排序，便於使用雙指標和跳過重複元素
     * 2. 固定第一個數字，將問題轉換為 2Sum 問題
     * 3. 使用雙指標技巧找到剩餘兩個數字
     * 4. 跳過重複元素避免重複的三元組
     * 5. 優化：當第一個數字大於0時提前結束
     * 
     * 時間複雜度：O(n²) - 外層迴圈O(n)，內層雙指標O(n)
     * 空間複雜度：O(1) - 不計算結果陣列的額外空間
     */
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        
        // 處理邊界情況
        if (nums == null || nums.length < 3) {
            return result;
        }
        
        // 排序陣列，便於使用雙指標和跳過重複元素
        Arrays.sort(nums);
        
        // 固定第一個數字
        for (int i = 0; i < nums.length - 2; i++) {
            // 優化：如果第一個數字大於0，後面不可能有和為0的三元組
            if (nums[i] > 0) {
                break;
            }
            
            // 跳過重複的第一個數字
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            
            // 使用雙指標找剩餘兩個數字
            int left = i + 1;
            int right = nums.length - 1;
            int target = -nums[i]; // 目標和為 0 - nums[i]
            
            while (left < right) {
                int sum = nums[left] + nums[right];
                
                if (sum == target) {
                    // 找到一個三元組
                    result.add(Arrays.asList(nums[i], nums[left], nums[right]));
                    
                    // 跳過左側重複元素
                    while (left < right && nums[left] == nums[left + 1]) {
                        left++;
                    }
                    
                    // 跳過右側重複元素
                    while (left < right && nums[right] == nums[right - 1]) {
                        right--;
                    }
                    
                    // 移動雙指標
                    left++;
                    right--;
                    
                } else if (sum < target) {
                    // 和太小，移動左指標增大和
                    left++;
                } else {
                    // 和太大，移動右指標減小和
                    right--;
                }
            }
        }
        
        return result;
    }
}
