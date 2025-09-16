/*
 * LeetCode 37: Sudoku Solver
 * 難度: Hard
 * 
 * 解題思路:
 * 1. 使用回溯法
 * 2. 找到空格，嘗試填入1-9
 * 3. 檢查是否有效，無效則回溯
 * 
 * 時間複雜度: O(9^(n*n)) 最壞情況
 * 空間複雜度: O(1) 除了遞歸棧
 */

class Solution {

    public void solveSudoku(char[][] board) {
        solve(board);
    }

    private boolean solve(char[][] board) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == '.') {
                    for (char c = '1'; c <= '9'; c++) {
                        if (isValid(board, i, j, c)) {
                            board[i][j] = c;

                            if (solve(board)) {
                                return true;
                            }

                            board[i][j] = '.'; // 回溯
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isValid(char[][] board, int row, int col, char c) {
        for (int i = 0; i < 9; i++) {
            // 檢查行
            if (board[row][i] == c) {
                return false;
            }
            // 檢查列
            if (board[i][col] == c) {
                return false;
            }
            // 檢查3x3子網格
            if (board[3 * (row / 3) + i / 3][3 * (col / 3) + i % 3] == c) {
                return false;
            }
        }
        return true;
    }
}

public class lt_37_sudokusolver {

    public static void main(String[] args) {
        Solution solution = new Solution();

        char[][] board = {
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

        solution.solveSudoku(board);

        // 輸出結果
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }
}
