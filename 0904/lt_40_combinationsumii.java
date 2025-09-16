/*
 * LeetCode 40: Combination Sum II
 * 難度: Medium
 * 
 * 解題思路:
 * 1. 與39題類似，但每個數字只能使用一次
 * 2. 先排序，便於去重
 * 3. 在同一層遞歸中跳過重複元素
 * 
 * 時間複雜度: O(2^n)
 * 空間複雜度: O(target)
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Solution {

    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(candidates); // 排序便於去重
        List<Integer> current = new ArrayList<>();
        backtrack(candidates, target, 0, current, result);
        return result;
    }

    private void backtrack(int[] candidates, int target, int start,
            List<Integer> current, List<List<Integer>> result) {
        if (target == 0) {
            result.add(new ArrayList<>(current));
            return;
        }

        if (target < 0) {
            return;
        }

        for (int i = start; i < candidates.length; i++) {
            // 跳過重複元素（同一層）
            if (i > start && candidates[i] == candidates[i - 1]) {
                continue;
            }

            current.add(candidates[i]);
            // 每個數字只能使用一次，所以傳入i+1
            backtrack(candidates, target - candidates[i], i + 1, current, result);
            current.remove(current.size() - 1); // 回溯
        }
    }
}

public class lt_40_combinationsumii {

    public static void main(String[] args) {
        Solution solution = new Solution();

        int[] candidates1 = {10, 1, 2, 7, 6, 1, 5};
        List<List<Integer>> result1 = solution.combinationSum2(candidates1, 8);
        System.out.println("結果1: " + result1);
        // 預期: [[1,1,6],[1,2,5],[1,7],[2,6]]

        int[] candidates2 = {2, 5, 2, 1, 2};
        List<List<Integer>> result2 = solution.combinationSum2(candidates2, 5);
        System.out.println("結果2: " + result2);
        // 預期: [[1,2,2],[5]]
    }
}
