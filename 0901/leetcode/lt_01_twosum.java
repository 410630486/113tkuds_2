import java.util.HashMap;
import java.util.Map;

class Solution {
    /**
     * Two Sum 解題思路：
     * 1. 使用 HashMap 儲存已遍歷過的數字及其索引
     * 2. 對於每個數字，計算其補數 (target - 當前數字)
     * 3. 如果補數已存在於 HashMap 中，表示找到答案
     * 4. 否則將當前數字和索引存入 HashMap 供後續查找
     * 
     * 時間複雜度：O(n) - 只需遍歷陣列一次
     * 空間複雜度：O(n) - HashMap 最多存儲 n 個元素
     */
    public int[] twoSum(int[] nums, int target) {
        // 創建 HashMap 來存儲 <數值, 索引> 的映射關係
        Map<Integer, Integer> map = new HashMap<>();
        
        // 遍歷整個陣列
        for (int i = 0; i < nums.length; i++) {
            // 計算當前數字的補數 (兩數相加等於 target 的另一個數)
            int complement = target - nums[i];
            
            // 檢查補數是否已經在 HashMap 中
            if (map.containsKey(complement)) {
                // 如果找到補數，返回補數的索引和當前索引
                return new int[]{map.get(complement), i};
            }
            
            // 將當前數字和其索引存入 HashMap
            // 格式：map.put(數值, 索引)
            map.put(nums[i], i);
        }
        
        // 根據題目保證有解，這行程式碼不會執行到
        return new int[0];
    }
}
