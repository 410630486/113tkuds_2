class Solution {
    /**
     * Median of Two Sorted Arrays 解題思路：
     * 1. 使用二分搜尋法 (Binary Search) 達到 O(log(m+n)) 時間複雜度
     * 2. 在較短的陣列上進行二分搜尋，減少搜尋空間
     * 3. 找到正確的分割點，使得左半部分的最大值 <= 右半部分的最小值
     * 4. 分割後左半部分元素個數 = (m + n + 1) / 2
     * 5. 根據總長度奇偶性計算中位數
     * 
     * 時間複雜度：O(log(min(m,n))) - 在較短陣列上做二分搜尋
     * 空間複雜度：O(1) - 只使用常數額外空間
     */
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        // 確保 nums1 是較短的陣列，這樣可以減少二分搜尋的範圍
        if (nums1.length > nums2.length) {
            return findMedianSortedArrays(nums2, nums1);
        }
        
        int m = nums1.length;
        int n = nums2.length;
        
        // 二分搜尋的範圍：nums1 的分割點可能在 0 到 m 之間
        int left = 0, right = m;
        
        while (left <= right) {
            // nums1 的分割點：左半部分包含 partition1 個元素
            int partition1 = (left + right) / 2;
            // nums2 的分割點：確保左半部分總共有 (m+n+1)/2 個元素
            int partition2 = (m + n + 1) / 2 - partition1;
            
            // 計算分割點左右兩側的值
            // 如果分割點在邊界，使用極值代替
            int maxLeft1 = (partition1 == 0) ? Integer.MIN_VALUE : nums1[partition1 - 1];
            int minRight1 = (partition1 == m) ? Integer.MAX_VALUE : nums1[partition1];
            
            int maxLeft2 = (partition2 == 0) ? Integer.MIN_VALUE : nums2[partition2 - 1];
            int minRight2 = (partition2 == n) ? Integer.MAX_VALUE : nums2[partition2];
            
            // 檢查是否找到正確的分割點
            if (maxLeft1 <= minRight2 && maxLeft2 <= minRight1) {
                // 找到正確分割點，計算中位數
                if ((m + n) % 2 == 0) {
                    // 總長度為偶數：中位數是中間兩數的平均值
                    return (Math.max(maxLeft1, maxLeft2) + Math.min(minRight1, minRight2)) / 2.0;
                } else {
                    // 總長度為奇數：中位數是左半部分的最大值
                    return Math.max(maxLeft1, maxLeft2);
                }
            }
            // 調整搜尋範圍
            else if (maxLeft1 > minRight2) {
                // nums1 的分割點太靠右，需要向左移動
                right = partition1 - 1;
            } else {
                // nums1 的分割點太靠左，需要向右移動
                left = partition1 + 1;
            }
        }
        
        // 理論上不會到達這裡，因為輸入保證有效
        throw new IllegalArgumentException("Input arrays are not sorted or invalid");
    }
}
