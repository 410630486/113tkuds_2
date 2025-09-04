
import java.util.*;

/**
 * LeetCode 1. Two Sum - 高鐵連假加班車
 *
 * 題目說明： 高鐵在連假增開加班車後，營運中心需要快速指派「兩個班次」把臨時新增的旅客人數精準吸收。
 * 給你一個陣列，每個值是該班次目前尚可釋出的座位數，以及一個 target（臨時新增需求總座位）。 請找出兩個不同班次索引 i, j，其座位數和正好等於
 * target。 若無法精準湊到，輸出 -1 -1。
 *
 * 解題思路： 1. 使用 HashMap 記錄每個數字對應的索引 2. 遍歷陣列時，檢查是否存在 target - current 的數字 3.
 * 如果存在，返回兩個索引；如果不存在，將當前數字和索引存入 map 4. 掃到一個數 x，若之前有人在等 x（即 map 裡存在 x），就完成 5.
 * 否則記錄「還需要 target-x」
 *
 * 時間複雜度：O(n)，只需要遍歷一次陣列 空間複雜度：O(n)，HashMap 最多存儲 n 個元素
 */

public class LC01_TwoSum_THSRHoliday {

    public static int[] twoSum(int[] nums, int target) {
        // 使用 HashMap 存儲 <需要的數, 索引>
        Map<Integer, Integer> needMap = new HashMap<>();

        // 遍歷陣列
        for (int i = 0; i < nums.length; i++) {
            int current = nums[i];

            // 檢查是否有人在等待這個數字
            if (needMap.containsKey(current)) {
                // 找到解，返回兩個索引
                int previousIndex = needMap.get(current);
                return new int[]{previousIndex, i};
            }

            // 記錄還需要的數字和當前索引
            int need = target - current;
            needMap.put(need, i);
        }

        // 沒有找到解
        return new int[]{-1, -1};
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 讀取輸入
        int n = scanner.nextInt();
        int target = scanner.nextInt();

        int[] seats = new int[n];
        for (int i = 0; i < n; i++) {
            seats[i] = scanner.nextInt();
        }

        // 求解
        Solution solution = new Solution();
        int[] result = solution.twoSum(seats, target);

        // 輸出結果
        System.out.println(result[0] + " " + result[1]);

        scanner.close();
    }
}
