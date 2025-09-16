import java.util.*;

/**
 * 練習 4.8：會議室排程最佳化
 * 難度：★★★★☆
 * 
 * 使用 heap 解決會議室調度問題
 */
public class MeetingRoomScheduler {

    /**
     * 會議類別
     */
    static class Meeting {
        int start;
        int end;
        String name;
        int priority; // 優先級，數字越大越重要

        Meeting(int start, int end) {
            this(start, end, "會議" + (start + "-" + end), 1);
        }

        Meeting(int start, int end, String name) {
            this(start, end, name, 1);
        }

        Meeting(int start, int end, String name, int priority) {
            this.start = start;
            this.end = end;
            this.name = name;
            this.priority = priority;
        }

        public int duration() {
            return end - start;
        }

        @Override
        public String toString() {
            return String.format("%s[%d-%d](時長:%d,優先級:%d)", name, start, end, duration(), priority);
        }
    }

    /**
     * 方法1：計算最少需要的會議室數量
     * 使用 Min Heap 追蹤會議室的結束時間
     */
    public static int minMeetingRooms(int[][] intervals) {
        if (intervals == null || intervals.length == 0) {
            return 0;
        }

        // 按開始時間排序
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));

        // Min Heap 儲存會議室的結束時間
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();

        for (int[] interval : intervals) {
            // 如果有會議室可用（最早結束的會議室結束時間 <= 當前會議開始時間）
            if (!minHeap.isEmpty() && minHeap.peek() <= interval[0]) {
                minHeap.poll(); // 移除已結束的會議
            }
            
            // 安排當前會議到會議室
            minHeap.offer(interval[1]);
        }

        return minHeap.size();
    }

    /**
     * 方法2：計算最少會議室數量（詳細版本）
     */
    public static int minMeetingRoomsDetailed(Meeting[] meetings, boolean showProcess) {
        if (meetings == null || meetings.length == 0) {
            return 0;
        }

        // 按開始時間排序
        Arrays.sort(meetings, (a, b) -> Integer.compare(a.start, b.start));

        // Min Heap 儲存會議室的結束時間
        PriorityQueue<Integer> roomEndTimes = new PriorityQueue<>();
        List<List<Meeting>> roomSchedules = new ArrayList<>();

        if (showProcess) {
            System.out.println("會議安排過程：");
        }

        for (Meeting meeting : meetings) {
            int roomIndex;
            
            // 檢查是否有可用的會議室
            if (!roomEndTimes.isEmpty() && roomEndTimes.peek() <= meeting.start) {
                // 有可用會議室
                int endTime = roomEndTimes.poll();
                roomIndex = findRoomByEndTime(roomSchedules, endTime);
                
                if (showProcess) {
                    System.out.printf("安排 %s 到會議室 %d (重用)\n", meeting, roomIndex + 1);
                }
            } else {
                // 需要新會議室
                roomIndex = roomSchedules.size();
                roomSchedules.add(new ArrayList<>());
                
                if (showProcess) {
                    System.out.printf("安排 %s 到會議室 %d (新建)\n", meeting, roomIndex + 1);
                }
            }
            
            // 更新會議室安排
            roomSchedules.get(roomIndex).add(meeting);
            roomEndTimes.offer(meeting.end);
        }

        if (showProcess) {
            System.out.println("\n最終會議室安排：");
            for (int i = 0; i < roomSchedules.size(); i++) {
                System.out.printf("會議室 %d: %s\n", i + 1, roomSchedules.get(i));
            }
        }

        return roomSchedules.size();
    }

    private static int findRoomByEndTime(List<List<Meeting>> roomSchedules, int endTime) {
        for (int i = 0; i < roomSchedules.size(); i++) {
            List<Meeting> schedule = roomSchedules.get(i);
            if (!schedule.isEmpty() && schedule.get(schedule.size() - 1).end == endTime) {
                return i;
            }
        }
        return -1; // 應該不會發生
    }

    /**
     * 方法3：有限會議室的最大化總會議時間
     * 使用動態規劃 + 貪心 + Heap
     */
    public static int maxMeetingTime(Meeting[] meetings, int numRooms) {
        if (meetings == null || meetings.length == 0 || numRooms <= 0) {
            return 0;
        }

        // 按結束時間排序（貪心策略）
        Arrays.sort(meetings, (a, b) -> {
            if (a.end != b.end) {
                return Integer.compare(a.end, b.end);
            }
            return Integer.compare(b.duration(), a.duration()); // 相同結束時間優先選較長的
        });

        // 使用 Min Heap 追蹤每個會議室的結束時間
        PriorityQueue<Integer> roomEndTimes = new PriorityQueue<>();
        List<Meeting> selectedMeetings = new ArrayList<>();

        for (Meeting meeting : meetings) {
            boolean canSchedule = false;
            
            if (roomEndTimes.size() < numRooms) {
                // 有空閒會議室
                canSchedule = true;
            } else if (roomEndTimes.peek() <= meeting.start) {
                // 最早結束的會議室可以使用
                roomEndTimes.poll();
                canSchedule = true;
            }
            
            if (canSchedule) {
                roomEndTimes.offer(meeting.end);
                selectedMeetings.add(meeting);
            }
        }

        return selectedMeetings.stream().mapToInt(Meeting::duration).sum();
    }

    /**
     * 方法4：考慮優先級的會議調度
     */
    public static List<Meeting> scheduleWithPriority(Meeting[] meetings, int numRooms) {
        if (meetings == null || meetings.length == 0 || numRooms <= 0) {
            return new ArrayList<>();
        }

        // 按優先級和持續時間排序
        Arrays.sort(meetings, (a, b) -> {
            if (a.priority != b.priority) {
                return Integer.compare(b.priority, a.priority); // 優先級高的優先
            }
            return Integer.compare(a.end, b.end); // 相同優先級早結束的優先
        });

        // 會議室結束時間
        PriorityQueue<Integer> roomEndTimes = new PriorityQueue<>();
        List<Meeting> scheduled = new ArrayList<>();

        for (Meeting meeting : meetings) {
            boolean canSchedule = false;
            
            if (roomEndTimes.size() < numRooms) {
                canSchedule = true;
            } else if (roomEndTimes.peek() <= meeting.start) {
                roomEndTimes.poll();
                canSchedule = true;
            }
            
            if (canSchedule) {
                roomEndTimes.offer(meeting.end);
                scheduled.add(meeting);
            }
        }

        return scheduled;
    }

    /**
     * 方法5：使用事件排序的方法
     */
    public static int minMeetingRoomsEventSort(int[][] intervals) {
        if (intervals == null || intervals.length == 0) {
            return 0;
        }

        // 創建事件列表
        List<int[]> events = new ArrayList<>();
        for (int[] interval : intervals) {
            events.add(new int[]{interval[0], 1});  // 開始事件
            events.add(new int[]{interval[1], -1}); // 結束事件
        }

        // 按時間排序，如果時間相同，結束事件優先
        events.sort((a, b) -> {
            if (a[0] != b[0]) {
                return Integer.compare(a[0], b[0]);
            }
            return Integer.compare(a[1], b[1]);
        });

        int currentRooms = 0;
        int maxRooms = 0;

        for (int[] event : events) {
            currentRooms += event[1];
            maxRooms = Math.max(maxRooms, currentRooms);
        }

        return maxRooms;
    }

    /**
     * 生成隨機會議
     */
    public static Meeting[] generateRandomMeetings(int count, int maxTime) {
        Random random = new Random(42);
        Meeting[] meetings = new Meeting[count];
        
        for (int i = 0; i < count; i++) {
            int start = random.nextInt(maxTime - 1);
            int end = start + 1 + random.nextInt(maxTime - start - 1);
            int priority = random.nextInt(5) + 1;
            meetings[i] = new Meeting(start, end, "會議" + (i + 1), priority);
        }
        
        return meetings;
    }

    /**
     * 效能測試
     */
    public static void performanceTest(int numMeetings, int maxTime) {
        System.out.printf("效能測試：%d 個會議，時間範圍 0-%d\n", numMeetings, maxTime);
        
        // 生成測試數據
        int[][] intervals = new int[numMeetings][2];
        Random random = new Random(42);
        
        for (int i = 0; i < numMeetings; i++) {
            int start = random.nextInt(maxTime - 1);
            int end = start + 1 + random.nextInt(maxTime - start - 1);
            intervals[i] = new int[]{start, end};
        }

        // 測試 Heap 方法
        long startTime = System.nanoTime();
        int result1 = minMeetingRooms(intervals);
        long heapTime = System.nanoTime() - startTime;

        // 測試事件排序方法
        startTime = System.nanoTime();
        int result2 = minMeetingRoomsEventSort(intervals);
        long eventTime = System.nanoTime() - startTime;

        System.out.printf("Heap 方法：%d 個會議室，耗時 %.2f ms\n", result1, heapTime / 1_000_000.0);
        System.out.printf("事件排序方法：%d 個會議室，耗時 %.2f ms\n", result2, eventTime / 1_000_000.0);
        System.out.printf("結果一致：%s\n\n", result1 == result2);
    }

    public static void main(String[] args) {
        System.out.println("=== 會議室排程最佳化 ===");

        // 測試案例1：基本會議室需求
        System.out.println("1. 基本會議室需求測試：");
        int[][] test1 = {{0, 30}, {5, 10}, {15, 20}};
        
        System.out.printf("會議時間：%s\n", Arrays.deepToString(test1));
        int rooms1 = minMeetingRooms(test1);
        System.out.printf("最少需要會議室數量：%d\n", rooms1);
        System.out.printf("期望：2\n");
        
        // 詳細過程
        Meeting[] meetings1 = {
            new Meeting(0, 30, "會議A"),
            new Meeting(5, 10, "會議B"),
            new Meeting(15, 20, "會議C")
        };
        minMeetingRoomsDetailed(meetings1, true);

        // 測試案例2：更複雜的情況
        System.out.println("\n" + "=".repeat(50));
        System.out.println("2. 複雜情況測試：");
        int[][] test2 = {{9, 10}, {4, 9}, {4, 17}};
        
        System.out.printf("會議時間：%s\n", Arrays.deepToString(test2));
        int rooms2 = minMeetingRooms(test2);
        System.out.printf("最少需要會議室數量：%d\n", rooms2);
        
        Meeting[] meetings2 = {
            new Meeting(9, 10, "短會議"),
            new Meeting(4, 9, "中會議"),
            new Meeting(4, 17, "長會議")
        };
        minMeetingRoomsDetailed(meetings2, true);

        // 測試案例3：有限會議室的最大化
        System.out.println("\n3. 有限會議室最大化測試：");
        Meeting[] meetings3 = {
            new Meeting(1, 4, "會議1"),
            new Meeting(2, 3, "會議2"),
            new Meeting(4, 6, "會議3"),
            new Meeting(5, 7, "會議4")
        };
        
        System.out.println("所有會議：");
        for (Meeting m : meetings3) {
            System.out.printf("  %s\n", m);
        }
        
        for (int numRooms = 1; numRooms <= 3; numRooms++) {
            int maxTime = maxMeetingTime(meetings3, numRooms);
            System.out.printf("使用 %d 個會議室的最大總會議時間：%d\n", numRooms, maxTime);
        }

        // 測試案例4：優先級調度
        System.out.println("\n" + "=".repeat(50));
        System.out.println("4. 優先級調度測試：");
        Meeting[] priorityMeetings = {
            new Meeting(1, 3, "高優先級會議", 5),
            new Meeting(2, 4, "普通會議A", 2),
            new Meeting(3, 5, "普通會議B", 2),
            new Meeting(4, 6, "重要會議", 4),
            new Meeting(5, 7, "普通會議C", 1)
        };
        
        System.out.println("所有會議（按優先級）：");
        for (Meeting m : priorityMeetings) {
            System.out.printf("  %s\n", m);
        }
        
        List<Meeting> scheduled = scheduleWithPriority(priorityMeetings, 2);
        System.out.println("\n使用 2 個會議室的調度結果：");
        for (Meeting m : scheduled) {
            System.out.printf("  %s\n", m);
        }

        // 測試案例5：邊界情況
        System.out.println("\n5. 邊界情況測試：");
        
        // 空會議列表
        int emptyRooms = minMeetingRooms(new int[0][]);
        System.out.printf("空會議列表：%d 個會議室\n", emptyRooms);
        
        // 單個會議
        int[][] singleMeeting = {{1, 2}};
        int singleRooms = minMeetingRooms(singleMeeting);
        System.out.printf("單個會議：%d 個會議室\n", singleRooms);
        
        // 所有會議不重疊
        int[][] nonOverlapping = {{1, 2}, {3, 4}, {5, 6}};
        int nonOverlapRooms = minMeetingRooms(nonOverlapping);
        System.out.printf("無重疊會議：%d 個會議室\n", nonOverlapRooms);
        
        // 所有會議完全重疊
        int[][] allOverlapping = {{1, 5}, {1, 5}, {1, 5}};
        int allOverlapRooms = minMeetingRooms(allOverlapping);
        System.out.printf("完全重疊會議：%d 個會議室\n", allOverlapRooms);

        // 測試案例6：方法比較
        System.out.println("\n6. 不同方法比較：");
        int[][] compareTest = {{1, 5}, {8, 9}, {8, 9}};
        
        int heapResult = minMeetingRooms(compareTest);
        int eventResult = minMeetingRoomsEventSort(compareTest);
        
        System.out.printf("測試數據：%s\n", Arrays.deepToString(compareTest));
        System.out.printf("Heap 方法結果：%d\n", heapResult);
        System.out.printf("事件排序方法結果：%d\n", eventResult);
        System.out.printf("結果一致：%s\n", heapResult == eventResult);

        // 測試案例7：效能測試
        System.out.println("\n" + "=".repeat(50));
        System.out.println("7. 效能測試：");
        
        performanceTest(100, 50);
        performanceTest(1000, 200);
        performanceTest(5000, 1000);

        // 測試案例8：實際應用場景
        System.out.println("8. 實際應用場景模擬：");
        
        // 模擬一天的會議安排（以30分鐘為單位，9點=18，11:30=23）
        Meeting[] dailyMeetings = {
            new Meeting(18, 20, "晨會", 3),        // 9:00-10:00
            new Meeting(20, 24, "項目討論", 4),    // 10:00-12:00
            new Meeting(22, 23, "緊急會議", 5),    // 11:00-11:30
            new Meeting(26, 28, "午餐會議", 2),    // 13:00-14:00
            new Meeting(28, 32, "客戶會議", 5),    // 14:00-16:00
            new Meeting(30, 34, "團隊會議", 3),    // 15:00-17:00
            new Meeting(32, 34, "總結會議", 2)     // 16:00-17:00
        };
        
        
        System.out.println("一天的會議安排：");
        for (Meeting m : dailyMeetings) {
            double startHour = 9.0 + m.start / 2.0 - 9.0; // 轉換回時間顯示
            double endHour = 9.0 + m.end / 2.0 - 9.0;
            System.out.printf("  %s: %d:%02d-%d:%02d (優先級:%d)\n", 
                m.name, 
                (int)(startHour + 9), (int)((startHour + 9 - (int)(startHour + 9)) * 60),
                (int)(endHour + 9), (int)((endHour + 9 - (int)(endHour + 9)) * 60),
                m.priority);
        }
        
        int dailyRooms = minMeetingRoomsDetailed(dailyMeetings, true);
        System.out.printf("需要會議室數量：%d\n", dailyRooms);

        // 測試案例9：複雜度分析
        System.out.println("\n9. 複雜度分析：");
        System.out.println("方法1 (Min Heap)：");
        System.out.println("  時間複雜度：O(n log n)");
        System.out.println("  空間複雜度：O(n)");
        System.out.println("  優點：直觀，易於擴展");
        
        System.out.println("\n方法2 (事件排序)：");
        System.out.println("  時間複雜度：O(n log n)");
        System.out.println("  空間複雜度：O(n)");
        System.out.println("  優點：更簡潔的實作");
        
        System.out.println("\n有限會議室優化：");
        System.out.println("  時間複雜度：O(n log n)");
        System.out.println("  空間複雜度：O(k)，k是會議室數量");
        System.out.println("  應用：資源受限的排程問題");

        System.out.println("\n=== 會議室排程最佳化測試完成 ===");
    }
}
