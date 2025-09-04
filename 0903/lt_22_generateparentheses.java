
import java.util.ArrayList;
import java.util.List;

/**
 * LeetCode 22. Generate Parentheses
 *
 * 解題思路： 1. 使用回溯法（Backtracking）生成所有有效組合 2. 維護左括號和右括號的計數 3. 確保左括號數量不超過n 4.
 * 確保右括號數量不超過左括號數量 5. 當字串長度達到2n時，找到一個有效組合
 *
 * 時間複雜度：O(4^n / √n)，卡塔蘭數的近似值 空間複雜度：O(4^n / √n)，儲存所有有效組合
 */
class Solution {

    public List<String> generateParenthesis(int n) {
        List<String> result = new ArrayList<>();
        backtrack(result, "", 0, 0, n);
        return result;
    }

    private void backtrack(List<String> result, String current, int open, int close, int max) {
        // 當前字串長度達到最大值時，加入結果集
        if (current.length() == max * 2) {
            result.add(current);
            return;
        }

        // 如果左括號數量還沒達到上限，可以添加左括號
        if (open < max) {
            backtrack(result, current + "(", open + 1, close, max);
        }

        // 如果右括號數量少於左括號，可以添加右括號
        if (close < open) {
            backtrack(result, current + ")", open, close + 1, max);
        }
    }
}
