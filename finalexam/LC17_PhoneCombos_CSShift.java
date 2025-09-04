
import java.util.*;

/**
 * LeetCode 17. Letter Combinations of a Phone Number
 */
class PhoneSolution {

    private final String[] KEYPAD = {
        "", // 0
        "", // 1
        "abc", // 2
        "def", // 3
        "ghi", // 4
        "jkl", // 5
        "mno", // 6
        "pqrs", // 7
        "tuv", // 8
        "wxyz" // 9
    };

    public List<String> letterCombinations(String digits) {
        List<String> result = new ArrayList<>();
        if (digits == null || digits.length() == 0) {
            return result;
        }

        backtrack(result, new StringBuilder(), digits, 0);
        return result;
    }

    private void backtrack(List<String> result, StringBuilder current, String digits, int index) {
        if (index == digits.length()) {
            result.add(current.toString());
            return;
        }

        int digit = digits.charAt(index) - '0';
        String letters = KEYPAD[digit];

        for (char letter : letters.toCharArray()) {
            current.append(letter);
            backtrack(result, current, digits, index + 1);
            current.deleteCharAt(current.length() - 1);
        }
    }
}

public class LC17_PhoneCombos_CSShift {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String digits = "";
        if (scanner.hasNextLine()) {
            digits = scanner.nextLine().trim();
        }

        PhoneSolution solution = new PhoneSolution();
        List<String> result = solution.letterCombinations(digits);

        for (String combination : result) {
            System.out.println(combination);
        }

        scanner.close();
    }
}
