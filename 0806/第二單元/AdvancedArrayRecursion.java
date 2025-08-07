
import java.util.*;

public class AdvancedArrayRecursion {

    /**
     * 遞迴實作快速排序演算法
     */
    public static void quickSort(int[] array, int low, int high) {
        if (low < high) {
            // 分割陣列，獲得分割點
            int pivotIndex = partition(array, low, high);

            // 遞迴排序分割點左邊的子陣列
            quickSort(array, low, pivotIndex - 1);

            // 遞迴排序分割點右邊的子陣列
            quickSort(array, pivotIndex + 1, high);
        }
    }

    /**
     * 快速排序的分割函式
     */
    private static int partition(int[] array, int low, int high) {
        // 選擇最後一個元素作為基準
        int pivot = array[high];
        int i = low - 1; // 較小元素的索引

        for (int j = low; j < high; j++) {
            // 如果當前元素小於或等於基準
            if (array[j] <= pivot) {
                i++;
                swap(array, i, j);
            }
        }

        // 將基準放到正確位置
        swap(array, i + 1, high);
        return i + 1;
    }

    /**
     * 快速排序的包裝函式
     */
    public static void quickSort(int[] array) {
        if (array.length > 1) {
            quickSort(array, 0, array.length - 1);
        }
    }

    /**
     * 遞迴合併兩個已排序的陣列
     */
    public static int[] mergeSortedArrays(int[] array1, int[] array2) {
        int[] result = new int[array1.length + array2.length];
        mergeSortedArraysHelper(array1, array2, result, 0, 0, 0);
        return result;
    }

    private static void mergeSortedArraysHelper(int[] array1, int[] array2, int[] result,
            int i, int j, int k) {
        // 基底情況：其中一個陣列已處理完
        if (i >= array1.length) {
            // 複製array2剩餘元素
            if (j < array2.length) {
                System.arraycopy(array2, j, result, k, array2.length - j);
            }
            return;
        }

        if (j >= array2.length) {
            // 複製array1剩餘元素
            System.arraycopy(array1, i, result, k, array1.length - i);
            return;
        }

        // 比較兩個陣列的當前元素
        if (array1[i] <= array2[j]) {
            result[k] = array1[i];
            mergeSortedArraysHelper(array1, array2, result, i + 1, j, k + 1);
        } else {
            result[k] = array2[j];
            mergeSortedArraysHelper(array1, array2, result, i, j + 1, k + 1);
        }
    }

    /**
     * 遞迴尋找陣列中的第 k 小元素（使用快速選擇演算法）
     */
    public static int findKthSmallest(int[] array, int k) {
        if (k < 1 || k > array.length) {
            throw new IllegalArgumentException("k 必須在 1 到 " + array.length + " 之間");
        }

        // 複製陣列以避免修改原陣列
        int[] copyArray = array.clone();
        return quickSelect(copyArray, 0, copyArray.length - 1, k - 1);
    }

    private static int quickSelect(int[] array, int low, int high, int k) {
        if (low == high) {
            return array[low];
        }

        // 分割陣列
        int pivotIndex = partition(array, low, high);

        if (k == pivotIndex) {
            return array[k];
        } else if (k < pivotIndex) {
            // 在左邊子陣列中尋找
            return quickSelect(array, low, pivotIndex - 1, k);
        } else {
            // 在右邊子陣列中尋找
            return quickSelect(array, pivotIndex + 1, high, k);
        }
    }

    /**
     * 遞迴檢查陣列是否存在子序列總和等於目標值
     */
    public static boolean hasSubsetSum(int[] array, int targetSum) {
        return hasSubsetSumHelper(array, array.length, targetSum);
    }

    private static boolean hasSubsetSumHelper(int[] array, int n, int sum) {
        // 基底情況
        if (sum == 0) {
            return true; // 找到總和為目標值的子集
        }

        if (n == 0) {
            return false; // 沒有更多元素可選
        }

        // 如果最後一個元素大於目標和，忽略它
        if (array[n - 1] > sum) {
            return hasSubsetSumHelper(array, n - 1, sum);
        }

        // 返回兩種情況的結果：包含或不包含最後一個元素
        return hasSubsetSumHelper(array, n - 1, sum)
                || hasSubsetSumHelper(array, n - 1, sum - array[n - 1]);
    }

    /**
     * 找到所有總和等於目標值的子序列
     */
    public static List<List<Integer>> findAllSubsetsWithSum(int[] array, int targetSum) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> currentSubset = new ArrayList<>();
        findAllSubsetsHelper(array, 0, targetSum, currentSubset, result);
        return result;
    }

    private static void findAllSubsetsHelper(int[] array, int index, int remainingSum,
            List<Integer> currentSubset, List<List<Integer>> result) {
        // 基底情況：找到目標和
        if (remainingSum == 0) {
            result.add(new ArrayList<>(currentSubset));
            return;
        }

        // 基底情況：索引超出範圍或剩餘和為負數
        if (index >= array.length || remainingSum < 0) {
            return;
        }

        // 包含當前元素
        currentSubset.add(array[index]);
        findAllSubsetsHelper(array, index + 1, remainingSum - array[index], currentSubset, result);
        currentSubset.remove(currentSubset.size() - 1);

        // 不包含當前元素
        findAllSubsetsHelper(array, index + 1, remainingSum, currentSubset, result);
    }

    /**
     * 遞迴實作合併排序
     */
    public static void mergeSort(int[] array) {
        if (array.length > 1) {
            mergeSortHelper(array, 0, array.length - 1);
        }
    }

    private static void mergeSortHelper(int[] array, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;

            // 遞迴排序左半部分
            mergeSortHelper(array, left, mid);

            // 遞迴排序右半部分
            mergeSortHelper(array, mid + 1, right);

            // 合併已排序的兩部分
            merge(array, left, mid, right);
        }
    }

    private static void merge(int[] array, int left, int mid, int right) {
        // 計算子陣列的大小
        int n1 = mid - left + 1;
        int n2 = right - mid;

        // 建立暫時陣列
        int[] leftArray = new int[n1];
        int[] rightArray = new int[n2];

        // 複製資料到暫時陣列
        System.arraycopy(array, left, leftArray, 0, n1);
        System.arraycopy(array, mid + 1, rightArray, 0, n2);

        // 合併暫時陣列回到原陣列
        int i = 0, j = 0, k = left;

        while (i < n1 && j < n2) {
            if (leftArray[i] <= rightArray[j]) {
                array[k] = leftArray[i];
                i++;
            } else {
                array[k] = rightArray[j];
                j++;
            }
            k++;
        }

        // 複製剩餘元素
        while (i < n1) {
            array[k] = leftArray[i];
            i++;
            k++;
        }

        while (j < n2) {
            array[k] = rightArray[j];
            j++;
            k++;
        }
    }

    /**
     * 遞迴尋找陣列的最大值
     */
    public static int findMax(int[] array, int n) {
        // 基底情況
        if (n == 1) {
            return array[0];
        }

        // 遞迴尋找前 n-1 個元素的最大值
        int maxOfRest = findMax(array, n - 1);

        // 返回最後一個元素和前面最大值的較大者
        return Math.max(array[n - 1], maxOfRest);
    }

    /**
     * 遞迴計算陣列元素總和
     */
    public static int arraySum(int[] array, int n) {
        if (n <= 0) {
            return 0;
        }

        return array[n - 1] + arraySum(array, n - 1);
    }

    /**
     * 輔助方法：交換陣列中的兩個元素
     */
    private static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    /**
     * 顯示陣列內容
     */
    public static void printArray(int[] array, String title) {
        System.out.println(title + ": " + Arrays.toString(array));
    }

    public static void main(String[] args) {
        System.out.println("=== 陣列遞迴操作進階示範 ===");

        // 測試資料
        int[] testArray = {64, 34, 25, 12, 22, 11, 90, 88, 76, 50, 42};
        int[] sortedArray1 = {1, 3, 5, 7, 9};
        int[] sortedArray2 = {2, 4, 6, 8, 10, 12};

        // 1. 快速排序測試
        System.out.println("1. 快速排序測試：");
        int[] quickSortArray = testArray.clone();
        printArray(quickSortArray, "排序前");
        quickSort(quickSortArray);
        printArray(quickSortArray, "快速排序後");

        // 2. 合併排序測試
        System.out.println("\n2. 合併排序測試：");
        int[] mergeSortArray = testArray.clone();
        printArray(mergeSortArray, "排序前");
        mergeSort(mergeSortArray);
        printArray(mergeSortArray, "合併排序後");

        // 3. 合併已排序陣列測試
        System.out.println("\n3. 合併已排序陣列測試：");
        printArray(sortedArray1, "陣列1");
        printArray(sortedArray2, "陣列2");
        int[] mergedArray = mergeSortedArrays(sortedArray1, sortedArray2);
        printArray(mergedArray, "合併結果");

        // 4. 第k小元素測試
        System.out.println("\n4. 第k小元素測試：");
        printArray(testArray, "原陣列");
        for (int k = 1; k <= 5; k++) {
            int kthSmallest = findKthSmallest(testArray, k);
            System.out.printf("第 %d 小的元素：%d\n", k, kthSmallest);
        }

        // 5. 子序列總和測試
        System.out.println("\n5. 子序列總和測試：");
        int[] subsetArray = {3, 34, 4, 12, 5, 2};
        int targetSum = 9;
        printArray(subsetArray, "測試陣列");
        System.out.printf("是否存在總和為 %d 的子序列：%s\n",
                targetSum, hasSubsetSum(subsetArray, targetSum));

        // 找出所有總和等於目標值的子序列
        List<List<Integer>> allSubsets = findAllSubsetsWithSum(subsetArray, targetSum);
        System.out.printf("所有總和為 %d 的子序列：\n", targetSum);
        for (List<Integer> subset : allSubsets) {
            System.out.println("  " + subset);
        }

        // 6. 其他遞迴操作測試
        System.out.println("\n6. 其他遞迴操作測試：");
        printArray(testArray, "測試陣列");
        System.out.printf("最大值：%d\n", findMax(testArray, testArray.length));
        System.out.printf("元素總和：%d\n", arraySum(testArray, testArray.length));

        // 7. 效能比較
        System.out.println("\n7. 排序演算法效能比較：");
        int[] largeArray = generateRandomArray(1000);

        // 快速排序效能測試
        int[] quickArray = largeArray.clone();
        long startTime = System.nanoTime();
        quickSort(quickArray);
        long endTime = System.nanoTime();
        double quickSortTime = (endTime - startTime) / 1_000_000.0;

        // 合併排序效能測試
        int[] mergeArray = largeArray.clone();
        startTime = System.nanoTime();
        mergeSort(mergeArray);
        endTime = System.nanoTime();
        double mergeSortTime = (endTime - startTime) / 1_000_000.0;

        System.out.printf("快速排序耗時：%.3f 毫秒\n", quickSortTime);
        System.out.printf("合併排序耗時：%.3f 毫秒\n", mergeSortTime);
    }

    /**
     * 產生隨機陣列
     */
    private static int[] generateRandomArray(int size) {
        int[] array = new int[size];
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(1000);
        }
        return array;
    }
}
