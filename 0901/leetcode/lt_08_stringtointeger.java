class Solution {
    /**
     * String to Integer (atoi) 解題思路：
     * 1. 忽略前導空白字符
     * 2. 檢查符號（+/-），預設為正數
     * 3. 讀取數字字符直到遇到非數字字符或字串結束
     * 4. 在構建過程中檢查32位整數溢出
     * 5. 如果溢出則返回對應的邊界值
     * 
     * 時間複雜度：O(n) - 遍歷字串一次
     * 空間複雜度：O(1) - 只使用常數額外空間
     */
    public int myAtoi(String s) {
        // 處理空字串
        if (s == null || s.length() == 0) {
            return 0;
        }
        
        int index = 0;
        int n = s.length();
        
        // 步驟1: 忽略前導空白
        while (index < n && s.charAt(index) == ' ') {
            index++;
        }
        
        // 如果全部都是空白字符
        if (index == n) {
            return 0;
        }
        
        // 步驟2: 檢查符號
        int sign = 1;
        if (s.charAt(index) == '+' || s.charAt(index) == '-') {
            sign = (s.charAt(index) == '-') ? -1 : 1;
            index++;
        }
        
        // 步驟3: 轉換數字
        int result = 0;
        while (index < n && Character.isDigit(s.charAt(index))) {
            int digit = s.charAt(index) - '0';
            
            // 步驟4: 檢查溢出
            // 檢查正向溢出
            if (result > Integer.MAX_VALUE / 10 || 
                (result == Integer.MAX_VALUE / 10 && digit > Integer.MAX_VALUE % 10)) {
                return sign == 1 ? Integer.MAX_VALUE : Integer.MIN_VALUE;
            }
            
            // 構建結果
            result = result * 10 + digit;
            index++;
        }
        
        // 步驟5: 應用符號並返回結果
        return sign * result;
    }
}
