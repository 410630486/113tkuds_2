
import java.util.*;

/**
 * 練習 4.7：最大化股票利潤 難度：★★★★☆
 *
 * 使用 heap 輔助解決有限交易次數的股票買賣問題
 */
public class StockMaximizer {

    /**
     * 交易機會類別
     */
    static class Transaction {

        int buyDay;
        int sellDay;
        int profit;

        Transaction(int buyDay, int sellDay, int profit) {
            this.buyDay = buyDay;
            this.sellDay = sellDay;
            this.profit = profit;
        }

        @Override
        public String toString() {
            return String.format("第%d天買入，第%d天賣出，利潤%d", buyDay + 1, sellDay + 1, profit);
        }
    }

    /**
     * 方法1：使用 Max Heap 找出最佳交易組合 適用於 K 較小的情況
     */
    public static int maxProfitWithHeap(int[] prices, int k) {
        if (prices == null || prices.length <= 1 || k <= 0) {
            return 0;
        }

        int n = prices.length;

        // 如果 k 足夠大，可以進行任意次數交易
        if (k >= n / 2) {
            return maxProfitUnlimited(prices);
        }

        // 找出所有可能的有利交易
        PriorityQueue<Transaction> profitableTransactions = new PriorityQueue<>(
                (a, b) -> Integer.compare(b.profit, a.profit) // 按利潤降序排列
        );

        // 生成所有可能的交易
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (prices[j] > prices[i]) {
                    profitableTransactions.offer(new Transaction(i, j, prices[j] - prices[i]));
                }
            }
        }

        // 選擇不衝突的前 k 個最佳交易
        List<Transaction> selectedTransactions = selectNonConflictingTransactions(profitableTransactions, k);

        return selectedTransactions.stream().mapToInt(t -> t.profit).sum();
    }

    /**
     * 選擇不衝突的交易
     */
    private static List<Transaction> selectNonConflictingTransactions(
            PriorityQueue<Transaction> transactions, int k) {

        List<Transaction> selected = new ArrayList<>();
        boolean[] used = new boolean[transactions.size() * 2]; // 假設足夠大

        while (!transactions.isEmpty() && selected.size() < k) {
            Transaction current = transactions.poll();

            // 檢查是否與已選交易衝突
            boolean conflict = false;
            for (Transaction existing : selected) {
                if (!(current.sellDay < existing.buyDay || current.buyDay > existing.sellDay)) {
                    conflict = true;
                    break;
                }
            }

            if (!conflict) {
                selected.add(current);
            }
        }

        return selected;
    }

    /**
     * 方法2：貪心算法 + 堆積優化 找出所有相鄰的獲利區間，然後合併最佳的 k 個
     */
    public static int maxProfitGreedyWithHeap(int[] prices, int k) {
        if (prices == null || prices.length <= 1 || k <= 0) {
            return 0;
        }

        int n = prices.length;

        if (k >= n / 2) {
            return maxProfitUnlimited(prices);
        }

        // 找出所有的買賣對
        List<int[]> transactions = new ArrayList<>();
        int i = 0;

        while (i < n - 1) {
            // 找到局部最低點
            while (i < n - 1 && prices[i + 1] <= prices[i]) {
                i++;
            }
            int buy = i;

            // 找到局部最高點
            while (i < n - 1 && prices[i + 1] > prices[i]) {
                i++;
            }
            int sell = i;

            if (buy < sell) {
                transactions.add(new int[]{buy, sell, prices[sell] - prices[buy]});
            }
            i++;
        }

        // 如果交易數量不超過 k，返回所有交易的利潤
        if (transactions.size() <= k) {
            return transactions.stream().mapToInt(t -> t[2]).sum();
        }

        // 使用堆積來合併交易
        return optimizeTransactions(transactions, k);
    }

    /**
     * 優化交易：合併相鄰交易或選擇最佳交易
     */
    private static int optimizeTransactions(List<int[]> transactions, int k) {
        // 使用最小堆來維護當前的 k 個最佳交易
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();

        for (int[] transaction : transactions) {
            int profit = transaction[2];

            if (minHeap.size() < k) {
                minHeap.offer(profit);
            } else if (profit > minHeap.peek()) {
                minHeap.poll();
                minHeap.offer(profit);
            }
        }

        return minHeap.stream().mapToInt(Integer::intValue).sum();
    }

    /**
     * 方法3：動態規劃（標準解法）
     */
    public static int maxProfitDP(int[] prices, int k) {
        if (prices == null || prices.length <= 1 || k <= 0) {
            return 0;
        }

        int n = prices.length;

        if (k >= n / 2) {
            return maxProfitUnlimited(prices);
        }

        // dp[i][j][0] = 第i天完成j次交易且不持股的最大利潤
        // dp[i][j][1] = 第i天完成j次交易且持股的最大利潤
        int[][][] dp = new int[n][k + 1][2];

        // 初始化
        for (int j = 0; j <= k; j++) {
            dp[0][j][0] = 0;
            dp[0][j][1] = -prices[0];
        }

        for (int i = 1; i < n; i++) {
            for (int j = 0; j <= k; j++) {
                dp[i][j][0] = Math.max(dp[i - 1][j][0], dp[i - 1][j][1] + prices[i]);

                if (j > 0) {
                    dp[i][j][1] = Math.max(dp[i - 1][j][1], dp[i - 1][j - 1][0] - prices[i]);
                } else {
                    dp[i][j][1] = Math.max(dp[i - 1][j][1], -prices[i]);
                }
            }
        }

        return dp[n - 1][k][0];
    }

    /**
     * 無限交易次數的最大利潤
     */
    private static int maxProfitUnlimited(int[] prices) {
        int profit = 0;
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > prices[i - 1]) {
                profit += prices[i] - prices[i - 1];
            }
        }
        return profit;
    }

    /**
     * 方法4：使用兩個堆積分別追蹤買入和賣出機會
     */
    public static int maxProfitTwoHeaps(int[] prices, int k) {
        if (prices == null || prices.length <= 1 || k <= 0) {
            return 0;
        }

        if (k >= prices.length / 2) {
            return maxProfitUnlimited(prices);
        }

        // 買入機會堆積（最小值優先）
        PriorityQueue<Integer> buyHeap = new PriorityQueue<>();
        // 賣出機會堆積（最大值優先）
        PriorityQueue<Integer> sellHeap = new PriorityQueue<>((a, b) -> Integer.compare(b, a));

        for (int price : prices) {
            buyHeap.offer(price);
            sellHeap.offer(price);
        }

        int totalProfit = 0;

        for (int i = 0; i < k; i++) {
            if (buyHeap.isEmpty() || sellHeap.isEmpty()) {
                break;
            }

            int buyPrice = buyHeap.poll();
            int sellPrice = sellHeap.poll();

            if (sellPrice > buyPrice) {
                totalProfit += sellPrice - buyPrice;
            } else {
                break; // 沒有更多有利可圖的交易
            }
        }

        return totalProfit;
    }

    /**
     * 分析股票價格，找出買賣時機
     */
    public static void analyzeStock(int[] prices, int k) {
        System.out.printf("股票價格分析（K=%d）：%s\n", k, Arrays.toString(prices));

        // 找出所有可能的有利交易
        List<Transaction> allTransactions = new ArrayList<>();

        for (int i = 0; i < prices.length; i++) {
            for (int j = i + 1; j < prices.length; j++) {
                if (prices[j] > prices[i]) {
                    allTransactions.add(new Transaction(i, j, prices[j] - prices[i]));
                }
            }
        }

        // 按利潤排序
        allTransactions.sort((a, b) -> Integer.compare(b.profit, a.profit));

        System.out.println("所有可能的有利交易（按利潤排序）：");
        for (int i = 0; i < Math.min(10, allTransactions.size()); i++) {
            System.out.printf("  %s\n", allTransactions.get(i));
        }

        // 選擇最佳的不衝突交易
        List<Transaction> topTransactions = allTransactions.subList(0, Math.min(20, allTransactions.size()));
        PriorityQueue<Transaction> heap = new PriorityQueue<>(
                (a, b) -> Integer.compare(b.profit, a.profit)
        );
        heap.addAll(topTransactions);
        List<Transaction> bestTransactions = selectNonConflictingTransactions(heap, k);

        System.out.println("\n推薦的交易策略：");
        int totalProfit = 0;
        for (Transaction t : bestTransactions) {
            System.out.printf("  %s\n", t);
            totalProfit += t.profit;
        }
        System.out.printf("總利潤：%d\n", totalProfit);
    }

    public static void main(String[] args) {
        System.out.println("=== 股票利潤最大化 ===");

        // 測試案例1：基本測試
        System.out.println("1. 基本測試案例：");
        int[] test1 = {2, 4, 1};
        int k1 = 2;

        System.out.printf("價格：%s，最多交易 %d 次\n", Arrays.toString(test1), k1);

        int result1_1 = maxProfitWithHeap(test1, k1);
        int result1_2 = maxProfitGreedyWithHeap(test1, k1);
        int result1_3 = maxProfitDP(test1, k1);

        System.out.printf("Heap 方法結果：%d\n", result1_1);
        System.out.printf("貪心+Heap 方法結果：%d\n", result1_2);
        System.out.printf("動態規劃結果：%d\n", result1_3);
        System.out.printf("期望結果：2\n");

        analyzeStock(test1, k1);

        // 測試案例2：複雜情況
        System.out.println("\n" + "=".repeat(50));
        System.out.println("2. 複雜情況測試：");
        int[] test2 = {3, 2, 6, 5, 0, 3};
        int k2 = 2;

        System.out.printf("價格：%s，最多交易 %d 次\n", Arrays.toString(test2), k2);

        int result2_1 = maxProfitWithHeap(test2, k2);
        int result2_2 = maxProfitGreedyWithHeap(test2, k2);
        int result2_3 = maxProfitDP(test2, k2);

        System.out.printf("Heap 方法結果：%d\n", result2_1);
        System.out.printf("貪心+Heap 方法結果：%d\n", result2_2);
        System.out.printf("動態規劃結果：%d\n", result2_3);
        System.out.printf("期望結果：7 (價格2買入價格6賣出得利潤4 + 價格0買入價格3賣出得利潤3)\n");

        analyzeStock(test2, k2);

        // 測試案例3：持續上漲
        System.out.println("\n3. 持續上漲測試：");
        int[] test3 = {1, 2, 3, 4, 5};
        int k3 = 2;

        System.out.printf("價格：%s，最多交易 %d 次\n", Arrays.toString(test3), k3);

        int result3_1 = maxProfitWithHeap(test3, k3);
        int result3_2 = maxProfitDP(test3, k3);

        System.out.printf("Heap 方法結果：%d\n", result3_1);
        System.out.printf("動態規劃結果：%d\n", result3_2);
        System.out.printf("期望結果：4 (一次交易：價格1買入價格5賣出)\n");

        // 測試案例4：K 值很大的情況
        System.out.println("\n4. K 值很大的情況：");
        int[] test4 = {1, 2, 4, 2, 5, 7, 2, 4, 9, 0};
        int k4 = 100; // K 很大，等同於無限交易

        System.out.printf("價格：%s，最多交易 %d 次\n", Arrays.toString(test4), k4);

        int result4_1 = maxProfitGreedyWithHeap(test4, k4);
        int result4_2 = maxProfitDP(test4, k4);
        int unlimited = maxProfitUnlimited(test4);

        System.out.printf("貪心+Heap 方法結果：%d\n", result4_1);
        System.out.printf("動態規劃結果：%d\n", result4_2);
        System.out.printf("無限交易結果：%d\n", unlimited);

        // 測試案例5：邊界情況
        System.out.println("\n5. 邊界情況測試：");

        // 空陣列
        int[] empty = {};
        System.out.printf("空陣列：%d\n", maxProfitDP(empty, 1));

        // 單個價格
        int[] single = {5};
        System.out.printf("單個價格：%d\n", maxProfitDP(single, 1));

        // 價格下跌
        int[] declining = {5, 4, 3, 2, 1};
        System.out.printf("持續下跌：%d\n", maxProfitDP(declining, 2));

        // K = 0
        int[] normal = {1, 3, 5};
        System.out.printf("K=0：%d\n", maxProfitDP(normal, 0));

        // 測試案例6：效能比較
        System.out.println("\n" + "=".repeat(50));
        System.out.println("6. 效能比較：");

        Random random = new Random(42);
        int[] largePrices = new int[1000];
        for (int i = 0; i < largePrices.length; i++) {
            largePrices[i] = random.nextInt(1000) + 1;
        }

        int[] testKs = {1, 5, 10, 50};

        for (int k : testKs) {
            System.out.printf("\nK = %d 的效能測試：\n", k);

            // 動態規劃方法
            long startTime = System.nanoTime();
            int dpResult = maxProfitDP(largePrices, k);
            long dpTime = System.nanoTime() - startTime;

            // 貪心+Heap 方法
            startTime = System.nanoTime();
            int greedyResult = maxProfitGreedyWithHeap(largePrices, k);
            long greedyTime = System.nanoTime() - startTime;

            System.out.printf("動態規劃：結果=%d，耗時=%.2f ms\n", dpResult, dpTime / 1_000_000.0);
            System.out.printf("貪心+Heap：結果=%d，耗時=%.2f ms\n", greedyResult, greedyTime / 1_000_000.0);
            System.out.printf("結果一致：%s\n", dpResult == greedyResult);
        }

        // 測試案例7：實際應用場景
        System.out.println("\n7. 實際應用場景模擬：");

        // 模擬一週的股票價格
        int[] weekPrices = {100, 105, 98, 110, 95, 120, 115};
        String[] days = {"週一", "週二", "週三", "週四", "週五", "週六", "週日"};

        System.out.println("一週股票價格：");
        for (int i = 0; i < weekPrices.length; i++) {
            System.out.printf("%s: $%d\n", days[i], weekPrices[i]);
        }

        for (int k = 1; k <= 3; k++) {
            System.out.printf("\n最多 %d 次交易的最大利潤：%d\n", k, maxProfitDP(weekPrices, k));
            analyzeStock(weekPrices, k);
        }

        // 測試案例8：算法複雜度說明
        System.out.println("\n8. 算法複雜度分析：");
        System.out.println("方法1 (Heap選擇)：");
        System.out.println("  時間複雜度：O(n² log n)");
        System.out.println("  空間複雜度：O(n²)");
        System.out.println("  適用：K 很小，需要詳細交易策略");

        System.out.println("\n方法2 (貪心+Heap)：");
        System.out.println("  時間複雜度：O(n + k log k)");
        System.out.println("  空間複雜度：O(n)");
        System.out.println("  適用：K 中等大小，快速近似解");

        System.out.println("\n方法3 (動態規劃)：");
        System.out.println("  時間複雜度：O(nk)");
        System.out.println("  空間複雜度：O(nk)");
        System.out.println("  適用：精確解，K 不太大");

        System.out.println("\n=== 股票利潤最大化測試完成 ===");
    }
}
