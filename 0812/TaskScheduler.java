
import java.util.*;

class Task {

    String name;
    int priority;
    long timestamp;

    Task(String name, int priority) {
        this.name = name;
        this.priority = priority;
        this.timestamp = System.nanoTime();
    }

    @Override
    public String toString() {
        return name + "(優先級:" + priority + ")";
    }
}

public class TaskScheduler {

    private PriorityQueue<Task> taskQueue;

    public TaskScheduler() {
        taskQueue = new PriorityQueue<>((a, b) -> {
            if (a.priority != b.priority) {
                return Integer.compare(b.priority, a.priority);
            }
            return Long.compare(a.timestamp, b.timestamp);
        });
    }

    public void addTask(String name, int priority) {
        Task task = new Task(name, priority);
        taskQueue.offer(task);
        System.out.println("新增任務: " + task + " (當前佇列大小: " + taskQueue.size() + ")");
    }

    public Task executeNextTask() {
        if (taskQueue.isEmpty()) {
            System.out.println("沒有待執行的任務");
            return null;
        }
        Task task = taskQueue.poll();
        System.out.println("執行任務: " + task + " (剩餘任務: " + taskQueue.size() + ")");
        return task;
    }

    public Task peekNextTask() {
        if (taskQueue.isEmpty()) {
            return null;
        }
        return taskQueue.peek();
    }

    public void showPendingTasks() {
        if (taskQueue.isEmpty()) {
            System.out.println("沒有待執行的任務");
        } else {
            System.out.println("下一個任務: " + peekNextTask());
            System.out.println("待執行任務總數: " + taskQueue.size());
        }
    }

    public static void main(String[] args) {
        TaskScheduler scheduler = new TaskScheduler();
        System.out.println("=== 任務調度系統示範 ===");
        scheduler.addTask("系統備份", 1);
        scheduler.addTask("緊急修復", 5);
        scheduler.addTask("資料清理", 2);
        scheduler.addTask("系統更新", 4);
        scheduler.addTask("日常維護", 1);
        scheduler.addTask("安全掃描", 3);
        System.out.println("\n當前狀態:");
        scheduler.showPendingTasks();
        System.out.println("\n開始執行任務:");
        while (!scheduler.taskQueue.isEmpty()) {
            scheduler.executeNextTask();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("\n所有任務執行完成！");
    }
}
