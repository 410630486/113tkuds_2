/*
 * Time Complexity: O(n log n)
 * 說明：建堆O(n)，n次取出堆頂並重新堆化每次O(log n)，總時間O(n log n)
 * 空間複雜度O(1)，原地排序不使用額外空間
 */

import java.util.*;

class Student {

    int score;
    int index;

    Student(int score, int index) {
        this.score = score;
        this.index = index;
    }
}

public class M11_HeapSortWithTie {

    private static void buildMaxHeap(Student[] arr) {
        int n = arr.length;
        for (int i = (n / 2) - 1; i >= 0; i--) {
            heapifyDown(arr, n, i);
        }
    }

    private static void heapifyDown(Student[] arr, int heapSize, int rootIndex) {
        int current = rootIndex;

        while (true) {
            int largest = current;
            int leftChild = 2 * current + 1;
            int rightChild = 2 * current + 2;

            if (leftChild < heapSize) {
                if (compare(arr[leftChild], arr[largest]) > 0) {
                    largest = leftChild;
                }
            }

            if (rightChild < heapSize) {
                if (compare(arr[rightChild], arr[largest]) > 0) {
                    largest = rightChild;
                }
            }

            if (largest == current) {
                break;
            }

            swap(arr, current, largest);
            current = largest;
        }
    }

    private static int compare(Student a, Student b) {
        if (a.score != b.score) {
            return Integer.compare(a.score, b.score);
        }
        return Integer.compare(b.index, a.index);
    }

    private static void swap(Student[] arr, int i, int j) {
        Student temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    private static void heapSort(Student[] arr) {
        int n = arr.length;

        buildMaxHeap(arr);

        for (int i = n - 1; i > 0; i--) {
            swap(arr, 0, i);
            heapifyDown(arr, i, 0);
        }
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int n = scanner.nextInt();
            Student[] students = new Student[n];

            for (int i = 0; i < n; i++) {
                int score = scanner.nextInt();
                students[i] = new Student(score, i);
            }

            heapSort(students);

            for (int i = 0; i < n; i++) {
                System.out.print(students[i].score);
                if (i < n - 1) {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }
}
