class Solution {
    /**
     * Integer to Roman 解題思路：
     * 1. 預先定義所有可能的羅馬數字符號及其對應值
     * 2. 包含特殊的減法形式 (IV, IX, XL, XC, CD, CM)
     * 3. 從最大值開始，貪心地選擇符號
     * 4. 重複減去當前符號的值，直到無法再減
     * 5. 移動到下一個較小的符號
     * 
     * 時間複雜度：O(1) - 最多處理13個符號，每個符號最多使用3次
     * 空間複雜度：O(1) - 使用固定大小的陣列
     */
    public String intToRoman(int num) {
        // 定義羅馬數字符號和對應的數值
        // 按照從大到小的順序排列，包含減法形式
        int[] values = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] symbols = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        
        StringBuilder result = new StringBuilder();
        
        // 遍歷所有符號，從最大值開始
        for (int i = 0; i < values.length; i++) {
            int value = values[i];
            String symbol = symbols[i];
            
            // 計算當前符號可以使用多少次
            int count = num / value;
            
            // 將符號重複添加對應次數
            for (int j = 0; j < count; j++) {
                result.append(symbol);
            }
            
            // 更新剩餘數值
            num = num % value;
            
            // 如果數值已經為0，提前結束
            if (num == 0) {
                break;
            }
        }
        
        return result.toString();
    }
}
