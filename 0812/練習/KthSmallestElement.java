import java.util.*;

/**
 * 練習 4.4：找到陣列中第 K 小的元素
 * 難度：★★☆☆☆
 * 
 * 使用不同的 Heap 方法解決第 K 小元素問題，並比較效率
 */
public class KthSmallestElement {

    /**
     * 方法1：使用大小為 K 的 Max Heap
     * 時間複雜度：O(n log k)
     * 空間複雜度：O(k)
     */
    public static Integer findKthSmallestWithMaxHeap(int[] arr, int k) {
        if (arr == null || k <= 0 || k > arr.length) {
            return null;
        }

        // 使用 Max Heap，保持大小為 k
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> Integer.compare(b, a));

        for (int num : arr) {
            maxHeap.offer(num);
            
            // 如果 heap 大小超過 k，移除最大值
            if (maxHeap.size() > k) {
                maxHeap.poll();
            }
        }

        return maxHeap.peek(); // heap 頂部就是第 k 小的元素
    }

    /**
     * 方法2：使用 Min Heap 然後提取 K 次
     * 時間複雜度：O(n log n + k log n)
     * 空間複雜度：O(n)
     */
    public static Integer findKthSmallestWithMinHeap(int[] arr, int k) {
        if (arr == null || k <= 0 || k > arr.length) {
            return null;
        }

        // 將所有元素加入 Min Heap
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        for (int num : arr) {
            minHeap.offer(num);
        }

        // 提取 k 次，第 k 次提取的就是第 k 小的元素
        for (int i = 0; i < k - 1; i++) {
            minHeap.poll();
        }

        return minHeap.poll();
    }

    /**
     * 方法3：使用排序後直接取值
     * 時間複雜度：O(n log n)
     * 空間複雜度：O(1) 或 O(n) 取決於排序算法
     */
    public static Integer findKthSmallestWithSorting(int[] arr, int k) {
        if (arr == null || k <= 0 || k > arr.length) {
            return null;
        }

        int[] sortedArr = arr.clone();
        Arrays.sort(sortedArr);
        return sortedArr[k - 1];
    }

    /**
     * 方法4：使用 QuickSelect 算法（最優解）
     * 平均時間複雜度：O(n)
     * 最壞時間複雜度：O(n²)
     * 空間複雜度：O(1)
     */
    public static Integer findKthSmallestWithQuickSelect(int[] arr, int k) {
        if (arr == null || k <= 0 || k > arr.length) {
            return null;
        }

        int[] workingArr = arr.clone();
        return quickSelect(workingArr, 0, workingArr.length - 1, k - 1);
    }

    private static int quickSelect(int[] arr, int left, int right, int k) {
        if (left == right) {
            return arr[left];
        }

        // 隨機選擇 pivot 以避免最壞情況
        Random random = new Random();
        int pivotIndex = left + random.nextInt(right - left + 1);
        pivotIndex = partition(arr, left, right, pivotIndex);

        if (k == pivotIndex) {
            return arr[k];
        } else if (k < pivotIndex) {
            return quickSelect(arr, left, pivotIndex - 1, k);
        } else {
            return quickSelect(arr, pivotIndex + 1, right, k);
        }
    }

    private static int partition(int[] arr, int left, int right, int pivotIndex) {
        int pivotValue = arr[pivotIndex];
        
        // 將 pivot 移到末尾
        swap(arr, pivotIndex, right);
        
        int storeIndex = left;
        for (int i = left; i < right; i++) {
            if (arr[i] < pivotValue) {
                swap(arr, storeIndex, i);
                storeIndex++;
            }
        }
        
        // 將 pivot 移到正確位置
        swap(arr, storeIndex, right);
        return storeIndex;
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    /**
     * 性能測試類別
     */
    static class PerformanceTest {
        public static void testPerformance(int[] arr, int k) {
            System.out.printf("測試陣列大小：%d，查找第 %d 小的元素\n", arr.length, k);
            
            // 方法1：Max Heap
            long startTime = System.nanoTime();
            Integer result1 = findKthSmallestWithMaxHeap(arr, k);
            long endTime = System.nanoTime();
            System.out.printf("方法1 (Max Heap)：結果=%d，耗時=%.2f ms\n", 
                result1, (endTime - startTime) / 1_000_000.0);

            // 方法2：Min Heap
            startTime = System.nanoTime();
            Integer result2 = findKthSmallestWithMinHeap(arr, k);
            endTime = System.nanoTime();
            System.out.printf("方法2 (Min Heap)：結果=%d，耗時=%.2f ms\n", 
                result2, (endTime - startTime) / 1_000_000.0);

            // 方法3：排序
            startTime = System.nanoTime();
            Integer result3 = findKthSmallestWithSorting(arr, k);
            endTime = System.nanoTime();
            System.out.printf("方法3 (排序)：結果=%d，耗時=%.2f ms\n", 
                result3, (endTime - startTime) / 1_000_000.0);

            // 方法4：QuickSelect
            startTime = System.nanoTime();
            Integer result4 = findKthSmallestWithQuickSelect(arr, k);
            endTime = System.nanoTime();
            System.out.printf("方法4 (QuickSelect)：結果=%d，耗時=%.2f ms\n", 
                result4, (endTime - startTime) / 1_000_000.0);

            // 驗證結果一致性
            boolean allSame = Objects.equals(result1, result2) && 
                             Objects.equals(result2, result3) && 
                             Objects.equals(result3, result4);
            System.out.printf("所有方法結果一致：%s\n\n", allSame);
        }
    }

    /**
     * 輔助方法：生成測試陣列
     */
    public static int[] generateRandomArray(int size, int maxValue) {
        Random random = new Random(42); // 固定種子確保可重現
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = random.nextInt(maxValue);
        }
        return arr;
    }

    /**
     * 輔助方法：生成已排序陣列
     */
    public static int[] generateSortedArray(int size) {
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = i + 1;
        }
        return arr;
    }

    /**
     * 輔助方法：生成逆序陣列
     */
    public static int[] generateReverseSortedArray(int size) {
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = size - i;
        }
        return arr;
    }

    /**
     * 驗證結果正確性
     */
    public static boolean verifyResult(int[] arr, int k, int result) {
        if (arr == null || k <= 0 || k > arr.length) {
            return false;
        }

        int[] sorted = arr.clone();
        Arrays.sort(sorted);
        return sorted[k - 1] == result;
    }

    public static void main(String[] args) {
        System.out.println("=== 找到陣列中第 K 小的元素 ===");

        // 測試案例1：基本測試
        System.out.println("1. 基本測試案例：");
        
        int[] test1 = {7, 10, 4, 3, 20, 15};
        int k1 = 3;
        System.out.printf("陣列：%s，查找第 %d 小的元素\n", Arrays.toString(test1), k1);
        
        int result1_1 = findKthSmallestWithMaxHeap(test1, k1);
        int result1_2 = findKthSmallestWithMinHeap(test1, k1);
        int result1_3 = findKthSmallestWithSorting(test1, k1);
        int result1_4 = findKthSmallestWithQuickSelect(test1, k1);
        
        System.out.printf("Max Heap 方法：%d\n", result1_1);
        System.out.printf("Min Heap 方法：%d\n", result1_2);
        System.out.printf("排序方法：%d\n", result1_3);
        System.out.printf("QuickSelect 方法：%d\n", result1_4);
        System.out.printf("期望結果：7 (排序後 [3,4,7,10,15,20])\n");
        System.out.printf("結果驗證：%s\n\n", verifyResult(test1, k1, result1_1));

        // 測試案例2：單元素陣列
        System.out.println("2. 單元素陣列測試：");
        int[] test2 = {1};
        int k2 = 1;
        System.out.printf("陣列：%s，查找第 %d 小的元素\n", Arrays.toString(test2), k2);
        
        int result2 = findKthSmallestWithMaxHeap(test2, k2);
        System.out.printf("結果：%d\n", result2);
        System.out.printf("期望結果：1\n");
        System.out.printf("結果驗證：%s\n\n", verifyResult(test2, k2, result2));

        // 測試案例3：有重複元素
        System.out.println("3. 重複元素測試：");
        int[] test3 = {3, 1, 4, 1, 5, 9, 2, 6};
        int k3 = 4;
        System.out.printf("陣列：%s，查找第 %d 小的元素\n", Arrays.toString(test3), k3);
        
        int result3 = findKthSmallestWithMaxHeap(test3, k3);
        System.out.printf("結果：%d\n", result3);
        System.out.printf("期望結果：3 (排序後 [1,1,2,3,4,5,6,9])\n");
        System.out.printf("結果驗證：%s\n\n", verifyResult(test3, k3, result3));

        // 測試案例4：邊界情況
        System.out.println("4. 邊界情況測試：");
        
        // k = 1 (最小值)
        int[] test4a = {5, 2, 8, 1, 9};
        int k4a = 1;
        int result4a = findKthSmallestWithMaxHeap(test4a, k4a);
        System.out.printf("第 1 小 (最小值)：%d，期望：1\n", result4a);
        
        // k = length (最大值)
        int k4b = test4a.length;
        int result4b = findKthSmallestWithMaxHeap(test4a, k4b);
        System.out.printf("第 %d 小 (最大值)：%d，期望：9\n", k4b, result4b);
        
        // 無效的 k
        Integer result4c = findKthSmallestWithMaxHeap(test4a, 0);
        Integer result4d = findKthSmallestWithMaxHeap(test4a, 10);
        System.out.printf("k=0 結果：%s，k=10 結果：%s\n\n", result4c, result4d);

        // 測試案例5：性能比較
        System.out.println("5. 性能比較測試：");
        
        // 小規模陣列
        int[] smallArray = generateRandomArray(100, 1000);
        PerformanceTest.testPerformance(smallArray, 25);
        
        // 中規模陣列
        int[] mediumArray = generateRandomArray(1000, 10000);
        PerformanceTest.testPerformance(mediumArray, 250);
        
        // 大規模陣列
        int[] largeArray = generateRandomArray(10000, 100000);
        PerformanceTest.testPerformance(largeArray, 2500);

        // 測試案例6：不同陣列類型的性能
        System.out.println("6. 不同陣列類型的性能測試：");
        
        int size = 5000;
        int k = 1000;
        
        // 隨機陣列
        System.out.println("隨機陣列：");
        int[] randomArr = generateRandomArray(size, 100000);
        PerformanceTest.testPerformance(randomArr, k);
        
        // 已排序陣列
        System.out.println("已排序陣列：");
        int[] sortedArr = generateSortedArray(size);
        PerformanceTest.testPerformance(sortedArr, k);
        
        // 逆序陣列
        System.out.println("逆序陣列：");
        int[] reverseArr = generateReverseSortedArray(size);
        PerformanceTest.testPerformance(reverseArr, k);

        // 測試案例7：算法複雜度分析
        System.out.println("7. 算法複雜度分析：");
        System.out.println("方法1 (Max Heap K個元素)：");
        System.out.println("  時間複雜度：O(n log k)");
        System.out.println("  空間複雜度：O(k)");
        System.out.println("  適用場景：k 遠小於 n 時最優");
        
        System.out.println("\n方法2 (Min Heap 全部元素)：");
        System.out.println("  時間複雜度：O(n log n + k log n)");
        System.out.println("  空間複雜度：O(n)");
        System.out.println("  適用場景：需要多次查詢不同的 k 值");
        
        System.out.println("\n方法3 (完全排序)：");
        System.out.println("  時間複雜度：O(n log n)");
        System.out.println("  空間複雜度：O(1) 或 O(n)");
        System.out.println("  適用場景：需要完整排序結果或多次查詢");
        
        System.out.println("\n方法4 (QuickSelect)：");
        System.out.println("  時間複雜度：平均 O(n)，最壞 O(n²)");
        System.out.println("  空間複雜度：O(1)");
        System.out.println("  適用場景：單次查詢的最優解");

        // 測試案例8：極端情況壓力測試
        System.out.println("\n8. 極端情況壓力測試：");
        
        // 所有元素相同
        int[] sameElements = new int[1000];
        Arrays.fill(sameElements, 42);
        int resultSame = findKthSmallestWithMaxHeap(sameElements, 500);
        System.out.printf("所有元素相同 (1000個42)，第500小：%d\n", resultSame);
        
        // 只有兩個不同值
        int[] twoValues = new int[1000];
        for (int i = 0; i < 1000; i++) {
            twoValues[i] = i % 2;
        }
        int resultTwo = findKthSmallestWithMaxHeap(twoValues, 600);
        System.out.printf("只有兩個值 (0,1交替)，第600小：%d\n", resultTwo);

        System.out.println("\n=== 第 K 小元素查找測試完成 ===");
    }
}
