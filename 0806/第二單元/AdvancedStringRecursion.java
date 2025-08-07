
import java.util.*;

public class AdvancedStringRecursion {

    /**
     * 遞迴產生字串的所有排列組合
     */
    public static List<String> generatePermutations(String str) {
        List<String> result = new ArrayList<>();
        generatePermutationsHelper(str.toCharArray(), 0, result);
        return result;
    }

    private static void generatePermutationsHelper(char[] chars, int index, List<String> result) {
        // 基底情況：已經處理完所有位置
        if (index == chars.length) {
            result.add(new String(chars));
            return;
        }

        // 嘗試將每個字符放在當前位置
        for (int i = index; i < chars.length; i++) {
            // 交換字符
            swap(chars, index, i);

            // 遞迴處理下一個位置
            generatePermutationsHelper(chars, index + 1, result);

            // 回溯：恢復原來的順序
            swap(chars, index, i);
        }
    }

    /**
     * 產生不重複的排列組合（處理重複字符）
     */
    public static List<String> generateUniquePermutations(String str) {
        Set<String> result = new HashSet<>();
        generatePermutationsHelper(str.toCharArray(), 0,
                new ArrayList<String>() {
            {
                addAll(result);
            }
        });
        return new ArrayList<>(result);
    }

    /**
     * 遞迴實作字串匹配演算法（KMP預處理）
     */
    public static List<Integer> findAllOccurrences(String text, String pattern) {
        List<Integer> occurrences = new ArrayList<>();
        findOccurrencesHelper(text, pattern, 0, occurrences);
        return occurrences;
    }

    private static void findOccurrencesHelper(String text, String pattern, int startIndex,
            List<Integer> occurrences) {
        // 基底情況：搜尋範圍不足
        if (startIndex > text.length() - pattern.length()) {
            return;
        }

        // 檢查當前位置是否匹配
        if (isMatchAtPosition(text, pattern, startIndex)) {
            occurrences.add(startIndex);
        }

        // 遞迴搜尋下一個位置
        findOccurrencesHelper(text, pattern, startIndex + 1, occurrences);
    }

    private static boolean isMatchAtPosition(String text, String pattern, int position) {
        if (position + pattern.length() > text.length()) {
            return false;
        }

        return isMatchHelper(text, pattern, position, 0);
    }

    private static boolean isMatchHelper(String text, String pattern, int textIndex, int patternIndex) {
        // 基底情況：模式字串已完全匹配
        if (patternIndex == pattern.length()) {
            return true;
        }

        // 基底情況：文本字串已結束但模式字串未完成
        if (textIndex >= text.length()) {
            return false;
        }

        // 檢查當前字符是否匹配
        if (text.charAt(textIndex) != pattern.charAt(patternIndex)) {
            return false;
        }

        // 遞迴檢查下一個字符
        return isMatchHelper(text, pattern, textIndex + 1, patternIndex + 1);
    }

    /**
     * 遞迴移除字串中的重複字符
     */
    public static String removeDuplicates(String str) {
        if (str.length() <= 1) {
            return str;
        }

        return removeDuplicatesHelper(str, 0, new StringBuilder(), new HashSet<>());
    }

    private static String removeDuplicatesHelper(String str, int index, StringBuilder result,
            Set<Character> seen) {
        // 基底情況：已處理完所有字符
        if (index >= str.length()) {
            return result.toString();
        }

        char currentChar = str.charAt(index);

        // 如果字符未出現過，添加到結果中
        if (!seen.contains(currentChar)) {
            seen.add(currentChar);
            result.append(currentChar);
        }

        // 遞迴處理下一個字符
        return removeDuplicatesHelper(str, index + 1, result, seen);
    }

    /**
     * 遞迴計算字串的所有子字串組合
     */
    public static List<String> generateAllSubstrings(String str) {
        List<String> result = new ArrayList<>();
        generateSubstringsHelper(str, 0, 0, result);
        return result;
    }

    private static void generateSubstringsHelper(String str, int start, int end,
            List<String> result) {
        // 基底情況：end 超出字串長度
        if (start >= str.length()) {
            return;
        }

        if (end >= str.length()) {
            // 當前起始位置的所有子字串已生成，移動到下一個起始位置
            generateSubstringsHelper(str, start + 1, start + 1, result);
            return;
        }

        // 添加當前子字串
        result.add(str.substring(start, end + 1));

        // 遞迴生成更長的子字串
        generateSubstringsHelper(str, start, end + 1, result);
    }

    /**
     * 遞迴計算字串的所有子序列
     */
    public static List<String> generateAllSubsequences(String str) {
        List<String> result = new ArrayList<>();
        generateSubsequencesHelper(str, 0, "", result);
        return result;
    }

    private static void generateSubsequencesHelper(String str, int index, String current,
            List<String> result) {
        // 基底情況：已處理完所有字符
        if (index == str.length()) {
            result.add(current);
            return;
        }

        // 不包含當前字符
        generateSubsequencesHelper(str, index + 1, current, result);

        // 包含當前字符
        generateSubsequencesHelper(str, index + 1, current + str.charAt(index), result);
    }

    /**
     * 遞迴檢查字串是否為回文
     */
    public static boolean isPalindrome(String str) {
        if (str == null || str.length() <= 1) {
            return true;
        }

        return isPalindromeHelper(str, 0, str.length() - 1);
    }

    private static boolean isPalindromeHelper(String str, int left, int right) {
        // 基底情況：指針相遇或交錯
        if (left >= right) {
            return true;
        }

        // 檢查當前字符是否相同
        if (str.charAt(left) != str.charAt(right)) {
            return false;
        }

        // 遞迴檢查內部字符
        return isPalindromeHelper(str, left + 1, right - 1);
    }

    /**
     * 遞迴反轉字串
     */
    public static String reverseString(String str) {
        if (str == null || str.length() <= 1) {
            return str;
        }

        return reverseStringHelper(str, str.length() - 1, new StringBuilder());
    }

    private static String reverseStringHelper(String str, int index, StringBuilder result) {
        // 基底情況：已處理完所有字符
        if (index < 0) {
            return result.toString();
        }

        // 添加當前字符到結果
        result.append(str.charAt(index));

        // 遞迴處理前一個字符
        return reverseStringHelper(str, index - 1, result);
    }

    /**
     * 遞迴計算字串中特定字符的出現次數
     */
    public static int countCharacter(String str, char target) {
        if (str == null || str.length() == 0) {
            return 0;
        }

        return countCharacterHelper(str, target, 0);
    }

    private static int countCharacterHelper(String str, char target, int index) {
        // 基底情況：已檢查完所有字符
        if (index >= str.length()) {
            return 0;
        }

        // 檢查當前字符
        int count = (str.charAt(index) == target) ? 1 : 0;

        // 遞迴檢查剩餘字符
        return count + countCharacterHelper(str, target, index + 1);
    }

    /**
     * 遞迴實作字串的編輯距離（Levenshtein距離）
     */
    public static int editDistance(String str1, String str2) {
        return editDistanceHelper(str1, str2, str1.length(), str2.length());
    }

    private static int editDistanceHelper(String str1, String str2, int m, int n) {
        // 基底情況
        if (m == 0) {
            return n; // str1為空，需要插入n個字符

                }if (n == 0) {
            return m; // str2為空，需要刪除m個字符
        }
        // 如果最後一個字符相同
        if (str1.charAt(m - 1) == str2.charAt(n - 1)) {
            return editDistanceHelper(str1, str2, m - 1, n - 1);
        }

        // 如果最後一個字符不同，考慮三種操作
        int insert = editDistanceHelper(str1, str2, m, n - 1);     // 插入
        int delete = editDistanceHelper(str1, str2, m - 1, n);     // 刪除
        int replace = editDistanceHelper(str1, str2, m - 1, n - 1); // 替換

        return 1 + Math.min(insert, Math.min(delete, replace));
    }

    /**
     * 遞迴檢查兩個字串是否為變位詞
     */
    public static boolean areAnagrams(String str1, String str2) {
        if (str1.length() != str2.length()) {
            return false;
        }

        return areAnagramsHelper(str1.toLowerCase(), str2.toLowerCase(),
                new int[26], new int[26], 0);
    }

    private static boolean areAnagramsHelper(String str1, String str2,
            int[] count1, int[] count2, int index) {
        // 基底情況：已處理完所有字符
        if (index >= str1.length()) {
            return Arrays.equals(count1, count2);
        }

        // 計算字符頻率
        if (Character.isLetter(str1.charAt(index))) {
            count1[str1.charAt(index) - 'a']++;
        }
        if (Character.isLetter(str2.charAt(index))) {
            count2[str2.charAt(index) - 'a']++;
        }

        // 遞迴處理下一個字符
        return areAnagramsHelper(str1, str2, count1, count2, index + 1);
    }

    /**
     * 輔助方法：交換字符陣列中的兩個元素
     */
    private static void swap(char[] chars, int i, int j) {
        char temp = chars[i];
        chars[i] = chars[j];
        chars[j] = temp;
    }

    /**
     * 輔助方法：顯示字串列表
     */
    private static void printStringList(List<String> list, String title) {
        System.out.println(title + ":");
        if (list.size() > 20) {
            System.out.printf("總共 %d 個結果，顯示前20個：\n", list.size());
            for (int i = 0; i < 20; i++) {
                System.out.printf("  %s\n", list.get(i));
            }
            System.out.println("  ...");
        } else {
            for (String s : list) {
                System.out.printf("  %s\n", s);
            }
        }
        System.out.println();
    }

    public static void main(String[] args) {
        System.out.println("=== 字串遞迴處理進階示範 ===");

        // 1. 字串排列組合測試
        System.out.println("1. 字串排列組合測試：");
        String testStr = "ABC";
        List<String> permutations = generatePermutations(testStr);
        printStringList(permutations, "\"" + testStr + "\" 的所有排列");

        // 2. 字串匹配測試
        System.out.println("2. 字串匹配測試：");
        String text = "ABABCABABA";
        String pattern = "ABA";
        List<Integer> occurrences = findAllOccurrences(text, pattern);
        System.out.printf("在 \"%s\" 中尋找 \"%s\"：\n", text, pattern);
        System.out.printf("找到的位置：%s\n\n", occurrences);

        // 3. 移除重複字符測試
        System.out.println("3. 移除重複字符測試：");
        String duplicateStr = "programming";
        String noDuplicates = removeDuplicates(duplicateStr);
        System.out.printf("原字串：\"%s\"\n", duplicateStr);
        System.out.printf("移除重複後：\"%s\"\n\n", noDuplicates);

        // 4. 子字串生成測試
        System.out.println("4. 子字串生成測試：");
        String subStr = "abc";
        List<String> substrings = generateAllSubstrings(subStr);
        printStringList(substrings, "\"" + subStr + "\" 的所有子字串");

        // 5. 子序列生成測試
        System.out.println("5. 子序列生成測試：");
        String subSeqStr = "abc";
        List<String> subsequences = generateAllSubsequences(subSeqStr);
        printStringList(subsequences, "\"" + subSeqStr + "\" 的所有子序列");

        // 6. 回文檢測測試
        System.out.println("6. 回文檢測測試：");
        String[] palindromeTests = {"racecar", "hello", "madam", "12321", "abcba"};
        for (String test : palindromeTests) {
            System.out.printf("\"%s\" 是回文：%s\n", test, isPalindrome(test));
        }
        System.out.println();

        // 7. 字串反轉測試
        System.out.println("7. 字串反轉測試：");
        String reverseTest = "Hello World";
        String reversed = reverseString(reverseTest);
        System.out.printf("原字串：\"%s\"\n", reverseTest);
        System.out.printf("反轉後：\"%s\"\n\n", reversed);

        // 8. 字符計數測試
        System.out.println("8. 字符計數測試：");
        String countStr = "programming";
        char targetChar = 'r';
        int count = countCharacter(countStr, targetChar);
        System.out.printf("在 \"%s\" 中字符 '%c' 出現 %d 次\n\n", countStr, targetChar, count);

        // 9. 編輯距離測試
        System.out.println("9. 編輯距離測試：");
        String str1 = "kitten";
        String str2 = "sitting";
        int distance = editDistance(str1, str2);
        System.out.printf("\"%s\" 和 \"%s\" 的編輯距離：%d\n\n", str1, str2, distance);

        // 10. 變位詞檢測測試
        System.out.println("10. 變位詞檢測測試：");
        String[][] anagramTests = {
            {"listen", "silent"},
            {"evil", "vile"},
            {"hello", "world"},
            {"stressed", "desserts"}
        };

        for (String[] pair : anagramTests) {
            boolean isAnagram = areAnagrams(pair[0], pair[1]);
            System.out.printf("\"%s\" 和 \"%s\" 是變位詞：%s\n",
                    pair[0], pair[1], isAnagram);
        }

        // 11. 效能測試
        System.out.println("\n11. 效能測試：");
        String largeStr = "abcdefgh";

        long startTime = System.nanoTime();
        List<String> largePermutations = generatePermutations(largeStr);
        long endTime = System.nanoTime();
        double duration = (endTime - startTime) / 1_000_000.0;

        System.out.printf("生成 \"%s\" 的 %d 個排列耗時：%.3f 毫秒\n",
                largeStr, largePermutations.size(), duration);
    }
}
