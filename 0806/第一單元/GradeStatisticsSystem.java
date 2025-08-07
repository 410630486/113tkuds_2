
import java.util.Arrays;

public class GradeStatisticsSystem {

    /**
     * 計算平均值
     */
    public static double calculateAverage(int[] grades) {
        int sum = 0;
        for (int grade : grades) {
            sum += grade;
        }
        return (double) sum / grades.length;
    }

    /**
     * 找出最高分
     */
    public static int findMaxGrade(int[] grades) {
        int max = grades[0];
        for (int grade : grades) {
            if (grade > max) {
                max = grade;
            }
        }
        return max;
    }

    /**
     * 找出最低分
     */
    public static int findMinGrade(int[] grades) {
        int min = grades[0];
        for (int grade : grades) {
            if (grade < min) {
                min = grade;
            }
        }
        return min;
    }

    /**
     * 根據分數判斷等第
     */
    public static char getGradeLevel(int score) {
        if (score >= 90) {
            return 'A'; 
        }else if (score >= 80) {
            return 'B'; 
        }else if (score >= 70) {
            return 'C'; 
        }else if (score >= 60) {
            return 'D'; 
        }else {
            return 'F';
        }
    }

    /**
     * 統計各等第人數
     */
    public static int[] countGradeLevels(int[] grades) {
        int[] counts = new int[5]; // A, B, C, D, F

        for (int grade : grades) {
            char level = getGradeLevel(grade);
            switch (level) {
                case 'A':
                    counts[0]++;
                    break;
                case 'B':
                    counts[1]++;
                    break;
                case 'C':
                    counts[2]++;
                    break;
                case 'D':
                    counts[3]++;
                    break;
                case 'F':
                    counts[4]++;
                    break;
            }
        }

        return counts;
    }

    /**
     * 計算高於平均分的學生人數
     */
    public static int countAboveAverage(int[] grades, double average) {
        int count = 0;
        for (int grade : grades) {
            if (grade > average) {
                count++;
            }
        }
        return count;
    }

    /**
     * 列印完整成績報表
     */
    public static void printGradeReport(int[] grades) {
        System.out.println("=== 成績統計報表 ===");
        System.out.println("原始成績：" + Arrays.toString(grades));
        System.out.printf("學生總數：%d 人\n", grades.length);

        // 基本統計
        double average = calculateAverage(grades);
        int maxGrade = findMaxGrade(grades);
        int minGrade = findMinGrade(grades);

        System.out.printf("平均分數：%.2f 分\n", average);
        System.out.printf("最高分數：%d 分\n", maxGrade);
        System.out.printf("最低分數：%d 分\n", minGrade);
        System.out.printf("分數範圍：%d 分\n", maxGrade - minGrade);

        // 等第統計
        int[] levelCounts = countGradeLevels(grades);
        String[] levelNames = {"A (90-100)", "B (80-89)", "C (70-79)", "D (60-69)", "F (0-59)"};

        System.out.println("\n=== 等第分布 ===");
        for (int i = 0; i < levelCounts.length; i++) {
            double percentage = (double) levelCounts[i] / grades.length * 100;
            System.out.printf("%s：%d 人 (%.1f%%)\n", levelNames[i], levelCounts[i], percentage);
        }

        // 高於平均分統計
        int aboveAverageCount = countAboveAverage(grades, average);
        double aboveAveragePercentage = (double) aboveAverageCount / grades.length * 100;
        System.out.printf("\n高於平均分：%d 人 (%.1f%%)\n", aboveAverageCount, aboveAveragePercentage);

        // 詳細成績列表
        System.out.println("\n=== 詳細成績列表 ===");
        for (int i = 0; i < grades.length; i++) {
            char level = getGradeLevel(grades[i]);
            String status = grades[i] > average ? "↑" : grades[i] == average ? "=" : "↓";
            System.out.printf("學生 %2d：%3d 分 (%c) %s\n", i + 1, grades[i], level, status);
        }
    }

    public static void main(String[] args) {
        // 測試資料
        int[] grades = {85, 92, 78, 96, 87, 73, 89, 94, 82, 90};

        // 列印完整報表
        printGradeReport(grades);

        // 額外測試
        System.out.println("\n=== 額外分析 ===");
        double average = calculateAverage(grades);

        // 找出最接近平均分的成績
        int closestToAverage = grades[0];
        double minDifference = Math.abs(grades[0] - average);

        for (int grade : grades) {
            double difference = Math.abs(grade - average);
            if (difference < minDifference) {
                minDifference = difference;
                closestToAverage = grade;
            }
        }

        System.out.printf("最接近平均分的成績：%d 分 (差距：%.2f)\n",
                closestToAverage, minDifference);

        // 計算標準差
        double variance = 0;
        for (int grade : grades) {
            variance += Math.pow(grade - average, 2);
        }
        variance /= grades.length;
        double standardDeviation = Math.sqrt(variance);

        System.out.printf("標準差：%.2f\n", standardDeviation);
    }
}
