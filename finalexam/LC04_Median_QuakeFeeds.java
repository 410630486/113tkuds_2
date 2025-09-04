
import java.util.*;

/**
 * LeetCode 4. Median of Two Sorted Arrays - 地震速報雙資料源中位數
 *
 * 題目說明： 地震速報系統同時接收中央氣象署與海外合作機構兩條「已排序（依時間或測站序）」
 * 的震度/加速度數列。為了快速估計當前事件的代表值，需要在「不真正合併」 兩個序列的前提下，計算它們聯合集的中位數。
 *
 * 解題思路： 1. 在較短陣列做二分搜尋，嘗試切割 2. 使左半元素數量 = (n+m+1)/2 3. 驗證左右最大/最小關係是否正確 4.
 * 處理邊界情況時使用 ±INF
 *
 * 時間複雜度：O(log(min(n,m))) 空間複雜度：O(1)
 */
class MedianSolution {

    public double findMedianSortedArrays(double[] nums1, double[] nums2) {
        // 確保 nums1 是較短的陣列
        if (nums1.length > nums2.length) {
            return findMedianSortedArrays(nums2, nums1);
        }

        int n = nums1.length;
        int m = nums2.length;
        int halfLength = (n + m + 1) / 2;

        int left = 0, right = n;

        while (left <= right) {
            int i = (left + right) / 2;  // nums1 的切割點
            int j = halfLength - i;      // nums2 的切割點

            // 處理邊界情況，使用極值
            double maxLeft1 = (i == 0) ? Double.NEGATIVE_INFINITY : nums1[i - 1];
            double maxLeft2 = (j == 0) ? Double.NEGATIVE_INFINITY : nums2[j - 1];
            double minRight1 = (i == n) ? Double.POSITIVE_INFINITY : nums1[i];
            double minRight2 = (j == m) ? Double.POSITIVE_INFINITY : nums2[j];

            // 檢查切割是否正確
            if (maxLeft1 <= minRight2 && maxLeft2 <= minRight1) {
                // 找到正確切割
                if ((n + m) % 2 == 1) {
                    // 奇數個元素，取左半最大值
                    return Math.max(maxLeft1, maxLeft2);
                } else {
                    // 偶數個元素，取中間兩值平均
                    return (Math.max(maxLeft1, maxLeft2) + Math.min(minRight1, minRight2)) / 2.0;
                }
            } else if (maxLeft1 > minRight2) {
                // nums1 切割點太右，左移
                right = i - 1;
            } else {
                // nums1 切割點太左，右移
                left = i + 1;
            }
        }

        throw new IllegalArgumentException("Input arrays are not sorted or invalid");
    }
}

public class LC04_Median_QuakeFeeds {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 讀取輸入
        int n = scanner.nextInt();
        int m = scanner.nextInt();

        double[] nums1 = new double[n];
        for (int i = 0; i < n; i++) {
            nums1[i] = scanner.nextDouble();
        }

        double[] nums2 = new double[m];
        for (int i = 0; i < m; i++) {
            nums2[i] = scanner.nextDouble();
        }

        // 求解
        MedianSolution solution = new MedianSolution();
        double result = solution.findMedianSortedArrays(nums1, nums2);

        // 輸出結果（保留 1 位小數）
        System.out.printf("%.1f%n", result);

        scanner.close();
    }
}
