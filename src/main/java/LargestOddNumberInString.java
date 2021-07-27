/**
 * <pre>
 * https://leetcode.com/problems/largest-odd-number-in-string/
 *
 * You are given a string num, representing a large integer. Return the largest-valued odd integer (as a string) that is a non-empty substring of num, or an empty string "" if no odd integer exists.
 *
 * Input: num = "52"
 * Output: "5"
 * Explanation: The only non-empty substrings are "5", "2", and "52". "5" is the only odd number.
 *
 * Input: num = "4206"
 * Output: ""
 * Explanation: There are no odd numbers in "4206".
 *
 * Input: num = "35427"
 * Output: "35427"
 * Explanation: "35427" is already an odd number.
 *
 * Constraints:
 * 1 <= num.length <= 10^5
 * num only consists of digits and does not contain any leading zeros.
 * </pre>
 */
public class LargestOddNumberInString {
    /**
     * Approach: Greedy, A number is odd if the last digit is odd. Since we have to find the largest number, we need to include as many digits as possible.
     * If we find the rightmost odd digit, and consider the substring from [0, i], this will be the largest odd number possible
     * <p>
     * It's a tricky question, took me around 10 minutes to figure out during the contest :( Lost a lot of ranking.
     *
     * {@link LargestNumberAfterMutatingSubstring}
     */
    public String largestOddNumber(String num) {
        for (int i = num.length() - 1; i >= 0; i--) {
            char c = num.charAt(i);
            int val = c - '0';
            if (val % 2 == 1) { //odd digit found
                return num.substring(0, i + 1);
            }
        }
        return "";
    }
}
