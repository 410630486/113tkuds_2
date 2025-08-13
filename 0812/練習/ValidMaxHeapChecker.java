import java.util.*;

/**
 * 練習 4.2：檢測陣列是否為有效 Max Heap
 * 難度：★☆☆☆☆
 * 
 * 功能：
 * - 判斷陣列是否符合 Max Heap 性質
 * - 找出第一個違反規則的節點位置
 * - 提供詳細的驗證報告
 */
public class ValidMaxHeapChecker {

    /**
     * 驗證結果類別
     */
    static class ValidationResult {
        boolean isValid;
        int violationIndex;
        String message;

        ValidationResult(boolean isValid, int violationIndex, String message) {
            this.isValid = isValid;
            this.violationIndex = violationIndex;
            this.message = message;
        }

        @Override
        public String toString() {
            return String.format("Valid: %s, %s", isValid, message);
        }
    }

    /**
     * 檢查陣列是否為有效的 Max Heap
     */
    public static ValidationResult isValidMaxHeap(int[] arr) {
        if (arr == null) {
            return new ValidationResult(false, -1, "陣列為 null");
        }

        if (arr.length <= 1) {
            return new ValidationResult(true, -1, "空陣列或單元素陣列總是有效的 heap");
        }

        // 檢查每個非葉子節點
        int lastNonLeafIndex = (arr.length - 2) / 2;

        for (int i = 0; i <= lastNonLeafIndex; i++) {
            int leftChild = 2 * i + 1;
            int rightChild = 2 * i + 2;

            // 檢查左子節點
            if (leftChild < arr.length && arr[i] < arr[leftChild]) {
                return new ValidationResult(false, leftChild, 
                    String.format("節點 %d (值:%d) 小於左子節點 %d (值:%d)", 
                    i, arr[i], leftChild, arr[leftChild]));
            }

            // 檢查右子節點
            if (rightChild < arr.length && arr[i] < arr[rightChild]) {
                return new ValidationResult(false, rightChild, 
                    String.format("節點 %d (值:%d) 小於右子節點 %d (值:%d)", 
                    i, arr[i], rightChild, arr[rightChild]));
            }
        }

        return new ValidationResult(true, -1, "符合 Max Heap 性質");
    }

    /**
     * 快速檢查：只返回是否有效
     */
    public static boolean isValidMaxHeapSimple(int[] arr) {
        return isValidMaxHeap(arr).isValid;
    }

    /**
     * 檢查指定節點是否違反 Max Heap 性質
     */
    public static boolean isNodeValid(int[] arr, int index) {
        if (index < 0 || index >= arr.length) {
            return true; // 超出範圍的節點視為有效
        }

        int leftChild = 2 * index + 1;
        int rightChild = 2 * index + 2;

        // 檢查左子節點
        if (leftChild < arr.length && arr[index] < arr[leftChild]) {
            return false;
        }

        // 檢查右子節點
        if (rightChild < arr.length && arr[index] < arr[rightChild]) {
            return false;
        }

        return true;
    }

    /**
     * 找出所有違反 Max Heap 性質的節點
     */
    public static List<Integer> findAllViolations(int[] arr) {
        List<Integer> violations = new ArrayList<>();

        if (arr == null || arr.length <= 1) {
            return violations;
        }

        int lastNonLeafIndex = (arr.length - 2) / 2;

        for (int i = 0; i <= lastNonLeafIndex; i++) {
            if (!isNodeValid(arr, i)) {
                violations.add(i);
            }
        }

        return violations;
    }

    /**
     * 打印陣列的樹狀結構
     */
    public static void printArrayAsTree(int[] arr) {
        if (arr == null || arr.length == 0) {
            System.out.println("空陣列");
            return;
        }

        System.out.println("陣列內容: " + Arrays.toString(arr));
        System.out.println("樹狀結構:");
        printTreeHelper(arr, 0, "", true);
    }

    private static void printTreeHelper(int[] arr, int index, String prefix, boolean isLast) {
        if (index >= arr.length) {
            return;
        }

        System.out.println(prefix + (isLast ? "└── " : "├── ") + arr[index] + " (索引:" + index + ")");

        int leftChild = 2 * index + 1;
        int rightChild = 2 * index + 2;

        if (leftChild < arr.length || rightChild < arr.length) {
            if (rightChild < arr.length) {
                printTreeHelper(arr, rightChild, prefix + (isLast ? "    " : "│   "), leftChild >= arr.length);
            }
            if (leftChild < arr.length) {
                printTreeHelper(arr, leftChild, prefix + (isLast ? "    " : "│   "), true);
            }
        }
    }

    /**
     * 提供修復建議
     */
    public static void suggestFix(int[] arr) {
        ValidationResult result = isValidMaxHeap(arr);
        
        if (result.isValid) {
            System.out.println("陣列已經是有效的 Max Heap，無需修復");
            return;
        }

        System.out.println("修復建議:");
        System.out.println("問題: " + result.message);
        
        List<Integer> violations = findAllViolations(arr);
        System.out.println("違反規則的節點索引: " + violations);
        
        // 簡單的修復策略：將違反的子節點與父節點交換
        System.out.println("可能的修復方式:");
        for (int violationIndex : violations) {
            int parentIndex = (violationIndex - 1) / 2;
            if (parentIndex >= 0) {
                System.out.printf("  交換索引 %d (值:%d) 和索引 %d (值:%d)\n", 
                    parentIndex, arr[parentIndex], violationIndex, arr[violationIndex]);
            }
        }
    }

    /**
     * 嘗試修復 Max Heap（簡單版本）
     */
    public static int[] repairMaxHeap(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return arr.clone();
        }

        int[] repairedArr = arr.clone();
        
        // 從最後一個非葉子節點開始，向上進行 heapify
        int lastNonLeafIndex = (repairedArr.length - 2) / 2;
        
        for (int i = lastNonLeafIndex; i >= 0; i--) {
            heapifyDown(repairedArr, i);
        }

        return repairedArr;
    }

    /**
     * Max Heap 的下沉操作
     */
    private static void heapifyDown(int[] arr, int index) {
        int size = arr.length;
        
        while (true) {
            int leftChild = 2 * index + 1;
            int rightChild = 2 * index + 2;
            int largest = index;

            // 找出最大的節點
            if (leftChild < size && arr[leftChild] > arr[largest]) {
                largest = leftChild;
            }

            if (rightChild < size && arr[rightChild] > arr[largest]) {
                largest = rightChild;
            }

            // 如果當前節點已經是最大的，停止
            if (largest == index) {
                break;
            }

            // 交換並繼續下沉
            swap(arr, index, largest);
            index = largest;
        }
    }

    /**
     * 交換陣列中的兩個元素
     */
    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void main(String[] args) {
        System.out.println("=== 有效 Max Heap 檢測器 ===");

        // 測試案例
        int[][] testCases = {
            {100, 90, 80, 70, 60, 75, 65},     // 有效的 Max Heap
            {100, 90, 80, 95, 60, 75, 65},     // 無效的 Max Heap (95 > 90)
            {50},                               // 單元素
            {},                                 // 空陣列
            {10, 9, 8, 7, 6, 5, 4, 3, 2, 1},  // 完美的 Max Heap
            {1, 2, 3, 4, 5, 6, 7},             // 完全相反的順序 (Min Heap)
            {100, 100, 100, 100},              // 所有元素相同
            {50, 30, 70, 20, 40, 60, 80}       // 混合情況
        };

        String[] descriptions = {
            "有效的 Max Heap",
            "無效的 Max Heap (95 > 90)",
            "單元素陣列",
            "空陣列",
            "完美的 Max Heap",
            "Min Heap 順序",
            "所有元素相同",
            "混合情況"
        };

        for (int i = 0; i < testCases.length; i++) {
            System.out.println("\n" + "=".repeat(60));
            System.out.printf("測試案例 %d: %s\n", i + 1, descriptions[i]);
            
            int[] testCase = testCases[i];
            
            // 基本驗證
            ValidationResult result = isValidMaxHeap(testCase);
            System.out.println("驗證結果: " + result);
            
            // 打印樹狀結構
            if (testCase.length > 0 && testCase.length <= 10) {
                printArrayAsTree(testCase);
            } else if (testCase.length > 10) {
                System.out.println("陣列過大，跳過樹狀結構顯示");
            }
            
            // 找出所有違反規則的節點
            List<Integer> violations = findAllViolations(testCase);
            if (!violations.isEmpty()) {
                System.out.println("違反規則的節點索引: " + violations);
                
                // 顯示具體的違反內容
                for (int violationIndex : violations) {
                    int leftChild = 2 * violationIndex + 1;
                    int rightChild = 2 * violationIndex + 2;
                    
                    if (leftChild < testCase.length && testCase[violationIndex] < testCase[leftChild]) {
                        System.out.printf("  節點 %d (%d) < 左子節點 %d (%d)\n", 
                            violationIndex, testCase[violationIndex], leftChild, testCase[leftChild]);
                    }
                    
                    if (rightChild < testCase.length && testCase[violationIndex] < testCase[rightChild]) {
                        System.out.printf("  節點 %d (%d) < 右子節點 %d (%d)\n", 
                            violationIndex, testCase[violationIndex], rightChild, testCase[rightChild]);
                    }
                }
                
                // 嘗試修復
                System.out.println("\n嘗試修復:");
                int[] repaired = repairMaxHeap(testCase);
                System.out.println("修復前: " + Arrays.toString(testCase));
                System.out.println("修復後: " + Arrays.toString(repaired));
                
                ValidationResult repairedResult = isValidMaxHeap(repaired);
                System.out.println("修復結果: " + repairedResult);
            }
        }

        // 詳細測試特殊案例
        System.out.println("\n" + "=".repeat(60));
        System.out.println("特殊案例詳細測試:");
        
        // 測試邊界條件
        System.out.println("\n1. 邊界條件測試:");
        
        // null 陣列
        ValidationResult nullResult = isValidMaxHeap(null);
        System.out.println("null 陣列: " + nullResult);
        
        // 大型陣列測試
        System.out.println("\n2. 大型陣列測試:");
        int[] largeValidHeap = new int[1000];
        
        // 構建一個有效的大型 Max Heap
        for (int i = 0; i < largeValidHeap.length; i++) {
            largeValidHeap[i] = 1000 - i;  // 遞減序列，根據 heap 性質調整
        }
        
        // 將其轉換為有效的 Max Heap
        largeValidHeap = repairMaxHeap(largeValidHeap);
        
        long startTime = System.currentTimeMillis();
        ValidationResult largeResult = isValidMaxHeap(largeValidHeap);
        long endTime = System.currentTimeMillis();
        
        System.out.printf("1000元素陣列驗證: %s (耗時: %d ms)\n", largeResult.isValid, endTime - startTime);
        
        // 性能測試
        System.out.println("\n3. 性能測試:");
        int[] sizes = {100, 1000, 10000};
        
        for (int size : sizes) {
            int[] perfTestArray = new int[size];
            for (int i = 0; i < size; i++) {
                perfTestArray[i] = size - i;
            }
            
            startTime = System.currentTimeMillis();
            ValidationResult perfResult = isValidMaxHeap(perfTestArray);
            endTime = System.currentTimeMillis();
            
            System.out.printf("大小 %d: %s (耗時: %d ms)\n", size, perfResult.isValid, endTime - startTime);
        }

        System.out.println("\n=== Max Heap 檢測器測試完成 ===");
    }
}
