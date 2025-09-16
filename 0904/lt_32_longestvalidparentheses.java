/*
 * LeetCode 32: Longest Valid Parentheses
 * 難度: Hard
 * 
 * 解題思路:
 * 1. 使用動態規劃
 * 2. dp[i]表示以索引i結尾的最長有效括號長度
 * 3. 當s[i] = ')'時，檢查匹配情況
 * 
 * 時間複雜度: O(n)
 * 空間複雜度: O(n)
 */

class Solution {

    public int longestValidParentheses(String s) {
        int n = s.length();
        int[] dp = new int[n];
        int maxLen = 0;

        for (int i = 1; i < n; i++) {
            if (s.charAt(i) == ')') {
                if (s.charAt(i - 1) == '(') {
                    // 情況1: ...()
                    dp[i] = (i >= 2 ? dp[i - 2] : 0) + 2;
                } else if (dp[i - 1] > 0) {
                    // 情況2: ...))
                    int matchIndex = i - dp[i - 1] - 1;
                    if (matchIndex >= 0 && s.charAt(matchIndex) == '(') {
                        dp[i] = dp[i - 1] + 2 + (matchIndex > 0 ? dp[matchIndex - 1] : 0);
                    }
                }
                maxLen = Math.max(maxLen, dp[i]);
            }
        }

        return maxLen;
    }
}

public class lt_32_longestvalidparentheses {

    public static void main(String[] args) {
        Solution solution = new Solution();

        System.out.println(solution.longestValidParentheses("(()"));    // 預期: 2
        System.out.println(solution.longestValidParentheses(")()())"));  // 預期: 4
        System.out.println(solution.longestValidParentheses(""));       // 預期: 0
        System.out.println(solution.longestValidParentheses("()(()"));  // 預期: 2
    }
}
