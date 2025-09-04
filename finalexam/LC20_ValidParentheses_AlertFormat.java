
import java.util.*;

/**
 * LeetCode 20. Valid Parentheses - 緊急通報格式括號檢查
 *
 * 題目說明： 災防緊急通報訊息使用一套輕量標記：() 表可選段、[] 表區域代碼群組、{} 表變數替換區。
 * 系統在發布前需驗證字串是否「完全正確巢狀」且無錯配。 給一段只含 ()[]{} 的格式字串，判斷括號是否成對、順序合法、無交錯錯誤。
 *
 * 解題思路： 1. 使用堆疊（Stack）來追蹤開括號 2. 遇到開括號時推入堆疊 3. 遇到閉括號時檢查堆疊頂是否為對應的開括號 4.
 * 如果匹配則彈出，不匹配則返回 false 5. 最終堆疊必須為空
 *
 * 時間複雜度：O(n) 空間複雜度：O(n)
 */
class ValidParenSolution {

    public boolean isValid(String s) {
        // 處理邊界情況
        if (s == null || s.length() == 0) {
            return true;
        }

        // 建立閉括號到開括號的映射
        Map<Character, Character> closeToOpen = new HashMap<>();
        closeToOpen.put(')', '(');
        closeToOpen.put(']', '[');
        closeToOpen.put('}', '{');

        Stack<Character> stack = new Stack<>();

        for (char ch : s.toCharArray()) {
            if (ch == '(' || ch == '[' || ch == '{') {
                // 遇到開括號，推入堆疊
                stack.push(ch);
            } else if (ch == ')' || ch == ']' || ch == '}') {
                // 遇到閉括號，檢查匹配
                if (stack.isEmpty()) {
                    // 堆疊空但遇到閉括號，不匹配
                    return false;
                }

                char top = stack.pop();
                char expectedOpen = closeToOpen.get(ch);

                if (top != expectedOpen) {
                    // 括號不匹配
                    return false;
                }
            }
        }

        // 檢查堆疊是否為空（所有括號都已匹配）
        return stack.isEmpty();
    }
}

public class LC20_ValidParentheses_AlertFormat {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 讀取輸入
        String s = "";
        if (scanner.hasNextLine()) {
            s = scanner.nextLine().trim();
        }

        // 求解
        ValidParenSolution solution = new ValidParenSolution();
        boolean result = solution.isValid(s);

        // 輸出結果
        System.out.println(result);

        scanner.close();
    }
}
