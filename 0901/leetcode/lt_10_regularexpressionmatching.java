class Solution {
    /**
     * Regular Expression Matching 解題思路：
     * 1. 使用動態規劃 (Dynamic Programming) 解決
     * 2. dp[i][j] 表示 s[0...i-1] 是否匹配 p[0...j-1]
     * 3. 處理三種情況：普通字符、'.' 萬用字符、'*' 重複字符
     * 4. '*' 字符需要考慮匹配 0 次或多次的情況
     * 5. 從底向上構建解答
     * 
     * 時間複雜度：O(m*n) - m 是字串長度，n 是模式長度
     * 空間複雜度：O(m*n) - 二維 DP 表
     */
    public boolean isMatch(String s, String p) {
        int m = s.length();
        int n = p.length();
        
        // dp[i][j] 表示 s[0...i-1] 是否匹配 p[0...j-1]
        boolean[][] dp = new boolean[m + 1][n + 1];
        
        // 基本情況：空字串匹配空模式
        dp[0][0] = true;
        
        // 處理空字串與帶 '*' 的模式匹配情況
        // 例如：s="", p="a*b*c*" 應該返回 true
        for (int j = 2; j <= n; j++) {
            if (p.charAt(j - 1) == '*') {
                dp[0][j] = dp[0][j - 2];
            }
        }
        
        // 填充 DP 表
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                char sChar = s.charAt(i - 1);
                char pChar = p.charAt(j - 1);
                
                if (pChar == '*') {
                    // 當前模式字符是 '*'
                    char prevPChar = p.charAt(j - 2);
                    
                    // 情況1：'*' 匹配 0 次，忽略 "前一個字符*" 這個組合
                    dp[i][j] = dp[i][j - 2];
                    
                    // 情況2：'*' 匹配 1 次或多次
                    // 前提：前一個字符能夠匹配當前字串字符
                    if (prevPChar == sChar || prevPChar == '.') {
                        dp[i][j] = dp[i][j] || dp[i - 1][j];
                    }
                } else if (pChar == '.' || pChar == sChar) {
                    // 當前模式字符是 '.' 或與字串字符相同
                    dp[i][j] = dp[i - 1][j - 1];
                }
                // 如果都不匹配，dp[i][j] 保持 false（預設值）
            }
        }
        
        return dp[m][n];
    }
}
