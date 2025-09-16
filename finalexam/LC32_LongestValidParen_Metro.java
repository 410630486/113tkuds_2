
import java.util.*;

/**
 * LeetCode 32. Longest Valid Parentheses - 北捷進出站最長有效片段
 *
 * 題目說明： 北捷閘門日誌以 ( 代表一次進站事件、) 代表一次出站事件 （已經前置保證資料總量接近，但仍可能有缺漏）。
 * 為分析某時段資料品質，需要找出最長的一段子字串，其內進出完全配對（括號合法）。 請輸出該最長合法片段長度，用於後續品質指標統計。
 *
 * 解題思路： 1. 使用索引堆疊：棧底放 -1 作為基準 2. 遇到 '(' 推入當前索引 3. 遇到 ')' 先彈出： -
 * 如果堆疊變空，推入當前索引作為新基準 - 否則計算長度 = 當前索引 - 堆疊頂 4. 持續更新最大長度
 *
 * 時間複雜度：O(n) 空間複雜度：O(n)
 */
class LongestValidSolution {

    public int longestValidParentheses(String s) {
        if (s == null || s.length() <= 1) {
            return 0;
        }

        Stack<Integer> stack = new Stack<>();
        stack.push(-1); // 初始基準位置

        int maxLength = 0;

        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);

            if (ch == '(') {
                // 遇到左括號，推入索引
                stack.push(i);
            } else { // ch == ')'
                // 遇到右括號，先彈出
                stack.pop();

                if (stack.isEmpty()) {
                    // 堆疊空了，推入當前索引作為新基準
                    stack.push(i);
                } else {
                    // 計算當前有效長度
                    int currentLength = i - stack.peek();
                    maxLength = Math.max(maxLength, currentLength);
                }
            }
        }

        return maxLength;
    }
}

public class LC32_LongestValidParen_Metro {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 讀取輸入
        String s = "";
        if (scanner.hasNextLine()) {
            s = scanner.nextLine().trim();
        }

        // 求解
        LongestValidSolution solution = new LongestValidSolution();
        int result = solution.longestValidParentheses(s);

        // 輸出結果
        System.out.println(result);

        scanner.close();
    }
}
