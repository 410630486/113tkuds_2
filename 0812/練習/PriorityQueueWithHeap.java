import java.util.*;

/**
 * 練習 4.3：使用 Heap 實作優先級佇列
 * 難度：★★☆☆☆
 * 
 * 功能：
 * - addTask(String name, int priority)：添加任務
 * - executeNext()：執行優先級最高的任務
 * - peek()：查看下一個要執行的任務
 * - changePriority(String name, int newPriority)：修改任務優先級
 */
public class PriorityQueueWithHeap {

    /**
     * 任務類別
     */
    static class Task {
        String name;
        int priority;
        long timestamp; // 用於處理相同優先級的情況

        Task(String name, int priority) {
            this.name = name;
            this.priority = priority;
            this.timestamp = System.nanoTime(); // 較早加入的任務優先執行
        }

        @Override
        public String toString() {
            return String.format("%s(優先級:%d)", name, priority);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Task task = (Task) obj;
            return Objects.equals(name, task.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }
    }

    /**
     * 優先級任務佇列類別
     */
    static class PriorityTaskQueue {
        private PriorityQueue<Task> heap;
        private Map<String, Task> taskMap; // 用於快速查找任務

        public PriorityTaskQueue() {
            // 優先級高的任務先執行，如果優先級相同則較早加入的先執行
            this.heap = new PriorityQueue<>((a, b) -> {
                if (a.priority != b.priority) {
                    return Integer.compare(b.priority, a.priority); // 優先級高的排前面
                }
                return Long.compare(a.timestamp, b.timestamp); // 時間早的排前面
            });
            this.taskMap = new HashMap<>();
        }

        /**
         * 添加任務
         */
        public void addTask(String name, int priority) {
            if (taskMap.containsKey(name)) {
                System.out.printf("任務 '%s' 已存在，請使用 changePriority 修改優先級\n", name);
                return;
            }

            Task task = new Task(name, priority);
            heap.offer(task);
            taskMap.put(name, task);
            
            System.out.printf("添加任務：%s\n", task);
        }

        /**
         * 執行下一個任務
         */
        public Task executeNext() {
            if (heap.isEmpty()) {
                System.out.println("沒有待執行的任務");
                return null;
            }

            Task task = heap.poll();
            taskMap.remove(task.name);
            
            System.out.printf("執行任務：%s\n", task);
            return task;
        }

        /**
         * 查看下一個要執行的任務
         */
        public Task peek() {
            if (heap.isEmpty()) {
                return null;
            }
            return heap.peek();
        }

        /**
         * 修改任務優先級
         */
        public boolean changePriority(String name, int newPriority) {
            Task existingTask = taskMap.get(name);
            if (existingTask == null) {
                System.out.printf("任務 '%s' 不存在\n", name);
                return false;
            }

            // 從堆中移除舊任務
            heap.remove(existingTask);
            taskMap.remove(name);

            // 創建新任務並添加
            Task newTask = new Task(name, newPriority);
            heap.offer(newTask);
            taskMap.put(name, newTask);

            System.out.printf("任務 '%s' 優先級從 %d 更改為 %d\n", name, existingTask.priority, newPriority);
            return true;
        }

        /**
         * 獲取任務數量
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
         * 獲取所有任務的列表（按優先級排序）
         */
        public List<Task> getAllTasks() {
            List<Task> tasks = new ArrayList<>(heap);
            tasks.sort((a, b) -> {
                if (a.priority != b.priority) {
                    return Integer.compare(b.priority, a.priority);
                }
                return Long.compare(a.timestamp, b.timestamp);
            });
            return tasks;
        }

        /**
         * 檢查任務是否存在
         */
        public boolean containsTask(String name) {
            return taskMap.containsKey(name);
        }

        /**
         * 獲取任務的優先級
         */
        public Integer getTaskPriority(String name) {
            Task task = taskMap.get(name);
            return task != null ? task.priority : null;
        }

        /**
         * 清空所有任務
         */
        public void clear() {
            heap.clear();
            taskMap.clear();
            System.out.println("已清空所有任務");
        }

        /**
         * 顯示當前任務佇列狀態
         */
        public void displayQueue() {
            if (heap.isEmpty()) {
                System.out.println("任務佇列為空");
                return;
            }

            System.out.println("當前任務佇列（按優先級排序）：");
            List<Task> tasks = getAllTasks();
            for (int i = 0; i < tasks.size(); i++) {
                Task task = tasks.get(i);
                System.out.printf("  %d. %s\n", i + 1, task);
            }
        }

        /**
         * 批量添加任務
         */
        public void addTasks(String[] names, int[] priorities) {
            if (names.length != priorities.length) {
                System.out.println("任務名稱和優先級陣列長度不匹配");
                return;
            }

            for (int i = 0; i < names.length; i++) {
                addTask(names[i], priorities[i]);
            }
        }

        /**
         * 執行所有任務
         */
        public List<Task> executeAllTasks() {
            List<Task> executedTasks = new ArrayList<>();
            
            System.out.println("\n開始執行所有任務：");
            while (!isEmpty()) {
                Task task = executeNext();
                if (task != null) {
                    executedTasks.add(task);
                }
            }
            
            return executedTasks;
        }
    }

    /**
     * 自定義 Max Heap 實作（用於展示原理）
     */
    static class CustomMaxHeap {
        private List<Task> heap;

        public CustomMaxHeap() {
            this.heap = new ArrayList<>();
        }

        public void insert(Task task) {
            heap.add(task);
            heapifyUp(heap.size() - 1);
        }

        public Task extractMax() {
            if (heap.isEmpty()) {
                return null;
            }

            Task max = heap.get(0);
            Task lastElement = heap.get(heap.size() - 1);
            heap.set(0, lastElement);
            heap.remove(heap.size() - 1);

            if (!heap.isEmpty()) {
                heapifyDown(0);
            }

            return max;
        }

        public Task peek() {
            return heap.isEmpty() ? null : heap.get(0);
        }

        public boolean isEmpty() {
            return heap.isEmpty();
        }

        public int size() {
            return heap.size();
        }

        private void heapifyUp(int index) {
            while (index > 0) {
                int parentIndex = (index - 1) / 2;
                
                if (compare(heap.get(index), heap.get(parentIndex)) > 0) {
                    swap(index, parentIndex);
                    index = parentIndex;
                } else {
                    break;
                }
            }
        }

        private void heapifyDown(int index) {
            while (true) {
                int leftChild = 2 * index + 1;
                int rightChild = 2 * index + 2;
                int largest = index;

                if (leftChild < heap.size() && compare(heap.get(leftChild), heap.get(largest)) > 0) {
                    largest = leftChild;
                }

                if (rightChild < heap.size() && compare(heap.get(rightChild), heap.get(largest)) > 0) {
                    largest = rightChild;
                }

                if (largest == index) {
                    break;
                }

                swap(index, largest);
                index = largest;
            }
        }

        private void swap(int i, int j) {
            Task temp = heap.get(i);
            heap.set(i, heap.get(j));
            heap.set(j, temp);
        }

        private int compare(Task a, Task b) {
            if (a.priority != b.priority) {
                return Integer.compare(a.priority, b.priority);
            }
            return Long.compare(b.timestamp, a.timestamp); // 時間早的優先
        }
    }

    public static void main(String[] args) {
        System.out.println("=== 優先級任務佇列系統 ===");

        PriorityTaskQueue taskQueue = new PriorityTaskQueue();

        // 測試案例1：基本操作
        System.out.println("1. 基本操作測試：");
        taskQueue.addTask("備份", 1);
        taskQueue.addTask("緊急修復", 5);
        taskQueue.addTask("更新", 3);

        System.out.println("\n當前佇列狀態：");
        taskQueue.displayQueue();

        System.out.printf("\n下一個要執行的任務：%s\n", taskQueue.peek());

        // 測試案例2：執行任務
        System.out.println("\n2. 執行任務測試：");
        System.out.println("預期執行順序：緊急修復 → 更新 → 備份");
        System.out.println("實際執行順序：");

        List<Task> executedOrder = new ArrayList<>();
        while (!taskQueue.isEmpty()) {
            Task executed = taskQueue.executeNext();
            if (executed != null) {
                executedOrder.add(executed);
            }
        }

        System.out.println("執行完成，最終順序：" + 
            executedOrder.stream().map(t -> t.name).toArray());

        // 測試案例3：優先級修改
        System.out.println("\n" + "=".repeat(50));
        System.out.println("3. 優先級修改測試：");

        taskQueue.addTask("任務A", 2);
        taskQueue.addTask("任務B", 4);
        taskQueue.addTask("任務C", 1);
        taskQueue.addTask("任務D", 3);

        System.out.println("\n修改前的佇列：");
        taskQueue.displayQueue();

        // 修改任務C的優先級
        taskQueue.changePriority("任務C", 5);

        System.out.println("\n修改任務C優先級為5後的佇列：");
        taskQueue.displayQueue();

        // 測試案例4：相同優先級
        System.out.println("\n4. 相同優先級測試（時間戳記排序）：");
        
        PriorityTaskQueue sameQueue = new PriorityTaskQueue();
        
        // 添加相同優先級的任務
        sameQueue.addTask("第一個任務", 3);
        try { Thread.sleep(1); } catch (InterruptedException e) {}
        sameQueue.addTask("第二個任務", 3);
        try { Thread.sleep(1); } catch (InterruptedException e) {}
        sameQueue.addTask("第三個任務", 3);

        System.out.println("相同優先級任務的執行順序：");
        while (!sameQueue.isEmpty()) {
            sameQueue.executeNext();
        }

        // 測試案例5：批量操作
        System.out.println("\n" + "=".repeat(50));
        System.out.println("5. 批量操作測試：");

        PriorityTaskQueue batchQueue = new PriorityTaskQueue();
        
        String[] taskNames = {"數據庫備份", "系統監控", "用戶反饋處理", "性能優化", "安全掃描"};
        int[] priorities = {2, 4, 3, 1, 5};

        batchQueue.addTasks(taskNames, priorities);
        
        System.out.println("\n批量添加後的佇列：");
        batchQueue.displayQueue();

        System.out.println("\n執行所有任務：");
        batchQueue.executeAllTasks();

        // 測試案例6：錯誤處理
        System.out.println("\n6. 錯誤處理測試：");

        PriorityTaskQueue errorQueue = new PriorityTaskQueue();
        
        // 嘗試執行空佇列
        errorQueue.executeNext();
        
        // 嘗試修改不存在的任務
        errorQueue.changePriority("不存在的任務", 5);
        
        // 嘗試添加重複任務
        errorQueue.addTask("測試任務", 3);
        errorQueue.addTask("測試任務", 4);

        // 測試案例7：自定義 Heap 實作對比
        System.out.println("\n" + "=".repeat(50));
        System.out.println("7. 自定義 Heap 實作對比：");

        CustomMaxHeap customHeap = new CustomMaxHeap();
        PriorityTaskQueue standardQueue = new PriorityTaskQueue();

        // 添加相同的任務到兩個實作
        String[] testTasks = {"低優先級", "高優先級", "中優先級"};
        int[] testPriorities = {1, 5, 3};

        for (int i = 0; i < testTasks.length; i++) {
            Task task = new Task(testTasks[i], testPriorities[i]);
            customHeap.insert(task);
            standardQueue.addTask(testTasks[i], testPriorities[i]);
        }

        System.out.println("自定義 Heap 執行順序：");
        while (!customHeap.isEmpty()) {
            System.out.println("  " + customHeap.extractMax());
        }

        System.out.println("標準 PriorityQueue 執行順序：");
        while (!standardQueue.isEmpty()) {
            System.out.println("  " + standardQueue.executeNext());
        }

        // 測試案例8：性能測試
        System.out.println("\n8. 性能測試：");
        
        PriorityTaskQueue perfQueue = new PriorityTaskQueue();
        Random random = new Random(42);
        
        long startTime = System.currentTimeMillis();
        
        // 添加大量任務
        for (int i = 0; i < 10000; i++) {
            perfQueue.addTask("任務" + i, random.nextInt(100));
        }
        
        long addTime = System.currentTimeMillis();
        
        // 執行所有任務
        int executed = 0;
        while (!perfQueue.isEmpty()) {
            perfQueue.executeNext();
            executed++;
        }
        
        long endTime = System.currentTimeMillis();
        
        System.out.printf("添加10000個任務耗時：%d ms\n", addTime - startTime);
        System.out.printf("執行%d個任務耗時：%d ms\n", executed, endTime - addTime);
        System.out.printf("總耗時：%d ms\n", endTime - startTime);

        System.out.println("\n=== 優先級任務佇列系統測試完成 ===");
    }
}
