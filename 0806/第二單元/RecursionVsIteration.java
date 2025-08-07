
import java.util.*;

public class RecursionVsIteration {

    // ==================== 計算二項式係數 ====================
    /**
     * 遞迴版本：計算二項式係數 C(n, k)
     */
    public static long binomialCoefficientRecursive(int n, int k) {
        // 基底情況
        if (k == 0 || k == n) {
            return 1;
        }

        if (k > n || n < 0 || k < 0) {
            return 0;
        }

        // 遞迴關係：C(n, k) = C(n-1, k-1) + C(n-1, k)
        return binomialCoefficientRecursive(n - 1, k - 1)
                + binomialCoefficientRecursive(n - 1, k);
    }

    /**
     * 迭代版本：計算二項式係數 C(n, k)
     */
    public static long binomialCoefficientIterative(int n, int k) {
        if (k > n || n < 0 || k < 0) {
            return 0;
        }

        if (k == 0 || k == n) {
            return 1;
        }

        // 選擇較小的 k 值來優化計算
        k = Math.min(k, n - k);

        long result = 1;
        for (int i = 0; i < k; i++) {
            result = result * (n - i) / (i + 1);
        }

        return result;
    }

    /**
     * 動態規劃版本：計算二項式係數 C(n, k)
     */
    public static long binomialCoefficientDP(int n, int k) {
        if (k > n || n < 0 || k < 0) {
            return 0;
        }

        if (k == 0 || k == n) {
            return 1;
        }

        long[][] dp = new long[n + 1][k + 1];

        // 初始化基底情況
        for (int i = 0; i <= n; i++) {
            dp[i][0] = 1;
            if (i <= k) {
                dp[i][i] = 1;
            }
        }

        // 填充DP表格
        for (int i = 2; i <= n; i++) {
            for (int j = 1; j < Math.min(i, k + 1); j++) {
                dp[i][j] = dp[i - 1][j - 1] + dp[i - 1][j];
            }
        }

        return dp[n][k];
    }

    // ==================== 陣列元素乘積 ====================
    /**
     * 遞迴版本：計算陣列所有元素的乘積
     */
    public static long arrayProductRecursive(int[] array, int index) {
        // 基底情況
        if (index >= array.length) {
            return 1;
        }

        // 遞迴計算：當前元素 × 剩餘元素的乘積
        return array[index] * arrayProductRecursive(array, index + 1);
    }

    public static long arrayProductRecursive(int[] array) {
        if (array.length == 0) {
            return 1;
        }
        return arrayProductRecursive(array, 0);
    }

    /**
     * 迭代版本：計算陣列所有元素的乘積
     */
    public static long arrayProductIterative(int[] array) {
        long product = 1;

        for (int num : array) {
            product *= num;
        }

        return product;
    }

    // ==================== 計算元音字母數量 ====================
    /**
     * 遞迴版本：計算字串中元音字母的數量
     */
    public static int countVowelsRecursive(String str, int index) {
        // 基底情況
        if (index >= str.length()) {
            return 0;
        }

        char ch = Character.toLowerCase(str.charAt(index));
        int count = (isVowel(ch)) ? 1 : 0;

        // 遞迴計算剩餘字符
        return count + countVowelsRecursive(str, index + 1);
    }

    public static int countVowelsRecursive(String str) {
        return countVowelsRecursive(str, 0);
    }

    /**
     * 迭代版本：計算字串中元音字母的數量
     */
    public static int countVowelsIterative(String str) {
        int count = 0;

        for (char ch : str.toCharArray()) {
            if (isVowel(Character.toLowerCase(ch))) {
                count++;
            }
        }

        return count;
    }

    /**
     * 檢查字符是否為元音字母
     */
    private static boolean isVowel(char ch) {
        return ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u';
    }

    // ==================== 括號配對檢查 ====================
    /**
     * 遞迴版本：檢查括號是否配對正確
     */
    public static boolean isValidParenthesesRecursive(String str) {
        return isValidParenthesesHelper(str, 0, new Stack<>());
    }

    private static boolean isValidParenthesesHelper(String str, int index, Stack<Character> stack) {
        // 基底情況：已處理完所有字符
        if (index >= str.length()) {
            return stack.isEmpty();
        }

        char ch = str.charAt(index);

        // 如果是開括號，推入堆疊
        if (isOpenBracket(ch)) {
            stack.push(ch);
        } // 如果是閉括號，檢查配對
        else if (isCloseBracket(ch)) {
            if (stack.isEmpty() || !isMatchingPair(stack.pop(), ch)) {
                return false;
            }
        }

        // 遞迴處理下一個字符
        return isValidParenthesesHelper(str, index + 1, stack);
    }

    /**
     * 迭代版本：檢查括號是否配對正確
     */
    public static boolean isValidParenthesesIterative(String str) {
        Stack<Character> stack = new Stack<>();

        for (char ch : str.toCharArray()) {
            if (isOpenBracket(ch)) {
                stack.push(ch);
            } else if (isCloseBracket(ch)) {
                if (stack.isEmpty() || !isMatchingPair(stack.pop(), ch)) {
                    return false;
                }
            }
        }

        return stack.isEmpty();
    }

    /**
     * 另一種遞迴方法：使用計數器檢查簡單括號
     */
    public static boolean isValidSimpleParenthesesRecursive(String str, int index, int count) {
        // 基底情況
        if (index >= str.length()) {
            return count == 0;
        }

        // 如果計數器變為負數，表示有未匹配的右括號
        if (count < 0) {
            return false;
        }

        char ch = str.charAt(index);

        if (ch == '(') {
            return isValidSimpleParenthesesRecursive(str, index + 1, count + 1);
        } else if (ch == ')') {
            return isValidSimpleParenthesesRecursive(str, index + 1, count - 1);
        } else {
            return isValidSimpleParenthesesRecursive(str, index + 1, count);
        }
    }

    public static boolean isValidSimpleParenthesesRecursive(String str) {
        return isValidSimpleParenthesesRecursive(str, 0, 0);
    }

    /**
     * 檢查是否為開括號
     */
    private static boolean isOpenBracket(char ch) {
        return ch == '(' || ch == '[' || ch == '{';
    }

    /**
     * 檢查是否為閉括號
     */
    private static boolean isCloseBracket(char ch) {
        return ch == ')' || ch == ']' || ch == '}';
    }

    /**
     * 檢查括號是否配對
     */
    private static boolean isMatchingPair(char open, char close) {
        return (open == '(' && close == ')')
                || (open == '[' && close == ']')
                || (open == '{' && close == '}');
    }

    // ==================== 效能測試方法 ====================
    /**
     * 效能測試：二項式係數
     */
    public static void testBinomialCoefficientPerformance() {
        System.out.println("=== 二項式係數效能測試 ===");
        int n = 15, k = 7;

        // 遞迴版本
        long startTime = System.nanoTime();
        long result1 = binomialCoefficientRecursive(n, k);
        long endTime = System.nanoTime();
        double recursiveTime = (endTime - startTime) / 1_000_000.0;

        // 迭代版本
        startTime = System.nanoTime();
        long result2 = binomialCoefficientIterative(n, k);
        endTime = System.nanoTime();
        double iterativeTime = (endTime - startTime) / 1_000_000.0;

        // 動態規劃版本
        startTime = System.nanoTime();
        long result3 = binomialCoefficientDP(n, k);
        endTime = System.nanoTime();
        double dpTime = (endTime - startTime) / 1_000_000.0;

        System.out.printf("C(%d,%d) = %d\n", n, k, result1);
        System.out.printf("遞迴版本耗時：%.3f 毫秒\n", recursiveTime);
        System.out.printf("迭代版本耗時：%.3f 毫秒\n", iterativeTime);
        System.out.printf("動態規劃耗時：%.3f 毫秒\n", dpTime);
        System.out.printf("結果一致性：%s\n\n",
                (result1 == result2 && result2 == result3) ? "✓" : "✗");
    }

    /**
     * 效能測試：陣列乘積
     */
    public static void testArrayProductPerformance() {
        System.out.println("=== 陣列乘積效能測試 ===");
        int[] testArray = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        // 遞迴版本
        long startTime = System.nanoTime();
        long result1 = arrayProductRecursive(testArray);
        long endTime = System.nanoTime();
        double recursiveTime = (endTime - startTime) / 1_000.0;

        // 迭代版本
        startTime = System.nanoTime();
        long result2 = arrayProductIterative(testArray);
        endTime = System.nanoTime();
        double iterativeTime = (endTime - startTime) / 1_000.0;

        System.out.printf("陣列 %s 的乘積：%d\n", Arrays.toString(testArray), result1);
        System.out.printf("遞迴版本耗時：%.3f 微秒\n", recursiveTime);
        System.out.printf("迭代版本耗時：%.3f 微秒\n", iterativeTime);
        System.out.printf("結果一致性：%s\n\n", (result1 == result2) ? "✓" : "✗");
    }

    /**
     * 效能測試：元音計數
     */
    public static void testVowelCountPerformance() {
        System.out.println("=== 元音計數效能測試 ===");
        String testStr = "Hello World! How are you doing today? This is a performance test string.";

        // 遞迴版本
        long startTime = System.nanoTime();
        int result1 = countVowelsRecursive(testStr);
        long endTime = System.nanoTime();
        double recursiveTime = (endTime - startTime) / 1_000.0;

        // 迭代版本
        startTime = System.nanoTime();
        int result2 = countVowelsIterative(testStr);
        endTime = System.nanoTime();
        double iterativeTime = (endTime - startTime) / 1_000.0;

        System.out.printf("字串：\"%s\"\n", testStr);
        System.out.printf("元音字母數量：%d\n", result1);
        System.out.printf("遞迴版本耗時：%.3f 微秒\n", recursiveTime);
        System.out.printf("迭代版本耗時：%.3f 微秒\n", iterativeTime);
        System.out.printf("結果一致性：%s\n\n", (result1 == result2) ? "✓" : "✗");
    }

    /**
     * 效能測試：括號檢查
     */
    public static void testParenthesesPerformance() {
        System.out.println("=== 括號檢查效能測試 ===");
        String testStr = "((()))([{}]){[()]}";

        // 遞迴版本
        long startTime = System.nanoTime();
        boolean result1 = isValidParenthesesRecursive(testStr);
        long endTime = System.nanoTime();
        double recursiveTime = (endTime - startTime) / 1_000.0;

        // 迭代版本
        startTime = System.nanoTime();
        boolean result2 = isValidParenthesesIterative(testStr);
        endTime = System.nanoTime();
        double iterativeTime = (endTime - startTime) / 1_000.0;

        System.out.printf("測試字串：\"%s\"\n", testStr);
        System.out.printf("括號配對正確：%s\n", result1);
        System.out.printf("遞迴版本耗時：%.3f 微秒\n", recursiveTime);
        System.out.printf("迭代版本耗時：%.3f 微秒\n", iterativeTime);
        System.out.printf("結果一致性：%s\n\n", (result1 == result2) ? "✓" : "✗");
    }

    public static void main(String[] args) {
        System.out.println("=== 遞迴與迭代比較示範 ===");

        // 1. 二項式係數比較測試
        System.out.println("1. 二項式係數比較：");
        System.out.println("計算 C(10,5)：");
        System.out.printf("遞迴結果：%d\n", binomialCoefficientRecursive(10, 5));
        System.out.printf("迭代結果：%d\n", binomialCoefficientIterative(10, 5));
        System.out.printf("動態規劃：%d\n\n", binomialCoefficientDP(10, 5));

        // 2. 陣列乘積比較測試
        System.out.println("2. 陣列乘積比較：");
        int[] testArray = {2, 3, 4, 5};
        System.out.printf("測試陣列：%s\n", Arrays.toString(testArray));
        System.out.printf("遞迴結果：%d\n", arrayProductRecursive(testArray));
        System.out.printf("迭代結果：%d\n\n", arrayProductIterative(testArray));

        // 3. 元音計數比較測試
        System.out.println("3. 元音計數比較：");
        String testStr = "Programming";
        System.out.printf("測試字串：\"%s\"\n", testStr);
        System.out.printf("遞迴結果：%d\n", countVowelsRecursive(testStr));
        System.out.printf("迭代結果：%d\n\n", countVowelsIterative(testStr));

        // 4. 括號檢查比較測試
        System.out.println("4. 括號檢查比較：");
        String[] bracketTests = {
            "(())",
            "([{}])",
            "(()",
            "{[}]",
            "((()))",
            ""
        };

        for (String test : bracketTests) {
            boolean recursive = isValidParenthesesRecursive(test);
            boolean iterative = isValidParenthesesIterative(test);
            System.out.printf("\"%s\" - 遞迴：%s, 迭代：%s\n",
                    test.isEmpty() ? "(空字串)" : test, recursive, iterative);
        }

        System.out.println("\n5. 簡單括號檢查（僅限圓括號）：");
        String simpleTest = "((()))";
        boolean simpleRecursive = isValidSimpleParenthesesRecursive(simpleTest);
        System.out.printf("\"%s\" - 簡單遞迴：%s\n", simpleTest, simpleRecursive);

        // 效能測試
        System.out.println("\n" + "=".repeat(50));
        System.out.println("效能測試結果：");
        System.out.println("=".repeat(50));

        testBinomialCoefficientPerformance();
        testArrayProductPerformance();
        testVowelCountPerformance();
        testParenthesesPerformance();

        // 總結比較
        System.out.println("=== 遞迴與迭代比較總結 ===");
        System.out.println("遞迴優點：");
        System.out.println("  - 程式碼簡潔易懂");
        System.out.println("  - 自然表達分治思想");
        System.out.println("  - 適合處理樹狀結構問題");

        System.out.println("\n遞迴缺點：");
        System.out.println("  - 可能導致堆疊溢位");
        System.out.println("  - 通常較慢（函式呼叫開銷）");
        System.out.println("  - 可能有重複計算");

        System.out.println("\n迭代優點：");
        System.out.println("  - 通常較快");
        System.out.println("  - 記憶體使用較少");
        System.out.println("  - 不會堆疊溢位");

        System.out.println("\n迭代缺點：");
        System.out.println("  - 程式碼可能較複雜");
        System.out.println("  - 某些問題不易用迭代表達");
    }
}
