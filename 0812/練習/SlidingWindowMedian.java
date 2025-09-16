
import java.util.*;

/**
 * 練習 4.6：滑動視窗的中位數 難 double getMedian() { cleanTop();
 *
 * int maxHeapSize = maxHeap.size() - getDelayedCount(maxHeap); int minHeapSize
 * = minHeap.size() - getDelayedCount(minHeap);
 *
 * if (maxHeapSize == 0 && minHeapSize == 0) { return 0.0; // 空堆的情況 }
 *
 * Integer maxTop = getTop(maxHeap); Integer minTop = getTop(minHeap);
 *
 * if (maxHeapSize == minHeapSize) { return (maxTop + minTop) / 2.0; } else if
 * (maxHeapSize > minHeapSize) { return maxTop != null ? maxTop : 0.0; } else {
 * return minTop != null ? minTop : 0.0; } } * 使用兩個 heap 計算滑動視窗的中位數
 */
public class SlidingWindowMedian {

    /**
     * 滑動視窗中位數計算器
     */
    static class MedianFinder {

        private PriorityQueue<Integer> maxHeap; // 儲存較小的一半（大的在頂部）
        private PriorityQueue<Integer> minHeap; // 儲存較大的一半（小的在頂部）
        private Map<Integer, Integer> delayedDeletes; // 延遲刪除的元素

        public MedianFinder() {
            this.maxHeap = new PriorityQueue<>((a, b) -> Integer.compare(b, a)); // Max heap
            this.minHeap = new PriorityQueue<>(); // Min heap  
            this.delayedDeletes = new HashMap<>();
        }

        /**
         * 添加數字
         */
        public void addNumber(int num) {
            if (maxHeap.isEmpty() || num <= maxHeap.peek()) {
                maxHeap.offer(num);
            } else {
                minHeap.offer(num);
            }
            balance();
        }

        /**
         * 移除數字（延遲刪除）
         */
        public void removeNumber(int num) {
            delayedDeletes.put(num, delayedDeletes.getOrDefault(num, 0) + 1);

            Integer maxTop = maxHeap.peek();
            if (maxTop != null && num <= maxTop) {
                balance();
            } else {
                balance();
            }
        }

        /**
         * 獲取中位數
         */
        public double getMedian() {
            cleanTop();

            int maxHeapSize = maxHeap.size() - getDelayedCount(maxHeap);
            int minHeapSize = minHeap.size() - getDelayedCount(minHeap);

            if (maxHeapSize == 0 && minHeapSize == 0) {
                return 0.0; // 空堆的情況
            }

            Integer maxTop = getTop(maxHeap);
            Integer minTop = getTop(minHeap);

            if (maxHeapSize == minHeapSize) {
                return (maxTop + minTop) / 2.0;
            } else if (maxHeapSize > minHeapSize) {
                return maxTop != null ? maxTop : 0.0;
            } else {
                return minTop != null ? minTop : 0.0;
            }
        }

        /**
         * 平衡兩個 heap
         */
        private void balance() {
            cleanTop();

            int maxHeapSize = maxHeap.size() - getDelayedCount(maxHeap);
            int minHeapSize = minHeap.size() - getDelayedCount(minHeap);

            if (maxHeapSize > minHeapSize + 1) {
                Integer element = maxHeap.poll();
                if (element != null) {
                    minHeap.offer(element);
                }
            } else if (minHeapSize > maxHeapSize + 1) {
                Integer element = minHeap.poll();
                if (element != null) {
                    maxHeap.offer(element);
                }
            }
        }

        /**
         * 清理頂部的延遲刪除元素
         */
        private void cleanTop() {
            while (!maxHeap.isEmpty() && delayedDeletes.getOrDefault(maxHeap.peek(), 0) > 0) {
                cleanDelayedElement(maxHeap.poll());
            }

            while (!minHeap.isEmpty() && delayedDeletes.getOrDefault(minHeap.peek(), 0) > 0) {
                cleanDelayedElement(minHeap.poll());
            }
        }

        /**
         * 清理延遲刪除的元素
         */
        private void cleanDelayedElement(int num) {
            int count = delayedDeletes.getOrDefault(num, 0);
            if (count > 0) {
                delayedDeletes.put(num, count - 1);
                if (delayedDeletes.get(num) == 0) {
                    delayedDeletes.remove(num);
                }
            }
        }

        /**
         * 獲取 heap 中延遲刪除的元素數量
         */
        private int getDelayedCount(PriorityQueue<Integer> heap) {
            int count = 0;
            for (Integer num : heap) {
                count += delayedDeletes.getOrDefault(num, 0);
            }
            return count;
        }

        /**
         * 獲取 heap 頂部元素（未被延遲刪除的）
         */
        private Integer getTop(PriorityQueue<Integer> heap) {
            while (!heap.isEmpty() && delayedDeletes.getOrDefault(heap.peek(), 0) > 0) {
                cleanDelayedElement(heap.poll());
            }
            return heap.isEmpty() ? null : heap.peek();
        }

        /**
         * 獲取當前有效大小
         */
        public int size() {
            cleanTop();
            return maxHeap.size() + minHeap.size() - getTotalDelayedCount();
        }

        private int getTotalDelayedCount() {
            return delayedDeletes.values().stream().mapToInt(Integer::intValue).sum();
        }

        /**
         * 顯示當前狀態
         */
        public void displayState() {
            cleanTop();
            System.out.printf("MaxHeap (較小一半): %s\n",
                    java.util.Arrays.toString(maxHeap.stream().filter(x -> delayedDeletes.getOrDefault(x, 0) == 0).toArray()));
            System.out.printf("MinHeap (較大一半): %s\n",
                    java.util.Arrays.toString(minHeap.stream().filter(x -> delayedDeletes.getOrDefault(x, 0) == 0).toArray()));
            System.out.printf("當前中位數: %.1f\n", getMedian());
        }
    }

    /**
     * 方法1：使用雙 heap 實作滑動視窗中位數
     */
    public static double[] medianSlidingWindow(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k <= 0) {
            return new double[0];
        }

        double[] result = new double[nums.length - k + 1];
        MedianFinder medianFinder = new MedianFinder();

        // 初始化前 k 個元素
        for (int i = 0; i < k; i++) {
            medianFinder.addNumber(nums[i]);
        }
        result[0] = medianFinder.getMedian();

        // 滑動視窗
        for (int i = k; i < nums.length; i++) {
            medianFinder.addNumber(nums[i]);           // 添加新元素
            medianFinder.removeNumber(nums[i - k]);    // 移除舊元素
            result[i - k + 1] = medianFinder.getMedian();
        }

        return result;
    }

    /**
     * 方法2：簡單實作（每次都排序） 時間複雜度：O(n * k log k)
     */
    public static double[] medianSlidingWindowSimple(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k <= 0) {
            return new double[0];
        }

        double[] result = new double[nums.length - k + 1];

        for (int i = 0; i <= nums.length - k; i++) {
            // 複製當前視窗
            int[] window = new int[k];
            System.arraycopy(nums, i, window, 0, k);

            // 排序
            Arrays.sort(window);

            // 計算中位數
            if (k % 2 == 1) {
                result[i] = window[k / 2];
            } else {
                result[i] = (window[k / 2 - 1] + window[k / 2]) / 2.0;
            }
        }

        return result;
    }

    /**
     * 方法3：使用 TreeMap 維護有序元素
     */
    public static double[] medianSlidingWindowTreeMap(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k <= 0) {
            return new double[0];
        }

        double[] result = new double[nums.length - k + 1];
        TreeMap<Integer, Integer> treeMap = new TreeMap<>();

        // 初始化前 k 個元素
        for (int i = 0; i < k; i++) {
            treeMap.put(nums[i], treeMap.getOrDefault(nums[i], 0) + 1);
        }
        result[0] = getMedianFromTreeMap(treeMap, k);

        // 滑動視窗
        for (int i = k; i < nums.length; i++) {
            // 添加新元素
            treeMap.put(nums[i], treeMap.getOrDefault(nums[i], 0) + 1);

            // 移除舊元素
            int oldNum = nums[i - k];
            treeMap.put(oldNum, treeMap.get(oldNum) - 1);
            if (treeMap.get(oldNum) == 0) {
                treeMap.remove(oldNum);
            }

            result[i - k + 1] = getMedianFromTreeMap(treeMap, k);
        }

        return result;
    }

    /**
     * 從 TreeMap 中獲取中位數
     */
    private static double getMedianFromTreeMap(TreeMap<Integer, Integer> treeMap, int k) {
        int count = 0;
        int median1 = 0, median2 = 0;
        boolean foundFirst = false;

        for (Map.Entry<Integer, Integer> entry : treeMap.entrySet()) {
            count += entry.getValue();

            if (!foundFirst && count >= (k + 1) / 2) {
                median1 = entry.getKey();
                foundFirst = true;
            }

            if (count >= (k + 2) / 2) {
                median2 = entry.getKey();
                break;
            }
        }

        return k % 2 == 1 ? median1 : (median1 + median2) / 2.0;
    }

    /**
     * 效能測試
     */
    public static void performanceTest(int[] nums, int k) {
        System.out.printf("效能測試：陣列長度 %d，視窗大小 %d\n", nums.length, k);

        // 雙 heap 方法
        long startTime = System.nanoTime();
        double[] result1 = medianSlidingWindow(nums, k);
        long endTime = System.nanoTime();
        double heapTime = (endTime - startTime) / 1_000_000.0;

        // 簡單排序方法
        startTime = System.nanoTime();
        double[] result2 = medianSlidingWindowSimple(nums, k);
        endTime = System.nanoTime();
        double simpleTime = (endTime - startTime) / 1_000_000.0;

        // TreeMap 方法
        startTime = System.nanoTime();
        double[] result3 = medianSlidingWindowTreeMap(nums, k);
        endTime = System.nanoTime();
        double treeMapTime = (endTime - startTime) / 1_000_000.0;

        System.out.printf("雙 Heap 方法：%.2f ms\n", heapTime);
        System.out.printf("簡單排序方法：%.2f ms\n", simpleTime);
        System.out.printf("TreeMap 方法：%.2f ms\n", treeMapTime);

        // 驗證結果一致性
        boolean consistent = Arrays.equals(result1, result2) && Arrays.equals(result2, result3);
        System.out.printf("結果一致性：%s\n\n", consistent);
    }

    public static void main(String[] args) {
        System.out.println("=== 滑動視窗中位數 ===");

        // 測試案例1：基本測試
        System.out.println("1. 基本測試案例：");
        int[] test1 = {1, 3, -1, -3, 5, 3, 6, 7};
        int k1 = 3;

        System.out.printf("陣列：%s，視窗大小：%d\n", Arrays.toString(test1), k1);

        double[] result1 = medianSlidingWindow(test1, k1);
        System.out.printf("結果：%s\n", Arrays.toString(result1));
        System.out.printf("期望：[1.0, -1.0, -1.0, 3.0, 5.0, 6.0]\n");

        // 詳細過程展示
        System.out.println("\n詳細視窗過程：");
        for (int i = 0; i <= test1.length - k1; i++) {
            int[] window = Arrays.copyOfRange(test1, i, i + k1);
            int[] sortedWindow = window.clone();
            Arrays.sort(sortedWindow);
            System.out.printf("視窗 %d: %s -> 排序後 %s -> 中位數 %.1f\n",
                    i + 1, Arrays.toString(window), Arrays.toString(sortedWindow), result1[i]);
        }

        // 測試案例2：偶數大小視窗
        System.out.println("\n" + "=".repeat(50));
        System.out.println("2. 偶數大小視窗測試：");
        int[] test2 = {1, 2, 3, 4};
        int k2 = 2;

        System.out.printf("陣列：%s，視窗大小：%d\n", Arrays.toString(test2), k2);

        double[] result2 = medianSlidingWindow(test2, k2);
        System.out.printf("結果：%s\n", Arrays.toString(result2));
        System.out.printf("期望：[1.5, 2.5, 3.5]\n");

        // 詳細過程
        System.out.println("詳細過程：");
        for (int i = 0; i <= test2.length - k2; i++) {
            int[] window = Arrays.copyOfRange(test2, i, i + k2);
            System.out.printf("視窗 %d: %s -> 中位數 %.1f\n",
                    i + 1, Arrays.toString(window), result2[i]);
        }

        // 測試案例3：MedianFinder 詳細演示
        System.out.println("\n3. MedianFinder 詳細演示：");
        MedianFinder mf = new MedianFinder();

        int[] demoArray = {5, 2, 8, 1, 9};
        System.out.println("逐步添加元素：");

        for (int num : demoArray) {
            mf.addNumber(num);
            System.out.printf("添加 %d 後：\n", num);
            mf.displayState();
            System.out.println();
        }

        // 測試案例4：邊界情況
        System.out.println("4. 邊界情況測試：");

        // k = 1
        int[] single = {1, 2, 3, 4, 5};
        double[] singleResult = medianSlidingWindow(single, 1);
        System.out.printf("k=1 的結果：%s\n", Arrays.toString(singleResult));

        // k = 陣列長度
        double[] fullResult = medianSlidingWindow(single, single.length);
        System.out.printf("k=陣列長度 的結果：%s\n", Arrays.toString(fullResult));

        // 重複元素
        int[] duplicates = {1, 1, 1, 2, 2, 2};
        double[] dupResult = medianSlidingWindow(duplicates, 3);
        System.out.printf("重複元素結果：%s\n", Arrays.toString(dupResult));

        // 測試案例5：方法比較
        System.out.println("\n" + "=".repeat(50));
        System.out.println("5. 不同方法比較：");

        int[] compareArray = {1, 3, -1, -3, 5, 3, 6, 7};
        int compareK = 3;

        double[] heapResult = medianSlidingWindow(compareArray, compareK);
        double[] simpleResult = medianSlidingWindowSimple(compareArray, compareK);
        double[] treeMapResult = medianSlidingWindowTreeMap(compareArray, compareK);

        System.out.printf("雙 Heap 結果：%s\n", Arrays.toString(heapResult));
        System.out.printf("簡單排序結果：%s\n", Arrays.toString(simpleResult));
        System.out.printf("TreeMap 結果：%s\n", Arrays.toString(treeMapResult));

        boolean allSame = Arrays.equals(heapResult, simpleResult)
                && Arrays.equals(simpleResult, treeMapResult);
        System.out.printf("所有方法結果一致：%s\n", allSame);

        // 測試案例6：效能測試
        System.out.println("\n6. 效能測試：");

        // 生成測試數據
        Random random = new Random(42);

        // 小規模測試
        int[] smallTest = new int[100];
        for (int i = 0; i < smallTest.length; i++) {
            smallTest[i] = random.nextInt(1000);
        }
        performanceTest(smallTest, 10);

        // 中規模測試
        int[] mediumTest = new int[1000];
        for (int i = 0; i < mediumTest.length; i++) {
            mediumTest[i] = random.nextInt(10000);
        }
        performanceTest(mediumTest, 50);

        // 大規模測試
        int[] largeTest = new int[5000];
        for (int i = 0; i < largeTest.length; i++) {
            largeTest[i] = random.nextInt(100000);
        }
        performanceTest(largeTest, 100);

        // 測試案例7：極端情況
        System.out.println("7. 極端情況測試：");

        // 所有元素相同
        int[] sameElements = new int[10];
        Arrays.fill(sameElements, 5);
        double[] sameResult = medianSlidingWindow(sameElements, 3);
        System.out.printf("所有元素相同：%s\n", Arrays.toString(sameResult));

        // 嚴格遞增
        int[] increasing = {1, 2, 3, 4, 5, 6, 7, 8};
        double[] incResult = medianSlidingWindow(increasing, 4);
        System.out.printf("嚴格遞增：%s\n", Arrays.toString(incResult));

        // 嚴格遞減
        int[] decreasing = {8, 7, 6, 5, 4, 3, 2, 1};
        double[] decResult = medianSlidingWindow(decreasing, 4);
        System.out.printf("嚴格遞減：%s\n", Arrays.toString(decResult));

        // 測試案例8：複雜度分析
        System.out.println("\n8. 複雜度分析：");
        System.out.println("方法1 (雙 Heap)：");
        System.out.println("  時間複雜度：O(n log k)");
        System.out.println("  空間複雜度：O(k)");
        System.out.println("  優點：高效處理大數據，支援動態更新");
        System.out.println("  缺點：實作複雜，需要延遲刪除機制");

        System.out.println("\n方法2 (每次排序)：");
        System.out.println("  時間複雜度：O(n * k log k)");
        System.out.println("  空間複雜度：O(k)");
        System.out.println("  優點：實作簡單，容易理解");
        System.out.println("  缺點：效率較低，不適合大數據");

        System.out.println("\n方法3 (TreeMap)：");
        System.out.println("  時間複雜度：O(n log k)");
        System.out.println("  空間複雜度：O(k)");
        System.out.println("  優點：平衡的性能，實作相對簡單");
        System.out.println("  缺點：常數因子較大");

        System.out.println("\n=== 滑動視窗中位數測試完成 ===");
    }
}
