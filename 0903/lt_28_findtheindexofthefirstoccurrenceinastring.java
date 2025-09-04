
/**
 * LeetCode 28. Find the Index of the First Occurrence in a String
 *
 * 解題思路：
 * 1. 使用暴力匹配法（Brute Force）
 * 2. 在haystack中逐一檢查每個可能的起始位置
 * 3. 對每個位置檢查是否匹配整個needle字串
 * 4. 找到第一個完全匹配的位置就返回索引
 * 5. 如果沒找到則返回-1
 *
 * 時間複雜度：O(n*m)，其中 n 是haystack長度，m 是needle長度
 * 空間複雜度：O(1)，只使用常數額外空間
 */
class Solution {

    public int strStr(String haystack, String needle) {
        if (needle.length() == 0) {
            return 0;
        }

        if (haystack.length() < needle.length()) {
            return -1;
        }

        // 遍歷所有可能的起始位置
        for (int i = 0; i <= haystack.length() - needle.length(); i++) {
            int j = 0;
            // 檢查當前位置是否匹配整個needle
            while (j < needle.length() && haystack.charAt(i + j) == needle.charAt(j)) {
                j++;
            }
            // 找到完全匹配
            if (j == needle.length()) {
                return i;
            }
        }

        return -1;  // 沒有找到匹配
    }
}
