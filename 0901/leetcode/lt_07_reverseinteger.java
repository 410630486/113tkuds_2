class Solution {
    /**
     * Reverse Integer 解題思路：
     * 1. 逐位取出原數字的最後一位數字
     * 2. 構建反轉後的數字，每次乘以10再加上新的一位
     * 3. 在每次操作前檢查是否會發生整數溢出
     * 4. 使用數學方法檢查溢出，不使用64位整數
     * 5. 如果溢出則返回0，否則返回反轉結果
     * 
     * 時間複雜度：O(log x) - x 的位數
     * 空間複雜度：O(1) - 只使用常數額外空間
     */
    public int reverse(int x) {
        int result = 0;
        
        // 逐位處理直到 x 變為 0
        while (x != 0) {
            // 取出 x 的最後一位數字
            int digit = x % 10;
            x /= 10;
            
            // 檢查溢出情況
            // Integer.MAX_VALUE = 2147483647, Integer.MIN_VALUE = -2147483648
            
            // 檢查正向溢出
            if (result > Integer.MAX_VALUE / 10 || 
                (result == Integer.MAX_VALUE / 10 && digit > Integer.MAX_VALUE % 10)) {
                return 0;
            }
            
            // 檢查負向溢出
            if (result < Integer.MIN_VALUE / 10 || 
                (result == Integer.MIN_VALUE / 10 && digit < Integer.MIN_VALUE % 10)) {
                return 0;
            }
            
            // 更新結果：將新數字添加到結果的末尾
            result = result * 10 + digit;
        }
        
        return result;
    }
}
