import java.util.*;

/**
 * 練習 4.9：資料流中的移動平均數
 * 難度：★★★★☆
 * 
 * 設計一個支援滑動視窗移動平均數、中位數和極值查詢的資料結構
 */
public class MovingAverageStream {

    /**
     * 移動平均數計算器（支援多種統計）
     */
    static class MovingAverage {
        private final int windowSize;
        private final Queue<Integer> window;
        private final TreeMap<Integer, Integer> sortedWindow; // 維護有序視窗
        private long sum;

        public MovingAverage(int size) {
            this.windowSize = size;
            this.window = new LinkedList<>();
            this.sortedWindow = new TreeMap<>();
            this.sum = 0;
        }

        /**
         * 添加新值並返回移動平均數
         */
        public double next(int val) {
            // 如果視窗已滿，移除最舊的元素
            if (window.size() >= windowSize) {
                int oldVal = window.poll();
                sum -= oldVal;
                
                // 從排序視窗中移除
                int count = sortedWindow.get(oldVal);
                if (count == 1) {
                    sortedWindow.remove(oldVal);
                } else {
                    sortedWindow.put(oldVal, count - 1);
                }
            }

            // 添加新元素
            window.offer(val);
            sum += val;
            
            // 添加到排序視窗
            sortedWindow.put(val, sortedWindow.getOrDefault(val, 0) + 1);

            return (double) sum / window.size();
        }

        /**
         * 獲取當前視窗的中位數
         */
        public double getMedian() {
            if (window.isEmpty()) {
                return 0.0;
            }

            int size = window.size();
            int medianIndex1 = (size - 1) / 2;
            int medianIndex2 = size / 2;

            List<Integer> sortedList = new ArrayList<>();
            for (Map.Entry<Integer, Integer> entry : sortedWindow.entrySet()) {
                for (int i = 0; i < entry.getValue(); i++) {
                    sortedList.add(entry.getKey());
                }
            }

            if (size % 2 == 1) {
                return sortedList.get(medianIndex1);
            } else {
                return (sortedList.get(medianIndex1) + sortedList.get(medianIndex2)) / 2.0;
            }
        }

        /**
         * 獲取當前視窗的最小值
         */
        public int getMin() {
            if (sortedWindow.isEmpty()) {
                throw new RuntimeException("視窗為空");
            }
            return sortedWindow.firstKey();
        }

        /**
         * 獲取當前視窗的最大值
         */
        public int getMax() {
            if (sortedWindow.isEmpty()) {
                throw new RuntimeException("視窗為空");
            }
            return sortedWindow.lastKey();
        }

        /**
         * 獲取當前視窗大小
         */
        public int size() {
            return window.size();
        }

        /**
         * 獲取當前視窗內容
         */
        public List<Integer> getWindow() {
            return new ArrayList<>(window);
        }

        /**
         * 獲取標準差
         */
        public double getStandardDeviation() {
            if (window.size() <= 1) {
                return 0.0;
            }

            double mean = (double) sum / window.size();
            double variance = 0.0;

            for (int val : window) {
                variance += Math.pow(val - mean, 2);
            }

            variance /= window.size();
            return Math.sqrt(variance);
        }

        /**
         * 獲取變異係數
         */
        public double getCoefficientOfVariation() {
            double mean = (double) sum / window.size();
            if (mean == 0) {
                return 0.0;
            }
            return getStandardDeviation() / Math.abs(mean);
        }
    }

    /**
     * 高效能移動平均數計算器（使用雙 heap 維護中位數）
     */
    static class AdvancedMovingAverage {
        private final int windowSize;
        private final Queue<Integer> window;
        private final PriorityQueue<Integer> maxHeap; // 較小的一半
        private final PriorityQueue<Integer> minHeap; // 較大的一半
        private final Map<Integer, Integer> delayedDeletes; // 延遲刪除
        private long sum;

        public AdvancedMovingAverage(int size) {
            this.windowSize = size;
            this.window = new LinkedList<>();
            this.maxHeap = new PriorityQueue<>((a, b) -> Integer.compare(b, a));
            this.minHeap = new PriorityQueue<>();
            this.delayedDeletes = new HashMap<>();
            this.sum = 0;
        }

        public double next(int val) {
            // 如果視窗已滿，移除最舊的元素
            if (window.size() >= windowSize) {
                int oldVal = window.poll();
                sum -= oldVal;
                markForDeletion(oldVal);
            }

            // 添加新元素
            window.offer(val);
            sum += val;
            addToHeaps(val);
            balanceHeaps();

            return (double) sum / window.size();
        }

        private void addToHeaps(int val) {
            if (maxHeap.isEmpty() || val <= getTop(maxHeap)) {
                maxHeap.offer(val);
            } else {
                minHeap.offer(val);
            }
        }

        private void markForDeletion(int val) {
            delayedDeletes.put(val, delayedDeletes.getOrDefault(val, 0) + 1);
        }

        private void balanceHeaps() {
            cleanTops();
            
            int maxHeapSize = getEffectiveSize(maxHeap);
            int minHeapSize = getEffectiveSize(minHeap);

            if (maxHeapSize > minHeapSize + 1) {
                minHeap.offer(maxHeap.poll());
                cleanDelayed(getTop(minHeap));
            } else if (minHeapSize > maxHeapSize + 1) {
                maxHeap.offer(minHeap.poll());
                cleanDelayed(getTop(maxHeap));
            }
        }

        private void cleanTops() {
            while (!maxHeap.isEmpty() && delayedDeletes.getOrDefault(maxHeap.peek(), 0) > 0) {
                cleanDelayed(maxHeap.poll());
            }
            while (!minHeap.isEmpty() && delayedDeletes.getOrDefault(minHeap.peek(), 0) > 0) {
                cleanDelayed(minHeap.poll());
            }
        }

        private void cleanDelayed(int val) {
            int count = delayedDeletes.getOrDefault(val, 0);
            if (count > 0) {
                delayedDeletes.put(val, count - 1);
                if (delayedDeletes.get(val) == 0) {
                    delayedDeletes.remove(val);
                }
            }
        }

        private int getEffectiveSize(PriorityQueue<Integer> heap) {
            int size = heap.size();
            for (Integer val : heap) {
                size -= delayedDeletes.getOrDefault(val, 0);
            }
            return Math.max(0, size);
        }

        private int getTop(PriorityQueue<Integer> heap) {
            while (!heap.isEmpty() && delayedDeletes.getOrDefault(heap.peek(), 0) > 0) {
                cleanDelayed(heap.poll());
            }
            if (heap.isEmpty()) {
                return 0;
            }
            Integer top = heap.peek();
            return top != null ? top : 0;
        }

        public double getMedian() {
            cleanTops();
            
            int maxHeapSize = getEffectiveSize(maxHeap);
            int minHeapSize = getEffectiveSize(minHeap);
            
            if (maxHeapSize == minHeapSize) {
                return (getTop(maxHeap) + getTop(minHeap)) / 2.0;
            } else if (maxHeapSize > minHeapSize) {
                return getTop(maxHeap);
            } else {
                return getTop(minHeap);
            }
        }

        public int getMin() {
            cleanTops();
            if (maxHeap.isEmpty() && minHeap.isEmpty()) {
                throw new RuntimeException("視窗為空");
            }
            
            int minFromMax = Integer.MAX_VALUE;
            for (Integer val : maxHeap) {
                if (val != null && delayedDeletes.getOrDefault(val, 0) == 0 && val < minFromMax) {
                    minFromMax = val;
                }
            }
            
            int minFromMin = Integer.MAX_VALUE;
            for (Integer val : minHeap) {
                if (val != null && delayedDeletes.getOrDefault(val, 0) == 0 && val < minFromMin) {
                    minFromMin = val;
                }
            }
            
            return Math.min(minFromMax, minFromMin);
        }

        public int getMax() {
            cleanTops();
            if (maxHeap.isEmpty() && minHeap.isEmpty()) {
                throw new RuntimeException("視窗為空");
            }
            
            int maxFromMax = Integer.MIN_VALUE;
            for (Integer val : maxHeap) {
                if (val != null && delayedDeletes.getOrDefault(val, 0) == 0 && val > maxFromMax) {
                    maxFromMax = val;
                }
            }
            
            int maxFromMin = Integer.MIN_VALUE;
            for (Integer val : minHeap) {
                if (val != null && delayedDeletes.getOrDefault(val, 0) == 0 && val > maxFromMin) {
                    maxFromMin = val;
                }
            }
            
            return Math.max(maxFromMax, maxFromMin);
        }

        public double getAverage() {
            return window.isEmpty() ? 0.0 : (double) sum / window.size();
        }

        public int size() {
            return window.size();
        }
    }

    /**
     * 多指標統計器
     */
    static class MultiMetricCalculator {
        private final MovingAverage basicCalculator;
        private final AdvancedMovingAverage advancedCalculator;
        private final List<Double> averageHistory;
        private final List<Double> medianHistory;

        public MultiMetricCalculator(int windowSize) {
            this.basicCalculator = new MovingAverage(windowSize);
            this.advancedCalculator = new AdvancedMovingAverage(windowSize);
            this.averageHistory = new ArrayList<>();
            this.medianHistory = new ArrayList<>();
        }

        public void addValue(int value) {
            double avg = basicCalculator.next(value);
            advancedCalculator.next(value);
            
            averageHistory.add(avg);
            medianHistory.add(basicCalculator.getMedian());
        }

        public void printCurrentStats() {
            System.out.printf("當前統計資訊:\n");
            System.out.printf("  視窗大小: %d\n", basicCalculator.size());
            System.out.printf("  視窗內容: %s\n", basicCalculator.getWindow());
            System.out.printf("  平均數: %.2f\n", averageHistory.get(averageHistory.size() - 1));
            System.out.printf("  中位數: %.2f\n", basicCalculator.getMedian());
            System.out.printf("  最小值: %d\n", basicCalculator.getMin());
            System.out.printf("  最大值: %d\n", basicCalculator.getMax());
            System.out.printf("  標準差: %.2f\n", basicCalculator.getStandardDeviation());
            System.out.printf("  變異係數: %.2f\n", basicCalculator.getCoefficientOfVariation());
            System.out.println();
        }

        public List<Double> getAverageHistory() {
            return new ArrayList<>(averageHistory);
        }

        public List<Double> getMedianHistory() {
            return new ArrayList<>(medianHistory);
        }
    }

    /**
     * 效能測試
     */
    public static void performanceTest(int windowSize, int numOperations) {
        System.out.printf("效能測試：視窗大小 %d，操作次數 %d\n", windowSize, numOperations);

        Random random = new Random(42);

        // 測試基本版本
        MovingAverage basic = new MovingAverage(windowSize);
        long startTime = System.nanoTime();
        
        for (int i = 0; i < numOperations; i++) {
            basic.next(random.nextInt(1000));
            if (i % (numOperations / 10) == 0) {
                basic.getMedian();
                basic.getMin();
                basic.getMax();
            }
        }
        
        long basicTime = System.nanoTime() - startTime;

        // 測試進階版本
        AdvancedMovingAverage advanced = new AdvancedMovingAverage(windowSize);
        startTime = System.nanoTime();
        
        for (int i = 0; i < numOperations; i++) {
            advanced.next(random.nextInt(1000));
            if (i % (numOperations / 10) == 0) {
                advanced.getMedian();
                advanced.getMin();
                advanced.getMax();
            }
        }
        
        long advancedTime = System.nanoTime() - startTime;

        System.out.printf("基本版本耗時：%.2f ms\n", basicTime / 1_000_000.0);
        System.out.printf("進階版本耗時：%.2f ms\n", advancedTime / 1_000_000.0);
        System.out.printf("效能提升：%.2fx\n", (double)basicTime / advancedTime);
        System.out.println();
    }

    public static void main(String[] args) {
        System.out.println("=== 資料流移動平均數系統 ===");

        // 測試案例1：基本功能測試
        System.out.println("1. 基本功能測試：");
        MovingAverage ma = new MovingAverage(3);

        int[] testData = {1, 10, 3, 5};
        for (int val : testData) {
            double avg = ma.next(val);
            System.out.printf("添加 %d: 平均數=%.2f, 中位數=%.1f, 最小值=%d, 最大值=%d\n",
                val, avg, ma.getMedian(), ma.getMin(), ma.getMax());
        }

        System.out.printf("期望結果：\n");
        System.out.printf("  添加 1: 平均數=1.00\n");
        System.out.printf("  添加 10: 平均數=5.50\n");
        System.out.printf("  添加 3: 平均數=4.67\n");
        System.out.printf("  添加 5: 平均數=6.00\n");

        // 測試案例2：詳細統計測試
        System.out.println("\n" + "=".repeat(50));
        System.out.println("2. 詳細統計測試：");
        
        MultiMetricCalculator calculator = new MultiMetricCalculator(4);
        int[] detailedData = {2, 4, 6, 8, 1, 3, 5, 7, 9};
        
        for (int i = 0; i < detailedData.length; i++) {
            calculator.addValue(detailedData[i]);
            System.out.printf("步驟 %d - 添加 %d:\n", i + 1, detailedData[i]);
            calculator.printCurrentStats();
        }

        // 測試案例3：進階版本對比
        System.out.println("3. 基本版本與進階版本對比：");
        
        MovingAverage basic = new MovingAverage(5);
        AdvancedMovingAverage advanced = new AdvancedMovingAverage(5);
        
        int[] compareData = {5, 2, 8, 1, 9, 3, 7, 4, 6};
        
        System.out.println("基本版本 vs 進階版本結果對比：");
        for (int val : compareData) {
            double basicAvg = basic.next(val);
            double advancedAvg = advanced.next(val);
            
            double basicMedian = basic.getMedian();
            double advancedMedian = advanced.getMedian();
            
            System.out.printf("添加 %d: 基本(%.2f, %.1f) vs 進階(%.2f, %.1f)\n",
                val, basicAvg, basicMedian, advancedAvg, advancedMedian);
        }

        // 測試案例4：邊界情況
        System.out.println("\n4. 邊界情況測試：");
        
        // 單元素視窗
        MovingAverage single = new MovingAverage(1);
        single.next(42);
        System.out.printf("單元素視窗：平均數=%.1f, 中位數=%.1f\n", 
            42.0, single.getMedian());
        
        // 相同元素
        MovingAverage same = new MovingAverage(3);
        for (int i = 0; i < 5; i++) {
            same.next(7);
        }
        System.out.printf("相同元素：平均數=%.1f, 中位數=%.1f, 標準差=%.2f\n",
            7.0, same.getMedian(), same.getStandardDeviation());
        
        // 極值測試
        MovingAverage extreme = new MovingAverage(3);
        extreme.next(Integer.MAX_VALUE);
        extreme.next(Integer.MIN_VALUE);
        extreme.next(0);
        System.out.printf("極值測試：最小值=%d, 最大值=%d\n",
            extreme.getMin(), extreme.getMax());

        // 測試案例5：實際應用場景
        System.out.println("\n" + "=".repeat(50));
        System.out.println("5. 實際應用場景 - 股票價格監控：");
        
        MovingAverage stockMonitor = new MovingAverage(5); // 5日移動平均
        int[] stockPrices = {100, 102, 98, 105, 103, 107, 101, 110, 108, 95};
        String[] days = {"週一", "週二", "週三", "週四", "週五", "下週一", "週二", "週三", "週四", "週五"};
        
        for (int i = 0; i < stockPrices.length; i++) {
            double avg = stockMonitor.next(stockPrices[i]);
            
            System.out.printf("%s 股價 $%d: 5日均價 $%.2f, 中位數 $%.1f, 波動性 %.2f%%\n",
                days[i], stockPrices[i], avg, stockMonitor.getMedian(),
                stockMonitor.getCoefficientOfVariation() * 100);
        }

        // 測試案例6：感測器資料處理
        System.out.println("\n6. 感測器資料處理模擬：");
        
        MovingAverage sensorData = new MovingAverage(10);
        Random random = new Random(42);
        
        // 模擬感測器資料（基準值 + 噪聲）
        double baseValue = 25.0; // 基準溫度
        System.out.println("溫度感測器 10點移動平均（模擬異常檢測）：");
        
        for (int i = 0; i < 20; i++) {
            // 正常情況下的溫度變化
            double noise = (random.nextGaussian() - 0.5) * 2; // 高斯噪聲
            int temperature = (int)(baseValue + noise);
            
            // 模擬異常值
            if (i == 10) {
                temperature = 40; // 異常高溫
            } else if (i == 15) {
                temperature = 10; // 異常低溫
            }
            
            double avg = sensorData.next(temperature);
            double stdDev = sensorData.getStandardDeviation();
            
            // 簡單的異常檢測（偏離平均值超過2個標準差）
            boolean isAnomaly = Math.abs(temperature - avg) > 2 * stdDev && sensorData.size() >= 5;
            
            System.out.printf("時間點 %2d: 溫度 %2d°C, 移動平均 %.1f°C, 標準差 %.1f%s\n",
                i + 1, temperature, avg, stdDev, isAnomaly ? " [異常!]" : "");
        }

        // 測試案例7：效能測試
        System.out.println("\n7. 效能測試：");
        
        performanceTest(10, 1000);
        performanceTest(100, 10000);
        performanceTest(1000, 100000);

        // 測試案例8：記憶體使用分析
        System.out.println("8. 記憶體使用分析：");
        
        Runtime runtime = Runtime.getRuntime();
        long memBefore = runtime.totalMemory() - runtime.freeMemory();
        
        // 創建大量移動平均計算器來測試記憶體
        long totalCalculations = 0;
        for (int i = 0; i < 1000; i++) {
            MovingAverage calc = new MovingAverage(100);
            for (int j = 0; j < 100; j++) {
                calc.next(j);
            }
            // 觸發記憶體分配
            calc.getMedian();
            totalCalculations++;
        }
        
        long memAfter = runtime.totalMemory() - runtime.freeMemory();
        System.out.printf("創建 %d 個大小為 100 的移動平均計算器：\n", totalCalculations);
        System.out.printf("記憶體使用增加：%.2f MB\n", (memAfter - memBefore) / 1024.0 / 1024.0);
        System.out.printf("平均每個計算器：%.2f KB\n", (memAfter - memBefore) / totalCalculations / 1024.0);

        // 測試案例9：算法複雜度說明
        System.out.println("\n9. 算法複雜度分析：");
        System.out.println("基本版本 (TreeMap 實作)：");
        System.out.println("  next(): O(log w)，w 是視窗大小");
        System.out.println("  getMedian(): O(w)");
        System.out.println("  getMin()/getMax(): O(1)");
        System.out.println("  空間複雜度: O(w)");
        
        System.out.println("\n進階版本 (雙 Heap + 延遲刪除)：");
        System.out.println("  next(): O(log w)");
        System.out.println("  getMedian(): O(log w)");
        System.out.println("  getMin()/getMax(): O(w) [可進一步優化]");
        System.out.println("  空間複雜度: O(w)");
        
        System.out.println("\n應用場景：");
        System.out.println("  - 即時資料流分析");
        System.out.println("  - 金融市場技術指標");
        System.out.println("  - 感測器資料平滑處理");
        System.out.println("  - 異常檢測系統");
        System.out.println("  - 效能監控系統");

        System.out.println("\n=== 資料流移動平均數系統測試完成 ===");
    }
}
