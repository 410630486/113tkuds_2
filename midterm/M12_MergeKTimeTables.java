
import java.util.*;

class TimeEntry {

    int time;
    int whichList;
    int index;

    TimeEntry(int time, int whichList, int index) {
        this.time = time;
        this.whichList = whichList;
        this.index = index;
    }
}

public class M12_MergeKTimeTables {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int K = scanner.nextInt();

            // 讀取所有時刻表
            List<List<Integer>> timeTables = new ArrayList<>();
            for (int i = 0; i < K; i++) {
                int len = scanner.nextInt();
                List<Integer> timeList = new ArrayList<>();
                for (int j = 0; j < len; j++) {
                    timeList.add(scanner.nextInt());
                }
                timeTables.add(timeList);
            }

            // 使用 Min-Heap 合併
            PriorityQueue<TimeEntry> minHeap = new PriorityQueue<>((a, b) -> {
                if (a.time != b.time) {
                    return Integer.compare(a.time, b.time);
                }
                if (a.whichList != b.whichList) {
                    return Integer.compare(a.whichList, b.whichList);
                }
                return Integer.compare(a.index, b.index);
            });

            // 將每個時刻表的第一個元素加入堆
            for (int i = 0; i < K; i++) {
                if (!timeTables.get(i).isEmpty()) {
                    minHeap.offer(new TimeEntry(timeTables.get(i).get(0), i, 0));
                }
            }

            List<Integer> result = new ArrayList<>();

            // 持續從堆中取出最小值
            while (!minHeap.isEmpty()) {
                TimeEntry current = minHeap.poll();
                result.add(current.time);

                // 將同一個時刻表的下一個時間加入堆
                int nextIndex = current.index + 1;
                if (nextIndex < timeTables.get(current.whichList).size()) {
                    int nextTime = timeTables.get(current.whichList).get(nextIndex);
                    minHeap.offer(new TimeEntry(nextTime, current.whichList, nextIndex));
                }
            }

            // 輸出結果
            for (int i = 0; i < result.size(); i++) {
                System.out.print(result.get(i));
                if (i < result.size() - 1) {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }
}
