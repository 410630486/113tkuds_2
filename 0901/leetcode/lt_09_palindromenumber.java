class Solution {
    /**
     * Palindrome Number 解題思路：
     * 1. 負數不可能是回文數，直接返回 false
     * 2. 反轉數字的一半，避免整數溢出問題
     * 3. 比較原數字的前半部分與反轉後的後半部分
     * 4. 考慮奇數位數和偶數位數的不同情況
     * 5. 當反轉數字 >= 原數字時停止反轉
     * 
     * 時間複雜度：O(log x) - 處理 x 的一半位數
     * 空間複雜度：O(1) - 只使用常數額外空間
     */
    public boolean isPalindrome(int x) {
        // 特殊情況處理
        // 1. 負數不是回文數
        // 2. 除了 0 以外，末尾為 0 的數字不是回文數（如 10, 100）
        if (x < 0 || (x % 10 == 0 && x != 0)) {
            return false;
        }
        
        // 單位數字一定是回文數
        if (x < 10) {
            return true;
        }
        
        int reversedHalf = 0;
        
        // 反轉數字的後半部分
        // 當原數字小於等於反轉數字時，表示已經處理了一半
        while (x > reversedHalf) {
            reversedHalf = reversedHalf * 10 + x % 10;
            x /= 10;
        }
        
        // 檢查是否為回文數
        // x == reversedHalf: 偶數位數的情況 (如 1221)
        // x == reversedHalf / 10: 奇數位數的情況 (如 12321，中間的數字可以忽略)
        return x == reversedHalf || x == reversedHalf / 10;
    }
}
