
import java.util.*;

public class M06_PalindromeClean {

    public static String cleanString(String s) {
        StringBuilder sb = new StringBuilder();
        for (char c : s.toCharArray()) {
            if (Character.isLetter(c)) {
                sb.append(Character.toLowerCase(c));
            }
        }
        return sb.toString();
    }

    public static boolean isPalindromeRecursive(String s, int left, int right) {
        if (left >= right) {
            return true;
        }

        if (s.charAt(left) != s.charAt(right)) {
            return false;
        }

        return isPalindromeRecursive(s, left + 1, right - 1);
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            String input = scanner.nextLine();
            String cleaned = cleanString(input);

            boolean result = isPalindromeRecursive(cleaned, 0, cleaned.length() - 1);

            System.out.println(result ? "Yes" : "No");
        }
    }
}
