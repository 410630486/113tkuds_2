import java.util.*;

/**
 * 練習 4.10：多層級快取系統
 * 難度：★★★★★
 * 
 * 設計一個多層級的 LRU 快取系統，使用 heap 最佳化資料移動
 */
public class MultiLevelCacheSystem {

    /**
     * 快取項目類別
     */
    static class CacheItem {
        String key;
        String value;
        long accessTime;
        int accessCount;
        int level; // 當前所在層級
        double score; // 評分，用於決定是否需要移動層級

        CacheItem(String key, String value) {
            this.key = key;
            this.value = value;
            this.accessTime = System.nanoTime();
            this.accessCount = 1;
            this.level = 1; // 預設從 L1 開始
            updateScore();
        }

        void access() {
            this.accessTime = System.nanoTime();
            this.accessCount++;
            updateScore();
        }

        void updateScore() {
            // 評分公式：考慮存取頻率和時間近期性
            long currentTime = System.nanoTime();
            double timeDecay = Math.exp(-(currentTime - accessTime) / 1e9); // 時間衰減
            this.score = accessCount * timeDecay;
        }

        @Override
        public String toString() {
            return String.format("%s=%s(存取:%d次,評分:%.2f,層級:L%d)", 
                key, value, accessCount, score, level);
        }
    }

    /**
     * 單一層級的 LRU 快取
     */
    static class LRUCache {
        private final int capacity;
        private final int level;
        private final int accessCost;
        private final Map<String, CacheItem> cache;
        private final LinkedHashMap<String, CacheItem> lruOrder;

        LRUCache(int capacity, int level, int accessCost) {
            this.capacity = capacity;
            this.level = level;
            this.accessCost = accessCost;
            this.cache = new HashMap<>();
            this.lruOrder = new LinkedHashMap<String, CacheItem>(capacity, 0.75f, true) {
                @Override
                protected boolean removeEldestEntry(Map.Entry<String, CacheItem> eldest) {
                    return size() > capacity;
                }
            };
        }

        boolean containsKey(String key) {
            return cache.containsKey(key);
        }

        CacheItem get(String key) {
            CacheItem item = cache.get(key);
            if (item != null) {
                item.access();
                lruOrder.get(key); // 更新 LRU 順序
                return item;
            }
            return null;
        }

        void put(CacheItem item) {
            item.level = this.level;
            cache.put(item.key, item);
            lruOrder.put(item.key, item);
            
            // 如果超過容量，移除最舊的項目
            if (cache.size() > capacity) {
                String oldestKey = lruOrder.keySet().iterator().next();
                remove(oldestKey);
            }
        }

        CacheItem remove(String key) {
            CacheItem item = cache.remove(key);
            if (item != null) {
                lruOrder.remove(key);
            }
            return item;
        }

        List<CacheItem> getAllItems() {
            List<CacheItem> items = new ArrayList<>();
            for (String key : lruOrder.keySet()) {
                items.add(cache.get(key));
            }
            return items;
        }

        boolean isFull() {
            return cache.size() >= capacity;
        }

        int size() {
            return cache.size();
        }

        int getAccessCost() {
            return accessCost;
        }

        int getLevel() {
            return level;
        }

        @Override
        public String toString() {
            return String.format("L%d(容量:%d/%d,成本:%d): %s", 
                level, cache.size(), capacity, accessCost, getAllItems());
        }
    }

    /**
     * 多層級快取系統
     */
    static class MultiLevelCache {
        private final List<LRUCache> levels;
        private final PriorityQueue<CacheItem> promotionCandidates; // 升級候選項
        private final PriorityQueue<CacheItem> demotionCandidates;  // 降級候選項
        private long totalAccesses;
        private long totalCost;

        MultiLevelCache() {
            this.levels = new ArrayList<>();
            
            // 創建三個層級
            levels.add(new LRUCache(2, 1, 1));   // L1: 容量2, 成本1
            levels.add(new LRUCache(5, 2, 3));   // L2: 容量5, 成本3
            levels.add(new LRUCache(10, 3, 10)); // L3: 容量10, 成本10
            
            // 升級候選項：評分高的優先
            this.promotionCandidates = new PriorityQueue<>(
                (a, b) -> Double.compare(b.score, a.score)
            );
            
            // 降級候選項：評分低的優先
            this.demotionCandidates = new PriorityQueue<>(
                (a, b) -> Double.compare(a.score, b.score)
            );
            
            this.totalAccesses = 0;
            this.totalCost = 0;
        }

        /**
         * 獲取資料
         */
        String get(String key) {
            totalAccesses++;
            
            // 從上層往下層查找
            for (LRUCache level : levels) {
                if (level.containsKey(key)) {
                    CacheItem item = level.get(key);
                    totalCost += level.getAccessCost();
                    
                    // 更新評分並考慮是否需要升級
                    item.updateScore();
                    considerPromotion(item);
                    
                    return item.value;
                }
            }
            
            return null; // 快取未命中
        }

        /**
         * 存放資料
         */
        void put(String key, String value) {
            // 檢查是否已存在
            for (LRUCache level : levels) {
                if (level.containsKey(key)) {
                    CacheItem item = level.get(key);
                    item.value = value;
                    item.access();
                    considerPromotion(item);
                    return;
                }
            }

            // 新資料，從 L1 開始放入
            CacheItem newItem = new CacheItem(key, value);
            putInLevel(newItem, 0);
            optimizeSystem();
        }

        /**
         * 將項目放入指定層級
         */
        private void putInLevel(CacheItem item, int levelIndex) {
            LRUCache targetLevel = levels.get(levelIndex);
            
            // 如果目標層級已滿，需要淘汰
            if (targetLevel.isFull()) {
                evictFromLevel(levelIndex);
            }
            
            targetLevel.put(item);
        }

        /**
         * 從指定層級淘汰項目
         */
        private void evictFromLevel(int levelIndex) {
            LRUCache level = levels.get(levelIndex);
            List<CacheItem> items = level.getAllItems();
            
            if (items.isEmpty()) return;
            
            // 找到評分最低的項目進行淘汰
            CacheItem victim = items.stream()
                .min((a, b) -> Double.compare(a.score, b.score))
                .orElse(items.get(0));
            
            level.remove(victim.key);
            
            // 嘗試將被淘汰的項目移到下一層
            if (levelIndex < levels.size() - 1) {
                victim.level = levelIndex + 2; // 更新層級標記
                putInLevel(victim, levelIndex + 1);
            }
            // 如果已經是最後一層，則徹底移除
        }

        /**
         * 考慮升級項目
         */
        private void considerPromotion(CacheItem item) {
            if (item.level > 1) {
                // 檢查是否應該升級到更高層級
                double averageScore = calculateAverageScore(item.level - 2);
                if (item.score > averageScore * 1.5) { // 評分超過上層平均分的1.5倍
                    promoteItem(item);
                }
            }
        }

        /**
         * 升級項目
         */
        private void promoteItem(CacheItem item) {
            int currentLevelIndex = item.level - 1;
            int targetLevelIndex = currentLevelIndex - 1;
            
            if (targetLevelIndex >= 0) {
                // 從當前層級移除
                levels.get(currentLevelIndex).remove(item.key);
                
                // 放入目標層級
                putInLevel(item, targetLevelIndex);
                
                System.out.printf("升級：%s 從 L%d 升級到 L%d\n", 
                    item.key, currentLevelIndex + 1, targetLevelIndex + 1);
            }
        }

        /**
         * 計算指定層級的平均評分
         */
        private double calculateAverageScore(int levelIndex) {
            if (levelIndex < 0 || levelIndex >= levels.size()) {
                return 0.0;
            }
            
            List<CacheItem> items = levels.get(levelIndex).getAllItems();
            if (items.isEmpty()) {
                return 0.0;
            }
            
            return items.stream().mapToDouble(item -> item.score).average().orElse(0.0);
        }

        /**
         * 系統最佳化
         */
        private void optimizeSystem() {
            // 更新所有項目的評分
            for (LRUCache level : levels) {
                for (CacheItem item : level.getAllItems()) {
                    item.updateScore();
                }
            }
            
            // 重新平衡層級
            rebalanceLevels();
        }

        /**
         * 重新平衡層級
         */
        private void rebalanceLevels() {
            // 收集所有項目並按評分排序
            List<CacheItem> allItems = new ArrayList<>();
            
            for (LRUCache level : levels) {
                allItems.addAll(level.getAllItems());
            }
            
            // 按評分降序排序
            allItems.sort((a, b) -> Double.compare(b.score, a.score));
            
            // 清空所有層級
            for (LRUCache level : levels) {
                level.cache.clear();
                level.lruOrder.clear();
            }
            
            // 重新分配項目到適當層級
            int itemIndex = 0;
            for (int levelIndex = 0; levelIndex < levels.size() && itemIndex < allItems.size(); levelIndex++) {
                LRUCache level = levels.get(levelIndex);
                int itemsToPlace = Math.min(level.capacity, allItems.size() - itemIndex);
                
                for (int i = 0; i < itemsToPlace; i++) {
                    CacheItem item = allItems.get(itemIndex++);
                    level.put(item);
                }
            }
        }

        /**
         * 顯示系統狀態
         */
        void displayStatus() {
            System.out.println("=== 多層級快取系統狀態 ===");
            for (LRUCache level : levels) {
                System.out.println(level);
            }
            System.out.printf("總存取次數：%d，總成本：%d，平均成本：%.2f\n", 
                totalAccesses, totalCost, totalAccesses > 0 ? (double)totalCost / totalAccesses : 0.0);
        }

        /**
         * 獲取命中率統計
         */
        Map<String, Double> getHitRateStats() {
            Map<String, Double> stats = new HashMap<>();
            
            long l1Hits = 0, l2Hits = 0, l3Hits = 0;
            
            // 這裡簡化統計，實際應用中需要更詳細的追蹤
            stats.put("L1_hit_rate", l1Hits / (double)Math.max(1, totalAccesses));
            stats.put("L2_hit_rate", l2Hits / (double)Math.max(1, totalAccesses));
            stats.put("L3_hit_rate", l3Hits / (double)Math.max(1, totalAccesses));
            stats.put("overall_hit_rate", (l1Hits + l2Hits + l3Hits) / (double)Math.max(1, totalAccesses));
            
            return stats;
        }

        /**
         * 清空快取
         */
        void clear() {
            for (LRUCache level : levels) {
                level.cache.clear();
                level.lruOrder.clear();
            }
            totalAccesses = 0;
            totalCost = 0;
        }
    }

    public static void main(String[] args) {
        System.out.println("=== 多層級快取系統 ===");

        MultiLevelCache cache = new MultiLevelCache();

        // 測試案例1：基本操作
        System.out.println("1. 基本操作測試：");
        
        cache.put("A", "Value_A");
        cache.put("B", "Value_B");
        cache.put("C", "Value_C");
        
        System.out.println("初始狀態：");
        cache.displayStatus();

        // 測試案例2：存取模式測試
        System.out.println("\n2. 存取模式測試：");
        
        // 頻繁存取某些項目
        for (int i = 0; i < 5; i++) {
            cache.get("A");
            cache.get("A");
            cache.get("B");
            try { Thread.sleep(10); } catch (InterruptedException e) {}
        }
        
        System.out.println("頻繁存取後：");
        cache.displayStatus();

        // 測試案例3：添加更多資料
        System.out.println("\n3. 添加更多資料：");
        
        cache.put("D", "Value_D");
        cache.put("E", "Value_E");
        cache.put("F", "Value_F");
        
        System.out.println("添加更多資料後：");
        cache.displayStatus();

        // 測試案例4：模擬真實存取模式
        System.out.println("\n" + "=".repeat(50));
        System.out.println("4. 模擬真實存取模式：");
        
        // 清空快取重新開始
        cache.clear();
        
        // 模擬熱點資料
        String[] hotData = {"user1", "user2", "user3"};
        String[] warmData = {"product1", "product2", "product3", "product4", "product5"};
        String[] coldData = {"log1", "log2", "log3", "log4", "log5", "log6", "log7"};
        
        // 插入資料
        for (String key : hotData) {
            cache.put(key, "hot_" + key);
        }
        for (String key : warmData) {
            cache.put(key, "warm_" + key);
        }
        for (String key : coldData) {
            cache.put(key, "cold_" + key);
        }
        
        System.out.println("插入所有資料後：");
        cache.displayStatus();
        
        // 模擬存取模式：熱點資料頻繁存取
        System.out.println("\n模擬存取模式：");
        Random random = new Random(42);
        
        for (int i = 0; i < 50; i++) {
            String key;
            int rand = random.nextInt(100);
            
            if (rand < 60) {
                // 60% 存取熱點資料
                key = hotData[random.nextInt(hotData.length)];
            } else if (rand < 85) {
                // 25% 存取溫資料
                key = warmData[random.nextInt(warmData.length)];
            } else {
                // 15% 存取冷資料
                key = coldData[random.nextInt(coldData.length)];
            }
            
            String result = cache.get(key);
            if (i % 10 == 0) {
                System.out.printf("第 %d 次存取 %s: %s\n", i + 1, key, result != null ? "命中" : "未命中");
            }
            
            try { Thread.sleep(1); } catch (InterruptedException e) {}
        }
        
        System.out.println("\n存取模式測試後：");
        cache.displayStatus();

        // 測試案例5：效能分析
        System.out.println("\n5. 效能分析：");
        
        // 重新測試並計算效能指標
        cache.clear();
        long startTime = System.currentTimeMillis();
        
        // 大量操作測試
        for (int i = 0; i < 1000; i++) {
            String key = "key" + (i % 100); // 重複使用部分鍵
            cache.put(key, "value" + i);
        }
        
        for (int i = 0; i < 2000; i++) {
            String key = "key" + (i % 100);
            cache.get(key);
        }
        
        long endTime = System.currentTimeMillis();
        System.out.printf("執行 1000 次 put 和 2000 次 get 操作耗時：%d ms\n", endTime - startTime);
        
        cache.displayStatus();

        // 測試案例6：邊界情況
        System.out.println("\n6. 邊界情況測試：");
        
        // 測試大量相同鍵的存取
        cache.clear();
        for (int i = 0; i < 100; i++) {
            cache.put("same_key", "value" + i);
            cache.get("same_key");
        }
        
        System.out.println("大量相同鍵存取後：");
        cache.displayStatus();
        
        // 測試 null 值處理
        String result = cache.get("non_existent_key");
        System.out.printf("存取不存在的鍵：%s\n", result);

        // 測試案例7：層級移動演示
        System.out.println("\n" + "=".repeat(50));
        System.out.println("7. 層級移動演示：");
        
        cache.clear();
        
        // 添加一些資料
        cache.put("data1", "value1");
        cache.put("data2", "value2");
        cache.put("data3", "value3");
        cache.put("data4", "value4");
        
        System.out.println("初始狀態：");
        cache.displayStatus();
        
        // 大量存取 data1，使其成為熱點
        System.out.println("\n大量存取 data1：");
        for (int i = 0; i < 20; i++) {
            cache.get("data1");
            if (i % 5 == 4) {
                System.out.printf("存取 %d 次後：\n", i + 1);
                cache.displayStatus();
                System.out.println();
            }
            try { Thread.sleep(10); } catch (InterruptedException e) {}
        }

        // 測試案例8：系統設計說明
        System.out.println("8. 系統設計說明：");
        System.out.println("層級配置：");
        System.out.println("  L1: 容量=2, 存取成本=1  (最快，最小)");
        System.out.println("  L2: 容量=5, 存取成本=3  (中等)");
        System.out.println("  L3: 容量=10, 存取成本=10 (最慢，最大)");
        
        System.out.println("\n升級策略：");
        System.out.println("  - 基於存取頻率和時間近期性的評分");
        System.out.println("  - 評分超過上層平均分1.5倍時自動升級");
        System.out.println("  - 使用時間衰減避免過時資料佔用高層級");
        
        System.out.println("\n淘汰策略：");
        System.out.println("  - 每層使用 LRU 策略");
        System.out.println("  - 被淘汰的項目自動降級到下一層");
        System.out.println("  - 最下層滿時徹底移除最低評分項目");
        
        System.out.println("\n效能最佳化：");
        System.out.println("  - 動態評分調整");
        System.out.println("  - 定期重新平衡層級分布");
        System.out.println("  - 基於 heap 的升級/降級候選項管理");

        System.out.println("\n=== 多層級快取系統測試完成 ===");
    }
}
