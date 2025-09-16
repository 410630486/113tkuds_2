class Solution {
    /**
     * Container With Most Water 解題思路：
     * 1. 使用雙指標技巧 (Two Pointers)
     * 2. 左指標從最左邊開始，右指標從最右邊開始
     * 3. 每次移動較短的那一邊的指標
     * 4. 移動較短邊的原因：移動較長邊不可能得到更大的面積
     * 5. 持續記錄和更新最大面積
     * 
     * 時間複雜度：O(n) - 每個元素最多被訪問一次
     * 空間複雜度：O(1) - 只使用常數額外空間
     */
    public int maxArea(int[] height) {
        // 處理邊界情況
        if (height == null || height.length < 2) {
            return 0;
        }
        
        // 初始化雙指標
        int left = 0;
        int right = height.length - 1;
        int maxWater = 0;
        
        // 雙指標相向移動
        while (left < right) {
            // 計算當前容器的面積
            // 面積 = 寬度 × 較短邊的高度
            int width = right - left;
            int currentHeight = Math.min(height[left], height[right]);
            int currentArea = width * currentHeight;
            
            // 更新最大面積
            maxWater = Math.max(maxWater, currentArea);
            
            // 移動較短的那一邊
            // 原理：移動較長邊不會增加面積，因為高度受限於較短邊
            // 而寬度會減少，所以面積只會變小或相等
            if (height[left] < height[right]) {
                left++;
            } else {
                right--;
            }
        }
        
        return maxWater;
    }
}
