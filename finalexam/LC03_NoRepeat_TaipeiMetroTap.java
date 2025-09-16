
import java.util.*;

/**
 * LeetCode 3. Longest Substring Without Repeating Characters - 北捷刷卡最長無重複片段
 *
 * 題目說明： 捷運進出站刷卡流水（字串 s）中每個字元代表一位乘客卡片 ID 的簡化碼（為了去識別化）。
 * 你要找的是一段最長的「連續區間」，期間內沒有任何乘客重複刷卡 （等價於最長不含重複字元子字串長度）。
 * 請回傳該最長長度，用於估算尖峰時同時不同乘客通過閘門的最大流量能力。
 *
 * 解題思路： 1. 使用滑動視窗技術，維護左右兩個指針 2. 右指針向右擴展，遇到重複字元時，左指針縮到該字元上次位置 +1 3. 使用 HashMap
 * 記錄每個字元的最新索引位置 4. 每次更新答案為當前視窗長度的最大值 5. 滑動視窗保證區間內無重複字元
 *
 * 時間複雜度：O(n)，每個字元最多被訪問兩次 空間複雜度：O(k)，k 為字元集大小，最多存儲不同字元的數量
 */
class Solution {

    public int lengthOfLongestSubstring(String s) {
        // 處理邊界情況
        if (s == null || s.length() == 0) {
            return 0;
        }

        // 使用 HashMap 記錄每個字元的最新索引位置
        Map<Character, Integer> charIndexMap = new HashMap<>();

        int maxLength = 0;  // 記錄最長長度
        int left = 0;       // 滑動視窗左指針

        // 右指針遍歷字串
        for (int right = 0; right < s.length(); right++) {
            char currentChar = s.charAt(right);

            // 如果當前字元已存在且在當前視窗內
            if (charIndexMap.containsKey(currentChar)) {
                int lastIndex = charIndexMap.get(currentChar);
                // 更新左指針到重複字元的下一位置
                // 但不能往後退，所以取 max
                left = Math.max(left, lastIndex + 1);
            }

            // 更新當前字元的索引位置
            charIndexMap.put(currentChar, right);

            // 更新最長長度
            int currentLength = right - left + 1;
            maxLength = Math.max(maxLength, currentLength);
        }

        return maxLength;
    }
}

public class LC03_NoRepeat_TaipeiMetroTap {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 讀取輸入
        String s = "";
        if (scanner.hasNextLine()) {
            s = scanner.nextLine().trim();
        }

        // 求解
        Solution solution = new Solution();
        int result = solution.lengthOfLongestSubstring(s);

        // 輸出結果
        System.out.println(result);

        scanner.close();
    }
}
