/*
 * LeetCode 36: Valid Sudoku
 * 難度: Medium
 * 
 * 解題思路:
 * 1. 檢查每一行、每一列、每個3x3子網格
 * 2. 使用HashSet記錄已出現的數字
 * 3. 只需檢查已填入的數字
 * 
 * 時間複雜度: O(1) - 固定9x9
 * 空間複雜度: O(1) - 固定大小
 */

import java.util.HashSet;
import java.util.Set;

class Solution {

    public boolean isValidSudoku(char[][] board) {
        // 檢查行
        for (int i = 0; i < 9; i++) {
            Set<Character> rowSet = new HashSet<>();
            for (int j = 0; j < 9; j++) {
                if (board[i][j] != '.') {
                    if (rowSet.contains(board[i][j])) {
                        return false;
                    }
                    rowSet.add(board[i][j]);
                }
            }
        }

        // 檢查列
        for (int j = 0; j < 9; j++) {
            Set<Character> colSet = new HashSet<>();
            for (int i = 0; i < 9; i++) {
                if (board[i][j] != '.') {
                    if (colSet.contains(board[i][j])) {
                        return false;
                    }
                    colSet.add(board[i][j]);
                }
            }
        }

        // 檢查3x3子網格
        for (int boxRow = 0; boxRow < 3; boxRow++) {
            for (int boxCol = 0; boxCol < 3; boxCol++) {
                Set<Character> boxSet = new HashSet<>();
                for (int i = boxRow * 3; i < boxRow * 3 + 3; i++) {
                    for (int j = boxCol * 3; j < boxCol * 3 + 3; j++) {
                        if (board[i][j] != '.') {
                            if (boxSet.contains(board[i][j])) {
                                return false;
                            }
                            boxSet.add(board[i][j]);
                        }
                    }
                }
            }
        }

        return true;
    }
}

public class lt_36_validsudoku {

    public static void main(String[] args) {
        Solution solution = new Solution();

        char[][] board1 = {
            {'5', '3', '.', '.', '7', '.', '.', '.', '.'},
            {'6', '.', '.', '1', '9', '5', '.', '.', '.'},
            {'.', '9', '8', '.', '.', '.', '.', '6', '.'},
            {'8', '.', '.', '.', '6', '.', '.', '.', '3'},
            {'4', '.', '.', '8', '.', '3', '.', '.', '1'},
            {'7', '.', '.', '.', '2', '.', '.', '.', '6'},
            {'.', '6', '.', '.', '.', '.', '2', '8', '.'},
            {'.', '.', '.', '4', '1', '9', '.', '.', '5'},
            {'.', '.', '.', '.', '8', '.', '.', '7', '9'}
        };

        System.out.println(solution.isValidSudoku(board1)); // 預期: true

        char[][] board2 = {
            {'8', '3', '.', '.', '7', '.', '.', '.', '.'},
            {'6', '.', '.', '1', '9', '5', '.', '.', '.'},
            {'.', '9', '8', '.', '.', '.', '.', '6', '.'},
            {'8', '.', '.', '.', '6', '.', '.', '.', '3'},
            {'4', '.', '.', '8', '.', '3', '.', '.', '1'},
            {'7', '.', '.', '.', '2', '.', '.', '.', '6'},
            {'.', '6', '.', '.', '.', '.', '2', '8', '.'},
            {'.', '.', '.', '4', '1', '9', '.', '.', '5'},
            {'.', '.', '.', '.', '8', '.', '.', '7', '9'}
        };

        System.out.println(solution.isValidSudoku(board2)); // 預期: false
    }
}
