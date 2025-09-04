
import java.util.*;

/**
 * LeetCode 28. Find the Index of the First Occurrence in a String - 公告全文搜尋
 *
 * 題目說明： 市府公告全文檢索：給主字串 haystack（公告全文）與子字串 needle（欲搜尋關鍵片段）， 找出 needle
 * 首次出現的起始索引；不存在回 -1。用於建立簡易索引前的基準實作。
 *
 * 解題思路： 1. 暴力：逐起點比較 2. 高效：KMP 失敗函數避免重複比對 3. 若長度小可直接暴力
 *
 * 時間複雜度：暴力 O(n*m)；KMP O(n+m) 空間複雜度：O(1) 或 O(m)
 */
public class LC28_StrStr_NoticeSearch {

    public static int strStr(String haystack, String needle) {
        if (needle.length() == 0) {
            return 0;
        }
        if (haystack.length() < needle.length()) {
            return -1;
        }

        // 暴力搜索法
        for (int i = 0; i <= haystack.length() - needle.length(); i++) {
            if (haystack.substring(i, i + needle.length()).equals(needle)) {
                return i;
            }
        }

        return -1;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String haystack = scanner.nextLine();
        String needle = scanner.nextLine();

        int result = strStr(haystack, needle);
        System.out.println(result);

        scanner.close();
    }
}
