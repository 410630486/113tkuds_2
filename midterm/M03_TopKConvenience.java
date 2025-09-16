/*
 * Time Complexity: O(n log k)
 * 說明：維護大小為k的min-heap，每個商品最多進出堆一次，堆操作O(log k)
 * 當k << n時效率高於完全排序的O(n log n)
 */

import java.nio.charset.StandardCharsets;
import java.util.*;

class Product {

    String name;
    int qty;
    int index;

    public Product(String name, int qty, int index) {
        this.name = name;
        this.qty = qty;
        this.index = index;
    }
}

public class M03_TopKConvenience {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8)) {
            int n = scanner.nextInt();
            int k = scanner.nextInt();

            PriorityQueue<Product> minHeap = new PriorityQueue<>((a, b) -> {
                if (a.qty != b.qty) {
                    return Integer.compare(a.qty, b.qty);
                }
                return Integer.compare(a.index, b.index);
            });

            for (int i = 0; i < n; i++) {
                String name = scanner.next();
                int qty = scanner.nextInt();
                Product product = new Product(name, qty, i);

                if (minHeap.size() < k) {
                    minHeap.offer(product);
                } else if (qty > minHeap.peek().qty
                        || (qty == minHeap.peek().qty && i < minHeap.peek().index)) {
                    minHeap.poll();
                    minHeap.offer(product);
                }
            }

            List<Product> result = new ArrayList<>();
            while (!minHeap.isEmpty()) {
                result.add(minHeap.poll());
            }

            Collections.reverse(result);

            // 輸出結果（按數量降序，數量相同時按輸入順序）
            for (Product p : result) {
                System.out.println(p.name + " " + p.qty);
            }
        }
    }
}
