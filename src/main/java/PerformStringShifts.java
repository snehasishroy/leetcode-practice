/**
 * https://leetcode.com/problems/perform-string-shifts/ Premium
 * <p>
 * You are given a string s containing lowercase English letters, and a matrix shift, where shift[i] = [direction, amount]:
 * <p>
 * direction can be 0 (for left shift) or 1 (for right shift).
 * amount is the amount by which string s is to be shifted.
 * A left shift by 1 means remove the first character of s and append it to the end.
 * Similarly, a right shift by 1 means remove the last character of s and add it to the beginning.
 * Return the final string after all operations.
 * <p>
 * Input: s = "abc", shift = [[0,1],[1,2]]
 * Output: "cab"
 * Explanation:
 * [0,1] means shift to left by 1. "abc" -> "bca"
 * [1,2] means shift to right by 2. "bca" -> "cab"
 * <p>
 * Input: s = "abcdefg", shift = [[1,1],[1,1],[0,2],[1,3]]
 * Output: "efgabcd"
 * Explanation:
 * [1,1] means shift to right by 1. "abcdefg" -> "gabcdef"
 * [1,1] means shift to right by 1. "gabcdef" -> "fgabcde"
 * [0,2] means shift to left by 2. "fgabcde" -> "abcdefg"
 * [1,3] means shift to right by 3. "abcdefg" -> "efgabcd"
 * <p>
 * Constraints:
 * 1 <= s.length <= 100
 * s only contains lower case English letters.
 * 1 <= shift.length <= 100
 * shift[i].length == 2
 * 0 <= shift[i][0] <= 1
 * 0 <= shift[i][1] <= 100
 */
public class PerformStringShifts {
    /**
     * Approach: Instead of actually shifting the strings every operation, keep track of accumulated string shifts and shift only once at the end.
     * Need to carefully handle modulo operation (negative value as well)
     * <p>
     * {@link MakeSumDivisibleByP} {@link ShiftingLetters}
     */
    public String stringShift(String s, int[][] shifts) {
        int requiredShifts = 0;
        for (int[] shift : shifts) {
            if (shift[0] == 0) { //left shift
                requiredShifts -= shift[1];
            } else { //right shift
                requiredShifts += shift[1];
            }
        }
        int n = s.length();
        char[] result = new char[n];
        for (int i = 0; i < n; i++) {
            int newIndex = (i + requiredShifts) % n;
            if (newIndex < 0) { //don't forget to handle negative indices
                newIndex += n;
            }
            result[newIndex] = s.charAt(i);
        }
        return new String(result);
    }
}
