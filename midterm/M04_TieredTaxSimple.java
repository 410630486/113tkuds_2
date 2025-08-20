/*
 * Time Complexity: O(n)
 * 說明：每筆收入計算稅額需固定4個級距檢查，總時間為O(n)
 * 空間複雜度O(1)，只需常數空間存儲級距和累計值
 */

import java.util.*;

public class M04_TieredTaxSimple {

    public static int calculateTax(int income) {
        int tax = 0;

        if (income > 1000000) {
            tax += (income - 1000000) * 30 / 100;
            income = 1000000;
        }

        if (income > 500000) {
            tax += (income - 500000) * 20 / 100;
            income = 500000;
        }

        if (income > 120000) {
            tax += (income - 120000) * 12 / 100;
            income = 120000;
        }

        if (income > 0) {
            tax += income * 5 / 100;
        }

        return tax;
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int n = scanner.nextInt();

            int[] taxes = new int[n];
            int totalTax = 0;

            for (int i = 0; i < n; i++) {
                int income = scanner.nextInt();
                taxes[i] = calculateTax(income);
                totalTax += taxes[i];
            }

            for (int tax : taxes) {
                System.out.println("Tax: " + tax);
            }

            int average = totalTax / n;
            System.out.println("Average: " + average);
        }
    }
}
