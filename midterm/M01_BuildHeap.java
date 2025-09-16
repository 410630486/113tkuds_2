/*
 * Time Complexity: O(n)
 * 說明：自底向上建堆，每層節點數與下沈深度乘積的總和收斂為O(n)
 * 大部分節點位於底層，下沈深度小，整體時間複雜度優於逐一插入的O(n log n)
 */

import java.util.*;

public class M01_BuildHeap {
    
    public static void buildHeap(int[] arr, boolean isMaxHeap) {
        int n = arr.length;
        for (int i = (n / 2) - 1; i >= 0; i--) {
            heapifyDown(arr, n, i, isMaxHeap);
        }
    }
    
    private static void heapifyDown(int[] arr, int heapSize, int rootIndex, boolean isMaxHeap) {
        int current = rootIndex;
        
        while (true) {
            int targetIndex = current;
            int leftChild = 2 * current + 1;
            int rightChild = 2 * current + 2;
            
            if (leftChild < heapSize) {
                if (isMaxHeap) {
                    if (arr[leftChild] > arr[targetIndex]) {
                        targetIndex = leftChild;
                    }
                } else {
                    if (arr[leftChild] < arr[targetIndex]) {
                        targetIndex = leftChild;
                    }
                }
            }
            
            if (rightChild < heapSize) {
                if (isMaxHeap) {
                    if (arr[rightChild] > arr[targetIndex]) {
                        targetIndex = rightChild;
                    }
                } else {
                    if (arr[rightChild] < arr[targetIndex]) {
                        targetIndex = rightChild;
                    }
                }
            }
            
            if (targetIndex == current) {
                break;
            }
            
            swap(arr, current, targetIndex);
            current = targetIndex;
        }
    }
    
    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
    
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            String type = scanner.next();
            boolean isMaxHeap = type.equals("max");
            
            int n = scanner.nextInt();
            int[] arr = new int[n];
            for (int i = 0; i < n; i++) {
                arr[i] = scanner.nextInt();
            }
            
            buildHeap(arr, isMaxHeap);
            
            for (int i = 0; i < n; i++) {
                System.out.print(arr[i]);
                if (i < n - 1) {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }
}
