class Solution {
    /**
     * Longest Palindromic Substring 解題思路：
     * 1. 使用中心擴展法 (Expand Around Centers)
     * 2. 對每個可能的中心點進行擴展檢查
     * 3. 考慮奇數長度回文（單一中心）和偶數長度回文（雙中心）
     * 4. 記錄最長回文的起始位置和長度
     * 5. 返回對應的子字串
     * 
     * 時間複雜度：O(n²) - 每個中心點最多擴展 n 次
     * 空間複雜度：O(1) - 只使用常數額外空間
     */
    public String longestPalindrome(String s) {
        // 特殊情況：空字串或單一字符
        if (s == null || s.length() <= 1) {
            return s;
        }
        
        // 記錄最長回文的起始位置和結束位置
        int start = 0;
        int maxLength = 1;
        
        // 遍歷每個可能的中心點
        for (int i = 0; i < s.length(); i++) {
            // 檢查奇數長度的回文（以 i 為中心）
            int len1 = expandAroundCenter(s, i, i);
            
            // 檢查偶數長度的回文（以 i 和 i+1 為中心）
            int len2 = expandAroundCenter(s, i, i + 1);
            
            // 取較長的回文長度
            int currentMaxLength = Math.max(len1, len2);
            
            // 如果找到更長的回文，更新記錄
            if (currentMaxLength > maxLength) {
                maxLength = currentMaxLength;
                // 計算回文的起始位置
                // 對於長度為 len 的回文，中心在 i，起始位置為 i - (len-1)/2
                start = i - (currentMaxLength - 1) / 2;
            }
        }
        
        // 返回最長回文子字串
        return s.substring(start, start + maxLength);
    }
    
    /**
     * 從指定中心點向外擴展，找到最長回文的長度
     * 
     * @param s 輸入字串
     * @param left 左中心點
     * @param right 右中心點
     * @return 以 left 和 right 為中心的最長回文長度
     */
    private int expandAroundCenter(String s, int left, int right) {
        // 向外擴展，直到字符不相等或超出邊界
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            left--;
            right++;
        }
        
        // 返回回文長度
        // 注意：當迴圈結束時，left 和 right 已經超出回文範圍一步
        return right - left - 1;
    }
}
