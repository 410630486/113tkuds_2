import java.util.*;

class Solution {
    /**
     * Letter Combinations of a Phone Number 解題思路：
     * 1. 建立數字與字母的映射關係
     * 2. 使用回溯法 (Backtracking) 生成所有組合
     * 3. 對每個數字，嘗試其對應的所有字母
     * 4. 當組合長度等於輸入長度時，將結果加入答案
     * 5. 回溯時移除最後加入的字母，嘗試其他可能性
     * 
     * 時間複雜度：O(3^N × 4^M) - N是對應3字母的數字個數，M是對應4字母的數字個數
     * 空間複雜度：O(3^N × 4^M) - 儲存所有可能的組合
     */
    private String[] mapping = {
        "",     // 0
        "",     // 1
        "abc",  // 2
        "def",  // 3
        "ghi",  // 4
        "jkl",  // 5
        "mno",  // 6
        "pqrs", // 7
        "tuv",  // 8
        "wxyz"  // 9
    };
    
    public List<String> letterCombinations(String digits) {
        List<String> result = new ArrayList<>();
        
        // 處理邊界情況：空字串
        if (digits == null || digits.length() == 0) {
            return result;
        }
        
        // 開始回溯搜尋
        backtrack(result, new StringBuilder(), digits, 0);
        return result;
    }
    
    /**
     * 回溯函數：遞歸生成字母組合
     * 
     * @param result      儲存最終結果的列表
     * @param combination 當前正在構建的字母組合
     * @param digits      輸入的數字字串
     * @param index       當前處理的數字位置
     */
    private void backtrack(List<String> result, StringBuilder combination, 
                          String digits, int index) {
        // 基礎情況：已處理完所有數字
        if (index == digits.length()) {
            result.add(combination.toString());
            return;
        }
        
        // 取得當前數字對應的字母
        String letters = mapping[digits.charAt(index) - '0'];
        
        // 遍歷當前數字對應的所有字母
        for (char letter : letters.toCharArray()) {
            // 選擇：將字母加入當前組合
            combination.append(letter);
            
            // 遞歸：處理下一個數字
            backtrack(result, combination, digits, index + 1);
            
            // 回溯：移除剛才加入的字母，嘗試其他可能
            combination.deleteCharAt(combination.length() - 1);
        }
    }
}
