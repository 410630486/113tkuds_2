
import java.util.*;

public class MaxHeap {

    private List<Integer> heap;

    public MaxHeap() {
        heap = new ArrayList<>();
    }

    private int parent(int i) {
        return (i - 1) / 2;
    }

    private int leftChild(int i) {
        return 2 * i + 1;
    }

    private int rightChild(int i) {
        return 2 * i + 2;
    }

    private void heapifyUp(int i) {
        while (i > 0 && heap.get(i) > heap.get(parent(i))) {
            Collections.swap(heap, i, parent(i));
            i = parent(i);
        }
    }

    private void heapifyDown(int i) {
        int maxIdx = i;
        int left = leftChild(i);
        int right = rightChild(i);
        if (left < heap.size() && heap.get(left) > heap.get(maxIdx)) {
            maxIdx = left;
        }
        if (right < heap.size() && heap.get(right) > heap.get(maxIdx)) {
            maxIdx = right;
        }
        if (maxIdx != i) {
            Collections.swap(heap, i, maxIdx);
            heapifyDown(maxIdx);
        }
    }

    public void insert(int value) {
        heap.add(value);
        heapifyUp(heap.size() - 1);
    }

    public int extractMax() {
        if (heap.isEmpty()) {
            throw new RuntimeException("Heap is empty");
        }
        int max = heap.get(0);
        heap.set(0, heap.get(heap.size() - 1));
        heap.remove(heap.size() - 1);
        if (!heap.isEmpty()) {
            heapifyDown(0);
        }
        return max;
    }

    public int peek() {
        if (heap.isEmpty()) {
            throw new RuntimeException("Heap is empty");
        }
        return heap.get(0);
    }

    public boolean isEmpty() {
        return heap.isEmpty();
    }

    public int size() {
        return heap.size();
    }

    public void display() {
        System.out.println("Heap 內容: " + heap);
    }

    public static void main(String[] args) {
        MaxHeap maxHeap = new MaxHeap();
        System.out.println("=== Max Heap 操作示範 ===");
        int[] values = {10, 20, 15, 30, 40};
        for (int value : values) {
            maxHeap.insert(value);
            System.out.println("插入 " + value + " 後: " + maxHeap.heap);
        }
        System.out.println("\n當前最大值: " + maxHeap.peek());
        System.out.println("\n依序取出最大值:");
        while (!maxHeap.isEmpty()) {
            int max = maxHeap.extractMax();
            System.out.println("取出: " + max + ", 剩餘: " + maxHeap.heap);
        }
    }
}
