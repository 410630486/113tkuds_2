import java.util.ArrayList;
import java.util.List;

class Solution {
    /**
     * Zigzag Conversion 解題思路：
     * 1. 使用 StringBuilder 陣列，每一行對應一個 StringBuilder
     * 2. 模擬 zigzag 的移動方向：向下 → 向右上斜 → 向下 → ...
     * 3. 使用方向標記 (goingDown) 來控制行號的變化
     * 4. 當到達第一行或最後一行時改變方向
     * 5. 最後將所有行的字符串連接起來
     * 
     * 時間複雜度：O(n) - 遍歷字串一次
     * 空間複雜度：O(n) - 需要存儲所有字符
     */
    public String convert(String s, int numRows) {
        // 特殊情況：只有一行或字串長度小於等於行數
        if (numRows == 1 || s.length() <= numRows) {
            return s;
        }
        
        // 為每一行創建一個 StringBuilder
        List<StringBuilder> rows = new ArrayList<>();
        for (int i = 0; i < numRows; i++) {
            rows.add(new StringBuilder());
        }
        
        // 當前行號和移動方向
        int currentRow = 0;
        boolean goingDown = false;
        
        // 遍歷字串中的每個字符
        for (char c : s.toCharArray()) {
            // 將字符添加到當前行
            rows.get(currentRow).append(c);
            
            // 判斷是否需要改變方向
            if (currentRow == 0 || currentRow == numRows - 1) {
                goingDown = !goingDown;
            }
            
            // 根據方向更新行號
            currentRow += goingDown ? 1 : -1;
        }
        
        // 將所有行連接成最終結果
        StringBuilder result = new StringBuilder();
        for (StringBuilder row : rows) {
            result.append(row);
        }
        
        return result.toString();
    }
}
