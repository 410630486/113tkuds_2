class Solution {
    /**
     * Longest Common Prefix 解題思路：
     * 1. 垂直掃描法：逐個字符位置進行比較
     * 2. 以第一個字串為基準，與其他字串逐位比較
     * 3. 當遇到不匹配或任一字串結束時停止
     * 4. 處理特殊情況：空陣列、空字串等
     * 5. 返回共同前綴的子字串
     * 
     * 時間複雜度：O(S) - S 是所有字串字符的總數
     * 空間複雜度：O(1) - 只使用常數額外空間
     */
    public String longestCommonPrefix(String[] strs) {
        // 處理邊界情況
        if (strs == null || strs.length == 0) {
            return "";
        }
        
        // 只有一個字串時，該字串就是共同前綴
        if (strs.length == 1) {
            return strs[0];
        }
        
        // 以第一個字串為基準進行比較
        String firstStr = strs[0];
        
        // 垂直掃描：逐個字符位置比較
        for (int i = 0; i < firstStr.length(); i++) {
            char currentChar = firstStr.charAt(i);
            
            // 檢查其他所有字串在位置 i 的字符
            for (int j = 1; j < strs.length; j++) {
                String currentStr = strs[j];
                
                // 檢查兩種停止條件：
                // 1. 當前字串長度不足（已到達字串結尾）
                // 2. 當前位置的字符不匹配
                if (i >= currentStr.length() || currentStr.charAt(i) != currentChar) {
                    // 返回到目前為止的共同前綴
                    return firstStr.substring(0, i);
                }
            }
        }
        
        // 如果完整掃描了第一個字串都沒有不匹配
        // 說明第一個字串就是最長共同前綴
        return firstStr;
    }
}
