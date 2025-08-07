
public class SelectionSortImplementation {

    /**
     * 選擇排序實作 時間複雜度：O(n²) 空間複雜度：O(1)
     */
    public static void selectionSort(int[] array) {
        int n = array.length;
        int comparisons = 0;
        int swaps = 0;

        System.out.println("開始選擇排序...");
        printArray(array, "原始陣列");

        for (int i = 0; i < n - 1; i++) {
            // 找出未排序部分的最小值索引
            int minIndex = i;

            for (int j = i + 1; j < n; j++) {
                comparisons++;
                if (array[j] < array[minIndex]) {
                    minIndex = j;
                }
            }

            // 如果最小值不在當前位置，則交換
            if (minIndex != i) {
                swap(array, i, minIndex);
                swaps++;

                System.out.printf("第 %d 輪：將最小值 %d 移到位置 %d\n",
                        i + 1, array[i], i);
                printArray(array, "當前狀態");
            }
        }

        System.out.printf("\n排序完成！總共進行了 %d 次比較，%d 次交換\n",
                comparisons, swaps);
    }

    /**
     * 選擇排序（遞減順序）
     */
    public static void selectionSortDescending(int[] array) {
        int n = array.length;

        System.out.println("開始選擇排序（遞減）...");
        printArray(array, "原始陣列");

        for (int i = 0; i < n - 1; i++) {
            // 找出未排序部分的最大值索引
            int maxIndex = i;

            for (int j = i + 1; j < n; j++) {
                if (array[j] > array[maxIndex]) {
                    maxIndex = j;
                }
            }

            // 如果最大值不在當前位置，則交換
            if (maxIndex != i) {
                swap(array, i, maxIndex);

                System.out.printf("第 %d 輪：將最大值 %d 移到位置 %d\n",
                        i + 1, array[i], i);
                printArray(array, "當前狀態");
            }
        }

        System.out.println("遞減排序完成！");
    }

    /**
     * 改良版選擇排序 - 雙向選擇排序 每次同時找出最大值和最小值
     */
    public static void bidirectionalSelectionSort(int[] array) {
        int n = array.length;
        int comparisons = 0;
        int swaps = 0;

        System.out.println("開始雙向選擇排序...");
        printArray(array, "原始陣列");

        for (int i = 0; i < n / 2; i++) {
            int minIndex = i;
            int maxIndex = i;

            // 同時找出最小值和最大值
            for (int j = i + 1; j < n - i; j++) {
                comparisons += 2;
                if (array[j] < array[minIndex]) {
                    minIndex = j;
                }
                if (array[j] > array[maxIndex]) {
                    maxIndex = j;
                }
            }

            // 將最小值放到前面
            if (minIndex != i) {
                swap(array, i, minIndex);
                swaps++;

                // 如果最大值原本在位置 i，更新其索引
                if (maxIndex == i) {
                    maxIndex = minIndex;
                }
            }

            // 將最大值放到後面
            if (maxIndex != n - 1 - i) {
                swap(array, maxIndex, n - 1 - i);
                swaps++;
            }

            if (minIndex != i || maxIndex != n - 1 - i) {
                System.out.printf("第 %d 輪：最小值 %d 到位置 %d，最大值 %d 到位置 %d\n",
                        i + 1, array[i], i, array[n - 1 - i], n - 1 - i);
                printArray(array, "當前狀態");
            }
        }

        System.out.printf("\n雙向排序完成！總共進行了 %d 次比較，%d 次交換\n",
                comparisons, swaps);
    }

    /**
     * 交換陣列中兩個元素的位置
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
        System.out.printf("%-12s: ", title);
        for (int i = 0; i < array.length; i++) {
            System.out.printf("%3d", array[i]);
            if (i < array.length - 1) {
                System.out.print(" ");
            }
        }
        System.out.println();
    }

    /**
     * 檢查陣列是否已排序
     */
    public static boolean isSorted(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            if (array[i] > array[i + 1]) {
                return false;
            }
        }
        return true;
    }

    /**
     * 效能測試
     */
    public static void performanceTest(int[] array) {
        System.out.println("\n=== 效能測試 ===");

        // 測試標準選擇排序
        int[] array1 = array.clone();
        long startTime = System.nanoTime();
        selectionSort(array1);
        long endTime = System.nanoTime();
        double duration1 = (endTime - startTime) / 1_000_000.0;

        System.out.printf("標準選擇排序耗時：%.3f 毫秒\n", duration1);
        System.out.println("排序結果正確：" + isSorted(array1));

        // 測試雙向選擇排序
        int[] array2 = array.clone();
        startTime = System.nanoTime();
        bidirectionalSelectionSort(array2);
        endTime = System.nanoTime();
        double duration2 = (endTime - startTime) / 1_000_000.0;

        System.out.printf("雙向選擇排序耗時：%.3f 毫秒\n", duration2);
        System.out.println("排序結果正確：" + isSorted(array2));

        if (duration2 < duration1) {
            System.out.printf("雙向排序快了 %.1f%%\n",
                    ((duration1 - duration2) / duration1) * 100);
        }
    }

    /**
     * 分析排序過程的複雜度
     */
    public static void analyzeComplexity(int n) {
        System.out.println("\n=== 選擇排序複雜度分析 ===");
        System.out.printf("陣列大小：%d\n", n);

        // 標準選擇排序
        int standardComparisons = n * (n - 1) / 2;
        int maxSwaps = n - 1;

        System.out.println("\n標準選擇排序：");
        System.out.printf("最大比較次數：%d\n", standardComparisons);
        System.out.printf("最大交換次數：%d\n", maxSwaps);

        // 雙向選擇排序
        int bidirectionalComparisons = n * (n - 1) / 4;
        int bidirectionalMaxSwaps = n - 1;

        System.out.println("\n雙向選擇排序：");
        System.out.printf("最大比較次數：%d\n", bidirectionalComparisons);
        System.out.printf("最大交換次數：%d\n", bidirectionalMaxSwaps);

        System.out.printf("\n比較次數減少：%.1f%%\n",
                ((double) (standardComparisons - bidirectionalComparisons)
                / standardComparisons) * 100);
    }

    /**
     * 生成測試陣列
     */
    public static int[] generateTestArray(int size, String type) {
        int[] array = new int[size];

        switch (type.toLowerCase()) {
            case "random":
                for (int i = 0; i < size; i++) {
                    array[i] = (int) (Math.random() * 100);
                }
                break;

            case "reverse":
                for (int i = 0; i < size; i++) {
                    array[i] = size - i;
                }
                break;

            case "sorted":
                for (int i = 0; i < size; i++) {
                    array[i] = i + 1;
                }
                break;

            case "duplicate":
                for (int i = 0; i < size; i++) {
                    array[i] = (i % 5) + 1; // 1-5的重複數字
                }
                break;

            default:
                // 預設隨機陣列
                for (int i = 0; i < size; i++) {
                    array[i] = (int) (Math.random() * 100);
                }
        }

        return array;
    }

    public static void main(String[] args) {
        System.out.println("=== 選擇排序實作與分析 ===");

        // 測試小陣列
        int[] testArray = {64, 34, 25, 12, 22, 11, 90, 5};

        System.out.println("1. 標準選擇排序測試：");
        int[] array1 = testArray.clone();
        selectionSort(array1);

        System.out.println("\n" + "=".repeat(50));

        System.out.println("2. 遞減排序測試：");
        int[] array2 = testArray.clone();
        selectionSortDescending(array2);

        System.out.println("\n" + "=".repeat(50));

        System.out.println("3. 雙向選擇排序測試：");
        int[] array3 = testArray.clone();
        bidirectionalSelectionSort(array3);

        // 複雜度分析
        analyzeComplexity(testArray.length);

        // 效能測試（較大的陣列）
        System.out.println("\n" + "=".repeat(50));
        int[] largeArray = generateTestArray(20, "random");
        printArray(largeArray, "大陣列測試");
        performanceTest(largeArray);

        // 不同類型陣列的測試
        System.out.println("\n" + "=".repeat(50));
        System.out.println("4. 不同類型陣列測試：");

        String[] types = {"random", "reverse", "sorted", "duplicate"};
        String[] typeNames = {"隨機陣列", "反向陣列", "已排序陣列", "重複元素陣列"};

        for (int i = 0; i < types.length; i++) {
            System.out.println("\n" + typeNames[i] + "：");
            int[] testArr = generateTestArray(10, types[i]);
            printArray(testArr, "原始");
            selectionSort(testArr.clone());
        }
    }
}
