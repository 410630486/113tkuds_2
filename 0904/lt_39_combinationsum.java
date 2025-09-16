/*
 * LeetCode 39: Combination Sum
 * 難度: Medium
 * 
 * 解題思路:
 * 1. 使用回溯法
 * 2. 每個數字可以重複使用
 * 3. 為避免重複，每次只考慮當前索引及之後的數字
 * 
 * 時間複雜度: O(N^(T/M)) N是數組長度，T是target，M是最小元素
 * 空間複雜度: O(T/M)
 */

import java.util.ArrayList;
import java.util.List;

class Solution {

    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
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
            current.add(candidates[i]);
            // 可以重複使用當前數字，所以傳入i而不是i+1
            backtrack(candidates, target - candidates[i], i, current, result);
            current.remove(current.size() - 1); // 回溯
        }
    }
}

public class lt_39_combinationsum {

    public static void main(String[] args) {
        Solution solution = new Solution();

        int[] candidates1 = {2, 3, 6, 7};
        List<List<Integer>> result1 = solution.combinationSum(candidates1, 7);
        System.out.println("結果1: " + result1);
        // 預期: [[2,2,3],[7]]

        int[] candidates2 = {2, 3, 5};
        List<List<Integer>> result2 = solution.combinationSum(candidates2, 8);
        System.out.println("結果2: " + result2);
        // 預期: [[2,2,2,2],[2,3,3],[3,5]]

        int[] candidates3 = {2};
        List<List<Integer>> result3 = solution.combinationSum(candidates3, 1);
        System.out.println("結果3: " + result3);
        // 預期: []
    }
}
