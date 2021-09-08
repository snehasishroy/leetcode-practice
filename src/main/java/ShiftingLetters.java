/**
 * <pre>
 * https://leetcode.com/problems/shifting-letters/
 *
 * You are given a string s of lowercase English letters and an integer array shifts of the same length.
 *
 * Call the shift() of a letter, the next letter in the alphabet, (wrapping around so that 'z' becomes 'a').
 *
 * For example, shift('a') = 'b', shift('t') = 'u', and shift('z') = 'a'.
 * Now for each shifts[i] = x, we want to shift the first i + 1 letters of s, x times.
 *
 * Return the final string after all such shifts to s are applied.
 *
 * Input: s = "abc", shifts = [3,5,9]
 * Output: "rpl"
 * Explanation: We start with "abc".
 * After shifting the first 1 letters of s by 3, we have "dbc".
 * After shifting the first 2 letters of s by 5, we have "igc".
 * After shifting the first 3 letters of s by 9, we have "rpl", the answer.
 *
 * Input: s = "aaa", shifts = [1,2,3]
 * Output: "gfd"
 *
 * Constraints:
 * 1 <= s.length <= 10^5
 * s consists of lowercase English letters.
 * shifts.length == s.length
 * 0 <= shifts[i] <= 10^9
 * </pre>
 */
public class ShiftingLetters {
    /**
     * Approach: Suffix Sum. Last character will be needs to shift shifted[n-1] times whereas second last character needs to shift
     * shifted[n-1] + shifted[n-2] times.
     * So keep track of the suffix sum and use modulo arithmetic to shift a char
     *
     * {@link PerformStringShifts}
     */
    public String shiftingLetters(String s, int[] shifts) {
        int n = s.length();
        char[] res = new char[n];
        int suffixSum = 0;
        for (int i = n - 1; i >= 0; i--) {
            suffixSum = (shifts[i] + suffixSum) % 26; //this is to avoid overflow
            //alternative would be to use long datatype for suffixSum
            res[i] = shift(s.charAt(i), suffixSum);
        }
        return new String(res);
    }

    private char shift (char c, int times) {
        int curIndex = c - 'a';
        int newIndex = (curIndex + times) % 26;
        return (char) ('a' + newIndex);
    }
}
