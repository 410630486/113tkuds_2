
import java.util.*;

public class NumberArrayProcessor {

    /**
     * 顯示陣列內容
     */
    public static void printArray(int[] array, String title) {
        System.out.println(title + ": " + Arrays.toString(array));
    }

    /**
     * 移除陣列中的重複元素
     */
    public static int[] removeDuplicates(int[] array) {
        if (array == null || array.length == 0) {
            return new int[0];
        }

        // 使用 LinkedHashSet 保持插入順序並去除重複
        Set<Integer> uniqueSet = new LinkedHashSet<>();
        for (int num : array) {
            uniqueSet.add(num);
        }

        // 轉換回陣列
        return uniqueSet.stream().mapToInt(Integer::intValue).toArray();
    }

    /**
     * 合併兩個已排序的陣列
     */
    public static int[] mergeSortedArrays(int[] array1, int[] array2) {
        int[] merged = new int[array1.length + array2.length];
        int i = 0, j = 0, k = 0;

        // 比較並合併
        while (i < array1.length && j < array2.length) {
            if (array1[i] <= array2[j]) {
                merged[k++] = array1[i++];
            } else {
                merged[k++] = array2[j++];
            }
        }

        // 加入剩餘元素
        while (i < array1.length) {
            merged[k++] = array1[i++];
        }

        while (j < array2.length) {
            merged[k++] = array2[j++];
        }

        return merged;
    }

    /**
     * 合併任意數量的陣列（無需排序）
     */
    public static int[] mergeArrays(int[]... arrays) {
        int totalLength = 0;
        for (int[] array : arrays) {
            totalLength += array.length;
        }

        int[] result = new int[totalLength];
        int index = 0;

        for (int[] array : arrays) {
            for (int element : array) {
                result[index++] = element;
            }
        }

        return result;
    }

    /**
     * 計算每個數字出現的次數
     */
    public static Map<Integer, Integer> calculateFrequency(int[] array) {
        Map<Integer, Integer> frequency = new HashMap<>();

        for (int num : array) {
            frequency.put(num, frequency.getOrDefault(num, 0) + 1);
        }

        return frequency;
    }

    /**
     * 顯示頻率統計
     */
    public static void displayFrequencyAnalysis(int[] array) {
        Map<Integer, Integer> frequency = calculateFrequency(array);

        System.out.println("頻率分析結果：");

        // 按數字大小排序顯示
        frequency.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry
                        -> System.out.printf("數字 %d 出現 %d 次\n",
                        entry.getKey(), entry.getValue()));

        // 找出出現最多和最少的數字
        int maxFreq = Collections.max(frequency.values());
        int minFreq = Collections.min(frequency.values());

        System.out.println("\n出現最多的數字：");
        frequency.entrySet().stream()
                .filter(entry -> entry.getValue() == maxFreq)
                .forEach(entry
                        -> System.out.printf("數字 %d (出現 %d 次)\n",
                        entry.getKey(), entry.getValue()));

        System.out.println("\n出現最少的數字：");
        frequency.entrySet().stream()
                .filter(entry -> entry.getValue() == minFreq)
                .forEach(entry
                        -> System.out.printf("數字 %d (出現 %d 次)\n",
                        entry.getKey(), entry.getValue()));
    }

    /**
     * 根據條件分割陣列 分為大於等於閾值和小於閾值的兩個陣列
     */
    public static int[][] splitArrayByThreshold(int[] array, int threshold) {
        List<Integer> greaterOrEqual = new ArrayList<>();
        List<Integer> less = new ArrayList<>();

        for (int num : array) {
            if (num >= threshold) {
                greaterOrEqual.add(num);
            } else {
                less.add(num);
            }
        }

        int[][] result = new int[2][];
        result[0] = greaterOrEqual.stream().mapToInt(Integer::intValue).toArray();
        result[1] = less.stream().mapToInt(Integer::intValue).toArray();

        return result;
    }

    /**
     * 分割陣列為奇數和偶數
     */
    public static int[][] splitByParity(int[] array) {
        List<Integer> even = new ArrayList<>();
        List<Integer> odd = new ArrayList<>();

        for (int num : array) {
            if (num % 2 == 0) {
                even.add(num);
            } else {
                odd.add(num);
            }
        }

        int[][] result = new int[2][];
        result[0] = even.stream().mapToInt(Integer::intValue).toArray();
        result[1] = odd.stream().mapToInt(Integer::intValue).toArray();

        return result;
    }

    /**
     * 查找子陣列
     */
    public static int findSubarray(int[] array, int[] subarray) {
        if (subarray.length > array.length) {
            return -1;
        }

        for (int i = 0; i <= array.length - subarray.length; i++) {
            boolean found = true;
            for (int j = 0; j < subarray.length; j++) {
                if (array[i + j] != subarray[j]) {
                    found = false;
                    break;
                }
            }
            if (found) {
                return i;
            }
        }

        return -1;
    }

    /**
     * 陣列統計資訊
     */
    public static void displayArrayStatistics(int[] array) {
        if (array.length == 0) {
            System.out.println("陣列為空");
            return;
        }

        int sum = Arrays.stream(array).sum();
        double average = (double) sum / array.length;
        int max = Arrays.stream(array).max().orElse(0);
        int min = Arrays.stream(array).min().orElse(0);

        System.out.println("=== 陣列統計資訊 ===");
        System.out.printf("元素個數：%d\n", array.length);
        System.out.printf("總和：%d\n", sum);
        System.out.printf("平均值：%.2f\n", average);
        System.out.printf("最大值：%d\n", max);
        System.out.printf("最小值：%d\n", min);
        System.out.printf("範圍：%d\n", max - min);
    }

    public static void main(String[] args) {
        System.out.println("=== 數字陣列處理器示範 ===");

        // 測試資料
        int[] array1 = {1, 2, 3, 2, 4, 1, 5, 3, 6, 4, 7};
        int[] array2 = {8, 9, 10, 8, 11, 12};
        int[] sortedArray1 = {1, 3, 5, 7, 9};
        int[] sortedArray2 = {2, 4, 6, 8, 10, 12};

        printArray(array1, "原始陣列1");
        printArray(array2, "原始陣列2");

        // 移除重複元素
        int[] unique = removeDuplicates(array1);
        printArray(unique, "移除重複後");

        // 合併陣列（無序）
        int[] merged = mergeArrays(array1, array2);
        printArray(merged, "合併陣列");

        // 合併已排序陣列
        printArray(sortedArray1, "已排序陣列1");
        printArray(sortedArray2, "已排序陣列2");
        int[] sortedMerged = mergeSortedArrays(sortedArray1, sortedArray2);
        printArray(sortedMerged, "合併已排序陣列");

        // 頻率分析
        System.out.println("\n=== 頻率分析 ===");
        displayFrequencyAnalysis(array1);

        // 根據閾值分割
        System.out.println("\n=== 根據閾值分割 (閾值=4) ===");
        int[][] splitByThreshold = splitArrayByThreshold(array1, 4);
        printArray(splitByThreshold[0], "大於等於4");
        printArray(splitByThreshold[1], "小於4");

        // 根據奇偶性分割
        System.out.println("\n=== 根據奇偶性分割 ===");
        int[][] splitByParity = splitByParity(array1);
        printArray(splitByParity[0], "偶數");
        printArray(splitByParity[1], "奇數");

        // 子陣列搜尋
        System.out.println("\n=== 子陣列搜尋 ===");
        int[] subarray = {2, 4, 1};
        printArray(subarray, "要搜尋的子陣列");
        int position = findSubarray(array1, subarray);
        if (position != -1) {
            System.out.printf("子陣列在位置 %d 找到\n", position);
        } else {
            System.out.println("子陣列未找到");
        }

        // 統計資訊
        System.out.println();
        displayArrayStatistics(array1);
    }
}
