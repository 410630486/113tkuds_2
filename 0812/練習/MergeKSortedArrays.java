import java.util.*;

/**
 * 練習 4.5：合併多個有序陣列
 * 難度：★★★☆☆
 * 
 * 使用 Min Heap 合併 K 個有序陣列成一個有序陣列
 */
public class MergeKSortedArrays {

    /**
     * 陣列元素包裝類，包含值、陣列索引和元素索引
     */
    static class ArrayElement {
        int value;
        int arrayIndex;
        int elementIndex;

        ArrayElement(int value, int arrayIndex, int elementIndex) {
            this.value = value;
            this.arrayIndex = arrayIndex;
            this.elementIndex = elementIndex;
        }

        @Override
        public String toString() {
            return String.format("值:%d (陣列%d[%d])", value, arrayIndex, elementIndex);
        }
    }

    /**
     * 方法1：使用 Min Heap 合併 K 個有序陣列
     * 時間複雜度：O(N log K)，其中 N 是所有元素的總數，K 是陣列數量
     * 空間複雜度：O(K)
     */
    public static int[] mergeKSortedArrays(int[][] arrays) {
        if (arrays == null || arrays.length == 0) {
            return new int[0];
        }

        // 計算總元素數量
        int totalElements = 0;
        for (int[] array : arrays) {
            if (array != null) {
                totalElements += array.length;
            }
        }

        if (totalElements == 0) {
            return new int[0];
        }

        // 使用 Min Heap 儲存每個陣列的當前最小元素
        PriorityQueue<ArrayElement> minHeap = new PriorityQueue<>(
            (a, b) -> Integer.compare(a.value, b.value)
        );

        // 將每個非空陣列的第一個元素加入 heap
        for (int i = 0; i < arrays.length; i++) {
            if (arrays[i] != null && arrays[i].length > 0) {
                minHeap.offer(new ArrayElement(arrays[i][0], i, 0));
            }
        }

        int[] result = new int[totalElements];
        int resultIndex = 0;

        // 持續從 heap 中取出最小值，並從該陣列中加入下一個元素
        while (!minHeap.isEmpty()) {
            ArrayElement current = minHeap.poll();
            result[resultIndex++] = current.value;

            // 如果該陣列還有更多元素，將下一個元素加入 heap
            if (current.elementIndex + 1 < arrays[current.arrayIndex].length) {
                int nextValue = arrays[current.arrayIndex][current.elementIndex + 1];
                minHeap.offer(new ArrayElement(nextValue, current.arrayIndex, current.elementIndex + 1));
            }
        }

        return result;
    }

    /**
     * 方法2：分治法合併
     * 時間複雜度：O(N log K)
     * 空間複雜度：O(N)
     */
    public static int[] mergeKSortedArraysDivideConquer(int[][] arrays) {
        if (arrays == null || arrays.length == 0) {
            return new int[0];
        }

        return mergeArraysRecursive(arrays, 0, arrays.length - 1);
    }

    private static int[] mergeArraysRecursive(int[][] arrays, int start, int end) {
        if (start == end) {
            return arrays[start] != null ? arrays[start].clone() : new int[0];
        }

        if (start + 1 == end) {
            return mergeTwoSortedArrays(arrays[start], arrays[end]);
        }

        int mid = start + (end - start) / 2;
        int[] left = mergeArraysRecursive(arrays, start, mid);
        int[] right = mergeArraysRecursive(arrays, mid + 1, end);

        return mergeTwoSortedArrays(left, right);
    }

    /**
     * 合併兩個有序陣列
     */
    private static int[] mergeTwoSortedArrays(int[] arr1, int[] arr2) {
        if (arr1 == null) arr1 = new int[0];
        if (arr2 == null) arr2 = new int[0];

        int[] result = new int[arr1.length + arr2.length];
        int i = 0, j = 0, k = 0;

        while (i < arr1.length && j < arr2.length) {
            if (arr1[i] <= arr2[j]) {
                result[k++] = arr1[i++];
            } else {
                result[k++] = arr2[j++];
            }
        }

        while (i < arr1.length) {
            result[k++] = arr1[i++];
        }

        while (j < arr2.length) {
            result[k++] = arr2[j++];
        }

        return result;
    }

    /**
     * 方法3：使用優先級佇列的詳細版本（顯示合併過程）
     */
    public static int[] mergeKSortedArraysDetailed(int[][] arrays, boolean showProcess) {
        if (arrays == null || arrays.length == 0) {
            return new int[0];
        }

        int totalElements = 0;
        for (int[] array : arrays) {
            if (array != null) {
                totalElements += array.length;
            }
        }

        if (totalElements == 0) {
            return new int[0];
        }

        PriorityQueue<ArrayElement> minHeap = new PriorityQueue<>(
            (a, b) -> {
                if (a.value != b.value) {
                    return Integer.compare(a.value, b.value);
                }
                // 如果值相同，按陣列索引排序以保持穩定性
                return Integer.compare(a.arrayIndex, b.arrayIndex);
            }
        );

        // 初始化 heap
        for (int i = 0; i < arrays.length; i++) {
            if (arrays[i] != null && arrays[i].length > 0) {
                minHeap.offer(new ArrayElement(arrays[i][0], i, 0));
                if (showProcess) {
                    System.out.printf("初始化：將陣列 %d 的第一個元素 %d 加入 heap\n", i, arrays[i][0]);
                }
            }
        }

        int[] result = new int[totalElements];
        int resultIndex = 0;

        if (showProcess) {
            System.out.println("\n開始合併過程：");
        }

        while (!minHeap.isEmpty()) {
            ArrayElement current = minHeap.poll();
            result[resultIndex++] = current.value;

            if (showProcess) {
                System.out.printf("步驟 %d：取出 %s，加入結果陣列\n", resultIndex, current);
            }

            // 從同一陣列中加入下一個元素
            if (current.elementIndex + 1 < arrays[current.arrayIndex].length) {
                int nextValue = arrays[current.arrayIndex][current.elementIndex + 1];
                ArrayElement nextElement = new ArrayElement(nextValue, current.arrayIndex, current.elementIndex + 1);
                minHeap.offer(nextElement);

                if (showProcess) {
                    System.out.printf("        從陣列 %d 加入下一個元素 %d\n", current.arrayIndex, nextValue);
                }
            }

            if (showProcess && !minHeap.isEmpty()) {
                System.out.printf("        當前 heap 頂部：%d\n", minHeap.peek().value);
            }
        }

        return result;
    }

    /**
     * 驗證陣列是否有序
     */
    public static boolean isSorted(int[] array) {
        for (int i = 1; i < array.length; i++) {
            if (array[i] < array[i - 1]) {
                return false;
            }
        }
        return true;
    }

    /**
     * 生成測試陣列
     */
    public static int[][] generateTestArrays(int k, int maxSize, int maxValue) {
        Random random = new Random(42);
        int[][] arrays = new int[k][];

        for (int i = 0; i < k; i++) {
            int size = random.nextInt(maxSize) + 1;
            arrays[i] = new int[size];
            
            // 生成有序陣列
            for (int j = 0; j < size; j++) {
                arrays[i][j] = random.nextInt(maxValue);
            }
            Arrays.sort(arrays[i]);
        }

        return arrays;
    }

    /**
     * 效能測試
     */
    public static void performanceTest(int k, int arraySize, int maxValue) {
        System.out.printf("效能測試：%d 個陣列，每個陣列最大 %d 個元素\n", k, arraySize);

        int[][] testArrays = generateTestArrays(k, arraySize, maxValue);

        // 測試 Heap 方法
        long startTime = System.nanoTime();
        int[] result1 = mergeKSortedArrays(testArrays);
        long endTime = System.nanoTime();
        double heapTime = (endTime - startTime) / 1_000_000.0;

        // 測試分治方法
        startTime = System.nanoTime();
        int[] result2 = mergeKSortedArraysDivideConquer(testArrays);
        endTime = System.nanoTime();
        double divideConquerTime = (endTime - startTime) / 1_000_000.0;

        System.out.printf("Heap 方法：%.2f ms，結果長度：%d，是否有序：%s\n", 
            heapTime, result1.length, isSorted(result1));
        System.out.printf("分治方法：%.2f ms，結果長度：%d，是否有序：%s\n", 
            divideConquerTime, result2.length, isSorted(result2));
        System.out.printf("結果是否相同：%s\n", Arrays.equals(result1, result2));
        System.out.println();
    }

    public static void main(String[] args) {
        System.out.println("=== 合併多個有序陣列 ===");

        // 測試案例1：基本測試
        System.out.println("1. 基本測試案例：");
        int[][] test1 = {
            {1, 4, 5},
            {1, 3, 4},
            {2, 6}
        };

        System.out.println("輸入陣列：");
        for (int i = 0; i < test1.length; i++) {
            System.out.printf("陣列 %d: %s\n", i, Arrays.toString(test1[i]));
        }

        int[] result1 = mergeKSortedArrays(test1);
        System.out.printf("合併結果：%s\n", Arrays.toString(result1));
        System.out.printf("期望結果：[1, 1, 2, 3, 4, 4, 5, 6]\n");
        System.out.printf("結果正確：%s\n", Arrays.equals(result1, new int[]{1, 1, 2, 3, 4, 4, 5, 6}));
        System.out.printf("結果有序：%s\n\n", isSorted(result1));

        // 測試案例2：詳細過程展示
        System.out.println("2. 詳細合併過程展示：");
        int[][] test2 = {
            {1, 4, 5},
            {1, 3, 4},
            {2, 6}
        };

        mergeKSortedArraysDetailed(test2, true);

        // 測試案例3：不同長度的陣列
        System.out.println("\n" + "=".repeat(50));
        System.out.println("3. 不同長度陣列測試：");
        int[][] test3 = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
        };

        System.out.println("輸入陣列：");
        for (int i = 0; i < test3.length; i++) {
            System.out.printf("陣列 %d: %s\n", i, Arrays.toString(test3[i]));
        }

        int[] result3 = mergeKSortedArrays(test3);
        System.out.printf("合併結果：%s\n", Arrays.toString(result3));
        System.out.printf("期望結果：[1, 2, 3, 4, 5, 6, 7, 8, 9]\n");
        System.out.printf("結果正確：%s\n\n", Arrays.equals(result3, new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9}));

        // 測試案例4：單元素陣列
        System.out.println("4. 單元素陣列測試：");
        int[][] test4 = {
            {1},
            {0}
        };

        int[] result4 = mergeKSortedArrays(test4);
        System.out.printf("輸入：%s 和 %s\n", Arrays.toString(test4[0]), Arrays.toString(test4[1]));
        System.out.printf("結果：%s\n", Arrays.toString(result4));
        System.out.printf("期望：[0, 1]\n");
        System.out.printf("結果正確：%s\n\n", Arrays.equals(result4, new int[]{0, 1}));

        // 測試案例5：邊界情況
        System.out.println("5. 邊界情況測試：");

        // 空陣列
        int[][] emptyArrays = {};
        int[] emptyResult = mergeKSortedArrays(emptyArrays);
        System.out.printf("空陣列輸入，結果：%s\n", Arrays.toString(emptyResult));

        // 包含空陣列
        int[][] withEmpty = {{1, 3, 5}, {}, {2, 4, 6}};
        int[] withEmptyResult = mergeKSortedArrays(withEmpty);
        System.out.printf("包含空陣列，結果：%s\n", Arrays.toString(withEmptyResult));

        // 只有一個陣列
        int[][] singleArray = {{1, 2, 3, 4, 5}};
        int[] singleResult = mergeKSortedArrays(singleArray);
        System.out.printf("單一陣列，結果：%s\n", Arrays.toString(singleResult));

        // 測試案例6：重複元素
        System.out.println("\n6. 重複元素測試：");
        int[][] duplicates = {
            {1, 1, 2, 3},
            {1, 2, 2, 4},
            {2, 3, 3, 3}
        };

        int[] duplicateResult = mergeKSortedArrays(duplicates);
        System.out.printf("有重複元素的陣列合併結果：%s\n", Arrays.toString(duplicateResult));
        System.out.printf("結果有序：%s\n", isSorted(duplicateResult));

        // 測試案例7：方法比較
        System.out.println("\n" + "=".repeat(50));
        System.out.println("7. 不同方法比較：");

        int[][] compareTest = {
            {1, 4, 7, 10},
            {2, 5, 8, 11},
            {3, 6, 9, 12}
        };

        // Heap 方法
        long startTime = System.nanoTime();
        int[] heapResult = mergeKSortedArrays(compareTest);
        long heapTime = System.nanoTime() - startTime;

        // 分治方法
        startTime = System.nanoTime();
        int[] divideResult = mergeKSortedArraysDivideConquer(compareTest);
        long divideTime = System.nanoTime() - startTime;

        System.out.printf("Heap 方法結果：%s (耗時：%.2f μs)\n", 
            Arrays.toString(heapResult), heapTime / 1000.0);
        System.out.printf("分治方法結果：%s (耗時：%.2f μs)\n", 
            Arrays.toString(divideResult), divideTime / 1000.0);
        System.out.printf("結果是否相同：%s\n", Arrays.equals(heapResult, divideResult));

        // 測試案例8：效能測試
        System.out.println("\n8. 效能測試：");
        
        performanceTest(5, 100, 1000);      // 小規模
        performanceTest(10, 500, 5000);     // 中規模
        performanceTest(100, 100, 10000);   // 大規模 K
        performanceTest(20, 1000, 50000);   // 大規模陣列

        // 測試案例9：極端情況
        System.out.println("9. 極端情況測試：");

        // 所有陣列都有相同元素
        int[][] sameElements = new int[3][];
        for (int i = 0; i < 3; i++) {
            sameElements[i] = new int[5];
            Arrays.fill(sameElements[i], 42);
        }
        int[] sameResult = mergeKSortedArrays(sameElements);
        System.out.printf("所有元素都是 42：%s\n", Arrays.toString(sameResult));

        // 非常不平衡的陣列大小
        int[][] unbalanced = {
            {1},
            {2, 3, 4, 5, 6, 7, 8, 9, 10},
            {11}
        };
        int[] unbalancedResult = mergeKSortedArrays(unbalanced);
        System.out.printf("不平衡大小陣列：%s\n", Arrays.toString(unbalancedResult));

        // 測試案例10：複雜度分析
        System.out.println("\n10. 複雜度分析：");
        System.out.println("Heap 方法：");
        System.out.println("  時間複雜度：O(N log K)，其中 N 是總元素數，K 是陣列數");
        System.out.println("  空間複雜度：O(K) 用於 heap");
        System.out.println("  優點：空間效率高，適合 K 較小的情況");
        System.out.println("  缺點：需要維護 heap 結構");

        System.out.println("\n分治方法：");
        System.out.println("  時間複雜度：O(N log K)");
        System.out.println("  空間複雜度：O(N) 用於遞迴和臨時陣列");
        System.out.println("  優點：實作簡單，容易理解");
        System.out.println("  缺點：空間使用較多");

        System.out.println("\n=== 合併多個有序陣列測試完成 ===");
    }
}
