import java.util.Arrays;

/**
 * https://leetcode.com/problems/shortest-distance-to-a-character/
 * <p>
 * Given a string S and a character C, return an array of integers representing the shortest distance from the character C in the string.
 * <p>
 * Input: S = "loveleetcode", C = 'e'
 * Output: [3, 2, 1, 0, 1, 0, 0, 1, 2, 2, 1, 0]
 */
public class ShortestDistanceToACharacter {
    /**
     * Approach: Keep track of the shortest distance to target from left and right separately. Result would be the min of those two distances
     *
     * {@link ShortestDistanceToTargetColor} {@link PushDominoes}
     */
    public int[] shortestToChar(String S, char C) {
        int n = S.length();
        int[] left = new int[n];
        int[] right = new int[n];
        Arrays.fill(left, Integer.MAX_VALUE);
        Arrays.fill(right, Integer.MAX_VALUE);
        for (int i = 0; i < n; i++) {
            if (S.charAt(i) == C) {
                left[i] = 0;
            } else if (i != 0 && left[i - 1] != Integer.MAX_VALUE) { //if target was present on left subarray, increase the current distance by 1
                left[i] = left[i - 1] + 1;
            }
        }
        for (int i = n - 1; i >= 0; i--) {
            if (S.charAt(i) == C) {
                right[i] = 0;
            } else if (i != n - 1 && right[i + 1] != Integer.MAX_VALUE) { //if target was present on right subarray, increase the current distance by 1
                right[i] = right[i + 1] + 1;
            }
        }
        for (int i = 0; i < n; i++) { //reuse the left subarray to store results
            left[i] = Math.min(left[i], right[i]);
        }
        return left;
    }
}
