
public class RecursiveMathCalculator {

    /**
     * 計算組合數 C(n, k) = n! / (k! * (n-k)!) 遞迴公式：C(n, k) = C(n-1, k-1) + C(n-1, k)
     */
    public static long combination(int n, int k) {
        // 基底情況
        if (k == 0 || k == n) {
            return 1;
        }

        // 邊界檢查
        if (k > n || n < 0 || k < 0) {
            return 0;
        }

        // 遞迴關係：C(n, k) = C(n-1, k-1) + C(n-1, k)
        return combination(n - 1, k - 1) + combination(n - 1, k);
    }

    /**
     * 使用記憶化改良的組合數計算
     */
    public static long combinationMemo(int n, int k) {
        long[][] memo = new long[n + 1][k + 1];
        return combinationMemoHelper(n, k, memo);
    }

    private static long combinationMemoHelper(int n, int k, long[][] memo) {
        if (k == 0 || k == n) {
            return 1;
        }

        if (k > n || n < 0 || k < 0) {
            return 0;
        }

        if (memo[n][k] != 0) {
            return memo[n][k];
        }

        memo[n][k] = combinationMemoHelper(n - 1, k - 1, memo)
                + combinationMemoHelper(n - 1, k, memo);

        return memo[n][k];
    }

    /**
     * 計算卡塔蘭數 C(n) 遞迴公式：C(n) = Σ(C(i) × C(n-1-i))，其中 i 從 0 到 n-1
     */
    public static long catalan(int n) {
        // 基底情況
        if (n <= 1) {
            return 1;
        }

        long result = 0;

        // C(n) = Σ(C(i) × C(n-1-i))，其中 i 從 0 到 n-1
        for (int i = 0; i < n; i++) {
            result += catalan(i) * catalan(n - 1 - i);
        }

        return result;
    }

    /**
     * 使用記憶化的卡塔蘭數計算
     */
    public static long catalanMemo(int n) {
        long[] memo = new long[n + 1];
        return catalanMemoHelper(n, memo);
    }

    private static long catalanMemoHelper(int n, long[] memo) {
        if (n <= 1) {
            return 1;
        }

        if (memo[n] != 0) {
            return memo[n];
        }

        long result = 0;
        for (int i = 0; i < n; i++) {
            result += catalanMemoHelper(i, memo) * catalanMemoHelper(n - 1 - i, memo);
        }

        memo[n] = result;
        return result;
    }

    /**
     * 計算漢諾塔移動步數 遞迴公式：hanoi(n) = 2 × hanoi(n-1) + 1
     */
    public static long hanoi(int n) {
        // 基底情況
        if (n == 1) {
            return 1;
        }

        if (n <= 0) {
            return 0;
        }

        // hanoi(n) = 2 × hanoi(n-1) + 1
        return 2 * hanoi(n - 1) + 1;
    }

    /**
     * 遞迴解決漢諾塔問題（顯示移動步驟）
     */
    public static void solveHanoi(int n, char from, char to, char auxiliary) {
        if (n == 1) {
            System.out.printf("將盤子 1 從 %c 移到 %c\n", from, to);
            return;
        }

        // 將前 n-1 個盤子從起始柱移到輔助柱
        solveHanoi(n - 1, from, auxiliary, to);

        // 將最大的盤子從起始柱移到目標柱
        System.out.printf("將盤子 %d 從 %c 移到 %c\n", n, from, to);

        // 將前 n-1 個盤子從輔助柱移到目標柱
        solveHanoi(n - 1, auxiliary, to, from);
    }

    /**
     * 判斷一個數字是否為回文數
     */
    public static boolean isPalindrome(int number) {
        String str = String.valueOf(Math.abs(number));
        return isPalindromeHelper(str, 0, str.length() - 1);
    }

    private static boolean isPalindromeHelper(String str, int left, int right) {
        // 基底情況
        if (left >= right) {
            return true;
        }

        // 檢查首尾字符是否相同
        if (str.charAt(left) != str.charAt(right)) {
            return false;
        }

        // 遞迴檢查內部字符
        return isPalindromeHelper(str, left + 1, right - 1);
    }

    /**
     * 遞迴計算數字的位數
     */
    public static int countDigits(int number) {
        number = Math.abs(number);

        if (number < 10) {
            return 1;
        }

        return 1 + countDigits(number / 10);
    }

    /**
     * 遞迴反轉數字
     */
    public static int reverseNumber(int number) {
        return reverseNumberHelper(Math.abs(number), 0);
    }

    private static int reverseNumberHelper(int number, int reversed) {
        if (number == 0) {
            return reversed;
        }

        return reverseNumberHelper(number / 10, reversed * 10 + number % 10);
    }

    /**
     * 遞迴計算數字各位數字之和
     */
    public static int sumOfDigits(int number) {
        number = Math.abs(number);

        if (number < 10) {
            return number;
        }

        return number % 10 + sumOfDigits(number / 10);
    }

    /**
     * 效能測試方法
     */
    public static void performanceTest() {
        System.out.println("=== 遞迴數學函式效能測試 ===");

        // 測試組合數
        System.out.println("\n組合數計算效能比較：");
        int n = 10, k = 5;

        long startTime = System.nanoTime();
        long result1 = combination(n, k);
        long endTime = System.nanoTime();
        double duration1 = (endTime - startTime) / 1_000_000.0;

        startTime = System.nanoTime();
        long result2 = combinationMemo(n, k);
        endTime = System.nanoTime();
        double duration2 = (endTime - startTime) / 1_000_000.0;

        System.out.printf("C(%d,%d) = %d\n", n, k, result1);
        System.out.printf("普通遞迴耗時：%.3f 毫秒\n", duration1);
        System.out.printf("記憶化遞迴耗時：%.3f 毫秒\n", duration2);

        // 測試卡塔蘭數
        System.out.println("\n卡塔蘭數計算效能比較：");
        int catalanN = 10;

        startTime = System.nanoTime();
        long catResult1 = catalan(catalanN);
        endTime = System.nanoTime();
        duration1 = (endTime - startTime) / 1_000_000.0;

        startTime = System.nanoTime();
        long catResult2 = catalanMemo(catalanN);
        endTime = System.nanoTime();
        duration2 = (endTime - startTime) / 1_000_000.0;

        System.out.printf("Catalan(%d) = %d\n", catalanN, catResult1);
        System.out.printf("普通遞迴耗時：%.3f 毫秒\n", duration1);
        System.out.printf("記憶化遞迴耗時：%.3f 毫秒\n", duration2);
    }

    public static void main(String[] args) {
        System.out.println("=== 遞迴數學計算器示範 ===");

        // 測試組合數
        System.out.println("1. 組合數計算：");
        for (int n = 0; n <= 6; n++) {
            for (int k = 0; k <= n; k++) {
                System.out.printf("C(%d,%d) = %d  ", n, k, combination(n, k));
            }
            System.out.println();
        }

        // 測試卡塔蘭數
        System.out.println("\n2. 卡塔蘭數序列：");
        System.out.print("Catalan數列前10項：");
        for (int i = 0; i < 10; i++) {
            System.out.printf("%d ", catalan(i));
        }
        System.out.println();

        // 測試漢諾塔
        System.out.println("\n3. 漢諾塔問題：");
        for (int i = 1; i <= 5; i++) {
            System.out.printf("%d個盤子需要移動 %d 步\n", i, hanoi(i));
        }

        System.out.println("\n漢諾塔解法演示（3個盤子）：");
        solveHanoi(3, 'A', 'C', 'B');

        // 測試回文數
        System.out.println("\n4. 回文數檢測：");
        int[] testNumbers = {12321, 12345, 1001, 7, 1234321, -121};
        for (int num : testNumbers) {
            System.out.printf("%d 是回文數：%s\n", num, isPalindrome(num));
        }

        // 測試其他數字操作
        System.out.println("\n5. 其他數字操作：");
        int testNum = 12345;
        System.out.printf("數字 %d：\n", testNum);
        System.out.printf("  位數：%d\n", countDigits(testNum));
        System.out.printf("  反轉：%d\n", reverseNumber(testNum));
        System.out.printf("  各位數字之和：%d\n", sumOfDigits(testNum));

        // 效能測試
        System.out.println();
        performanceTest();
    }
}
