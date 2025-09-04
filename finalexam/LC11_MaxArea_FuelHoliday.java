
import java.util.*;

/**
 * LeetCode 11. Container With Most Water - 連假油量促銷最大區間
 *
 * 題目說明： 連假前加油站促銷活動要在多個臨時油槽（不同高度代表可用輸出能力）間 選「兩個位置」架設暫時輸油管，期望管線橫距 * 可用最小高度
 * 形成的"輸出帶寬"最大。 給定整數陣列 heights，選兩指標 i, j（i<j）最大化 (j-i)*min(heights[i],
 * heights[j])。
 *
 * 解題思路： 1. 使用雙指針技術：最左和最右開始 2. 唯有提升短邊才可能變大，所以移動較短邊 3. 每次計算面積並更新最大值 4. 當左右指針相遇時結束
 *
 * 時間複雜度：O(n) 空間複雜度：O(1)
 */
class ContainerSolution {

    public int maxArea(int[] height) {
        if (height == null || height.length < 2) {
            return 0;
        }

        int left = 0;
        int right = height.length - 1;
        int maxArea = 0;

        while (left < right) {
            // 計算當前面積
            int width = right - left;
            int minHeight = Math.min(height[left], height[right]);
            int currentArea = width * minHeight;

            // 更新最大面積
            maxArea = Math.max(maxArea, currentArea);

            // 移動較短邊的指針
            if (height[left] < height[right]) {
                left++;
            } else {
                right--;
            }
        }

        return maxArea;
    }
}

public class LC11_MaxArea_FuelHoliday {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 讀取輸入
        int n = scanner.nextInt();
        int[] heights = new int[n];

        for (int i = 0; i < n; i++) {
            heights[i] = scanner.nextInt();
        }

        // 求解
        ContainerSolution solution = new ContainerSolution();
        int result = solution.maxArea(heights);

        // 輸出結果
        System.out.println(result);

        scanner.close();
    }
}
