
import java.util.*;

/**
 * LeetCode 30. Substring with Concatenation of All Words
 *
 * 解題思路： 1. 使用優化的滑動窗口技術 2. 對每個可能的起始偏移量（0到wordLen-1）分別處理 3. 維護窗口內的單詞計數，避免重複計算 4.
 * 當窗口大小超過要求時，移除左邊的單詞 5. 只需要掃描 wordLen 次，每次掃描整個字串
 *
 * 時間複雜度：O(n * k)，其中 n 是字串長度，k 是單詞長度 空間複雜度：O(m * k)，儲存單詞頻率映射
 */
class Solution {

    public List<Integer> findSubstring(String s, String[] words) {
        List<Integer> result = new ArrayList<>();

        if (s == null || s.length() == 0 || words == null || words.length == 0) {
            return result;
        }

        int wordLen = words[0].length();
        int wordCount = words.length;
        int totalLen = wordLen * wordCount;

        if (s.length() < totalLen) {
            return result;
        }

        // 構建單詞頻率map
        Map<String, Integer> wordMap = new HashMap<>();
        for (String word : words) {
            wordMap.put(word, wordMap.getOrDefault(word, 0) + 1);
        }

        // 對每個可能的起始偏移量處理
        for (int offset = 0; offset < wordLen; offset++) {
            Map<String, Integer> windowMap = new HashMap<>();
            int left = offset;
            int count = 0; // 窗口中匹配的單詞數

            // 滑動窗口
            for (int right = offset; right <= s.length() - wordLen; right += wordLen) {
                String word = s.substring(right, right + wordLen);

                if (wordMap.containsKey(word)) {
                    windowMap.put(word, windowMap.getOrDefault(word, 0) + 1);
                    count++;

                    // 如果某個單詞出現次數過多，縮小窗口
                    while (windowMap.get(word) > wordMap.get(word)) {
                        String leftWord = s.substring(left, left + wordLen);
                        windowMap.put(leftWord, windowMap.get(leftWord) - 1);
                        left += wordLen;
                        count--;
                    }

                    // 檢查是否找到完全匹配
                    if (count == wordCount) {
                        result.add(left);

                        // 移動左邊界，準備下一次匹配
                        String leftWord = s.substring(left, left + wordLen);
                        windowMap.put(leftWord, windowMap.get(leftWord) - 1);
                        left += wordLen;
                        count--;
                    }
                } else {
                    // 遇到不在詞典中的單詞，重置窗口
                    windowMap.clear();
                    count = 0;
                    left = right + wordLen;
                }
            }
        }

        return result;
    }
}
