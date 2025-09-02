import java.util.HashMap;
import java.util.Map;

class Solution {
    /**
     * Roman to Integer 解題思路：
     * 1. 使用 HashMap 存儲羅馬數字符號與對應數值的映射
     * 2. 從左到右遍歷羅馬數字字串
     * 3. 如果當前字符的值小於下一個字符的值，則減去當前值（減法情況）
     * 4. 否則加上當前字符的值（正常情況）
     * 5. 最後一個字符總是加上其值
     * 
     * 時間複雜度：O(n) - n 是羅馬數字字串的長度
     * 空間複雜度：O(1) - HashMap 大小固定為7個符號
     */
    public int romanToInt(String s) {
        // 處理邊界情況
        if (s == null || s.length() == 0) {
            return 0;
        }
        
        // 建立羅馬數字符號與數值的映射表
        Map<Character, Integer> romanMap = new HashMap<>();
        romanMap.put('I', 1);
        romanMap.put('V', 5);
        romanMap.put('X', 10);
        romanMap.put('L', 50);
        romanMap.put('C', 100);
        romanMap.put('D', 500);
        romanMap.put('M', 1000);
        
        int result = 0;
        int n = s.length();
        
        // 遍歷羅馬數字字串
        for (int i = 0; i < n; i++) {
            char currentChar = s.charAt(i);
            int currentValue = romanMap.get(currentChar);
            
            // 檢查是否是減法情況
            // 條件：不是最後一個字符 且 當前字符值 < 下一個字符值
            if (i < n - 1 && currentValue < romanMap.get(s.charAt(i + 1))) {
                // 減法情況：IV, IX, XL, XC, CD, CM
                result -= currentValue;
            } else {
                // 正常情況：直接加上當前字符的值
                result += currentValue;
            }
        }
        
        return result;
    }
}
