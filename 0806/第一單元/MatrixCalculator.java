
public class MatrixCalculator {

    /**
     * 顯示矩陣
     */
    public static void printMatrix(int[][] matrix, String title) {
        System.out.println(title + ":");
        for (int[] row : matrix) {
            for (int element : row) {
                System.out.printf("%6d ", element);
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * 矩陣加法
     */
    public static int[][] addMatrices(int[][] matrix1, int[][] matrix2) {
        int rows = matrix1.length;
        int cols = matrix1[0].length;

        // 檢查矩陣維度是否相同
        if (rows != matrix2.length || cols != matrix2[0].length) {
            throw new IllegalArgumentException("矩陣維度必須相同才能進行加法運算");
        }

        int[][] result = new int[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[i][j] = matrix1[i][j] + matrix2[i][j];
            }
        }

        return result;
    }

    /**
     * 矩陣乘法
     */
    public static int[][] multiplyMatrices(int[][] matrix1, int[][] matrix2) {
        int rows1 = matrix1.length;
        int cols1 = matrix1[0].length;
        int rows2 = matrix2.length;
        int cols2 = matrix2[0].length;

        // 檢查是否可以相乘：第一個矩陣的列數必須等於第二個矩陣的行數
        if (cols1 != rows2) {
            throw new IllegalArgumentException(
                    String.format("無法相乘：矩陣維度 %dx%d 和 %dx%d 不符合乘法條件",
                            rows1, cols1, rows2, cols2));
        }

        int[][] result = new int[rows1][cols2];

        for (int i = 0; i < rows1; i++) {
            for (int j = 0; j < cols2; j++) {
                for (int k = 0; k < cols1; k++) {
                    result[i][j] += matrix1[i][k] * matrix2[k][j];
                }
            }
        }

        return result;
    }

    /**
     * 矩陣轉置
     */
    public static int[][] transposeMatrix(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;

        int[][] transposed = new int[cols][rows];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                transposed[j][i] = matrix[i][j];
            }
        }

        return transposed;
    }

    /**
     * 找出矩陣中的最大值
     */
    public static int findMaxValue(int[][] matrix) {
        int max = matrix[0][0];

        for (int[] row : matrix) {
            for (int element : row) {
                if (element > max) {
                    max = element;
                }
            }
        }

        return max;
    }

    /**
     * 找出矩陣中的最小值
     */
    public static int findMinValue(int[][] matrix) {
        int min = matrix[0][0];

        for (int[] row : matrix) {
            for (int element : row) {
                if (element < min) {
                    min = element;
                }
            }
        }

        return min;
    }

    /**
     * 找出最大值和最小值的位置
     */
    public static void findExtremeValues(int[][] matrix) {
        int max = matrix[0][0];
        int min = matrix[0][0];
        int maxRow = 0, maxCol = 0;
        int minRow = 0, minCol = 0;

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] > max) {
                    max = matrix[i][j];
                    maxRow = i;
                    maxCol = j;
                }
                if (matrix[i][j] < min) {
                    min = matrix[i][j];
                    minRow = i;
                    minCol = j;
                }
            }
        }

        System.out.printf("最大值：%d，位置：[%d][%d]\n", max, maxRow, maxCol);
        System.out.printf("最小值：%d，位置：[%d][%d]\n", min, minRow, minCol);
    }

    /**
     * 檢查是否為方陣
     */
    public static boolean isSquareMatrix(int[][] matrix) {
        return matrix.length == matrix[0].length;
    }

    /**
     * 計算矩陣對角線元素總和（僅適用於方陣）
     */
    public static int[] calculateDiagonalSums(int[][] matrix) {
        if (!isSquareMatrix(matrix)) {
            throw new IllegalArgumentException("只有方陣才能計算對角線總和");
        }

        int n = matrix.length;
        int mainDiagonalSum = 0;    // 主對角線
        int antiDiagonalSum = 0;    // 反對角線

        for (int i = 0; i < n; i++) {
            mainDiagonalSum += matrix[i][i];
            antiDiagonalSum += matrix[i][n - 1 - i];
        }

        return new int[]{mainDiagonalSum, antiDiagonalSum};
    }

    public static void main(String[] args) {
        // 測試矩陣
        int[][] matrix1 = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
        };

        int[][] matrix2 = {
            {9, 8, 7},
            {6, 5, 4},
            {3, 2, 1}
        };

        int[][] matrix3 = {
            {1, 2},
            {3, 4},
            {5, 6}
        };

        int[][] matrix4 = {
            {7, 8, 9},
            {10, 11, 12}
        };

        System.out.println("=== 矩陣運算器示範 ===");

        // 顯示原始矩陣
        printMatrix(matrix1, "矩陣 A");
        printMatrix(matrix2, "矩陣 B");

        // 矩陣加法
        try {
            int[][] sum = addMatrices(matrix1, matrix2);
            printMatrix(sum, "A + B");
        } catch (IllegalArgumentException e) {
            System.out.println("矩陣加法錯誤：" + e.getMessage());
        }

        // 矩陣乘法
        try {
            int[][] product = multiplyMatrices(matrix1, matrix2);
            printMatrix(product, "A × B");
        } catch (IllegalArgumentException e) {
            System.out.println("矩陣乘法錯誤：" + e.getMessage());
        }

        // 矩陣轉置
        int[][] transposed = transposeMatrix(matrix1);
        printMatrix(transposed, "A 的轉置");

        // 極值查找
        System.out.println("=== 矩陣 A 的極值 ===");
        findExtremeValues(matrix1);

        // 對角線總和（方陣）
        if (isSquareMatrix(matrix1)) {
            int[] diagonalSums = calculateDiagonalSums(matrix1);
            System.out.printf("主對角線總和：%d\n", diagonalSums[0]);
            System.out.printf("反對角線總和：%d\n", diagonalSums[1]);
        }

        // 測試不同維度的矩陣乘法
        System.out.println("\n=== 不同維度矩陣運算 ===");
        printMatrix(matrix3, "矩陣 C (3×2)");
        printMatrix(matrix4, "矩陣 D (2×3)");

        try {
            int[][] product2 = multiplyMatrices(matrix3, matrix4);
            printMatrix(product2, "C × D");
        } catch (IllegalArgumentException e) {
            System.out.println("矩陣乘法錯誤：" + e.getMessage());
        }

        try {
            int[][] product3 = multiplyMatrices(matrix4, matrix3);
            printMatrix(product3, "D × C");
        } catch (IllegalArgumentException e) {
            System.out.println("矩陣乘法錯誤：" + e.getMessage());
        }
    }
}
