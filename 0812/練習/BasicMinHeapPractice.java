import java.util.*;

/**
 * 練習 4.1：實作基本 Min Heap 操作
 * 難度：★☆☆☆☆
 * 
 * 功能：
 * - insert(int val)：插入一個元素
 * - extractMin()：取出並返回最小元素
 * - getMin()：查看最小元素但不移除
 * - size()：返回 heap 的大小
 * - isEmpty()：檢查 heap 是否為空
 */
public class BasicMinHeapPractice {

    /**
     * Min Heap 實作類別
     */
    static class MinHeap {
        private List<Integer> heap;

        public MinHeap() {
            this.heap = new ArrayList<>();
        }

        /**
         * 插入元素
         */
        public void insert(int val) {
            heap.add(val);
            heapifyUp(heap.size() - 1);
        }

        /**
         * 取出最小元素
         */
        public Integer extractMin() {
            if (isEmpty()) {
                return null;
            }

            int min = heap.get(0);
            
            // 將最後一個元素移到根部
            int lastElement = heap.get(heap.size() - 1);
            heap.set(0, lastElement);
            heap.remove(heap.size() - 1);

            // 如果還有元素，進行下沉操作
            if (!isEmpty()) {
                heapifyDown(0);
            }

            return min;
        }

        /**
         * 查看最小元素
         */
        public Integer getMin() {
            if (isEmpty()) {
                return null;
            }
            return heap.get(0);
        }

        /**
         * 返回 heap 大小
         */
        public int size() {
            return heap.size();
        }

        /**
         * 檢查是否為空
         */
        public boolean isEmpty() {
            return heap.isEmpty();
        }

        /**
         * 上浮操作：維持 heap 性質
         */
        private void heapifyUp(int index) {
            while (index > 0) {
                int parentIndex = (index - 1) / 2;
                
                // 如果當前節點小於父節點，交換
                if (heap.get(index) < heap.get(parentIndex)) {
                    swap(index, parentIndex);
                    index = parentIndex;
                } else {
                    break;
                }
            }
        }

        /**
         * 下沉操作：維持 heap 性質
         */
        private void heapifyDown(int index) {
            while (true) {
                int leftChild = 2 * index + 1;
                int rightChild = 2 * index + 2;
                int smallest = index;

                // 找出最小的節點
                if (leftChild < heap.size() && heap.get(leftChild) < heap.get(smallest)) {
                    smallest = leftChild;
                }

                if (rightChild < heap.size() && heap.get(rightChild) < heap.get(smallest)) {
                    smallest = rightChild;
                }

                // 如果當前節點已經是最小的，停止
                if (smallest == index) {
                    break;
                }

                swap(index, smallest);
                index = smallest;
            }
        }

        /**
         * 交換兩個元素
         */
        private void swap(int i, int j) {
            int temp = heap.get(i);
            heap.set(i, heap.get(j));
            heap.set(j, temp);
        }

        /**
         * 獲取 heap 的字串表示
         */
        public String getHeapString() {
            return heap.toString();
        }

        /**
         * 打印 heap 結構
         */
        public void printHeap() {
            if (isEmpty()) {
                System.out.println("空的 heap");
                return;
            }

            System.out.println("Heap 結構（陣列形式）: " + heap);
            System.out.println("Heap 結構（樹形式）:");
            printHeapTree(0, "", true);
        }

        /**
         * 以樹狀結構打印 heap
         */
        private void printHeapTree(int index, String prefix, boolean isLast) {
            if (index >= heap.size()) {
                return;
            }

            System.out.println(prefix + (isLast ? "└── " : "├── ") + heap.get(index));

            int leftChild = 2 * index + 1;
            int rightChild = 2 * index + 2;

            if (leftChild < heap.size() || rightChild < heap.size()) {
                if (rightChild < heap.size()) {
                    printHeapTree(rightChild, prefix + (isLast ? "    " : "│   "), leftChild >= heap.size());
                }
                if (leftChild < heap.size()) {
                    printHeapTree(leftChild, prefix + (isLast ? "    " : "│   "), true);
                }
            }
        }

        /**
         * 驗證 heap 的正確性
         */
        public boolean isValidMinHeap() {
            for (int i = 0; i < heap.size(); i++) {
                int leftChild = 2 * i + 1;
                int rightChild = 2 * i + 2;

                if (leftChild < heap.size() && heap.get(i) > heap.get(leftChild)) {
                    return false;
                }

                if (rightChild < heap.size() && heap.get(i) > heap.get(rightChild)) {
                    return false;
                }
            }
            return true;
        }
    }

    public static void main(String[] args) {
        System.out.println("=== 基本 Min Heap 操作練習 ===");

        MinHeap minHeap = new MinHeap();

        // 測試案例1：插入順序 15, 10, 20, 8, 25, 5
        System.out.println("1. 插入測試：");
        int[] testValues = {15, 10, 20, 8, 25, 5};

        for (int val : testValues) {
            System.out.printf("插入 %d\n", val);
            minHeap.insert(val);
            System.out.printf("  當前最小值：%d\n", minHeap.getMin());
            System.out.printf("  Heap 大小：%d\n", minHeap.size());
            System.out.printf("  Heap 陣列：%s\n", minHeap.getHeapString());
            System.out.printf("  是否為有效 Min Heap：%s\n", minHeap.isValidMinHeap());
            System.out.println();
        }

        System.out.println("2. 完整的 Heap 結構：");
        minHeap.printHeap();

        // 測試案例2：提取最小元素
        System.out.println("\n3. 提取最小元素測試：");
        List<Integer> extractedValues = new ArrayList<>();

        while (!minHeap.isEmpty()) {
            Integer min = minHeap.extractMin();
            extractedValues.add(min);
            System.out.printf("提取最小值：%d\n", min);
            System.out.printf("  剩餘 Heap 大小：%d\n", minHeap.size());
            if (!minHeap.isEmpty()) {
                System.out.printf("  新的最小值：%d\n", minHeap.getMin());
                System.out.printf("  Heap 陣列：%s\n", minHeap.getHeapString());
            } else {
                System.out.println("  Heap 已空");
            }
            System.out.printf("  是否為有效 Min Heap：%s\n", minHeap.isValidMinHeap());
            System.out.println();
        }

        System.out.printf("期望的提取順序：[5, 8, 10, 15, 20, 25]\n");
        System.out.printf("實際的提取順序：%s\n", extractedValues);
        
        boolean isCorrectOrder = extractedValues.equals(Arrays.asList(5, 8, 10, 15, 20, 25));
        System.out.printf("提取順序是否正確：%s\n", isCorrectOrder);

        // 測試案例3：邊界情況
        System.out.println("\n" + "=".repeat(50));
        System.out.println("4. 邊界情況測試：");

        // 空 heap 操作
        MinHeap emptyHeap = new MinHeap();
        System.out.println("空 heap 測試：");
        System.out.printf("  isEmpty(): %s\n", emptyHeap.isEmpty());
        System.out.printf("  size(): %d\n", emptyHeap.size());
        System.out.printf("  getMin(): %s\n", emptyHeap.getMin());
        System.out.printf("  extractMin(): %s\n", emptyHeap.extractMin());

        // 單元素 heap
        System.out.println("\n單元素 heap 測試：");
        MinHeap singleHeap = new MinHeap();
        singleHeap.insert(42);
        System.out.printf("  插入 42 後大小：%d\n", singleHeap.size());
        System.out.printf("  最小值：%d\n", singleHeap.getMin());
        System.out.printf("  提取最小值：%d\n", singleHeap.extractMin());
        System.out.printf("  提取後是否為空：%s\n", singleHeap.isEmpty());

        // 重複元素測試
        System.out.println("\n重複元素 heap 測試：");
        MinHeap duplicateHeap = new MinHeap();
        int[] duplicateValues = {5, 3, 5, 1, 3, 1};
        
        for (int val : duplicateValues) {
            duplicateHeap.insert(val);
        }
        
        System.out.printf("插入 %s\n", Arrays.toString(duplicateValues));
        System.out.printf("Heap 陣列：%s\n", duplicateHeap.getHeapString());
        
        List<Integer> duplicateExtracted = new ArrayList<>();
        while (!duplicateHeap.isEmpty()) {
            duplicateExtracted.add(duplicateHeap.extractMin());
        }
        
        System.out.printf("提取順序：%s\n", duplicateExtracted);

        // 大數據測試
        System.out.println("\n大數據測試：");
        MinHeap largeHeap = new MinHeap();
        Random random = new Random(42); // 固定種子確保可重現
        
        // 插入1000個隨機數
        for (int i = 0; i < 1000; i++) {
            largeHeap.insert(random.nextInt(10000));
        }
        
        System.out.printf("插入1000個隨機數後的大小：%d\n", largeHeap.size());
        System.out.printf("是否為有效 Min Heap：%s\n", largeHeap.isValidMinHeap());
        
        // 提取前10個最小值
        List<Integer> top10 = new ArrayList<>();
        for (int i = 0; i < 10 && !largeHeap.isEmpty(); i++) {
            top10.add(largeHeap.extractMin());
        }
        
        System.out.printf("前10個最小值：%s\n", top10);
        
        // 驗證是否為遞增序列
        boolean isIncreasing = true;
        for (int i = 1; i < top10.size(); i++) {
            if (top10.get(i) < top10.get(i-1)) {
                isIncreasing = false;
                break;
            }
        }
        
        System.out.printf("前10個值是否為遞增序列：%s\n", isIncreasing);
        System.out.printf("提取後剩餘大小：%d\n", largeHeap.size());
        System.out.printf("剩餘 heap 是否仍有效：%s\n", largeHeap.isValidMinHeap());

        System.out.println("\n=== Min Heap 操作練習完成 ===");
    }
}
