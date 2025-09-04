
/**
 * LeetCode 29. Divide Two Integers
 *
 * 解題思路：
 * 1. 不使用乘法、除法、mod運算符實現除法
 * 2. 使用位運算和減法模擬除法過程
 * 3. 處理符號：結果符號由兩數符號決定
 * 4. 使用倍增技巧加速計算過程
 * 5. 特別處理溢出和邊界情況
 *
 * 時間複雜度：O(log n)，使用位運算倍增加速
 * 空間複雜度：O(1)，只使用常數額外空間
 */
class Solution {

    public int divide(int dividend, int divisor) {
        // 處理溢出情況
        if (dividend == Integer.MIN_VALUE && divisor == -1) {
            return Integer.MAX_VALUE;
        }

        // 確定結果符號
        boolean negative = (dividend < 0) ^ (divisor < 0);

        // 轉為long避免溢出
        long ldividend = Math.abs((long) dividend);
        long ldivisor = Math.abs((long) divisor);

        long result = 0;

        // 使用位運算模擬除法
        while (ldividend >= ldivisor) {
            long temp = ldivisor;
            long multiple = 1;

            // 找到最大的倍數（使用位移加速）
            while (ldividend >= (temp << 1)) {
                temp <<= 1;
                multiple <<= 1;
            }

            ldividend -= temp;
            result += multiple;
        }

        return negative ? (int) -result : (int) result;
    }
}
