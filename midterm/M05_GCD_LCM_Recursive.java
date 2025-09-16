/*
 * Time Complexity: O(log(min(a,b)))
 * 說明：歐幾里得算法每次遞迴至少將較大數減半，遞迴深度為對數級
 * 避免乘法溢位：LCM = a/GCD * b，先除後乘確保結果在long範圍內
 */

import java.util.*;

public class M05_GCD_LCM_Recursive {

    public static long gcd(long a, long b) {
        if (b == 0) {
            return a;
        }
        return gcd(b, a % b);
    }

    public static long lcm(long a, long b) {
        long g = gcd(a, b);
        return a / g * b;
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            long a = scanner.nextLong();
            long b = scanner.nextLong();

            long g = gcd(a, b);
            long l = lcm(a, b);

            System.out.println("GCD: " + g);
            System.out.println("LCM: " + l);
        }
    }
}
