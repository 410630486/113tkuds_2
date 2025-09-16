
import java.util.*;

/**
 * LeetCode 39. Combination Sum - 防災物資組合總和
 *
 * 題目說明： 防災物資採購：有一批物資價格清單（可含重複價格）。 欲在預算 target 下列出所有可行物資組合。 I
 * 版：同一價格可無限次採購（視為庫存充足）。
 *
 * 解題思路： 1. 回溯樹：節點為目前選擇，邊為加入一數 2. 剩餘值=0 收錄，<0 回溯 3. I：下一層仍傳 i 4. 回溯 + 剩餘值剪枝
 *
 * 時間複雜度：指數級 空間複雜度：O(target/min)
 */
public class LC39_CombinationSum_PPE {

    public static List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(candidates); // 排序便於剪枝
        backtrack(result, new ArrayList<>(), candidates, target, 0);
        return result;
    }

    private static void backtrack(List<List<Integer>> result, List<Integer> current,
            int[] candidates, int remaining, int start) {
        if (remaining == 0) {
            result.add(new ArrayList<>(current));
            return;
        }

        if (remaining < 0) {
            return; // 剪枝
        }

        for (int i = start; i < candidates.length; i++) {
            // 剪枝：如果當前候選數已經大於剩餘值，後面的更大數也不用考慮
            if (candidates[i] > remaining) {
                break;
            }

            current.add(candidates[i]);
            // 注意：可以重複使用同一個數，所以仍傳 i
            backtrack(result, current, candidates, remaining - candidates[i], i);
            current.remove(current.size() - 1);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();
        int target = scanner.nextInt();
        int[] candidates = new int[n];

        for (int i = 0; i < n; i++) {
            candidates[i] = scanner.nextInt();
        }

        List<List<Integer>> result = combinationSum(candidates, target);

        // 輸出結果
        for (List<Integer> combination : result) {
            for (int i = 0; i < combination.size(); i++) {
                if (i > 0) {
                    System.out.print(" ");
                }
                System.out.print(combination.get(i));
            }
            System.out.println();
        }

        scanner.close();
    }
}
