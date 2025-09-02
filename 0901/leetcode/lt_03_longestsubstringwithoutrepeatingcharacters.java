import java.util.HashMap;
import java.util.Map;

class Solution {
    /**
     * Longest Substring Without Repeating Characters 解題思路：
     * 1. 使用滑動窗口 (Sliding Window) 技巧
     * 2. 維護一個 HashMap 記錄字符最後出現的位置
     * 3. 使用兩個指標 left 和 right 維護窗口範圍
     * 4. 當遇到重複字符時，移動左指標到重複字符的下一個位置
     * 5. 持續更新最大長度
     * 
     * 時間複雜度：O(n) - 每個字符最多被訪問兩次
     * 空間複雜度：O(min(m,n)) - m 是字符集大小，n 是字串長度
     */
    public int lengthOfLongestSubstring(String s) {
        // 特殊情況：空字串
        if (s == null || s.length() == 0) {
            return 0;
        }
        
        // HashMap 存儲字符及其最後出現的索引位置
        Map<Character, Integer> charMap = new HashMap<>();
        
        // 滑動窗口的左邊界
        int left = 0;
        // 記錄最長子字串的長度
        int maxLength = 0;
        
        // 右指標遍歷整個字串
        for (int right = 0; right < s.length(); right++) {
            char currentChar = s.charAt(right);
            
            // 如果當前字符已存在且在當前窗口內
            if (charMap.containsKey(currentChar) && charMap.get(currentChar) >= left) {
                // 移動左指標到重複字符的下一個位置
                left = charMap.get(currentChar) + 1;
            }
            
            // 更新當前字符的最新位置
            charMap.put(currentChar, right);
            
            // 計算當前窗口的長度並更新最大值
            int currentLength = right - left + 1;
            maxLength = Math.max(maxLength, currentLength);
        }
        
        return maxLength;
    }
}
