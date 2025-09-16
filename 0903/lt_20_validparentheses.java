
import java.util.Stack;

/**
 * LeetCode 20. Valid Parentheses
 *
 * 解題思路： 1. 使用堆疊（Stack）來檢查括號匹配 2. 遇到左括號（'(', '[', '{'）就壓入堆疊 3. 遇到右括號（')', ']',
 * '}'）就檢查是否與堆疊頂端匹配 4. 如果堆疊為空或不匹配則返回false 5. 最後檢查堆疊是否為空（確保所有括號都有匹配）
 *
 * 時間複雜度：O(n) - 需要遍歷字串一次 空間複雜度：O(n) - 最壞情況下所有字符都是左括號
 */
class Solution {

    public boolean isValid(String s) {
        Stack<Character> stack = new Stack<>();

        // 遍歷字串中的每個字符
        for (char c : s.toCharArray()) {
            // 遇到左括號就壓入堆疊
            if (c == '(' || c == '[' || c == '{') {
                stack.push(c);
            } // 遇到右括號就檢查匹配
            else if (c == ')' || c == ']' || c == '}') {
                // 如果堆疊為空，表示沒有對應的左括號
                if (stack.isEmpty()) {
                    return false;
                }

                // 檢查是否與堆疊頂端的左括號匹配
                char top = stack.pop();
                if ((c == ')' && top != '(')
                        || (c == ']' && top != '[')
                        || (c == '}' && top != '{')) {
                    return false;
                }
            }
        }

        // 最後檢查堆疊是否為空（所有括號都已匹配）
        return stack.isEmpty();
    }
}
