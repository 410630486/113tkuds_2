
public class TicTacToeBoard {

    // 棋盤大小
    private static final int BOARD_SIZE = 3;

    // 棋盤狀態
    private char[][] board;

    // 玩家符號
    private static final char PLAYER_X = 'X';
    private static final char PLAYER_O = 'O';
    private static final char EMPTY = ' ';

    // 當前玩家
    private char currentPlayer;

    /**
     * 建構子 - 初始化棋盤
     */
    public TicTacToeBoard() {
        board = new char[BOARD_SIZE][BOARD_SIZE];
        initializeBoard();
        currentPlayer = PLAYER_X; // X 先開始
    }

    /**
     * 初始化棋盤為空
     */
    private void initializeBoard() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = EMPTY;
            }
        }
    }

    /**
     * 顯示棋盤
     */
    public void displayBoard() {
        System.out.println("\n當前棋盤狀態：");
        System.out.println("   0   1   2");

        for (int i = 0; i < BOARD_SIZE; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < BOARD_SIZE; j++) {
                System.out.print(" " + board[i][j] + " ");
                if (j < BOARD_SIZE - 1) {
                    System.out.print("|");
                }
            }
            System.out.println();

            if (i < BOARD_SIZE - 1) {
                System.out.println("  -----------");
            }
        }
        System.out.println();
    }

    /**
     * 在指定位置下棋
     */
    public boolean makeMove(int row, int col) {
        // 檢查位置是否有效
        if (!isValidPosition(row, col)) {
            System.out.println("無效的位置！請輸入 0-2 之間的數字。");
            return false;
        }

        // 檢查位置是否為空
        if (board[row][col] != EMPTY) {
            System.out.println("該位置已被佔用！");
            return false;
        }

        // 下棋
        board[row][col] = currentPlayer;
        return true;
    }

    /**
     * 檢查位置是否有效
     */
    private boolean isValidPosition(int row, int col) {
        return row >= 0 && row < BOARD_SIZE && col >= 0 && col < BOARD_SIZE;
    }

    /**
     * 檢查是否有玩家獲勝
     */
    public char checkWinner() {
        // 檢查橫行
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (checkLine(board[i][0], board[i][1], board[i][2])) {
                return board[i][0];
            }
        }

        // 檢查直列
        for (int j = 0; j < BOARD_SIZE; j++) {
            if (checkLine(board[0][j], board[1][j], board[2][j])) {
                return board[0][j];
            }
        }

        // 檢查對角線
        if (checkLine(board[0][0], board[1][1], board[2][2])) {
            return board[0][0];
        }

        if (checkLine(board[0][2], board[1][1], board[2][0])) {
            return board[0][2];
        }

        return EMPTY; // 沒有獲勝者
    }

    /**
     * 檢查三個位置是否構成獲勝條件
     */
    private boolean checkLine(char pos1, char pos2, char pos3) {
        return pos1 != EMPTY && pos1 == pos2 && pos2 == pos3;
    }

    /**
     * 檢查棋盤是否已滿
     */
    public boolean isBoardFull() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] == EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 檢查遊戲是否結束
     */
    public boolean isGameOver() {
        return checkWinner() != EMPTY || isBoardFull();
    }

    /**
     * 切換玩家
     */
    public void switchPlayer() {
        currentPlayer = (currentPlayer == PLAYER_X) ? PLAYER_O : PLAYER_X;
    }

    /**
     * 取得當前玩家
     */
    public char getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * 重置棋盤
     */
    public void resetBoard() {
        initializeBoard();
        currentPlayer = PLAYER_X;
    }

    /**
     * 取得遊戲結果描述
     */
    public String getGameResult() {
        char winner = checkWinner();
        if (winner != EMPTY) {
            return "玩家 " + winner + " 獲勝！";
        } else if (isBoardFull()) {
            return "平手！";
        } else {
            return "遊戲進行中...";
        }
    }

    /**
     * 顯示可用位置
     */
    public void showAvailablePositions() {
        System.out.println("可下棋的位置：");
        boolean hasAvailable = false;

        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] == EMPTY) {
                    System.out.printf("(%d,%d) ", i, j);
                    hasAvailable = true;
                }
            }
        }

        if (!hasAvailable) {
            System.out.println("沒有可用位置");
        }
        System.out.println();
    }

    /**
     * 分析遊戲狀態
     */
    public void analyzeGameState() {
        System.out.println("=== 遊戲狀態分析 ===");

        // 統計每個玩家的棋子數量
        int xCount = 0, oCount = 0, emptyCount = 0;

        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                switch (board[i][j]) {
                    case PLAYER_X:
                        xCount++;
                        break;
                    case PLAYER_O:
                        oCount++;
                        break;
                    case EMPTY:
                        emptyCount++;
                        break;
                }
            }
        }

        System.out.printf("X 的棋子數量：%d\n", xCount);
        System.out.printf("O 的棋子數量：%d\n", oCount);
        System.out.printf("空位數量：%d\n", emptyCount);
        System.out.printf("當前輪到：%c\n", currentPlayer);
        System.out.println("遊戲狀態：" + getGameResult());
    }

    /**
     * 示範遊戲流程
     */
    public static void demonstrateGame() {
        TicTacToeBoard game = new TicTacToeBoard();

        System.out.println("=== 井字遊戲示範 ===");
        game.displayBoard();

        // 模擬遊戲過程
        int[][] moves = {
            {1, 1}, // X 下中央
            {0, 0}, // O 下左上角
            {0, 1}, // X 下上中
            {2, 1}, // O 下下中
            {2, 2} // X 下右下角 (X獲勝)
        };

        for (int[] move : moves) {
            int row = move[0];
            int col = move[1];

            System.out.printf("玩家 %c 下棋於位置 (%d,%d)\n",
                    game.getCurrentPlayer(), row, col);

            if (game.makeMove(row, col)) {
                game.displayBoard();
                game.analyzeGameState();

                if (game.isGameOver()) {
                    System.out.println("=== 遊戲結束 ===");
                    System.out.println(game.getGameResult());
                    break;
                }

                game.switchPlayer();
                System.out.println("輪到玩家 " + game.getCurrentPlayer());
            }

            System.out.println("---");
        }
    }

    public static void main(String[] args) {
        // 示範遊戲
        demonstrateGame();

        System.out.println("\n" + "=".repeat(40));
        System.out.println("開始新的遊戲測試：");

        // 測試平手情況
        TicTacToeBoard testGame = new TicTacToeBoard();

        // 模擬平手的棋局
        int[][] tieGame = {
            {0, 0}, // X
            {0, 1}, // O
            {0, 2}, // X
            {1, 1}, // O
            {1, 0}, // X
            {1, 2}, // O
            {2, 1}, // X
            {2, 0}, // O
            {2, 2} // X (平手)
        };

        System.out.println("模擬平手遊戲：");
        testGame.displayBoard();

        for (int[] move : tieGame) {
            testGame.makeMove(move[0], move[1]);
            testGame.switchPlayer();
        }

        testGame.displayBoard();
        testGame.analyzeGameState();
    }
}
