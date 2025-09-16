/*
 * LeetCode 38: Count and Say
 * 難度: Medium
 * 
 * 解題思路:
 * 1. 遞歸或迭代實現
 * 2. 從"1"開始，每次描述前一個字符串
 * 3. 計算連續相同字符的數量
 * 
 * 時間複雜度: O(4^n / 3) 近似
 * 空間複雜度: O(4^n / 3)
 */

class Solution {

    public String countAndSay(int n) {
        if (n == 1) {
            return "1";
        }

        String result = "1";

        for (int i = 2; i <= n; i++) {
            result = getNext(result);
        }

        return result;
    }

    private String getNext(String s) {
        StringBuilder sb = new StringBuilder();
        int i = 0;

        while (i < s.length()) {
            char current = s.charAt(i);
            int count = 1;

            // 計算連續相同字符的數量
            while (i + count < s.length() && s.charAt(i + count) == current) {
                count++;
            }

            sb.append(count).append(current);
            i += count;
        }

        return sb.toString();
    }
}

public class lt_38_countandsay {

    public static void main(String[] args) {
        Solution solution = new Solution();

        for (int i = 1; i <= 6; i++) {
            System.out.println("n=" + i + ": " + solution.countAndSay(i));
        }

        // 預期輸出:
        // n=1: 1
        // n=2: 11
        // n=3: 21
        // n=4: 1211
        // n=5: 111221
        // n=6: 312211
    }
}
