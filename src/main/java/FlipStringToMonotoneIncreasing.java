/**
 * <pre>
 * https://leetcode.com/problems/flip-string-to-monotone-increasing/
 *
 * A binary string is monotone increasing if it consists of some number of 0's (possibly none), followed by some number of 1's (also possibly none).
 *
 * You are given a binary string s. You can flip s[i] changing it from 0 to 1 or from 1 to 0.
 *
 * Return the minimum number of flips to make s monotone increasing.
 *
 * Input: s = "00110"
 * Output: 1
 * Explanation: We flip the last digit to get 00111.
 *
 * Input: s = "010110"
 * Output: 2
 * Explanation: We flip to get 011111, or alternatively 000111.
 *
 * Input: s = "00011000"
 * Output: 2
 * Explanation: We flip to get 00000000.
 *
 * Constraints:
 * 1 <= s.length <= 10^5
 * s[i] is either '0' or '1'.
 * </pre>
 */
public class FlipStringToMonotoneIncreasing {
    /**
     * Approach: Tricky two pass DP, You have to choose an index i such that for all indices < i, we have 0 and for all indices > i, we have 1.
     * So for each index we need to track no of flips required to make all zeroes on the left and no of flips required to make all ones on the right.
     * In the end, we find the index with minimum cost.
     *
     * Happy to solve this on my own.
     * {@link FindTwoNonOverlappingSubarrayWithTargetSum} {@link TrappingRainWater}
     */
    public int minFlipsMonoIncr(String S) {
        int n = S.length();
        //flipToZero[i] no of flips required to make all values zero till ith index
        int[] flipToZero = new int[n + 2]; //n+2 to add padding before 0th index and after n-1th index
        int[] flipToOne = new int[n + 2];
        int onesFound = 0;
        for (int i = 0; i < n; i++) { //left to right
            onesFound += S.charAt(i) == '1' ? 1 : 0;
            flipToZero[i + 1] = onesFound;
        }
        int zeroesFound = 0;
        for (int i = n - 1; i >= 0; i--) { //right to left
            zeroesFound += S.charAt(i) == '0' ? 1 : 0;
            flipToOne[i + 1] = zeroesFound;
        }
        int minFlips = Integer.MAX_VALUE;
        for (int i = 1; i < n + 2; i++) { //notice the bounds, this handles cases when result needs to be 00000 or 111111
            int flipsRequiredToMakeStringMonotone = flipToOne[i] + flipToZero[i - 1];
            minFlips = Math.min(minFlips, flipsRequiredToMakeStringMonotone);
        }
        return minFlips;
    }
}
